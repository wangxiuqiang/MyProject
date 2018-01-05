package cn.pc.exam.controller;

import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.Impl.AdminManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminManagerServiceImpl adminManagerService;

    @RequestMapping(value = "/select")
    public String select(Model model, LoginPo loginPo)throws Exception {
        TeacherExtend teacher = adminManagerService.queryTeacherForOne(loginPo.getId());
        StudentExtend student  =adminManagerService.queryStudentForOne(loginPo.getId());
        if(teacher ==null){
            if(student == null) {
                model.addAttribute("no" , "没有找到,请检查输入是否错误");
            }else {
                model.addAttribute("student" ,student );
            }
        }else{
            model.addAttribute("teacher" ,teacher );
        }
        return "/admin/AdminSelect";
    }
}
