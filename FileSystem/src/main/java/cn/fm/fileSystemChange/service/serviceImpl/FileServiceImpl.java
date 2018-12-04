package cn.fm.fileSystemChange.service.serviceImpl;

import cn.fm.admin.dao.AdminMapper;
import cn.fm.fileSystemChange.dao.FileDao;
import cn.fm.fileSystemChange.service.FileService;
import cn.fm.pojo.Borrow;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.pojo.User;
import cn.fm.user.dao.UserMapper;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.BorrowCFExtends;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FileDao fileDao ;


    /**
     * 录入用户 ,对应一个mapper
     * @param companyFile
     *  @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int insertCompanyFile(CompanyFile companyFile , String uid ) throws  Exception{
        //结果
        int result = 0;
        //表示进行待分配,将借阅表的信息添加进去
        if( uid != null ) {
            int id = Integer.parseInt( uid );
            //先根据id找到用户信息 ,从AdminMapper中 ,
            User user = adminMapper.findWorkerById( id );
            //录入文件信息,返回录入后的文件的id,这里录入的时候设置文件的待领取字段为 1 ,
            companyFile.setWaitborrow( 1 );
            result = fileDao.insertCompanyFile( companyFile );
            //然后将用户的id和wid以及文件id和当前分配日期录入
            result += fileDao.insertBorrow_CompanyFile( companyFile.getCfid(),
                    user.getUid(), DateToStringUtils.dataTostring() ,user.getWid());
            //如果两条都成功就返回1,否则为0
            if( result == 2 ) {
                return 1;
            }else {
                return 0;
            }
        } else {
            //直接录入文件的信息
            result = fileDao.insertCompanyFile( companyFile );
            if ( result > 0 ) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * 修改文件,一般是根据id  ,对应一个mapper,先查询是不是已经有待领取人了,如果有了就提示存在,
     * 可以强制修改. 直接把上一个的借阅表中uid改掉.
     *
     * 如果有uid的话,就查看文件的信息,如果没有待领取信息,就直接先进行信息录入,同时修改文件的待领取字段
     * 如果文件被接走了,而且uid和待领取人uid一样,直接修改, 不一样就不能修改,待借阅字段设置为2,防止前段传过来这个值造成修改
     * 如果文件没有被借走,而且uid一样,就直接修改文件信息,不一样返回是否强制修改,待借阅字段要进行设置为1
     * 强制修改之后就将借阅信息也改掉
     * ,没有uid直接修改
     * @param companyFile
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int updateCompanyFile( CompanyFile companyFile , String uid , int doIt ) throws  Exception {
        int result = 0;
        if( uid != null ) {
            int id =Integer.parseInt(uid);
            //根据文件id查看文件信息
            CompanyFile companyFile1 = fileDao.selectCompanyFileByCFId(companyFile.getCfid() );

            //设置查询条件
            Borrow borrow2 = new Borrow();
            borrow2.setFileid( companyFile.getCfid() );
            //根据文件id查询借阅信息
            Borrow borrow = fileDao.selectBorrow_CompanyFile( borrow2 );
            //如果借阅表信息为空,就需要添加一个借阅表,
            if ( borrow == null || companyFile1.getWaitborrow() == 0 ) {
                //首先查找用户的信息,
                User user = adminMapper.findWorkerById( id );
                //录入借阅表
                result = fileDao.insertBorrow_CompanyFile( companyFile.getCfid(),
                        user.getUid(), DateToStringUtils.dataTostring() ,user.getWid());
                //修改文件的待借阅字段
                companyFile.setWaitborrow( 1 );
                //修改文件信息
                result += fileDao.updateCompanyFileById( companyFile );
                if( result == 2 ) {
                    return 1;
                } else {
                    return 0;
                }
            }
            //如果借阅表有信息
            //查询是不是被领走了,如果领走了就不能进行分配了,waitborrow = 2, isborrow =1的状态
            else if( companyFile1.getWaitborrow() == 2  ) {
                //文件被借走了,并且归还了,想借给其他人的时候
//                if( companyFile1.getIsborrow() == 2) {
//
//                }
//                //防止前端传过来,造成修改
//                companyFile.setWaitborrow( 2 );
                if( id == borrow.getUid() ) {
                    //更新文件信息
                   result = fileDao.updateCompanyFileById( companyFile );
                   if( result > 0 ) {
                       return 1;
                   } else {
                       return 0;
                   }
                } else {
                    //表示文件被拿走了,而且带领取人不一致,不能修改
                    return -2;
                }
            }
            // 先查询是不是分配过了waitborrow=1,isborrow=0的状态,看看id是不是一样,一样就继续录入,只是不修改借阅信息,
            else if( companyFile1.getWaitborrow() == 1  ) {
//                //防止前端传过来,造成修改
//                companyFile.setWaitborrow( 1 );
               if( id == borrow.getUid() ) {
                   //更新文件信息
                   result = fileDao.updateCompanyFileById( companyFile );
                   if( result > 0 ) {
                       return 1;
                   } else {
                       return 0;
                   }
               } else {
                   //如果不一样,查看doIt,如果为1,强制修改,根据文件的id,如果不是就返回错误
                   if( doIt != 1 ) {
                       //文件没被领走,但是领取人不一致,并且不能强制修改
                       return -1;
                   } else {
                       //doIt等于1,修改文件,并且根据文件id修改借阅表
                       result = fileDao.updateCompanyFileById( companyFile );
                       //首先查找用户的信息,
                       User user = adminMapper.findWorkerById( id );
                       //设置要更新的borrow信息的内容
                       Borrow borrow1 = new Borrow();
                       borrow1.setFileid( companyFile.getCfid() );
                       borrow1.setUid( user.getUid() );
                       borrow1.setWid( user.getWid() );
                       borrow1.setGivetime( DateToStringUtils.dataTostring() );
                       //根据文件id信息修改
                       result += fileDao.updateBorrow_CompanyFileByCFId( borrow );
                       if( result == 2 ) {
                           return 1;
                       }else {
                           return 0;
                       }
                   }
               }
           }
           else {
                //上面的状态就三种,waitborrow 0 1 2 ,这个else暂时没有用处
               return 0;
            }
        } else {
            //直接修改
            return fileDao.updateCompanyFileById( companyFile );
        }
    }
    /**
     * 根据文件的id查找文件的信息
     * @param cfid
     * @return
     * @throws Exception
     */
    public CompanyFile selectCompanyFileById( int cfid ) throws Exception{
        return fileDao.selectCompanyFileByCFId( cfid );
    }
    /**
     * 根据文件id删除文件信息, state=0
     * @param cfid
     * @return
     * @throws Exception
     */
    @Override
    public int delCompanyFileById(String cfid ) throws Exception{
        String[] cfidStrings =  cfid.split(",");
        int cfidInt = 0;
        for (int i = 0; i < cfidStrings.length; i++) {
            cfidInt = Integer.parseInt( cfidStrings[i] );
            CompanyFile companyFile = selectCompanyFileById( cfidInt );
            if( companyFile.getIsborrow() == 1 ) {
                //不能删除,有未还的文件
                return -5;
            }
        }
        int result = 0;
        for (int i = 0; i < cfidStrings.length; i++) {
            cfidInt = Integer.parseInt( cfidStrings[i] );
            result += fileDao.delCompanyFileById( cfidInt );

            result += fileDao.delBorrow_CompanyFileByCFId(cfidInt);
        }
        if( result == cfidStrings.length*2 ) {
            return 1;
        } else {
            return 0;
        }

    }
    /**
     * 根据文件信息 查出文件信息 ,状态值level的使用,
     * level 1表示涉密,2表示普通, 0表示全部
     * @param companyFile
     * @param endtime
     * @return
     * @throws Exception
     */
    @Override
    public List<BorrowCFExtends> selectCompanyFile(CompanyFile companyFile , String endtime ,int level ,int state) throws Exception{
//        System.out.println( " 进来了");
        List<BorrowCFExtends> borrowCFExtends = fileDao.selectCompanyFile( companyFile, endtime, level, state);
//        System.out.println( "2进来了");
//        System.out.println( borrowCFExtends );
        borrowCFExtends.forEach( n -> {
            try {
                if( n.getBorrow() != null ) {
                    User user = adminMapper.findWorkerById( n.getBorrow().getUid() );
                    n.setUser( user );
                    user = adminMapper.findWorkerById(n.getBorrow().getSecondUid());
                    n.setUserSecond( user );
                }
            } catch (Exception e) {
               System.out.println( e.getMessage() );
               e.printStackTrace();
            }
        });
        return borrowCFExtends ;
    }

    /**
     * 根据用户的id 查出文件信息,包括他领取的,分配给他的 ,涉密和非涉密都算
     * @return
     * @throws Exception
     */
    @Override
    public List<BorrowCFExtends> selectCompanyFileByUid( int uid ,int state  ) throws Exception{
        List<BorrowCFExtends> borrowCFExtends = fileDao.selectCompanyFileByUid( uid , state);
        borrowCFExtends.forEach( n -> {
            try {
                if( n.getBorrow() != null ) {
                    User user = adminMapper.findWorkerById( n.getBorrow().getUid() );
                    n.setUser( user );
                    user = adminMapper.findWorkerById(n.getBorrow().getSecondUid());
                    n.setUserSecond( user );
                }
            } catch (Exception e) {
                System.out.println( e.getMessage() );
                e.printStackTrace();
            }
        });
        return borrowCFExtends ;
    }


}
