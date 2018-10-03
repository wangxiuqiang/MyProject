package cn.query.controller;

import cn.query.pojo.Admin;
import cn.query.pojo.FileInSystem;
import cn.query.service.AdminService;
import cn.query.utils.PassWordHelper;
import cn.query.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;



    @RequestMapping( value = "/logins")
    public String login() throws Exception {
        return "login";
    }

    @RequestMapping(value = "/loginGo")
    public String loginGo (Admin admin , Model model) throws Exception {
        admin.setPasswd(PassWordHelper.SHA256(admin.getPasswd()));
        Admin admin1 = adminService.selectAdmin(admin.getName());
        if(admin1 != null) {
            if (admin1.getName().equals(admin.getName())) {
                if(admin1.getPasswd().equals(admin.getPasswd())) {
                    return "add";
                }
            }
        }
        model.addAttribute("failure", "密码或用户名错误!");
        return "login";
    }

    @RequestMapping(value = "/insertInfo",method = RequestMethod.POST)
    public String insertInfo( MultipartFile file ,FileInSystem fileInSystem,Model model) throws Exception {
        String filename = UploadUtils.upload(file);
        fileInSystem.setFileurl(filename);
         int a = adminService.insertFile(fileInSystem);
         if(a > 0) {
             model.addAttribute("success","录入成功");
             return "add";
         }
        model.addAttribute("failure","录入失败");
        return "add";
    }
}
