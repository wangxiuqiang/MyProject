package cn.fm.user.controller;
/**
 * 用户设置密码和 数据库备份
 */

import cn.fm.admin.service.AdminService;
import cn.fm.admin.service.serviceImpl.AdminServiceImpl;
import cn.fm.fileSystemChange.service.FileService;
import cn.fm.pojo.*;
import cn.fm.user.service.UserService;
import cn.fm.utils.*;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import cn.fm.vo.UserExtend;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/worker" , produces = "application/json;charset=utf8" ,method = RequestMethod.POST)
public class UserController {

    private static String address = "";
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @Autowired
    FileService fileService;
    /**
     * 用户在邮箱中跳转,实现激活
     * @param code
     * @param model
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/activecode" )
//    public String activecode(String code,Model model) throws Exception {
//        model.addAttribute("code",code);
//
//        return "/user/userReg";
//    }

    /**
     * 从数据库中查找这个时间,比较时间的变化是不是超了24小时
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/regbefore")
//    @ResponseBody
//    public String regbefore(String code) throws Exception {
//        HashMap<String,Integer> map = new HashMap<>();
//
//        long now = System.currentTimeMillis();
////        从数据库找个取出时间的字符串类型数据
//        String dateString = userService.selectUserupdatetime(code);
//        Date date  = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if(dateString == null) {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//            return JSON.toJSONString(map);
//        }
//       date =  sdf.parse(dateString);
//       long before = date.getTime();
//
//       if((((now - before)/1000)/3600) >= 24 ) {
//           map.put(StatusUtils.statecode,StatusUtils.TIME_OUT);
//           return JSON.toJSONString(map);
//       }else {
//           map.put(StatusUtils.statecode,StatusUtils.TIME_IN);
//           return JSON.toJSONString(map);
//       }
//    }

    /**
     * 查看是不是激活完成了
     * @param code
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/checkState")
//    @ResponseBody
//    public String checkState(String code ) throws Exception {
//        HashMap<String,Integer> map = new HashMap<>();
//        if( code == null ) {
//            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
//            return JSON.toJSONString( map );
//        }
//        int result = userService.selectState( code );
//        if( result != 0 )  {
//            map.put( StatusUtils.statecode , StatusUtils.ALREADY_REG );
//        } else {
//            map.put( StatusUtils.statecode , StatusUtils.NEED_REG ) ;
//        }
//        return JSON.toJSONString( map );
//    }

    /**
     * 发送激活的code 和设置密码
     */

//    @RequestMapping(value = "/reg")
//    @ResponseBody
//    public String reg( String upwd, String code) throws Exception {
//        if (userService.updateUserpwd(upwd, code) != 0) {
//            HashMap<String,Integer> map = new HashMap<>();
//            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
//            return JSON.toJSONString(map);
//        }
//        HashMap<String,Integer> map = new HashMap<>();
//        map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//        return JSON.toJSONString(map);
//    }

    /**
     * 备份数据库
     *
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping(value = "/backupDatabase")
    @ResponseBody
    public String backupDatabase(HttpServletResponse response , HttpServletRequest request ) throws Exception {

        if (MysqlBackupUtils.backup() == 0) {


            String time = MysqlBackupUtils.sdf.format(new Date());
            File file = new File(MysqlBackupUtils.BACKUP_DIR + time + ".sql");
            if(file.exists()) {
                //设置响应内容
                response.setContentType("application/sql");
                response.setHeader("Content-Disposition" , "attachment; filename=filesystem_" + time );
                FileInputStream fis = null ;
                BufferedInputStream bis = null;

                try{
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    byte[] buffer = new byte[fis.available()];
                    int i = bis.read( buffer );
                    while ( i != -1 ) {
                        os.write(buffer , 0 , i);
                        i = bis.read( buffer );
                    }
                }catch ( IOException e) {
                    e.printStackTrace();
                    return JSON.toJSONString("备份失败");
                }finally {
                    if ( fis != null) {
                        try {
                            fis.close();
                        } catch ( IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if( bis != null ) {
                        try{
                            bis.close();
                        } catch ( IOException e) {
                            e.printStackTrace();

                        }

                    }
                }
            }

            return JSON.toJSONString("备份成功");
        } else {
            return JSON.toJSONString("备份失败");

        }

    }

    /**
     * 恢复数据库
     */
//    @RequiresRoles(value = {"admin"}, logical = Logical.OR)
    @RequestMapping(value = "/recoverDatabase")
    @RequiresRoles(value = "admin")
    @ResponseBody
    public String recoverDatabase(MultipartFile file) throws Exception {

        if (MysqlRecoverUtils.recover(file) == 0) {
            return JSON.toJSONString("恢复成功");
        } else {
            return JSON.toJSONString("恢复失败");

        }

    }

//    /**
//     * 在借阅的时候提供 用户信息以供选择
//     * @param uname
//     * @return
//     * @throws Exception
//     */
//    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping(value = "/selectUserByName")
    @RequiresRoles(value = "admin")
    @ResponseBody
    public String selectUserByName(String uname , Integer wid ) throws Exception{
        int widInt = 0;
        if( wid != null ){
            widInt = wid;
        }
        List<User> users = userService.selectUserByName(uname,widInt);
        if(users != null &&users.size() >0) {
            return JSON.toJSONString(users);
        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);

    }

