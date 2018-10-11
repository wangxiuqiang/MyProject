package cn.query.controller;

import cn.query.pojo.FileInSystem;
import cn.query.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping( value = "/")
    public String index() throws Exception {
        return "index";
    }

    @RequestMapping(value = "/selectInfo" ,method = RequestMethod.POST)
    public String selectInfo(String name,Model model) throws Exception {
        List<FileInSystem> fileInSystems = userService.selectFileByName(name);
        if(fileInSystems != null && fileInSystems.size() > 0) {
           model.addAttribute("info" ,fileInSystems);
           return "index";
        }
        model.addAttribute("failure","查询失败");
        return "index";
    }

    @RequestMapping(value = "/selectInfoById/{id}" )
    public String selectInfoById(@PathVariable int id, Model model) throws Exception {
        FileInSystem fileInSystems = userService.selectFileById(id);
        if(fileInSystems != null ) {
            model.addAttribute("file" ,fileInSystems);
            return "showOne";
        }
        model.addAttribute("failure","查询失败");
        return "index";
    }

    @RequestMapping(value = "/selectAll" )
    public String selectAll( Model model) throws Exception {
        List<FileInSystem> fileInSystems = userService.selectFile();
        if(fileInSystems != null && fileInSystems.size() > 0) {
            model.addAttribute("lists" ,fileInSystems);
            return "allShow";
        }
        model.addAttribute("failure","查询失败");
        return "index";
    }
}
