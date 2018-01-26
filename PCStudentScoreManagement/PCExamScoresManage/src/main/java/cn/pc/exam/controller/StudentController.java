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

    /**
     *   将课程信息查出来以供选择,查询出成绩的前一个页面
     * @param model
     * @param Sid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectScore/{Sid}")
    public String selectScore(Model model,@PathVariable String Sid) throws Exception{
        model.addAttribute("Sid",Sid);
        model.addAttribute("courseList",studentManagerService.selectCourses(Sid));
        return "/student/StudentSelectScore";
    }

    /**
     * 查询出学生的成绩,使用Cid和Sid两个参数
     * @param model
     * @param Cid
     * @param Sid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectScoreSuccess/{Sid}")
    public String selectScoreSuccess(Model model,String Cid,@PathVariable String Sid) throws Exception{
        //cid用来将select中的值确定为上面页面选择好的值,不会从头显示
       //控制select标签中选中的置顶
        model.addAttribute("cid",Cid);
       // System.out.println(Cid);
        //显示课程使用
        model.addAttribute("courseList",studentManagerService.selectCourses(Sid));
       //成绩查出来
        model.addAttribute("score",studentManagerService.selectScore(Cid,Sid));
        return "/student/StudentSelectScore";
    }

}