    /**
     * 在录入指纹的时候弹出的信息,查看是不是有没有归还的文件,,可以用这个来实现获取id和wid的情况,正好是显示没有归还的信息
     * 用来归还,取走文件  ,通过wid 来查看,查看出来的未归还的文件
     * @param finger
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/beforeAddBorrowcfInfo")
    @ResponseBody
    public String beforeAddBorrowcfInfo( MultipartFile finger ) throws Exception {

        HashMap<String,Integer> map = new HashMap<>();
        //通过指纹找uid
        //首先将这个指纹文件保存,获取文件的名字
        String name = UploadUtils.upload( finger , 0);
//        //将文件读成二进制数组,
//        byte[] image = Files.readAllBytes(Paths.get(name));
//        //将文件做成比较的模板
//        FingerprintTemplate template = new FingerprintTemplate()
//                .dpi(500)
//                .create(image);
//
        //从数据库中取出数据

        User user = adminService.selectAllFingerInfoAndCompare(name);
        if( user != null ) {
            //根据用户去找他是不是用其他借阅的信息 ,2表示没有归还的
            System.out.println( user.getUname());
           List<BorrowCFExtends> cfExtends = fileService.selectCompanyFileBorrowInfo( user.getWid() , 2 );
           if( cfExtends != null && cfExtends.size() > 0 ) {
               return JSON.toJSONString( cfExtends,SerializerFeature.DisableCircularReferenceDetect );
           }else {
               map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
               return JSON.toJSONString( map );
           }
        } else {
            map.put( StatusUtils.statecode , StatusUtils.NO_ROLE_PERMISSION );
            return JSON.toJSONString( map );
        }
    }
    /**
     * 在录入指纹的时候弹出的信息,查看是不是有没有归还的文件,返回了文件的id
     * @param finger
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/beforeAddBorrowgfInfo")
    @ResponseBody
    public String beforeAddBorrowgfInfo( MultipartFile finger ) throws Exception {

        HashMap<String,Integer> map = new HashMap<>();
        //通过指纹找uid
        //首先将这个指纹文件保存,获取文件的名字
        String name = UploadUtils.upload( finger , 0);
//        //将文件读成二进制数组,
//        byte[] image = Files.readAllBytes(Paths.get(name));
//        //将文件做成比较的模板
//        FingerprintTemplate template = new FingerprintTemplate()
//                .dpi(500)
//                .create(image);
//
        //从数据库中取出数据

        User user = adminService.selectAllFingerInfoAndCompare( name );
        if( user != null ) {
            //根据用户去找他是不是用其他借阅的信息 ,2 表示没有归还的 ,
            List<BorrowGFExtends> gfExtends = fileService.selectGetFileAndBorrowInfo( user.getWid() , 2 );
            if( gfExtends != null && gfExtends.size() > 0 ) {
                return JSON.toJSONString( gfExtends,SerializerFeature.DisableCircularReferenceDetect );
            }else {
                map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
                return JSON.toJSONString( map );
            }
        } else {
            map.put( StatusUtils.statecode , StatusUtils.NO_ROLE_PERMISSION );
            return JSON.toJSONString( map );
        }
    }

    /**
     * 根据用户的wid查看 ,待领取的文件
     * @param uid
     * @return
     * @throws Exception
     */
//    @RequiresRoles(value = "admin")
//    @RequestMapping(value = "/showcfWaitBorrow")
//    @ResponseBody
//    public String showcfWaitBorrow( int uid , int wid) throws  Exception {
//        HashMap<String,Integer> map = new HashMap<>();
//        List<CompanyFile> companyFiles = userService.selectcfWaitBorrow( uid , wid );
//        if( companyFiles != null && companyFiles.size() > 0 ) {
//            return JSON.toJSONString( companyFiles, SerializerFeature.DisableCircularReferenceDetect );
//        }
//        map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
//        return JSON.toJSONString( map );
//
//    }

