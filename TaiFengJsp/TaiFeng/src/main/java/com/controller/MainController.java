package com.controller;

import com.service.ReadFileName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    ReadFileName readFinishImageFile;

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
 @RequestMapping(value = "/ImageShow")
    public String ImageShow(){
        return "ImageShow";
    }
    @RequestMapping(value = "/ImageShowFinish")
    public String ImageShowFinish(Model model){
        String[] name = readFinishImageFile.fileNames();
        model.addAttribute("name",name);
        return "ImageShowFinish";
    }
    @RequestMapping(value = "/ImageShowRace")
    public String ImageShowRace(){
        return "ImageShowRace";
    }
    @RequestMapping(value = "/ImageShowTraining")
    public String ImageShowTraining(){
        return "ImageShowTraining";
    }
}
