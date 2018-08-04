package cn.fm.user.controller;

import cn.fm.pojo.CompanyFile;
import cn.fm.user.service.UserCompanyFileService;
import cn.fm.user.service.serviceImpl.UserCompanyFileServiceImpl;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/findTypeCompanyFiles")
    @ResponseBody
    public String findTypeCompanyFiles(CompanyFile companyFile) throws Exception {
        if(companyFile.getCfname() != null || companyFile.getCfclassifyid() != 0 || companyFile.getCflanguage() != null
                || companyFile.getCfdate() != null || companyFile.getCfnumber() != 0) {
            return JSON.toJSONString(userCompanyFileService.findTypeFiles(companyFile));

        }
        return JSON.toJSONString(StatusUtils.IS_NULL);
    }

    /**
     * 插入数据
     * @param companyFile
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/infoSubCompanyFile")
    @ResponseBody
    public String infoSubCompanyFile(CompanyFile companyFile) throws Exception {

        if(userCompanyFileService.insertCompanyFile(companyFile) != 0 ) {
            return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
        }else {
            return  JSON.toJSONString(StatusUtils.FAILURE_INSERT);
        }
    }

    /**
     * 根据id更新  文件
     */
    @RequestMapping(value = "/updateSubCompanyFile")
    @ResponseBody
    public String updateSubGetFile(CompanyFile companyFile) throws Exception {

        if(userCompanyFileService.updateCompanyFileById(companyFile) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
        }
    }
    /**
     * 根据id删除单条记录
     */
    @RequestMapping(value = "/delCompanyFile")
    @ResponseBody
    public String delGetFile(int cfid) throws Exception {
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
