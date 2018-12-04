package cn.fm.fileSystemChange.controller;

import cn.fm.fileSystemChange.service.FileService;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.BorrowCFExtends;
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

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/admin" ,produces = "application/json;charset=utf-8" ,method = RequestMethod.POST)
public class FileController {

    @Autowired
    FileService fileService;
    /**
     * 添加一个用户或者修改一个用户,Controller
     *添加和修改,将整个类进行传递,在添加的时候指定领取人,
     *(根据文件id是不是存在判断是添加还是 修改),直接将整个类传过来,参数是类,用户id
     * @param companyFile
     * @param uid ,
     *            doIt 强制将领取人替换  1 0
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/insertCompanyFileInfoNew")
    @ResponseBody
    public String insertGetFileInfoNew(CompanyFile companyFile , String uid , int doIt ) throws Exception {
        HashMap<String , Integer > map = new HashMap<>();
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
     * @param state 查看删除的还是没删除的,1 没删除的, 0 删除了的
     * @return
     * @throws Exception
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/selectCompanyFileNew/{level}/{state}/{page}/{pageSize}")
    @ResponseBody
    public String selectCompanyFileNew( @PathVariable int level ,  @PathVariable int state , @PathVariable int page
            ,@PathVariable int pageSize ,CompanyFile companyFile, String endtime ) throws Exception {
       //设置爱当前页码和页面显示条数
        PageHelper.startPage(page,pageSize);
        System.out.println(companyFile.getCfname());
//查询
        List<BorrowCFExtends> borrowCFExtends = fileService.selectCompanyFile( companyFile , endtime ,level ,state );
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
    @RequestMapping(value = "/selectCompanyFileByUidNew/{state}/{page}/{pageSize}")
    @ResponseBody
    public String selectCompanyFileByUidNew( @PathVariable int state, @PathVariable int page
            ,@PathVariable int pageSize , int uid  ) throws Exception {
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
}
