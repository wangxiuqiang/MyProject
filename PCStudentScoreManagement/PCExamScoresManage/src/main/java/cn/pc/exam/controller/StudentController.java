package cn.pc.exam.controller;

import cn.pc.exam.service.Impl.StudentManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentManagerServiceImpl studentManagerService;

    /**
     * 用来返回更改页面,不管是不是成功修改
     * @param model
     * @return
     */
    @RequestMapping("/beforeUpdatePassword/{Sid}")
    public String beforeUpdatePassword(Model model,@PathVariable String Sid){
       model.addAttribute("Sid",Sid);
       return "/student/StudentUpdatePassword";
    }

    /**
     *   先查询再进行比较,再输入,这个业务逻辑都在UpdatePassword()方法中
     * @param redirect
     * @param model
     * @param Sid 学号
     * @param firstPassword   原先的密码
     * @param afterPassword 更改的密码
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePassword1/{Sid}")
    public String updatePassword1(RedirectAttributes redirect, Model model, @PathVariable String Sid, String firstPassword
    , String afterPassword,String againPassword)throws Exception{
      if(afterPassword.equals(againPassword)) {
          if (studentManagerService.updatePassword(firstPassword, afterPassword, Sid) == 1) {
              redirect.addFlashAttribute("success", "修改成功");
              return "redirect:/student/beforeUpdatePassword/" + Sid;
          }else{
              model.addAttribute("success","修改失败");
              return "/student/StudentUpdatePassword" ;
          }
      }else{
            model.addAttribute("success","修改失败");
            return "/student/StudentUpdatePassword" ;
        }
    }
}
