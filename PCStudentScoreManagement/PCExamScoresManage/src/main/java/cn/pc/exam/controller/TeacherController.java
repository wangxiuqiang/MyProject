package cn.pc.exam.controller;

import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.Impl.AdminManagerServiceImpl;
import cn.pc.exam.service.Impl.TeacherManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    AdminManagerServiceImpl adminManagerService;

    @Autowired
    TeacherManagerServiceImpl teacherManagerService;
    @RequestMapping("/updatePassword/{id}")
    public String updatePassword(Model model, @PathVariable String id) throws Exception{
        model.addAttribute("flag","update");
        TeacherExtend teacher = adminManagerService.queryTeacherForOne(id);
        model.addAttribute("teacherEx",teacher);
        return "/teacher/TeacherIndex";
    }

    @RequestMapping("/updatePasswordSuccess/{id}")
    public String updatePasswordSuccess(@PathVariable String id
            , Model model,TeacherExtend teacherExtend) throws Exception{
           if(teacherExtend.getTpassword().equals(teacherExtend.getNewPassword())){
               teacherManagerService.updatePassword(teacherExtend);
           }
           model.addAttribute("success","success");
           return "/teacher/TeacherPasswordSuccess";
    }
}