    /**
     * 根据用户的wid查看 待领取的文件,
//     * @param uid
     * @return
     * @throws Exception
     */
//    @RequiresRoles(value = "admin")
//    @RequestMapping(value = "/showgfWaitBorrow")
//    @ResponseBody
//    public String showgfWaitBorrow( int uid , int wid ) throws  Exception {
//        HashMap<String,Integer> map = new HashMap<>();
//        List<GetFile> getFiles = userService.selectgfWaitBorrow( uid , wid );
//        if( getFiles != null && getFiles.size() > 0 ) {
//            return JSON.toJSONString( getFiles,SerializerFeature.DisableCircularReferenceDetect  );
//        }
//        map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
//        return JSON.toJSONString( map );
//
//    }
//    /**
//     *  将uid和fid wid穿过来进行借阅
//     *
//     * @param uid
//     * @param cfid
//     *
//     * @return
//     * @throws Exception
//     */
//    @RequiresRoles(value = "admin")
//    @RequestMapping(value = "/insertBorrowcfInfo")
//    @ResponseBody
//    public String insertBorrowcfInfo( int uid, int cfid , int wid ) throws Exception {
//       // System.out.println("------------------" + cfid +"--------------------------");
//        HashMap<String,Integer> map = new HashMap<>();
//
////        String[] cfidStrings = cfid.split(",");
////        int[] cfids = new int[cfidStrings.length];
////        int i = 0;
////        for( i = 0; i <cfids.length ; i++) {
////            cfids[i] = Integer.parseInt(cfidStrings[i]);
////        }
//        int result = userService.insertBorrowcfInfo(uid, cfid ,wid);
//        if (result == -5) {
//            map.put(StatusUtils.statecode,StatusUtils.IS_BORROW);
//            return JSON.toJSONString(map);
//
//        }
//        if (result != -4) {
//            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
//            return JSON.toJSONString(map);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//            return JSON.toJSONString(map);
//        }
//
//    }
//    @RequiresRoles(value = "admin")
//    @RequestMapping(value = "/insertBorrowgfInfo")
//    @ResponseBody
//    public String insertBorrowgfInfo( int uid,
//                                      int gfid , int wid ) throws Exception {
//      //  System.out.println("------------------" + gfid +"--------------------------");
//        HashMap<String,Integer> map = new HashMap<>();
////        String[] gfidStrings = gfid.split(",");
////        int[] gfids = new int[gfidStrings.length];
////        int i = 0;
////        for( i = 0; i <gfids.length ; i++) {
////            gfids[i] = Integer.parseInt(gfidStrings[i]);
////        }
//        int result = userService.insertBorrowgfInfo(uid, gfid ,wid);
//        if (result == -5) {
//            map.put(StatusUtils.statecode,StatusUtils.IS_BORROW);
//            return JSON.toJSONString(map);
//
//        }
//        if (result != -4) {
//            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
//            return JSON.toJSONString(map);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//            return JSON.toJSONString(map);
//        }
//    }
//
//    /**
//     * 更新借阅归还时间,归还文件,再还之前,根据指纹找到需要的这个单位借阅的内容,这个是
//     * 先进行文件字段的更新,然后将借阅表进行更新,所以说这是最后一步的更新,首先需要获取指纹,用指纹在进行
//     * 获取到用户的id,进一步获取到用户的wid,这样,如果是uid不一样,但是wid一样也可以实现归还,一次一份文件只能借一个人,
//     * 用cfid归还的话,直接找cfid并且backtime = null的就可以了,因为通过指纹的检查,会找到没有归还的文件,直接选择然后返回id就可以啦
//     *
//     *和借阅一样使用上面的一些指纹查找就可以啦
//     */
//    @RequiresRoles(value = "admin")
//    @RequestMapping(value = "/updatecfBackTime")
//    @ResponseBody
//    public String updatecfBackTime(String cfid) throws Exception {
//        HashMap<String,Integer> map = new HashMap<>();
//        if( cfid == null ) {
//            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
//            return JSON.toJSONString( map );
//        }
//        String[] cfidStrings = cfid.split(",");
//        int[] cfids = new int[cfidStrings.length];
//        for(int i = 0; i < cfidStrings.length; i++) {
//            cfids[i] = Integer.parseInt(cfidStrings[i]);
//        }
//        int result = userService.updatecfBackTime(cfids);
//        if(result == -5){
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//            return JSON.toJSONString(map);
//        }
//
//        if(result > 0) {
//            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
//            return JSON.toJSONString(map);
//         }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//            return JSON.toJSONString(map);
//         }
//    }
//    @RequiresRoles(value = "admin")
//    @RequestMapping(value = "/updategfBackTime")
//    @ResponseBody
//    public String updategfBackTime(String gfid , int uid ,int wid) throws Exception {
//
//        HashMap<String,Integer> map = new HashMap<>();
//        if( gfid == null ) {
//            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
//            return JSON.toJSONString( map );
//        }
////        Borrow borrow = new Borrow();
//////        System.out.println(uid + "---------------------" + gfid);
////        borrow.setFileid(gfid);
////        borrow.setUid(uid);
//        String[] gfidStrings = gfid.split(",");
////        System.out.println(gfidStrings[0]);
//        int[] gfids = new int[gfidStrings.length];
////        System.out.println(gfids[0]);
//        for(int i = 0; i < gfidStrings.length; i++) {
//            gfids[i] = Integer.parseInt(gfidStrings[i]);
//        }
//        int result = userService.updategfBackTime(gfids,uid,wid);
//        if(result == -5){
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//            return JSON.toJSONString(map);
//        }
//        if(result > 0) {
//            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
//            return JSON.toJSONString(map);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//            return JSON.toJSONString(map);
//        }
//    }
//
//
//    /**
//     * 根据单位id查询
//     * flag 0 表示查全部
//     * 1 查借阅中的,
//     * 2 已经归还的
//     */
////    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
//    @RequestMapping(value = "/selectBorrowcfInfo/{flag}/{page}/{pageSize}")
//    @RequiresRoles(value = "admin")
//    @ResponseBody
//    public String selectBorrowcfInfo(@PathVariable int flag,@PathVariable int page, int wid ,@PathVariable int pageSize) throws Exception{
//        HashMap<String,Integer> map = new HashMap<>();
//        PageHelper.startPage(page,pageSize);
//        List<BorrowCFExtends> bcfes = userService.selectBorrowcfInfo(wid,flag);
//        PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(bcfes);
//        if(bcfes != null && bcfes.size() > 0) {
//            return JSON.toJSONString(pageInfo,SerializerFeature.DisableCircularReferenceDetect);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//            return JSON.toJSONString(map);
//        }
//    }
////    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
//    @RequestMapping(value = "/selectBorrowgfInfo/{flag}/{page}/{pageSize}")
//    @RequiresRoles(value = "admin")
//    @ResponseBody
//    public String selectBorrowgfInfo(@PathVariable int flag,@PathVariable Integer page,int wid,@PathVariable int pageSize) throws Exception {
//        //flag = 0 查询所有的, flag = 1 查询 在借阅中的,flag = 2 归还了的
//        HashMap<String,Integer> map = new HashMap<>();
//
//
//        PageHelper.startPage(page,pageSize);
//
//        List<BorrowGFExtends> bgfes = userService.selectBorrowgfInfo(wid,flag);
//
//        PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(bgfes);
//        if(bgfes != null  && bgfes.size() > 0) {
//            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//            return JSON.toJSONString(map);
//        }
//    }
//
//    /**
//     * 根据文件id查这个文件的流向, 差全面,将用户的借阅信息和用户信息全查出来
//     */
////    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
//    @RequestMapping(value = "/selectBorrowcfInfoByFileid/{page}/{pageSize}")
//    @RequiresRoles(value = "admin")
//    @ResponseBody
//    public String selectBorrowcfInfoByFileid(@PathVariable int page, int cfid,@PathVariable int pageSize) throws Exception{
//        HashMap<String,Integer> map = new HashMap<>();
//        PageHelper.startPage(page,pageSize);
//        List<BorrowCFExtends> bcfes = userService.selectBorrowcfInfoByFileid(cfid);
//        PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(bcfes);
//        if(bcfes != null && bcfes.size() > 0) {
//            return JSON.toJSONString(pageInfo,SerializerFeature.DisableCircularReferenceDetect);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//            return JSON.toJSONString(map);
//        }
//    }
////    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
//    @RequestMapping(value = "/selectBorrowgfInfoByFileid/{page}/{pageSize}")
//    @RequiresRoles(value = "admin")
//    @ResponseBody
//    public String selectBorrowgfInfoByFileid(@PathVariable Integer page,int gfid,@PathVariable int pageSize) throws Exception {
//        //flag = 0 查询所有的, flag = 1 查询 在借阅中的,flag = 2 归还了的
//        HashMap<String,Integer> map = new HashMap<>();
//
//
//        PageHelper.startPage(page,pageSize);
//
//        List<BorrowGFExtends> bgfes = userService.selectBorrowgfInfoByFileid(gfid);
//
//        PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(bgfes);
//        if(bgfes != null  && bgfes.size() > 0) {
//            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//            return JSON.toJSONString(map);
//        }
//    }
//
////    public String selectBorrowcfInfo(String uname,String ucompany,@PathVariable int flag,@PathVariable Integer page) throws Exception {
////        HashMap<String,Integer> map = new HashMap<>();
////
////
////        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);
////
////        List<BorrowCFExtends> bcfes = userService.selectBorrowcfInfo(uname,ucompany,flag);
////
////        PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(bcfes);
////
////        if(bcfes != null && bcfes.size() > 0) {
////            return JSON.toJSONString(pageInfo,SerializerFeature.DisableCircularReferenceDetect);
////        }else {
////            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
////            return JSON.toJSONString(map);
////        }
////    }
////    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
////
////    @RequestMapping(value = "/selectBorrowgfInfo/{flag}/{page}")
////    @ResponseBody
////    public String selectBorrowgfInfo(String uname,String ucompany,@PathVariable int flag,@PathVariable Integer page) throws Exception {
////        //flag = 0 查询所有的, flag = 1 查询 在借阅中的,flag = 2 归还了的
////        HashMap<String,Integer> map = new HashMap<>();
////
////
////        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);
////
////        List<BorrowGFExtends> bgfes = userService.selectBorrowgfInfo(uname,ucompany,flag);
////
////        PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(bgfes);
////        if(bgfes != null  && bgfes.size() > 0) {
////            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
////        }else {
////            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
////            return JSON.toJSONString(map);
////        }
////    }
//
//    /**
//     * 查找最顶层的 分类
//     * @return
//     * @throws Exception
//     */
////    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/selectClassifyBiggest")
    @RequiresRoles(value = "admin")
    @ResponseBody
    public String selectClassifyBiggst() throws Exception {
        List<Classify> classifies = userService.selectClassifyBiggest();
        if(classifies != null && classifies.size() > 0) {
            return JSON.toJSONString(classifies);
        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);
    }
//    /**
//     * 查找下一级的分类, 并且 打印出这些分类的总的存放地址
//     */
//    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/selectClassifyByFather/{fatherid}")
    @RequiresRoles(value = "admin")
    @ResponseBody
    public String selectClassifyByFather(@PathVariable int fatherid) throws Exception{
        List<Classify> classifies = userService.selectClassifyByFatherId(fatherid);
//        如果这个 cyid = 0 则带出去的是选择了的分类的地址
        if(classifies != null && classifies.size() > 0) {
//            for (Classify c: classifies) {
//                if(c.getCyid() == 0) {
//                    address = address + "-" + c.getCyaddress();
//                    c.setCyaddress(address);
//                }
//            }
            return JSON.toJSONString(classifies);
        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);
    }
    /**
     * 查看自己的用户信息
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/selectMyself")
    @ResponseBody
    public String selectMyself() throws Exception{
        Subject subject = SecurityUtils.getSubject();
        String uaccount = (String)subject.getPrincipal();
//        System.out.println(uemail);
       UserExtend ue = userService.selectMySelf(uaccount);
       if(ue != null) {
           return  JSON.toJSONString(ue);
       }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);
    }
//
    /**
     * 根据日期范围查询未归还的用户信息及其借阅的文件信息
     * @param starttime
     * @param endtime
     * @return
     * @throws Exception
     */
//    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
//    @RequestMapping(value = "/selectBorrowcfByborrowtime")
//    @RequiresRoles(value = "admin")
//    @ResponseBody
//    public String selectBorrowcfByborrowtime(String starttime , String endtime ) throws  Exception {
//        HashMap<String , Integer > map = new HashMap<>();
//        if( starttime == null || endtime == null ) {
//           map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
//           return JSON.toJSONString(map);
//        }
//        List<BorrowCFExtends> bcf = userService.selectBorrowcfByborrowtime(starttime, endtime );
//        if( bcf != null && bcf.size() > 0  ) {
//            return JSON.toJSONString(bcf);
//        }else {
//            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
//            return JSON.toJSONString(map);
//        }
//
//    }
////
//    /**
//     * 根据日期范围查询未归还的用户信息及其借阅的文件信息
//     * @param starttime
//     * @param endtime
//     * @return
//     * @throws Exception
//     */
////    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
//    @RequestMapping(value = "/selectBorrowgfByborrowtime")
//    @RequiresRoles(value = "admin")
//    @ResponseBody
//    public String selectBorrowgfByborrowtime(String starttime , String endtime ) throws  Exception {
//        HashMap<String , Integer > map = new HashMap<>();
//        if( starttime == null || endtime == null ) {
//            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
//            return JSON.toJSONString(map);
//        }
//        List<BorrowGFExtends> bgf = userService.selectBorrowgfByborrowtime(starttime, endtime );
//        if( bgf != null ) {
//            return JSON.toJSONString(bgf);
//        }else {
//            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
//            return JSON.toJSONString(map);
//        }
//
//    }
////
////    /**
////     * 返回ocr读取的数据
////     * @param file
////     * @param type
////     * @return
////     * @throws Exception
////     */
//////    @RequiresRoles(value = {"admin"} )
//////    @RequestMapping(value = "/returnOcrInfo/{type}")
//////    @ResponseBody
//////    public String returnOcrInfo ( MultipartFile file , @PathVariable int type ) throws Exception {
//////        String path = UploadUtils.upload(file , 1);
//////        HashMap<String , Integer > map = new HashMap<>();
//////        if (path == null ) {
//////            map.put(StatusUtils.statecode , StatusUtils.IS_NULL );
//////            return JSON.toJSONString( map );
//////        }
////////        int flag = OCRHelper.convertFile(path);
////////        if( flag == 0 ) {
//////            //type == 1 表示这是companyFile type == 2 表示这个是GetFile
//////            if( type == 1) {
//////               CompanyFile companyFile = OCRHelper.ocrCompanyFile( path );
//////               return JSON.toJSONString( companyFile );
//////            }
//////            if (type == 2 ) {
//////                GetFile getFile = OCRHelper.ocrGetFile( path );
//////                return JSON.toJSONString( getFile );
//////            }
////////        }
//////
//////        map.put( StatusUtils.statecode, StatusUtils.FAILURE_OCR );
//////        return JSON.toJSONString( map );
//////    }
////
////    /**
////     * 查看文件借阅超时的接口
////     * @return
////     * @throws Exception
////     */
////    @RequiresRoles(value = {"admin"} )
////    @RequestMapping(value = "/selectFileIsPassTime/{type}")
////    @ResponseBody
////    public String selectFileIsPassTime(@PathVariable int type ) throws Exception {
////        HashMap<String,Integer> map = new HashMap<>();
////       if( type == 1 ) {
////           List<BorrowCFExtends> bcfs = userService.selectcfIsPassTime();
////           if( bcfs != null && bcfs.size() > 0 ) {
////               return JSON.toJSONString( bcfs );
////           }else {
////               map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
////               return JSON.toJSONString( map );
////           }
////       }else {
////           List<BorrowGFExtends> bgfs = userService.selectgfIsPassTime();
////           if( bgfs != null && bgfs.size() > 0 ) {
////               return JSON.toJSONString( bgfs );
////           }else {
////               map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
////               return JSON.toJSONString( map );
////           }
////       }
////    }
////
//    /**
//     * 预分配接口,将文件的待借阅标记修改为1, 然后将待借阅的信息,uid,wid,fileid ,写入借阅表中 , 可以多选
//     * @param type
//     * @param fileid
//     * @return
//     * @throws Exception
//     */
//    @RequiresRoles(value = {"admin"} )
//    @RequestMapping(value = "/insertWaitBorrowInfo/{type}")
//    @ResponseBody
//    public String insertWaitBorrowInfo( @PathVariable int type , int fileid ,String uid , String wid ) throws Exception {
//        HashMap<String , Integer> map = new HashMap<>();
//        int result = userService.addBorrowInfo( fileid , type ,uid , wid );
//        if( result == -3 ) {
//            map.put( StatusUtils.statecode , StatusUtils.IS_NULL  );
//            return  JSON.toJSONString( map );
//        }
//        if( result == -5 ) {
//            map.put( StatusUtils.statecode , StatusUtils.IS_BORROW );
//            return  JSON.toJSONString( map );
//        }
//        if( result == -2 ) {
//            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
//            return  JSON.toJSONString( map );
//        }
//        if( result > 0 ){
//            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
//        } else {
//            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT  );
//        }
//        return  JSON.toJSONString( map );
//    }
}