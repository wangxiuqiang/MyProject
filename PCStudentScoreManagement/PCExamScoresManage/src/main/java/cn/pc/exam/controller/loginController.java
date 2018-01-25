package cn.pc.exam.controller;


import cn.pc.exam.md5.Md5Salt;
import cn.pc.exam.pojo.Admin;
import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;
import cn.pc.exam.service.LoginSelectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/login") //正式使用的时候打开
public class loginController {

    @Autowired
    LoginSelectService loginSelectService ;
    //调用service方法来调用mapper函数中的sql语句

    /**
     * 通过对根地址进行访问,用于测试使用,
     * @param loginPo  loginPo是一个登录类,用来接收id和password和radio组件传过来的
     *                 哪种用户进行登录
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String loginIndex(LoginPo loginPo, Model model) {
        model.addAttribute("loginPo",loginPo);
        return "/login/index";
    }

    @RequestMapping(value = "/login")
    public String login(LoginPo loginPo, Model model) {
        model.addAttribute("loginPo",loginPo);
        return "/login/index";
    }

    /**
     * 和上面一样,用来将项目部署到tomcat后自己输入网址查看
     * @param loginPo
     * @param model
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/index ")
//     public String login(LoginPo loginPo, Model model) {
//        model.addAttribute("loginPo",loginPo);
//        return "/login/index";
//}

    /**
     * 登录检测,登录成功就返回登录成功后的主页面,失败就显示登录页面,并提示错误信息
     * @param loginPo  index页面传来的id和password,用来和数据库中的进行比较
     *                 得出是否让其进行登录 ,admin teacher  student 都是从数据库查出来的数据
     * @param model
     * @return
     * @throws Exception
     * 首先比较loginPo中的selectWhoIn确定哪种用户进行登录,进行相应的代码块
     * 在比较数据库查出来的内容是否为空,并且是否和传过来的内容相符,并执行相应的代码块
     */
    @RequestMapping(value = "/success")
    public String loginSuccess(LoginPo loginPo, Model model)  throws  Exception{

        String password = Md5Salt.md5(loginPo.getPassword());

//          return "success";
        if(loginPo.getSelectWhoIn().equals("admin")) {
            Admin admin= loginSelectService.queryAdminIDAndPassWd(loginPo.getId());

            if(admin != null  && loginPo.getId().equals(admin.getAname()) && password.equals(admin.getApassword())) {
                model.addAttribute("admin1","this an admin");
                model.addAttribute("loginPo" ,loginPo);
                model.addAttribute("admin",admin);
                return "/admin/AdminSelect";
            }else {
                model.addAttribute("no", "no");
                return "/login/index";
            }
        }else if(loginPo.getSelectWhoIn().equals("teacher") ){
            Teacher teacher= loginSelectService.queryTeacher(loginPo.getId());
            if(teacher != null && loginPo.getId().equals(teacher.getTid()) && password.equals(teacher.getTpassword())) {
                model.addAttribute("teacher1","this is teacher");
                model.addAttribute("teacher",teacher);
                return "/teacher/TeacherIndex";
            }else {
                model.addAttribute("no", "no");
                return "/login/index";
            }
        }else {
            Student student = loginSelectService.queryStudent(loginPo.getId());
            if(student != null && loginPo.getId().equals(student.getSid()) && password.equals(student.getSpassword())) {
                model.addAttribute("student1","this is student");
                model.addAttribute("student",student);
                return "/student/StudentIndex";
            }else {
                model.addAttribute("no", "no");
                return "/login/index";
            }
        }
    }

}
