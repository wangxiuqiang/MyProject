package cn.fm.user.service.serviceImpl;

import cn.fm.admin.dao.AdminMapper;
import cn.fm.admin.service.AdminService;
import cn.fm.pojo.*;
import cn.fm.user.dao.UserCompanyFileMapper;
import cn.fm.user.dao.UserGetFileMapper;
import cn.fm.user.dao.UserMapper;
import cn.fm.user.service.UserService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.PassWordHelper;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    AdminService adminService;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserCompanyFileMapper userCompanyFileMapper;
    @Autowired
    UserGetFileMapper userGetFileMapper;

    @Override
    public int selectState(String code) throws Exception {
        return userMapper.selectState( code );
    }

    /**
     * 设置密码
     * @param upwd
     * @param code
     * @return
     * @throws Exception
     */


    @Override
    public int updateUserpwd(String upwd, String code) throws Exception {
        PassWordHelper pwh = new PassWordHelper();
        upwd = pwh.SHA256(upwd);
        if(upwd != null) {
            return userMapper.updateUserpwd(upwd,code);
        }else {
            return 0;
        }

    }

/**
 * 通过姓名进行查询 信息 ,给下面借文件提供id
 */
    @Override
    public List<User> selectUserByName(String name,int wid) throws Exception{
        return userMapper.selectUserId(name , wid);
    }

    /**
     * 将日期延长1天
     * @return
     * @throws Exception
     */

    public String dateAddToTomorrow() throws Exception {
        long time = System.currentTimeMillis();
        long timeTomorrow = 24 * 60 * 60 * 1000;
        time = time + timeTomorrow;
        Date date = new Date();
        date.setTime( time );
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        return sdf.format( date );

    }
    /**
     * 添加借阅信息,同时在这里修改文件的状态,同时修改待领取字段的状态,只改了cf 没有改 gf
     * @param uid,cfid
     * @return
     * @throws Exception
     */
    @Override
    public int insertBorrowcfInfo(int uid,int cfid , int wid) throws Exception{

        Borrow borrow = new Borrow();

        //查看这些文件是不是被借了,如果被借了就返回 - 5
        int isBorrow = userMapper.selectcfisBorrow(cfid);
        if( isBorrow == 2) {
            return -5;
        }
//        for(int j = 0; j < isBorrow.length; j++){
//            if(isBorrow[j] == 2) {
//                return -5;
//            }
        //领取人的id
            borrow.setSecondUid(uid);
            borrow.setWid(wid);
            borrow.setFileid(cfid);
            borrow.setBorrowtime(DateToStringUtils.dataTostring());
           //设置为24小时之后为归还日期
           borrow.setShouldback( dateAddToTomorrow() );
            //添加文件的借出状态,更新借阅表的borrowtime
            int res = userMapper.insertBorrowcfInfo(borrow);

            if(res == 0 ) {
                return -4;
            }
            //设置文件被借出 ,更新文件的字段 isborrow waitborrow
            int re = userMapper.updateCompanyFileIsBorrow(cfid);
            if(re == 0) {
                return -4;
            }
//        }
        return  1;
    }
    @Override
    public int insertBorrowgfInfo(int uid,int gfid ,int wid) throws Exception{
        Borrow borrow = new Borrow();

        //查看这些文件是不是被借了,如果被借了就返回 - 5
        int isBorrow = userMapper.selectgfisBorrow(gfid);
//        for(int j = 0; j < isBorrow.length; j++){
//            if(isBorrow[j] == 2) {
//                return -5;
//            }
        if( isBorrow == 2) {
            return -5;
        }
        borrow.setWid(wid);
        //领取人的id
        borrow.setSecondUid(uid);
            borrow.setFileid(gfid);
            borrow.setBorrowtime(DateToStringUtils.dataTostring());
            //设置为24小时之后为归还日期
        borrow.setShouldback( dateAddToTomorrow() );
        //修改借阅表
            int r = userMapper.insertBorrowgfInfo(borrow);
            if(r == 0) {
                return -4;
            }
            //修改文件表,文件信息
           int re = userMapper.updateGetFileIsBorrow(gfid);
            if(re == 0) {
                return -4;
            }
//        }
        return  1;
    }
