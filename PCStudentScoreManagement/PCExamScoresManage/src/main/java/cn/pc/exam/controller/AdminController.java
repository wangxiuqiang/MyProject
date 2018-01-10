package cn.pc.exam.controller;

import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.Impl.AdminManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminManagerServiceImpl adminManagerService;

    /**
     * 根据输入的id查询需要的教师或学生的信息
     * @param model
     * @param loginPo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/select")
    public String select(Model model, LoginPo loginPo)throws Exception {
        TeacherExtend teacher = adminManagerService.queryTeacherForOne(loginPo.getId());
        StudentExtend student  =adminManagerService.queryStudentForOne(loginPo.getId());
        int show = 1;
        model.addAttribute("show",show);
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

    /**
     *  通过点击的信息 查询老师或者学生的全部信息,通过flag来确定是老师还是学生
     * @param model
     * @param flag
     * @return
     * @throws Exception
     */
   @RequestMapping(value ="/selectAll/{flag}")
    public String selectAll(Model model , @PathVariable int flag) throws  Exception{
        model.addAttribute("flag",flag);
        if(flag == 1){
            List<TeacherExtend> listTeacher = adminManagerService.queryAllTeacher();
            if(listTeacher != null) {
                model.addAttribute("listTeacher",listTeacher );
                return "/admin/AdminSelect";
            }else {
                return null;
            }
        }else {
            List<StudentExtend> listStudent = adminManagerService.queryAllStudent();
            if(listStudent != null){
                model.addAttribute("listStudent",listStudent);
                return "/admin/AdminSelect";
            }else{
                return null;
            }
        }
   }
    @RequestMapping(value = "/deleteIndex")
    /**
     * 用来跳转到删除界面
     */
    public String deleteIndex(){
       return "/admin/AdminDelete";
    }

    /**
     * flag  用来表示是查询老师还是学生
     * @param model
     * @param whoSelect  用来将页面radio的值传过来,对flag进行赋值的测试
     * @return
     * @throws Exception
     */
   @RequestMapping(value = "/delete/{flag}")
    public String delete(Model model ,String whoSelect,@PathVariable int flag) throws Exception{

       if(whoSelect != null){

       if(whoSelect.equals("student")){
           flag = 2;
       }else if(whoSelect.equals("teacher")){
           flag = 1 ;
       }
       else{
           flag = flag;
       }
       }
       model.addAttribute("flag",flag);
       selectAll(model,flag);
       return "/admin/AdminDelete";
   }

    /**
     *
     * @param flag 用来获取删除老师还是学生,并将flag传递到删除成功界面,以便返回到删除页面的时候会出现删除后的信息
     * @param id 用来传递要删除的信息的  编号
     * @param redirect  将flag重定义到删除成功页面的参数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteSuccess/{flag}/{id}")
    public String deleteSuccess(@PathVariable int flag,  @PathVariable String id,
                                RedirectAttributes redirect) throws  Exception{
       int delete = 0;

       if(flag == 2) {
           delete = adminManagerService.delectStudentOne(id);
       }else{
           delete = adminManagerService.delectTeacherOne(id);
       }

      if(delete != 0 ){
          redirect.addFlashAttribute("flag",flag);
          return "redirect:/admin/deleteSuccessRedirect";
      }else {
          return null;
      }
    }
    @RequestMapping("/deleteSuccessRedirect")
    public String deleteSuccessRedirect(){
       return "/admin/AdminDeleteSuccess";
    }
}
