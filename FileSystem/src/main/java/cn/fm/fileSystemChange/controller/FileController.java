package cn.fm.fileSystemChange.controller;

import cn.fm.fileSystemChange.service.FileService;
import cn.fm.pojo.Borrow;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.pojo.User;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/admin" ,produces = "application/json;charset=utf-8" ,method = RequestMethod.POST)
public class FileController {

    @Autowired
    FileService fileService;
    /**
     * 添加一个用户或者修改一个文件,Controller
     *添加和修改,将整个类进行传递,在添加的时候指定领取人,
     *(根据文件id是不是存在判断是添加还是 修改),直接将整个类传过来,参数是类,用户id
     * @param companyFile
     * @param uid ,
     *            doIt 强制将领取人替换  1   , 0表示不替换
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/insertCompanyFileInfoNew")
    @ResponseBody
    public String insertCompanyFileInfoNew(CompanyFile companyFile , String uid ,Integer  doIt ) throws Exception {
        HashMap<String , Integer > map = new HashMap<>();
        if ( doIt == null ) {
            doIt = 0;
        }
        int result = 0;//用来判定是否成功
        if( companyFile == null ) {
            map.put(StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        if ( companyFile.getCfid() != 0 ) {
            //修改
          result = fileService.updateCompanyFile( companyFile , uid , doIt );
          //doIt=0不能强制修改,所以要进行确认,就是再发一遍链接,将doIt改为1,强制修改
          if( result == -1) {
              map.put( StatusUtils.statecode , StatusUtils.IS_EXIST_BORROW_UID );
              return JSON.toJSONString( map );
          } else if ( result == -2 ) {
              //文件被借走了,两个id不一样不能修改
              map.put(StatusUtils.statecode , StatusUtils.IS_EXIST_BORROW_UID_FILEGO );
              return JSON.toJSONString( map );
          } else {
              //修改成功或者失败
              if( result > 0 ) {
                  map.put(StatusUtils.statecode , StatusUtils.SUCCESS_INSERT);
                  return JSON.toJSONString( map );
              }else {
                  map.put(StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
                  return JSON.toJSONString( map );
              }
          }
        } else {
            companyFile.setCfdate( DateToStringUtils.dataTostring() );
            //添加
           result = fileService.insertCompanyFile( companyFile , uid );

        }
        if( result > 0 ) {
            map.put(StatusUtils.statecode , StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString( map );
        }else {
            map.put(StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
            return JSON.toJSONString( map );
        }
    }

    /**
     * 先查看是不是被领取或者被领走状态,如果是,就不能删除,如果不是修改状态位,同时修改这个文件借阅信息的状态位
     * 多选操作
     * 不需要查文件借阅信息的backtime直接通过isborrow就可以,0没有领走,1领走没还, 2 还了
     * @param cfid
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/delCompanyFileNew")
    @ResponseBody
    public String delCompanyFileNew( String cfid ) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        if( cfid == null ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        int result = fileService.delCompanyFileById( cfid );
        if( result == -5 ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_BORROW );
        } else if(result == 1) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_DEL );
        } else if(result == -2 ){
            map.put( StatusUtils.statecode , StatusUtils.FILE_IS_NOTEXISTS );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_DEL );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 查找文件的信息,同时查找文件的借阅信息,找出了领取人和待领取人信息,根据文件的所有可见属性查找
     * @param level 是否保密 0全部, 1保密,2普通
     * @param page 页码
     * @param pageSize 每页的数量
     * @param companyFile 文件
     * @param endtime 时间期限
     *  查看删除的还是没删除的,1 没删除的, 0 删除了的
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/selectCompanyFileNew/{level}/{page}/{pageSize}")
    @ResponseBody
    public String selectCompanyFileNew( @PathVariable int level , @PathVariable int page
            ,@PathVariable int pageSize ,CompanyFile companyFile, String endtime,Integer wid ) throws Exception {
       //设置爱当前页码和页面显示条数
        PageHelper.startPage(page,pageSize);
        System.out.println(companyFile.getCfname());
//查询
        if( wid == null ) {
            wid = 0;
        }
        List<BorrowCFExtends> borrowCFExtends = fileService.selectCompanyFile( companyFile , endtime ,level,wid  );
        if( borrowCFExtends != null  && borrowCFExtends.size() > 0 ) {
            PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(borrowCFExtends);

            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        }else {
            HashMap<String ,Integer> map = new HashMap<>();
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return  JSON.toJSONString( map );
        }
    }

    /**
     * 根据用户的id查分配给他的或者被他领走的文件,同样,返回borrowCfExtend类,
     * 但是传值不一样,要使用不同的service和dao
     * @param state 查看删除的还是没删除的,1 没删除的, 0 删除了的
     * @param page
     * @param pageSize
     * @param uid
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/selectCompanyFileByUidNew/{page}/{pageSize}")
    @ResponseBody
    public String selectCompanyFileByUidNew( Integer state, @PathVariable int page
            ,@PathVariable int pageSize , int uid  ) throws Exception {
        if( state == null ) {
            state = 1;
        }
        //设置爱当前页码和页面显示条数
        PageHelper.startPage(page,pageSize);
//查询
        List<BorrowCFExtends> borrowCFExtends = fileService.selectCompanyFileByUid( uid ,state );
        if( borrowCFExtends != null  && borrowCFExtends.size() > 0 ) {
            PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(borrowCFExtends);
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        }else {
            HashMap<String ,Integer> map = new HashMap<>();
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return  JSON.toJSONString( map );
        }
    }
    /**
      * 先修改文件的标志位,然后录入文件的借阅信息 ,根据文件id ,用户id, 单位id, 分配时间填入借阅表,根据文件id修改文件的waitborrow
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/insertCompanyFilesWaitBorrowNew")
    @ResponseBody
    public String insertCompanyFilesWaitBorrowNew( int uid, int wid, int fileid ) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        int result = fileService.insertCompanyFilsWaitBorrow( uid , wid, fileid );
        if( result > 0 ) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 因为发文一个单位发一份,所以就根据文件id修改就可了,在借阅表里面,一个文件只有一条记录,但是要保存领取人的id信息
     * 所以参数是 uid 和 fileid
     * 领走或归还文件, 要修改文件的标志位 isborrow = 1, 然后修改文件的信息,borrowtime 和shouldback
       flag = 1 则是领走, 2表示归还
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/updateCompanyFileIsBorrowOrBackNew/{flag}")
    @ResponseBody
    public String updateCompanyFileIsBorrowNew( String fileid , @PathVariable  int flag , int uid ) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        int result = fileService.updateCompanyFileIsBorrowOrBackNew( uid, fileid, flag );
        if ( result == -2 ) {
            map.put( StatusUtils.statecode , StatusUtils.FILE_IS_NOTBORROW );
            return  JSON.toJSONString( map );
        }
        if( result > 0 ) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 清退文件,要先查看这些文件是不是被归还了,就是isborrow=2的情况.,如果是的话,就进行清退
     * @param fileid
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/updateCompanyFileIsBack")
    @ResponseBody
    public String updateCompanyFileIsBack( String fileid ) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        int result = fileService.updateCompanyFileIsBack( fileid );
        if(result == -5 ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_BORROW );
        }
        if( result > 0 ) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 销毁文件,通过文件的id,可以多选,同时录入销毁的时间, 没被清退就不能销毁
     * @param fileid
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/updateCompanyFileIsDestroy")
    @ResponseBody
    public String updateCompanyFileIsDestroy( String fileid ) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        int result = fileService.updateCompanyFileIsDestroy( fileid );
        if(result == -5 ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NOTBACK );
        }
        if( result > 0 ) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 查询文件的借阅信息,,虽然和查询文件的信息差不多,但是不同之处在于, 上面的那个是通过left join之后用CompanyFile文件
     * 的信息进行筛选, 这个是通过borrow里面的wid进行筛选.
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/selectCompanyFileBorrowInfo/{page}/{pageSize}")
    @ResponseBody
    public String selectCompanyFileBorrowInfo( Integer wid , Integer flag
    , @PathVariable int page , @PathVariable int pageSize ) throws Exception {
        if( wid == null) {
            wid = 0;
        }
        if ( flag == null ) {
            flag = 0;
        }
        HashMap<String ,Integer> map = new HashMap<>();
        //设置爱当前页码和页面显示条数
        PageHelper.startPage(page,pageSize);
        List<BorrowCFExtends> bcfs = fileService.selectCompanyFileBorrowInfo(wid,flag);
        if( bcfs != null  && bcfs.size() > 0 ) {
            PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(bcfs);
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        }else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return  JSON.toJSONString( map );
        }
    }
    /**
     *    下面是收文的接口
     */
    // 下面是用户的收文的接口
    //只查找文件的信息,, 返回list ,根据文件的全部信息查,返回之后,将为用户的传阅人的名字和id传过去
    @RequiresRoles("admin")
    @RequestMapping(value = "/selectGetFile/{page}/{pageSize}")
    @ResponseBody
    public String selectGetFile( Integer level,  GetFile getFile, @PathVariable int page , @PathVariable int pageSize
    ,String endtime) throws Exception {
        if ( level == null ) {
            level = 0;
        }
        HashMap<String ,Integer> map = new HashMap<>();
        PageHelper.startPage(page,pageSize);
        List<GetFile> getFiles = fileService.selectGetFile( level ,getFile ,endtime );
        if( getFiles != null  && getFiles.size() > 0 ) {
            PageInfo<GetFile> pageInfo = new PageInfo<GetFile>(getFiles);
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        }else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return  JSON.toJSONString( map );
        }
    }



    /**
     * 修改文件的信息. 可以对文件进行恢复 , 或者添加文件的信息,根据是不是有文件的id来进行
     * @param getFile
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/insertGetFileInfoNew")
    @ResponseBody
    public String insertGetFileInfoNew( GetFile getFile  ) throws Exception {
        int result = 0;
        HashMap<String ,Integer> map = new HashMap<>();
        if( getFile.getGfid() != 0 ) {
            //修改,
            result = fileService.updateGetFileByGFid( getFile );
        } else {
            //添加
            result = fileService.insertGetFile( getFile );
        }
        if( result == -5 ) {
            //已经分配过了
            map.put( StatusUtils.statecode , StatusUtils.EXIST_CONTENT );
            return  JSON.toJSONString( map );

        }
        if (result == -2 ) {
            //没有归还
            map.put( StatusUtils.statecode , StatusUtils.IS_BORROW );
            return  JSON.toJSONString( map );
        }
        if(result > 0 ) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return  JSON.toJSONString( map );

    }


    /**
     * 修改文件的借阅表的信息 ,根据文件的id和单位id
     * 进行录入借阅信息,两种,归还或借走, 如果是归还,那么就要知道这是多个人中的第几个,用read和readcom进行比较 ,
     * 如果在这一次录入之前 read -1和readcom一样长,那么就能确定isborrow可以修改为2, 否则修改为-1 方便多个人中的下一个进行借阅
     * 修改文件的借阅为,和文件的待领取位., 另外修改文件的借阅信息,
     * @param wid,uid,fileid,flag 1 取走, 2 归还,
     *
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/updateBorrow_GetFileByGFId")
    @ResponseBody
    public String updateBorrow_GetFileByGFId( int wid ,int fileid, int uid , int flag ) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        int result = fileService.updateBorrow_GetFileByGFId( wid, fileid ,uid ,flag);
        if ( result == -2 ) {
            map.put( StatusUtils.statecode , StatusUtils.FILE_IS_NOTBORROW );
            return  JSON.toJSONString( map );
        }
        if(result > 0 ) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return  JSON.toJSONString( map );
    }

    /**
     * 设置文件的标记位为0 ,同时修改借阅表的信息
     * @param gfids
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/delGetFileByGfid")
    @ResponseBody
    public String delGetFileByGfid( String gfids ) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        int result = fileService.delGetFileByGfid( gfids );
        if(result > 0 ) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_DEL );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_DEL );
        }
        return JSON.toJSONString( map );
    }


    /**
     * 查询借阅的信息,包含文件信息
     * @param wid
     * @param flag
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/selectGetFileAndBorrowInfo/{page}/{pageSize}")
    @ResponseBody
    public String selectGetFileAndBorrowInfo(Integer wid , int flag ,@PathVariable int page , @PathVariable int pageSize  ) throws Exception {
        if (wid == null ) {
            wid = 0;
        }
        HashMap<String ,Integer> map = new HashMap<>();
        PageHelper.startPage(page,pageSize);
        List<BorrowGFExtends> borrowGFExtends = fileService.selectGetFileAndBorrowInfo( wid,flag );
        if( borrowGFExtends != null && borrowGFExtends.size() > 0 ) {
            PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(borrowGFExtends);
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND ) ;
            return JSON.toJSONString( map );
        }
    }

    /**
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/selectGetFileByUid/{page}/{pageSize}")
    @ResponseBody
    public String selectGetFileByUid( int uid , int flag
    ,@PathVariable int page , @PathVariable int pageSize) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        PageHelper.startPage(page,pageSize);
        List<BorrowGFExtends> borrowGFExtends = fileService.selectGetFileByUid( uid,flag );
        if( borrowGFExtends != null && borrowGFExtends.size() > 0 ) {
            PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(borrowGFExtends);
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND ) ;
            return JSON.toJSONString( map );
        }
    }
}

