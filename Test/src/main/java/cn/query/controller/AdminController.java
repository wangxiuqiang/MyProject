package cn.query.controller;

import cn.query.pojo.Admin;
import cn.query.pojo.FileInSystem;
import cn.query.service.AdminService;
import cn.query.service.UserService;
import cn.query.utils.PassWordHelper;
import cn.query.utils.UploadUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

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
                    List<FileInSystem> lists = userService.selectFile();
                    model.addAttribute( "lists", lists );
                    return "allShow";
                }
            }
        }
        model.addAttribute("failure", "密码或用户名错误!");
        return "login";
    }

    @RequestMapping( value ="/add")
    public String add() throws Exception {
        return "add";
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

    @RequestMapping(value = "/delInfo/{id}")
    public String delInfo (@PathVariable int id , Model model ) throws Exception {
        int result = adminService.delFile( id );
        if(result > 0 ) {
            model.addAttribute("success","删除成功");
            return "add";
        } else {
            model.addAttribute("failure","录入失败");
            return "add";
        }
    }

    @RequestMapping( value = "/update/{id}")
    public String update(Model model , @PathVariable int id ) throws Exception {
       FileInSystem fileInSystem = userService.selectFileById( id );
        if ( fileInSystem != null ) {
            model.addAttribute("Info" , fileInSystem);
            return "update";
        } else {
            model.addAttribute("failure","查询失败");
            return "add";
        }

    }
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    public String updateInfo(MultipartFile file , FileInSystem fileInSystem , Model model) throws Exception {
        String filename = UploadUtils.upload(file);
        fileInSystem.setFileurl(filename);
        int a = adminService.updateFile(fileInSystem);
        if(a > 0) {
            model.addAttribute("success","录入成功");
            return "add";
        }
        model.addAttribute("failure","录入失败");
        return "add";
    }
}
