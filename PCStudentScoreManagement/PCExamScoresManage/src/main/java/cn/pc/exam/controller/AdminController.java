package cn.pc.exam.controller;

import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;
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

    /**
     * 用来通过班级查询学生的信息
     * @param flag 从页面获取flag  表明这是学生的查询,并将其返回 指定固定的内容在页面显示
     * @param model 将内容传到页面上
     * @param Gid 参数从页面获取(通过数据绑定) , 表示班级的编号,作为函数参数,进行查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectStudentForGrade/{flag}")
    public String selectStudentForGrade(@PathVariable int flag,Model model,String Gid) throws Exception{
      StudentExtend student = adminManagerService.queryStudentForGrade(Gid);
      model.addAttribute("flag",flag);
      if(student != null) {
          model.addAttribute("studentGrade",student);
          return "/admin/AdminSelect";
      }else{
          return null;
      }
    }

    /**
     * 用来跳转到删除界面
     */
    @RequestMapping(value = "/deleteIndex")
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

    /**
     * 使对数据库的增删改操作都进行内部重定向到页面,因此多写一个函数来将重定向的页面导入
     * 防止刷新多次执行问题
     * @return
     */
    @RequestMapping("/deleteSuccessRedirect")
    public String deleteSuccessRedirect(){
       return "/admin/AdminDeleteSuccess";
    }

    /**
     * 第一次访问insert页面时,或者从主页面访问insert页面时调用的函数
     * @return
     */
    @RequestMapping("/insert")
    public String insert(){
        return "/admin/AdminInsert";
    }

    /**
     *  who用来返回给页面,提示页面用来显示那一部分内容
     * @param model
     * @param insertWho  用来接收页面上传来的参数进行判断
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertInput")
    public String insertInput(Model model, String insertWho) throws Exception{
        int who = 0;
        if(insertWho.equals("teacher")){
           who = 1;
        }else{
            who = 2;
        }
        model.addAttribute("who",who);
        return "/admin/AdminInsert";
    }

    /**
     *  count 用确定函数执行是否成功
     * @param model
     * @param who 页面传到函数,说明写入的是哪种类型,来确定调用的函数类型
     * @param teacher
     * @param student
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertSuccessOrFaliure/{who}")
    public String insertSuccessOrFaliure(Model model,@PathVariable int who
            , Teacher teacher , Student student) throws Exception{
        int count;
        /**
         * 用来将没写入的外键赋值,防止页面传过来的值不是外键需要的值(空或其他表存在的值)
         * 赋值为空,表示需要为空
         * 尝试过将页面中input的value设置为"null"和"" ,都不能写入,并出现错误
         */
        if(student.getSEid().equals("")){
            student.setSEid(null);
        }
        if(student.getSGid().equals("")){
            student.setSGid(null);
        }
    //    System.out.println(student.getSEid());
        if(who == 2) {
             count = adminManagerService.insertStudent(student);

        }else {
             count = adminManagerService.insertTeacher(teacher);
        }
        //确保输入一个完成还能继续输入第二个
        model.addAttribute("who",who);
        if(count != 0){
            model.addAttribute("result","success");
        }else{
            model.addAttribute("result","shibai");

        }
        return "/admin/AdminInsert";
    }
}
