package cn.fm.fileSystemChange.service.serviceImpl;

import cn.fm.admin.dao.AdminMapper;
import cn.fm.fileSystemChange.dao.FileDao;
import cn.fm.fileSystemChange.service.FileService;
import cn.fm.pojo.Borrow;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.pojo.User;
//import cn.fm.user.dao.UserMapper;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    AdminMapper adminMapper;
//    @Autowired
//    UserMapper userMapper;
    @Autowired
    FileDao fileDao;

    /**
     * 日期修改,设置应该归还的日期
     *
     * @return
     * @throws Exception
     */
    public String dateAddToTomorrow() throws Exception {
        long time = System.currentTimeMillis();
        long timeTomorrow = 24 * 60 * 60 * 1000;
        time = time + timeTomorrow;
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);

    }

    /**
     * 录入用户 ,对应一个mapper
     *
     * @param companyFile
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int insertCompanyFile(CompanyFile companyFile, String uid) throws Exception {
        //结果
        int result = 0;
        //表示进行待分配,将借阅表的信息添加进去
        if (uid != null) {
            int id = Integer.parseInt(uid);
            //先根据id找到用户信息 ,从AdminMapper中 ,
            User user = adminMapper.findWorkerById(id);
            //录入文件信息,返回录入后的文件的id,这里录入的时候设置文件的待领取字段为 1 ,
            companyFile.setWaitborrow(1);
            result = fileDao.insertCompanyFile(companyFile);
            //然后将用户的id和wid以及文件id和当前分配日期录入
            result += fileDao.insertBorrow_CompanyFile(companyFile.getCfid(),
                    user.getUid(), DateToStringUtils.dataTostring(), user.getWid());
            //如果两条都成功就返回1,否则为0
            if (result == 2) {
                return 1;
            } else {
                return 0;
            }
        } else {
            //直接录入文件的信息
            result = fileDao.insertCompanyFile(companyFile);
            if (result > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * 修改文件,一般是根据id  ,对应一个mapper,先查询是不是已经有待领取人了,如果有了就提示存在,
     * 可以强制修改. 直接把上一个的借阅表中uid改掉.
     * <p>
     * 如果有uid的话,就查看文件的信息,如果没有待领取信息,就直接先进行信息录入,同时修改文件的待领取字段
     * 如果文件被接走了,而且uid和待领取人uid一样,直接修改, 不一样就不能修改,待借阅字段设置为2,防止前段传过来这个值造成修改
     * 如果文件没有被借走,而且uid一样,就直接修改文件信息,不一样返回是否强制修改,待借阅字段要进行设置为1
     * 强制修改之后就将借阅信息也改掉
     * ,没有uid直接修改
     *
     * @param companyFile
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int updateCompanyFile(CompanyFile companyFile, String uid, int doIt) throws Exception {
        int result = 0;
        //如果是将删除的文件恢复,就将数据库中的借阅信息也恢复
        if (companyFile.getState() == 1) {
            int[] cfids = new int[1];
            cfids[0] = companyFile.getCfid();
            fileDao.delBorrow_CompanyFileByCFId(cfids, 1);
        }
        if ( Integer.parseInt( uid ) != 0 && uid != null) {
            int id = Integer.parseInt(uid);
            //根据文件id查看文件信息
            CompanyFile companyFile1 = fileDao.selectCompanyFileByCFId(companyFile.getCfid());

            //设置查询条件
            Borrow borrow2 = new Borrow();
            borrow2.setFileid(companyFile.getCfid());
            borrow2.setBorrowstate(1);
            //根据文件id查询借阅信息
            Borrow borrow = fileDao.selectBorrow_CompanyFile(borrow2);
            //如果借阅表信息为空,就需要添加一个借阅表,
            if (borrow == null || companyFile1.getWaitborrow() == 0) {
                //首先查找用户的信息,
                User user = adminMapper.findWorkerById(id);
                //录入借阅表
                result = fileDao.insertBorrow_CompanyFile(companyFile.getCfid(),
                        user.getUid(), DateToStringUtils.dataTostring(), user.getWid());
                //修改文件的待借阅字段
                companyFile.setWaitborrow(1);
                //修改文件信息
                result += fileDao.updateCompanyFileById(companyFile);
                if (result == 2) {
                    return 1;
                } else {
                    return 0;
                }
            }
            //如果借阅表有信息
            //查询是不是被领走了,如果领走了就不能进行分配了,waitborrow = 2, isborrow =1的状态
            else if (companyFile1.getWaitborrow() == 2) {
                //文件被借走了,并且归还了,想借给其他人的时候
//                if( companyFile1.getIsborrow() == 2) {
//
//                }
//                //防止前端传过来,造成修改
//                companyFile.setWaitborrow( 2 );
                if (id == borrow.getUid()) {
                    //更新文件信息
                    result = fileDao.updateCompanyFileById(companyFile);
                    if (result > 0) {
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
            else if (companyFile1.getWaitborrow() == 1) {
//                //防止前端传过来,造成修改
//                companyFile.setWaitborrow( 1 );
                if (id == borrow.getUid()) {
                    //更新文件信息
                    result = fileDao.updateCompanyFileById(companyFile);
                    if (result > 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    //如果不一样,查看doIt,如果为1,强制修改,根据文件的id,如果不是就返回错误
                    if (doIt != 1) {
                        //文件没被领走,但是领取人不一致,并且不能强制修改
                        return -1;
                    } else {
                        //doIt等于1,修改文件,并且根据文件id修改借阅表
                        result = fileDao.updateCompanyFileById(companyFile);
                        //首先查找用户的信息,
                        User user = adminMapper.findWorkerById(id);
                        //设置要更新的borrow信息的内容
                        Borrow borrow1 = new Borrow();
                        borrow1.setFileid(companyFile.getCfid());
                        borrow1.setUid(user.getUid());
                        borrow1.setWid(user.getWid());
                        borrow1.setGivetime(DateToStringUtils.dataTostring());
                        //根据文件id信息修改
                        result += fileDao.updateBorrow_CompanyFileByCFId(borrow1);
                        if (result == 2) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            } else {
                //上面的状态就三种,waitborrow 0 1 2 ,这个else暂时没有用处
                return 0;
            }
        } else {
            //直接修改
            return fileDao.updateCompanyFileById(companyFile);
        }
    }

    /**
     * 根据文件的id查找文件的信息
     *
     * @param cfid
     * @return
     * @throws Exception
     */
    public CompanyFile selectCompanyFileById(int cfid) throws Exception {
        return fileDao.selectCompanyFileByCFId(cfid);
    }

    /**
     * 根据文件id删除文件信息, state=0 同时删除文件借阅信息( 如果文件的借阅信息存在)
     *
     * @param cfid
     * @return
     * @throws Exception
     */
    @Override
    public int delCompanyFileById(String cfid) throws Exception {
        String[] cfidStrings = cfid.split(",");
        int[] cfidInt = new int[cfidStrings.length];
        for (int i = 0; i < cfidStrings.length; i++) {
            cfidInt[i] = Integer.parseInt(cfidStrings[i]);

            CompanyFile companyFile = selectCompanyFileById(cfidInt[i]);
            if (companyFile.getState() == 0) {
                return -2;
            }
            if (companyFile.getIsborrow() == 1 || companyFile.getWaitborrow() == 1) {
                //不能删除,有未还的文件
                return -5;
            }
        }

        int result = 0;

//        int[] fileid = new int[];

//        //根据文件的id查文件的相应的借阅
//        for (int i : cfidInt) {
//            Borrow borrow = new Borrow();
//            borrow.setFileid( i );
//            Borrow borrow1 = fileDao.selectBorrow_CompanyFile( borrow );
//            if( borrow1 != null ) {
//
//            }
//        }
        result += fileDao.delCompanyFileById(cfidInt);
        //如果没有借阅信息,根据这个id进行删除也不会出错.query ok 0 rows affected ,
        int result1 = fileDao.delBorrow_CompanyFileByCFId(cfidInt, 0);
        //测试返回多少,多选情况下
//        System.out.println( result + "这里删除了3个文件,返回的值为多少 ---------------------------------------");
        //如果他没有借阅信息，就不会删除借阅信息，也就不会等于2,多选的情况,返回多少

        if (result == 1 && result1 == 1 ) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * 根据文件信息 查出文件信息 ,状态值level的使用,
     * level 1表示涉密,2表示普通, 0表示全部
     *
     * @param companyFile
     * @param endtime
     * @return
     * @throws Exception
     */
    @Override
    public List<BorrowCFExtends> selectCompanyFile(CompanyFile companyFile, String endtime, int level, int wid) throws Exception {
//        System.out.println( " 进来了");
        List<BorrowCFExtends> borrowCFExtends = fileDao.selectCompanyFile(companyFile, endtime, level, wid);
//        System.out.println( "2进来了");
//        System.out.println( borrowCFExtends );
        borrowCFExtends.forEach(n -> {
            try {
                if (n.getBorrow() != null) {
                    User user = adminMapper.findWorkerById(n.getBorrow().getUid());
                    n.setUser(user);
                    user = adminMapper.findWorkerById(n.getBorrow().getSecondUid());
                    n.setUserSecond(user);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
        return borrowCFExtends;
    }

    /**
     * 根据用户的id 查出文件信息,包括他领取的,分配给他的 ,涉密和非涉密都算
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<BorrowCFExtends> selectCompanyFileByUid(int uid, int state) throws Exception {
        List<BorrowCFExtends> borrowCFExtends = fileDao.selectCompanyFileByUid(uid, state);
        borrowCFExtends.forEach(n -> {
            try {
                if (n.getBorrow() != null) {
                    User user = adminMapper.findWorkerById(n.getBorrow().getUid());
                    n.setUser(user);
                    user = adminMapper.findWorkerById(n.getBorrow().getSecondUid());
                    n.setUserSecond(user);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
        return borrowCFExtends;
    }

    /**
     * 分配文件
     */
    public int insertCompanyFilsWaitBorrow(int uid, int wid, int fileid) throws Exception {
        int result = 0; // 结果判断标记
        //先找到文件的信息
        CompanyFile companyFile = fileDao.selectCompanyFileByCFId(fileid);
        //如果没有被销毁,没有被分配,没有被领走,就修改文件的状态,
        if (companyFile.getDestroy() != 1) {
            if (companyFile.getWaitborrow() != 1 && companyFile.getWaitborrow() != 2) {
                CompanyFile companyFile1 = new CompanyFile();
                //设置待领取位为1
                companyFile1.setWaitborrow(1);
                companyFile1.setCfid(fileid);
                result = fileDao.updateCompanyFileById(companyFile1);
            }
        }
        if (result > 0) {
            //录入借阅标的信息
//            Borrow borrow = new Borrow();
//            borrow.setFileid( fileid );
//            borrow.setWid( wid );
//            borrow.setUid( uid );
//            borrow.setGivetime( DateToStringUtils.dataTostring() );
            result += fileDao.insertBorrow_CompanyFile(fileid, uid, DateToStringUtils.dataTostring(), wid);
            if (result == 2) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 归还或领走文件
     *
     * @param uid
     * @param fileid
     * @return
     * @throws Exception
     */
    public int updateCompanyFileIsBorrowOrBackNew(int uid, String fileid, int flag) throws Exception {
        String[] fileids = fileid.split(",");
        int result = 0;
        if (flag == 1) {
            //先查一下这些文件是不是被分配了
            for (String s : fileids) {
                int fid = Integer.parseInt(s);
                CompanyFile companyFile = fileDao.selectCompanyFileByCFId(fid);
                if (companyFile.getWaitborrow() != 1) {
                    return 0;
                }
            }
            //如果被分配了,设置 待领取为2,领取为 1
            for (String s : fileids) {
                int fid = Integer.parseInt(s);
                CompanyFile companyFile = new CompanyFile();
                companyFile.setCfid(fid);
                companyFile.setWaitborrow(2);
                companyFile.setIsborrow(1);
                result += fileDao.updateCompanyFileById(companyFile);
                Borrow borrow = new Borrow();
                borrow.setFileid(fid);
                //设置领取人
                borrow.setSecondUid(uid);
                borrow.setBorrowtime(DateToStringUtils.dataTostring());
                borrow.setShouldback(dateAddToTomorrow());
                result += fileDao.updateBorrow_CompanyFileByCFId(borrow);
            }
        } else {
            //先查一下这些文件是不是被领走了了
            for (String s : fileids) {
                int fid = Integer.parseInt(s);
                CompanyFile companyFile = fileDao.selectCompanyFileByCFId(fid);
                if (companyFile.getWaitborrow() != 2 && companyFile.getIsborrow() != 1) {
                    return -2;
                }
            }
            //如果被分配了,设置 待领取为2,领取为 1
            for (String s : fileids) {
                int fid = Integer.parseInt(s);
                CompanyFile companyFile = new CompanyFile();
                companyFile.setCfid(fid);
                companyFile.setIsborrow(2);
                result += fileDao.updateCompanyFileById(companyFile);
                Borrow borrow = new Borrow();
                borrow.setFileid(fid);
                //设置领取人
                borrow.setBacktime(DateToStringUtils.dataTostring());
                result += fileDao.updateBorrow_CompanyFileByCFId(borrow);
            }
        }
        if (result == fileids.length * 2) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 清退一个文件
     */
    public int updateCompanyFileIsBack(String fileid) throws Exception {
        String[] fileids = fileid.split(",");
        int result = 0;
        //第一个循环检查是不是都归还了
        for (int i = 0; i < fileids.length; i++) {
            CompanyFile companyFile = fileDao.selectCompanyFileByCFId(Integer.parseInt(fileids[i]));
            if (companyFile.getIsborrow() != 2) {
                return -5;
            }
        }
        //第二个循环用来改文件的标记位
        for (int i = 0; i < fileids.length; i++) {
            CompanyFile companyFile = new CompanyFile();
            companyFile.setCfid(Integer.parseInt(fileids[i]));
            companyFile.setBack(1);
            companyFile.setBackDate(DateToStringUtils.dataTostring());
            result += fileDao.updateCompanyFileById(companyFile);
        }
        if (result == fileids.length) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 销毁一个文件
     *
     * @param fileid
     * @return
     * @throws Exception
     */
    public int updateCompanyFileIsDestroy(String fileid) throws Exception {
        int result = 0;
        //先检查文件是不是被清退了,如果被请退了,就可以销毁
        //第一个循环检查是不是都归还了
        String[] fileids = fileid.split(",");
        for (int i = 0; i < fileids.length; i++) {
            CompanyFile companyFile = fileDao.selectCompanyFileByCFId(Integer.parseInt(fileids[i]));
            if (companyFile.getBack() != 1) {
                return -5;
            }
        }

        for (int i = 0; i < fileids.length; i++) {
            CompanyFile companyFile = new CompanyFile();
            companyFile.setCfid(Integer.parseInt(fileids[i]));
            companyFile.setDestroy(1);
            companyFile.setDestroyDate(DateToStringUtils.dataTostring());
            result += fileDao.updateCompanyFileById(companyFile);
        }
        if (result == fileids.length) {
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * 查询出所有的借阅信息, 或者根据用户的id查找指定用户的借阅信息.所有状态的,归还,未借,借走,
     * 用户id 可以是领取人或实际领取人
     * . 根据flag的值进行查询, 0 全部, 1 待领取, 2 待归还 , 3,归还了的
     *
     * @param
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectCompanyFileBorrowInfo(int wid, int flag) throws Exception {
        //用来存放新的信息的数组
        List<BorrowCFExtends> bcfs = new ArrayList<>();
        //包含文件信息和借阅信息 ,只需要再放入用户id就行
        List<BorrowCFExtends> borrowCFExtends = fileDao.selectCompanyFileBorrowInfo(wid);
        borrowCFExtends.forEach(n -> {
            try {
                User user = adminMapper.findWorkerById(n.getBorrow().getUid());
                n.setUser(user);
                if (n.getBorrow().getSecondUid() != 0) {
                    user = adminMapper.findWorkerById(n.getBorrow().getSecondUid());
                    n.setUserSecond(user);
                }
                if (flag == 0) {
                    bcfs.add(n);
                } else if (flag == 1 && n.getBorrow().getBorrowtime() == null && n.getCompanyFile().getWaitborrow() == 1) {
                    //待领取的
                    //如果文件待借阅字段为1 并且用户的借阅表中的borrowtime =null
                    bcfs.add(n);
                } else if (flag == 2 && n.getBorrow().getBacktime() == null && n.getCompanyFile().getIsborrow() == 1) {
                    // 待归还的
                    bcfs.add(n);
                } else if (flag == 3 && n.getBorrow().getBacktime() != null && n.getCompanyFile().getIsborrow() == 2) {
                    //归还了的
                    bcfs.add(n);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return bcfs;
    }

    // 下面是用户的收文的接口
    //只查找文件的信息,, 返回list ,根据文件的全部信息查,返回之后,将为用户的传阅人的名字和id传过去
    public List<GetFile> selectGetFile(int level, GetFile getFile , String endtime) throws Exception {
        List<GetFile> files = fileDao.selectGetFile(level, getFile , endtime);
        files.forEach(n -> {
            User user = null;
            //如果不等于null的话就要查出用户的信息来
            if (n.getGfpersonRead() != null) {
                String[] uids = n.getGfpersonRead().split(",");
                String[] names = new String[uids.length];
                int[] ids = new int[uids.length];
                for (int i = 0; i < uids.length; i++) {
                    try {
                        //查出user,然后往字段里写信息
                        user = adminMapper.findWorkerById(Integer.parseInt(uids[i]));
                        names[i] = user.getUname();
                        ids[i] = user.getUid();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                n.setGfpersonReadNames(names);
                n.setGfpersonReadIds(ids);
            }
        });
        return files;
    }

    /**
     * 查找单个的文件的id
     *
     * @param gfid
     * @return
     * @throws Exception
     */
    public GetFile selectGetFileByGFid(int gfid) throws Exception {
        return fileDao.selectGetFileByGFid(gfid);
    }

    /**
     * 修改文件的信息. 可以对文件进行恢复,, 对文件进行清退,清退的要求是
     *
     * @param getFile
     * @return
     * @throws Exception
     */
    public int updateGetFileByGFid(GetFile getFile) throws Exception {
//        如果是恢复文件,那就将借阅信息一起恢复
        if (getFile.getState() == 1) {
            int ids[] = new int[1];
            ids[0] = getFile.getGfid();
            fileDao.delGetFileBorrowInfoByGfid(ids, 1);
        }
        //如果分配了,而且两次分配的不一样,就不行
        GetFile getFile1 = selectGetFileByGFid(getFile.getGfid());
        //如果进行分配,也就是传过来的gfpersonRead 不为null,且查出来的也不为null ,不一样 ; ,且不一样的话,就返回 -5 表示,已经分配,不能再分配
        //如果传过来的为 null 就不进行分配,跳过这个if
        if( getFile.getGfpersonRead() != null ) {
            if (getFile1.getGfpersonRead() != null && !getFile.getGfpersonRead().equals(getFile1.getGfpersonRead())) {
                return -5;
            }
        }

        User user = null;
        Borrow borrow = new Borrow();
        //同时要录入借阅的信息,文件id在里面有, 用护id也有,就差单位id,所以要查找user
        if (getFile1.getGfpersonRead() == null && getFile.getGfpersonRead() != null) {
            String[] users = getFile.getGfpersonRead().split(",");
            for (int i = 0; i < users.length; i++) {
                //循环一次,录入一次借阅信息
                user = adminMapper.findWorkerById(Integer.parseInt(users[i]));
                //将查出来的用户信息录入借阅表里面
                borrow.setFileid(getFile.getGfid());
                borrow.setUid(user.getUid());
                borrow.setWid(user.getWid());
                //录入分配时间
                borrow.setGivetime(DateToStringUtils.dataTostring());
                fileDao.insertBorrow_GetFile(borrow);
            }
            getFile.setWaitborrow(1);
        }
        //清退,如果不是归还状态,不能进行清退
        if (getFile.getBack() == 1) {
            GetFile getFile2 = fileDao.selectGetFileByGFid( getFile.getGfid() );
            if( getFile2.getIsborrow() == 2 ) {
                getFile.setBackDate(DateToStringUtils.dataTostring());
            } else {
                return -2;
            }

        }
        int result = fileDao.updateGetFileByGFid(getFile);
        if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 添加文件信息,先添加文件的录入时间
     */
    public int insertGetFile(GetFile getFile) throws Exception {
        getFile.setGfdatetime(DateToStringUtils.dataTostring());
        int result = 0;
        if ( getFile.getGfpersonRead() != null ) {
            //如果分配了,要设置文件的待领取位
            getFile.setWaitborrow( 1 );
            //因为要获取文件的id,所以必须先插入文件的信息
            result = fileDao.insertGetFile(getFile);
            User user = null;
            Borrow borrow = new Borrow();
            //同时要录入借阅的信息,文件id在里面有, 用护id也有,就差单位id,所以要查找user
            String[] users = getFile.getGfpersonRead().split(",");
            for (int i = 0; i < users.length; i++) {
                //循环一次,录入一次借阅信息
                user = adminMapper.findWorkerById(Integer.parseInt(users[i]));
                //将查出来的用户信息录入借阅表里面
                borrow.setFileid(getFile.getGfid());
                borrow.setUid(user.getUid());
                borrow.setWid(user.getWid());
                //录入分配时间
                borrow.setGivetime(DateToStringUtils.dataTostring());
                result += fileDao.insertBorrow_GetFile(borrow);
            }
//            System.out.println( "关于result的值问题" + result );
//            System.out.println( "关于result的值问题" + users.length );
            if (result == users.length + 1 ) {
                return 1;
            } else {
                return 0;
            }
        } else {
            //如果不进行待分配,直接录入文件,
            result = fileDao.insertGetFile(getFile);
            if (result > 0) {
                return 1;
            }
            return 0;
        }

    }

    /**
     * 修改文件的借阅表的信息 ,根据文件的id和单位id
     * 进行录入借阅信息,两种,归还或借走, 如果是归还,那么就要知道这是多个人中的第几个,用read和readcom进行比较 ,
     * 如果在这一次录入之前 read -1和readcom一样长,那么就能确定isborrow可以修改为2, 否则修改为-1 方便多个人中的下一个进行借阅
     * 修改文件的借阅为,和文件的待领取位., 另外修改文件的借阅信息,
     *
     * @param wid,uid,fileid,flag 1 取走, 2 归还,
     * @return
     * @throws Exception
     */
    public int updateBorrow_GetFileByGFId(int wid, int fileid, int uid, int flag) throws Exception {
        int result = 0;
        if (flag == 1) {
            //同时修改文件的标记位
            GetFile getFile = new GetFile();
            getFile.setIsborrow(1);
            getFile.setGfid(fileid);
            getFile.setWaitborrow(2);
            result += fileDao.updateGetFileByGFid(getFile);
            //取走
            Borrow borrow = new Borrow();
            borrow.setWid(wid);
            borrow.setFileid(fileid);
            //设置领取人
            borrow.setSecondUid(uid);
            //设置领取时间和应该归还的时间
            borrow.setBorrowtime(DateToStringUtils.dataTostring());

            borrow.setShouldback(dateAddToTomorrow());
            result += fileDao.updateBorrow_GetFileByGFId(borrow);
        } else {


            GetFile getFile = new GetFile();

            getFile.setGfid(fileid);
            //从数据库查这个文件的信息,用来进行isborrow添加的判断
            GetFile getFile1 = fileDao.selectGetFileByGFid(fileid);
            //如果isborrow 不等于1 是不允许归还的, 因为没有借出去
            if ( getFile1.getIsborrow() != 1 ) {
                return -2;
            }

//            String[] reads1 = getFile1.getGfpersonRead().split(",");
//            String[] reads2 = getFile1.getGfpersonReadCom().split(",");
            //如果是空的话,说明是第一次归还,直接就能进行,
            if (getFile1.getGfpersonReadCom() == null ) {
                //同时修改文件的标记位
                getFile.setIsborrow(-1);
                getFile.setWaitborrow(1);

            } else {
                //不是第一次归还就要进行判断,判断是不是最后一次归还
                //用来判断这两个字符串包含的用户的长度是不是一样长
                String[] reads1 = getFile1.getGfpersonRead().split(",");
                String[] reads2 = getFile1.getGfpersonReadCom().split(",");
                //如果长度不一样就设置 isborrow 为2 ,否则都是把isborrow设置为 -1 ,这样来标明下一次可以借出
                System.out.println( reads1.length  + "--------------" + reads2.length );
                if( reads1.length == ( reads2.length + 1 ) ) {
                    // 如果这一次的归还是最后一个,就设置isborrow = 2 为办比状态
                    getFile.setIsborrow(2);
                    getFile.setWaitborrow(2);
                } else {

                    getFile.setIsborrow(-1);
                    getFile.setWaitborrow(1);
                }

            }

            String com;
            if (getFile1.getGfpersonReadCom() != null) {
                //如果查出来的com的字符串不是null的话,就要加 , 和id值,
                com = getFile1.getGfpersonReadCom() + "," + uid;
            } else {
                //如果为null 表示是第一个值,可以不用加任何的逗号
                com = "" + uid;
            }
            getFile.setGfpersonReadCom(com);
            result += fileDao.updateGetFileByGFid(getFile);
            Borrow borrow = new Borrow();
            borrow.setWid(wid);
            borrow.setFileid(fileid);
            //设置归还人
            borrow.setThirdUid( uid );
            //设置归还时间
            borrow.setBacktime(DateToStringUtils.dataTostring());
            result += fileDao.updateBorrow_GetFileByGFId(borrow);
        }
        if (result == 2) {
            return 1;
        }
        return 0;
    }

    /**
     * 设置文件的标记位为0
     *
     * @param gfids
     * @return
     * @throws Exception
     */
    public int delGetFileByGfid(String gfids) throws Exception {
        //先将string转换为数组
        String[] gfidstring = gfids.split(",");
        int gfidsInt[] = new int[gfidstring.length];
        for (int i = 0; i < gfidstring.length; i++) {
            gfidsInt[i] = Integer.parseInt(gfidstring[i]);
        }
        int result = fileDao.delGetFileByGfid(gfidsInt) + fileDao.delGetFileBorrowInfoByGfid(gfidsInt, 0);
//        System.out.println( result );
        if (result > 0) {
            return 1;
        }
        return 0;

    }
    /**
     * 设置借阅信息的标记位为0
     */
//    public int delGetFileBorrowInfoByGfid(  int gfids ,  int state ) throws Exception {
//
//    }

    /**
     * 添加借阅表的信息
     * @param borrow
     * @return
     * @throws Exception
     */
//    public int insertBorrow_GetFile( Borrow borrow ) throws Exception {
//
//    }

    /**
     * 查询借阅的信息,包含文件信息
     *
     * @param wid
     * @param flag
     * @return
     * @throws Exception
     */
    public List<BorrowGFExtends> selectGetFileAndBorrowInfo(int wid,
                                                            int flag) throws Exception {

        List<BorrowGFExtends> borrowGFExtends = fileDao.selectGetFileAndBorrowInfo(wid);
        List<BorrowGFExtends> bgfs = new ArrayList<>();

        borrowGFExtends.forEach(n -> {
            //首先根据用户筛选出需要的值,然后在找他的用户的信息
            User user = new User();
            try {
                user = adminMapper.findWorkerById(n.getBorrow().getUid());
                n.setUser(user);
                if (n.getBorrow().getSecondUid() != 0) {
                    user = adminMapper.findWorkerById(n.getBorrow().getSecondUid());
                    n.setUserSecond(user);
                }
                if (n.getBorrow().getThirdUid() != 0) {
                    user = adminMapper.findWorkerById(n.getBorrow().getThirdUid());
                    n.setUserThird(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag == 0) {
                bgfs.add(n);
            } else if (flag == 1 && n.getBorrow().getBorrowtime() == null) {
//                System.out.println( n.getBorrow().getBorrowtime() );
                bgfs.add(n);
            } else if (flag == 2 && n.getBorrow().getBacktime() == null && n.getBorrow().getBorrowtime() != null) {
                bgfs.add(n);
            } else if (flag == 3 && n.getBorrow().getBacktime() != null) {
                bgfs.add(n);
            } else {

            }
        });
        return bgfs;
    }

    /**
     * @param uid
     * @return
     * @throws Exception
     */
    public List<BorrowGFExtends> selectGetFileByUid(int uid, int flag) throws Exception {
        List<BorrowGFExtends> borrowGFExtends = fileDao.selectGetFileByUid(uid);
        List<BorrowGFExtends> bgfs = new ArrayList<>();

        borrowGFExtends.forEach(n -> {
            //首先根据用户筛选出需要的值,然后在找他的用户的信息
            User user = new User();
            try {
                user = adminMapper.findWorkerById(n.getBorrow().getUid());
                n.setUser(user);
                if (n.getBorrow().getSecondUid() != 0) {
                    user = adminMapper.findWorkerById(n.getBorrow().getSecondUid());
                    n.setUserSecond(user);
                }
                if (n.getBorrow().getThirdUid() != 0) {
                    user = adminMapper.findWorkerById(n.getBorrow().getThirdUid());
                    n.setUserThird(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag == 0) {
                bgfs.add(n);
            } else if (flag == 1 && n.getBorrow().getBorrowtime() == null) {
                bgfs.add(n);
            } else if (flag == 2 && n.getBorrow().getBacktime() == null && n.getBorrow().getBorrowtime() != null) {
                bgfs.add(n);
            } else if (flag == 3 && n.getBorrow().getBacktime() != null) {
                bgfs.add(n);
            } else {
            }
        });
        return bgfs;
    }

}
