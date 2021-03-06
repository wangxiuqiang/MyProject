package cn.pc.exam.controller;

import cn.pc.exam.md5.Md5Salt;
import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.Impl.AdminManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
     * 用来跳转到删除界面 ,进行选择. 删除的第一步,
     */
    @RequestMapping(value = "/deleteIndex")
    public String deleteIndex(){
       return "/admin/AdminDelete";
    }

    /**
     * flag  用来表示是查询老师还是学生 ,用来显示要删除的内容的,删除的前一步,先显示
     * 里面调用了该类的selectAll函数,用来显示所有的老师或学生, 因为selectAll已经进行过model
     * 所以  只调用就可以啦
     * @param model
     * @param whoSelect  用来将页面radio的值传过来,对flag进行赋值的测试  ,
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
     *  单个的删除, 点击删除之后 ,该函数将其删除,重定向到另一url
     * @param flag 用来获取删除老师还是学生,并将flag传递到删除成功界面,以便返回到删除页面的时候会出现删除后的信息
     * @param id 用来传递要删除的信息的  编号
     * @param redirect  将flag重定义到删除成功页面的参数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteOneSuccess/{flag}/{id}")
    public String deleteOneSuccess(@PathVariable int flag,  @PathVariable String id,
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

    @RequestMapping(value = "/deleteAllSuccess/{flag}")
    public String deleteAllSuccess(@PathVariable int flag,RedirectAttributes redirect , String[] deleteSome
    ) throws Exception{

        int count = 0;
        redirect.addFlashAttribute("flag",flag);
        if(flag == 1){
           count = adminManagerService.deleteSomeTeacher(deleteSome);
        }else{
           count = adminManagerService.deleteSomeStudent(deleteSome);
        }
       if(count != 0 ){
            return "redirect:/admin/deleteIndex";
       }else{
           return null;
       }
    }
    /**
     *
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

    /**failure
     *  count 用确定函数执行是否成功
     * @param model
     * @param who 页面传到函数,说明写入的是哪种类型,来确定调用的函数类型
     * @param teacher
     * @param student
     * @return  重新定向到insert的url ,传过去的值依旧生效, 因为是同一个页面
     * @throws Exception
     */
    @RequestMapping(value = "/insertSuccessOrFaliure/{who}")
    public String insertSuccessOrFaliure(Model model, @PathVariable int who
            ,@Validated Teacher teacher ,BindingResult bindingResultT
            , @Validated Student student, BindingResult bindingResultS
    , RedirectAttributes redirect) throws Exception{
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
    //    //转换为MD5编码  System.out.println(student.getSEid());
        if(who == 2) {
            if(bindingResultS.hasErrors()){
                List<ObjectError> errors = bindingResultS.getAllErrors();
                for(ObjectError objectError:errors){
                    System.out.println(objectError.getDefaultMessage());
                }
                model.addAttribute("errors",errors);
                return "/admin/AdminInsert";
            }
            //转换为MD5编码
            student.setSpassword(Md5Salt.md5(student.getSpassword()));
             count = adminManagerService.insertStudent(student);

        }else {
            if(bindingResultT.hasErrors()){
                List<ObjectError> error = bindingResultT.getAllErrors();
                model.addAttribute("errors",error);
                return "/admin/AdminInsert";
            }
            //转换为MD5编码
            teacher.setTpassword(Md5Salt.md5(teacher.getTpassword()));
             count = adminManagerService.insertTeacher(teacher);
        }
        //将who传过去 确保输入一个完成还能继续输入第二个
        redirect.addFlashAttribute("who",who);
        if(count != 0){
           redirect.addFlashAttribute("result","success");
        }else{
            redirect.addFlashAttribute("result","shibai");

        }
        return "redirect:/admin/insert";
    }

    /**
     * 刚开始进入的修改页面
     * @return
     */
    @RequestMapping(value = "/update")
    public String update() {
        return "/admin/AdminUpdate";
    }

    /**
     *
     * @param model
     * @param Gid   用来接收页面传过来的值,在按班级查询的时候接收
     * @param flag 用来判断是老师还是学生
     * @param part   用来判断那一部分的执行,2 表示执行的全部的查询, 包括老师学生,
     *               用flag判断老师还是学生 1 或2 调用本类的selectAll方法
     *               part中3 表示的是按班级查询雪神的部分,调用本类的selectStudentForGrade方法
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateForType/{flag}/{part}")
    public String updateForType(Model model,String Gid
    ,@PathVariable int flag, @PathVariable int part
    ) throws Exception{
           if(part == 2){
              selectAll(model,flag);
          }else{
              selectStudentForGrade(flag,model,Gid);
          }
          return "/admin/AdminUpdate";
    }

    /**
     *   进行更改的页面,一个个进行改,将查出来的内容进行显示,方便完成更改
     * @param model
     * @param id   表示更改的用户的id
     * @param flag  表示更改的是老师还是学生
     *              通过flag和id一起来确定要改哪一个表中的数据 从而获取出这个数据信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateOne/{id}/{flag}")
    public String updateOne(Model model,@PathVariable String id,@PathVariable int flag )throws Exception{
        model.addAttribute("flag",flag);
        if(flag  == 1){
            TeacherExtend teacher = adminManagerService.queryTeacherForOne(id);
            model.addAttribute("teacher",teacher);
            //将查出来的id先保存一份,以便改的时候,如果改id,可以用这个作为条件
            Teacher.setBeforeTid(teacher.getTid());
            return "/admin/AdminUpdateNextForOne";
        }else{
            StudentExtend student = adminManagerService.queryStudentForOne(id);
            //将查出来的id先保存一份,以便改的时候,如果改id,可以用这个作为条件
            Student.setBeforeSid(student.getSid());
            model.addAttribute("student",student);
            return "/admin/AdminUpdateNextForOne";
        }
    }

    /**
     * 进行修改,点击提交后执行的函数,将信息写会数据库
     * @param teacher   将teacher的内容写回数据库
     * @param bindingResultT  校验器使用的错误收集参数
     * @param student 将student的内容写回数据库
     * @param bindingResultS  校验器使用的错误收集参数
     * @param model
     * @param flag  用来判断是将哪个类往哪个数据表中写,如将student类写回student数据表
     * @param redirect 重定向用到的,将需要显示在下一个页面的值,传到下一个页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateSuccess/{flag}")
    public String updateSuccess(@Validated Teacher teacher , BindingResult bindingResultT,
    @Validated Student student,BindingResult bindingResultS,Model model
    ,@PathVariable int flag,RedirectAttributes redirect) throws Exception{
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
        //转换为MD5编码  System.out.println(student.getSEid());
        if(flag == 2) {
            if(bindingResultS.hasErrors()){
                List<ObjectError> errors = bindingResultS.getAllErrors();
//                for(ObjectError objectError:errors){
//                    System.out.println(objectError.getDefaultMessage());
//                }
                model.addAttribute("errors",errors);
                return "/admin/AdminUpdateNextForOne";
            }
//            //转换为MD5编码
//            student.setSpassword(Md5Salt.md5(student.getSpassword()));
              adminManagerService.updateStudentOne(student);
              redirect.addFlashAttribute("student",student);
        }else {
            if(bindingResultT.hasErrors()){
                List<ObjectError> errors = bindingResultT.getAllErrors();
                model.addAttribute("errors",errors);
                return "/admin/AdminUpdateNextForOne";
            }
//            //转换为MD5编码
//            teacher.setTpassword(Md5Salt.md5(teacher.getTpassword()));
              adminManagerService.updateTeacherOne(teacher);
              redirect.addFlashAttribute("teacher",teacher);
        }

        redirect.addFlashAttribute("updateSuccess","更改成功,如下");
        redirect.addFlashAttribute("flag",flag);
        return "redirect:/admin/updateSuccessNext";
    }

    /**
     * 用来在实现了修改之后,需要跳到的页面,以此来查看修改后的结果
     * @return
     */
    @RequestMapping("/updateSuccessNext")
    public String updateSuccessNext() {
        return "/admin/AdminUpdateNextForOne";
    }
}