/**
 *  查询一个用户所有的借阅出去的文件 ,如果flag = 0 表示所有的借阅信息, fflag = 1 表示没有还的,flag = 2表示还了的.
 */
    @Override
    public List<BorrowCFExtends> selectBorrowcfInfo(int uid,int flag) throws Exception{
        if(uid == 0) {
            List<Borrow> cs = userMapper.selectBorrowcfById(0);
            List<BorrowCFExtends> bcf = new ArrayList<BorrowCFExtends>();
            cs.forEach(n -> {
                try {
                    BorrowCFExtends bcfe = new BorrowCFExtends();
                    CompanyFile cf = userCompanyFileMapper.selectCompanyFileById(n.getFileid());
                    System.out.println(n.getUid() + "``````````````````````````````````````");
                    User user = adminMapper.findWorkerById(n.getUid());
                    bcfe.setUser(user);
                    if( n.getSecondUid() != 0 ) {
                        user = adminMapper.findWorkerById( n.getSecondUid() );
                        bcfe.setUserSecond( user );
                    }
                    bcfe.setCompanyFile(cf);
                    bcfe.setBorrowtime(n.getBorrowtime());
                    bcfe.setBacktime(n.getBacktime());
                    if(flag == 0) {
                        bcf.add(bcfe);
                    }
                    if(flag == 1 && n.getBacktime() == null) {
                        bcf.add(bcfe);
                    }
                    if(flag == 2 && n.getBacktime() != null){
                        bcf.add(bcfe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return bcf;
        }


        //根据用户id找到借阅的文件信息
        List<Borrow> cs = userMapper.selectBorrowcfById(uid);
        List<BorrowCFExtends> bcf = new ArrayList<BorrowCFExtends>();
        cs.forEach(n -> {
            try {
                BorrowCFExtends bcfe = new BorrowCFExtends();
                CompanyFile cf = userCompanyFileMapper.selectCompanyFileById(n.getFileid());
                User user = adminMapper.findWorkerById(uid);
                bcfe.setUser(user);
                //找到用户信息
                if( n.getSecondUid() != 0 ) {
                    user = adminMapper.findWorkerById( n.getSecondUid() );
                    bcfe.setUserSecond( user );
                }
                bcfe.setCompanyFile(cf);
                bcfe.setBorrowtime(n.getBorrowtime());
                bcfe.setBacktime(n.getBacktime());
                if(flag == 0) {
                    bcf.add(bcfe);
                }
                if(flag == 1 && n.getBacktime() == null) {
                    bcf.add(bcfe);
                }
                if(flag == 2 && n.getBacktime() != null){
                    bcf.add(bcfe);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
       return bcf;
    }
    @Override
    public List<BorrowGFExtends> selectBorrowgfInfo(int uid,int flag) throws Exception{
        if(uid == 0) {
            List<Borrow> bs = userMapper.selectBorrowgfById(0);
            List<BorrowGFExtends> bgf = new ArrayList<BorrowGFExtends>();
            bs.forEach(n -> {
                try {
                    BorrowGFExtends bgfe = new BorrowGFExtends();
                    GetFile gf = userGetFileMapper.selectGetFileById(n.getFileid());
                    User user = adminMapper.findWorkerById(n.getUid());
                    System.out.println(user.toString() + "`````````````````````````");
                    bgfe.setUser(user);
                    bgfe.setGetFile(gf);
                    bgfe.setBorrowtime(n.getBorrowtime());
                    bgfe.setGivetime( n.getGivetime() );
                    bgfe.setSholdback( n.getBacktime() );
                    bgfe.setBacktime(n.getBacktime());
                    if(flag == 0) {
                        bgf.add(bgfe);
                    }
                    if(flag == 1 && n.getBacktime() == null) {
                        bgf.add(bgfe);
                    }
                    if(flag == 2 && n.getBacktime() != null){
                        bgf.add(bgfe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return bgf;
        }


        //找到用户信息
        User user = adminMapper.findWorkerById(uid);
        //根据用户id找到借阅的文件信息
        List<Borrow> bs = userMapper.selectBorrowgfById(uid);
        List<BorrowGFExtends> bgf = new ArrayList<BorrowGFExtends>();
        bs.forEach(n -> {
            try {
                BorrowGFExtends bgfe = new BorrowGFExtends();
                GetFile gf = userGetFileMapper.selectGetFileById(n.getFileid());
                bgfe.setUser(user);
                bgfe.setGetFile(gf);
                bgfe.setBorrowtime(n.getBorrowtime());
                bgfe.setGivetime( n.getGivetime() );
                bgfe.setSholdback( n.getBacktime() );
                bgfe.setBacktime(n.getBacktime());
                if(flag == 0) {
                    bgf.add(bgfe);
                }
                if(flag == 1 && n.getBacktime() == null) {
                    bgf.add(bgfe);
                }
                if(flag == 2 && n.getBacktime() != null){
                    bgf.add(bgfe);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bgf;
    }

    /**
     * 查询一个文件所有的借阅数据
     */
    @Override
    public List<BorrowCFExtends> selectBorrowcfInfoByFileid(int fileid) throws Exception{
        List<Borrow> borrows = userMapper.selectBorrowcfInfoByFileid(fileid);
        List<BorrowCFExtends> bcfes = new ArrayList<>();
        CompanyFile companyFile = userCompanyFileMapper.selectCompanyFileById(fileid);
        borrows.forEach(n -> {
            try {
              BorrowCFExtends bcf = new BorrowCFExtends();
              User user = adminMapper.findWorkerById(n.getUid());
              bcf.setBacktime(n.getBacktime());
              bcf.setBorrowtime(n.getBorrowtime());
              bcf.setCompanyFile(companyFile);
              bcf.setUser(user);
                //找到用户信息
                if( n.getSecondUid() != 0 ) {
                    user = adminMapper.findWorkerById( n.getSecondUid() );
                    bcf.setUserSecond( user );
                }
              bcfes.add(bcf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bcfes;
    }
    @Override
    public List<BorrowGFExtends> selectBorrowgfInfoByFileid(int fileid) throws Exception{
        List<Borrow> borrows = userMapper.selectBorrowgfInfoByFileid(fileid);
        List<BorrowGFExtends> bgfes = new ArrayList<>();
        GetFile getFile = userGetFileMapper.selectGetFileById(fileid);
        borrows.forEach(n -> {
            try {
                BorrowGFExtends bgf = new BorrowGFExtends();
                User user = adminMapper.findWorkerById(n.getUid());
                bgf.setBacktime(n.getBacktime());
                bgf.setBorrowtime(n.getBorrowtime());
                bgf.setGetFile(getFile);
                bgf.setUser(user);
                bgfes.add(bgf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bgfes;
    }
    /**
     * 更新归还时间  ,同时在这里面完成对文件状态的改变
     */
    @Override
    public int updatecfBackTime(int[] fileid) throws Exception{
        //如果还多个文件, 就先从这些里面查找是不是用文件已经还了,如果没有的话,在进行归还
        //归还原则:根据数组的长度一个个找出来进行判断
        for(int i = 0;i < fileid.length; i++) {
            //没有被借出 ,表明 传输的有错误 ,  返回一个-5 ,表示  不对
             int isborrow = userMapper.selectcfisBorrow(fileid[i]);
            if( isborrow != 2) {
                return -5;
            }
        }
        //先更新将文件的改了 ,在修改借阅表的字段
        if( userMapper.updateCompanyFileBack(fileid) != 0){
            if(userMapper.updatecfBackTime(fileid) != 0) {
                return 1;
            }
        }
        return 0;

//        String time = DateToStringUtils.dataTostring();
//        if(borrow.getBorrowtime() != null) {
//            borrow.setBacktime(time);
//        }
//        return userMapper.updateCompanyFileBack(borrow.getFileid()) +userMapper.updatecfBackTime(borrow);
    }
    @Override
    public int updategfBackTime(int[] fileid) throws Exception{
        //如果还多个文件, 就先从这些里面查找是不是用文件已经还了,如果没有的话,在进行归还
        //归还原则:根据数组的长度一个个找出来进行判断
        for(int i = 0;i < fileid.length; i++) {
            //没有被借出 ,表明 传输的有错误 ,  返回一个-5 ,表示  不对
            int isborrow = userMapper.selectgfisBorrow(fileid[i]);
            if(isborrow != 2) {
                return -5;
            }
        }
        if(userMapper.updateGetFileBack(fileid) != 0) {
            System.out.println(userMapper.updateGetFileBack(fileid));
            if(userMapper.updategfBackTime(fileid) != 0) {
                return 1;
            }
        }
        return 0;
//        String time = DateToStringUtils.dataTostring();
//        if(borrow.getBorrowtime() != null) {
//            borrow.setBacktime(time);
//        }
//        return userMapper.updateGetFileBack(borrow.getFileid()) +userMapper.updategfBackTime(borrow);
    }




    /**
     * 用在录入或修改文件信息的时候,,俩文件
     * 查最顶层的分类
     */
    @Override
    public List<Classify> selectClassifyBiggest() throws Exception{
        return userMapper.selectClassifyBiggest();
    }


    /**
     * 用在录入或修改文件信息的时候,,俩文件
     * 根据父类id查找子类 信息
     * @param fatherid
     * @return
     * @throws Exception
     */
    @Override
    public List<Classify> selectClassifyByFatherId(int fatherid) throws Exception{
//        Classify classify = userGetFileMapper.selectClassify(fatherid);
//        classify.setCyid(0);
//        classify.setCyname("0");
//        classify.setCyfatherid(0);
        List<Classify> classifies = userMapper.selectClassifyByFatherId(fatherid);
//        classifies.add(classify);
        return classifies;
    }

    /***
     * 根据邮箱找到自己的信息 ,包括其中的角色和权限信息
     */
    @Override
    public UserExtend selectMySelf(String uaccount ) throws Exception{

        User user = adminMapper.findUserByUaccount(uaccount);
        UserExtend userEd = adminService.findWorkerById( user.getUid() );
        return userEd;
    }


    /**
     * 根据code 查找用户的录入时间
     */
//    @Override
//    public String selectUserupdatetime(String code) throws Exception{
//        return userMapper.selectUserupdatetime(code);
//    }

    /**
     * 查看是不是 被借出去了
     * @param cfid
     * @return
     * @throws Exception
     */

    public int selectcfisBorrow( int cfid) throws Exception{
        return userMapper.selectcfisBorrow(cfid);
    }
    public int selectgfisBorrow(int gfid) throws Exception{
        return userMapper.selectgfisBorrow(gfid);
    }

    /**
     * 根据传入的开始时间和终止时间查询 没有归还的文件信息, 范围查询,并通过这里面的用户id和文件id去查询用户和文件信息
     * @param starttime
     * @param endtime
     * @return
     * @throws Exception
     */
    @Override
    public List<BorrowCFExtends> selectBorrowcfByborrowtime( String starttime , String endtime ) throws Exception {
        List<Borrow> list = userMapper.selectBorrowcfByborrowtime(starttime, endtime );
        List<BorrowCFExtends> bcf = new ArrayList<>();
        list.forEach(n -> {
            try {
                User user = adminMapper.findWorkerById(n.getUid());
                CompanyFile companyFile = userCompanyFileMapper.selectCompanyFileById( n.getFileid() );
                BorrowCFExtends bc = new BorrowCFExtends();
                bc.setBacktime(n.getBacktime());
                bc.setBorrowtime(n.getBorrowtime());
                bc.setCompanyFile(companyFile);
                bc.setUser( user );
                bcf.add( bc );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bcf;
    }
    @Override
    public List<BorrowGFExtends> selectBorrowgfByborrowtime( String starttime , String endtime ) throws Exception{
        List<Borrow> list = userMapper.selectBorrowgfByborrowtime(starttime, endtime );
        List<BorrowGFExtends> bgf = new ArrayList<>();
        list.forEach(n -> {
            try {
                User user = adminMapper.findWorkerById(n.getUid());
                GetFile getFile = userGetFileMapper.selectGetFileById( n.getFileid() );
                BorrowGFExtends bg = new BorrowGFExtends();
                bg.setBacktime(n.getBacktime());
                bg.setBorrowtime(n.getBorrowtime());
                bg.setGetFile(getFile);
                bg.setUser( user );
                bgf.add( bg );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bgf;
    }

    /**
     * 根据用户部门id查找没有领取的文件
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public List<CompanyFile> selectcfWaitBorrow(   int uid, int wid  ) throws Exception{
        int fileids[] = userMapper.selectcfWaitBorrow(  wid ) ;
        List<CompanyFile> companyFiles = new ArrayList<>();
        for( int i = 0; i < fileids.length; i++ ) {
            CompanyFile companyFile = userCompanyFileMapper.selectCompanyFileById( fileids[i] );
            if(companyFile != null ) {
                companyFiles.add(  companyFile );
            }

        }
        if( companyFiles.size() > 0 ) {
            return companyFiles;
        }
        return null;
    }
    @Override
    public List<GetFile> selectgfWaitBorrow(   int uid , int wid ) throws Exception{
        int fileids[] = userMapper.selectgfWaitBorrow(  wid ) ;
        List<GetFile> getFiles = new ArrayList<>();
        for( int i = 0; i < fileids.length; i++ ) {
            GetFile getFile = userGetFileMapper.selectGetFileById( fileids[i] );
            if(getFile != null ) {
                getFiles.add(  getFile );
            }

        }

        if( getFiles.size() > 0 ) {
            return getFiles;
        }
        return null;
    }
    /**
     * 查找借出的文件,然后进行比较,比较完之后将超时的文件的信息和用户的信息封装在一起输出,
     * 可以先进行排序,让一样的文件在一块
     * @return
     * @throws Exception
     */
    @Override
    public List<BorrowCFExtends> selectcfIsPassTime () throws Exception{
        //获取了没有归还的文件
        List<Borrow> borrows = userMapper.selectcfIsPassTime();
        //设置一个新的borrow来存放超时的借阅信息
        List<Borrow> newBorrows = new ArrayList<>();
        //String用来获取应该归还的日期
        String shouldback = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        for (Borrow borrow : borrows) {
            shouldback = borrow.getShouldback();
            System.currentTimeMillis();
            date = sdf.parse( shouldback );
            //通过系统时间去减掉应该还的时间,如果 >= 0 表示超时了,就将这些添加到新的List中
            long time = ( System.currentTimeMillis() - date.getTime() ) ;
            if( time >= 0 ) {
                newBorrows.add( borrow );
            }
        }
        //然后根据文件id和用户id获取需要的文件和用户信息 ,存放到扩展类里面
        List<BorrowCFExtends> bcf = new ArrayList<BorrowCFExtends>();
        newBorrows.forEach( n -> {
            try {
                User user = adminMapper.findWorkerById( n.getUid() );
                CompanyFile companyFile = userCompanyFileMapper.selectCompanyFileById( n.getFileid() );
                BorrowCFExtends borrowCFExtend = new BorrowCFExtends();
                borrowCFExtend.setUser( user );
                borrowCFExtend.setCompanyFile( companyFile );
                borrowCFExtend.setSholdback( n.getShouldback() );
                borrowCFExtend.setBorrowtime( n.getBorrowtime() );
                borrowCFExtend.setGivetime( n.getGivetime());
                bcf.add( borrowCFExtend );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bcf;
    }
    @Override
    public List<BorrowGFExtends> selectgfIsPassTime () throws Exception{
        //获取了没有归还的文件
        List<Borrow> borrows = userMapper.selectgfIsPassTime();
        //设置一个新的borrow来存放超时的借阅信息
        List<Borrow> newBorrows = new ArrayList<>();
        //String用来获取应该归还的日期
        String shouldback = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        for (Borrow borrow : borrows) {
            shouldback = borrow.getShouldback();
            System.currentTimeMillis();
            date = sdf.parse( shouldback );
            //通过系统时间去减掉应该还的时间,如果 >= 0 表示超时了,就将这些添加到新的List中
            long time = ( System.currentTimeMillis() - date.getTime() ) ;
            if( time >= 0 ) {
                newBorrows.add( borrow );
            }
        }
        //然后根据文件id和用户id获取需要的文件和用户信息 ,存放到扩展类里面
        List<BorrowGFExtends> bgf = new ArrayList<BorrowGFExtends>();
        newBorrows.forEach( n -> {
            try {
                User user = adminMapper.findWorkerById( n.getUid() );
                GetFile getFile = userGetFileMapper.selectGetFileById( n.getFileid() );
                BorrowGFExtends borrowGFExtend = new BorrowGFExtends();
                borrowGFExtend.setUser( user );
                borrowGFExtend.setGetFile( getFile );
                borrowGFExtend.setSholdback( n.getShouldback() );
                borrowGFExtend.setBorrowtime( n.getBorrowtime() );
                borrowGFExtend.setGivetime( n.getGivetime());
                bgf.add( borrowGFExtend );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bgf;
    }


    /**
     * 整合下面的四个方法 ,预分配
     */
    @Override
    public int addBorrowInfo( int fileid , int type ,String uid ,String wid ) throws Exception{
        int result = 0;
        Borrow borrow = new Borrow();
        String[] uids = uid.split(",");
        String[] wids = wid.split(",");
        borrow.setFileid( fileid );
        if( type == 2) {
            //收文
            result = updategfWaitBorrow( fileid );
//            System.out.println( result );

            if( result > 0) {
                for ( int i = 0; i < uids.length ; i++ ) {
                    borrow.setUid( Integer.parseInt( uids[i] ) );
                    borrow.setWid( Integer.parseInt( wids[i] ) );
                    result = insertgfWaitBorrowInfo( borrow );
                }

            }

            return result;
        } else {
            result = updatecfWaitBorrow( fileid );
//            System.out.println( result );
            if( result > 0) {
                for ( int i = 0; i < uids.length ; i++ ) {
                    borrow.setUid( Integer.parseInt( uids[i] ) );
                    borrow.setWid( Integer.parseInt( wids[i] ) );
                    result = insertcfWaitBorrowInfo( borrow );
                }

            }
            return result;
        }
    }
    /**
     * 下面的四个方法是预分配接口调用的
     * 更新文件的待借阅标记,默认为0 表示没有被分配, 1表示已经分配
     * @return
     * @throws Exception
     */
    @Override
    public int updatecfWaitBorrow(int cfid ) throws Exception{
        return userMapper.updatecfWaitBorrow(cfid);
    }
    @Override
    public int updategfWaitBorrow( int gfid ) throws Exception{
        return userMapper.updategfWaitBorrow(gfid);

    }

    /**
     * 预分配的接口实现将预分配的文件信息录入
     * @param borrow
     * @return
     * @throws Exception
     */
    @Override
    public int insertgfWaitBorrowInfo( Borrow borrow ) throws  Exception{
        borrow.setGivetime( DateToStringUtils.dataTostring());
        return userMapper.insertgfWaitBorrowInfo( borrow );
    }
    @Override
    public int insertcfWaitBorrowInfo( Borrow borrow ) throws  Exception{
        borrow.setGivetime( DateToStringUtils.dataTostring());
        return userMapper.insertcfWaitBorrowInfo( borrow );
    }


    /**
     * 涉密非涉密 分开查询,
     */

    //涉密
//    @Override
//    public List<BorrowCFExtends> selectLevelCompanyFile() throws Exception{
//        List<CompanyFile> companyFiles = userCompanyFileMapper.selectLevelCompanyFile();
//        companyFiles.forEach( n -> {
//            try {
//                //根据文件id,获取到该文件的被借阅历史.
//                List<BorrowCFExtends> borrowCFExtends = selectBorrowcfInfoByFileid( n.getCfid() );
//                for (BorrowCFExtends borrowCFExtend : borrowCFExtends) {
//                    if( borrowCFExtend.getUser() != null ) {
//                        n.setUid( borrowCFExtend.getUser().getUid() );
//                        n.setUname( borrowCFExtend.getUser().getUname() );
//                    }
//                    if( borrowCFExtend.getUserSecond() != null ) {
//                        n.setSecondUid( borrowCFExtend.getUserSecond().getUid() );
//                        n.setSecondName( borrowCFExtend.getUserSecond().getUname() );
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//    @Override
//    public List<BorrowGFExtends> selectLevelGetFile() throws Exception{
//
//    }
//    //非涉密
//    @Override
//    public List<BorrowCFExtends> selectNOTLevelCompanyFile() throws Exception{
//
//    }
//    @Override
//    public List<BorrowGFExtends> selectNOTLevelGetFile() throws Exception{
//
//    }
}
