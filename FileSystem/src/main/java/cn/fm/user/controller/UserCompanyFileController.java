package cn.fm.user.controller;

import cn.fm.pojo.CompanyFile;
import cn.fm.user.service.UserCompanyFileService;
import cn.fm.user.service.serviceImpl.UserCompanyFileServiceImpl;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
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

import java.util.List;

@Controller
@RequestMapping(value = "/worker",produces = "application/json;charset=utf8")
public class UserCompanyFileController {

    @Autowired
    UserCompanyFileService userCompanyFileService;

    /**
     * 查询所有的文件信息
     * @return
     * @throws Exception
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
   @RequestMapping(value = "/findCompanyFiles")
   @ResponseBody
    public String findCompanyFiles() throws Exception {
       return JSON.toJSONString(userCompanyFileService.selectAllCompanyFile());
   }

     /**
     * 对文件进行单项的查询 多项
     * @param companyFile
     * @return
     * @throws Exception
     */
//     @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findTypeCompanyFiles")
    @ResponseBody
    public String findTypeCompanyFiles(@RequestBody CompanyFile companyFile) throws Exception {
        if( companyFile.getCfname() != null || companyFile.getCfclassifyid() != 0 || companyFile.getCflanguage() != null
                || companyFile.getCfdate() != null || companyFile.getCfnumber() != 0) {
            return JSON.toJSONString(userCompanyFileService.findTypeFiles(companyFile));

        }
        return JSON.toJSONString(StatusUtils.IS_NULL);
    }

    /**
     * 根据id查找文件
     */
//    @RequestMapping(value = "/findCompanyFileById")
    @ResponseBody
    public String findCompanyFileById(Integer cfid) throws Exception {

        if(userCompanyFileService.selectCompanyFileById(cfid) != null) {
            return JSON.toJSONString(userCompanyFileService.selectCompanyFileById(cfid));
        }else{
            return JSON.toJSONString(StatusUtils.FAILURE_FIND);
        }
    }
    /**
     * 插入数据
     * @param companyFile
     * @return
     * @throws Exception
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/infoSubCompanyFile")
    @ResponseBody
    public String infoSubCompanyFile(@RequestBody @Validated CompanyFile companyFile,
                                     BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return JSON.toJSONString(allErrors);
        }


        if(userCompanyFileService.insertCompanyFile(companyFile) != 0 ) {
            return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
        }else {
            return  JSON.toJSONString(StatusUtils.FAILURE_INSERT);
        }
    }

    /**
     * 根据id更新  文件
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/updateSubCompanyFile")
    @ResponseBody
    public String updateSubGetFile(@RequestBody CompanyFile companyFile) throws Exception {

        if(userCompanyFileService.updateCompanyFileById(companyFile) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
        }
    }
    /**
     * 根据id删除单条记录
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/delCompanyFile")
    @ResponseBody
    public String delGetFile(@RequestBody int cfid) throws Exception {
        if(cfid == 0) {
            return JSON.toJSONString(StatusUtils.IS_NULL);
        }
        if(userCompanyFileService.deleteCompanyFileById(cfid) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_DEL);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_DEL);
        }
    }


}
