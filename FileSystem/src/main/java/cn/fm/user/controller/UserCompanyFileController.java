package cn.fm.user.controller;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.User;
import cn.fm.user.dao.UserMapper;
import cn.fm.user.service.UserCompanyFileService;
import cn.fm.user.service.UserService;
import cn.fm.user.service.serviceImpl.UserCompanyFileServiceImpl;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.CompanyFileExtends;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/worker",produces = "application/json;charset=utf8" , method = RequestMethod.POST)
public class UserCompanyFileController {

    @Autowired
    UserService userService;
    @Autowired
    UserCompanyFileService userCompanyFileService;

    /**
     * 查询所有的文件信息
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
   @RequestMapping(value = "/findCompanyFiles/{page}")
   @ResponseBody
    public String findCompanyFiles(@PathVariable Integer page) throws Exception {
       PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

       List<CompanyFile> companyFiles= userCompanyFileService.selectAllCompanyFile();

       PageInfo<CompanyFile> pageInfo = new PageInfo<CompanyFile>(companyFiles);
       return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
   }

     /**
     * 对文件进行单项的查询 多项
     * @param companyFile
     * @return
     * @throws Exception
     */
     @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findTypeCompanyFiles/{page}")
    @ResponseBody
    public String findTypeCompanyFiles( CompanyFile companyFile,@PathVariable  Integer page) throws Exception {
        if( companyFile.getCfname() != null || companyFile.getCfclassifyid() != 0 || companyFile.getCflanguage() != null
                || companyFile.getCfdate() != null || companyFile.getCffontid() != null) {
            PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

            List<CompanyFile> companyFiles = userCompanyFileService.findTypeFiles(companyFile);

            PageInfo<CompanyFile> pageInfo = new PageInfo<CompanyFile>(companyFiles);
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);

        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
        return JSON.toJSONString(map);

    }

    /**
     * 根据id查找文件
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)

    @RequestMapping(value = "/findCompanyFileById/{cfid}")
    @ResponseBody
    public String findCompanyFileById(@PathVariable int cfid) throws Exception {

        if(userCompanyFileService.selectCompanyFileById(cfid) != null) {
            return JSON.toJSONString(userCompanyFileService.selectCompanyFileById(cfid), SerializerFeature.DisableCircularReferenceDetect);
        }else{
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);

        }
    }
    /**
     * 插入数据
     * @param companyFile
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/infoSubCompanyFile")
    @ResponseBody
    public String infoSubCompanyFile( @Validated CompanyFile companyFile,
                                     BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return JSON.toJSONString(allErrors);
        }


        if(userCompanyFileService.insertCompanyFile(companyFile) != 0 ) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
    }

    /**
     * 根据id更新  文件
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/updateSubCompanyFile")
    @ResponseBody
    public String updateSubGetFile(CompanyFile companyFile) throws Exception {

        if(userCompanyFileService.updateCompanyFileById(companyFile) != 0) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);

        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
    }
    /**
     * 根据id删除单条记录
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/delCompanyFile")
    @ResponseBody
    public String delGetFile(String cfid) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        String[] cfidstring =cfid.split(",");
        int[] cfids = new int[cfidstring.length];
        for (int i = 0; i < cfidstring.length; i++) {
            cfids[i] = Integer.parseInt(cfidstring[i]);
            System.out.println(cfids[i]);
        }

        if(cfids.length <= 0) {
            return JSON.toJSONString(StatusUtils.IS_NULL);
        }
        if(userCompanyFileService.deleteCompanyFileById(cfids) != 0) {
            if(userCompanyFileService.deleteCompanyFileBorrowInfo(cfids) != 0){
                map.put(StatusUtils.statecode,StatusUtils.SUCCESS_DEL);
                return JSON.toJSONString(map);
            }
        }
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_DEL);
        return JSON.toJSONString(map);

    }


}
