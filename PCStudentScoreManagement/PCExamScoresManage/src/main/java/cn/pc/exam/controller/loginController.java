package cn.pc.exam.controller;


import cn.pc.exam.pojo.Admin;
import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;
import cn.pc.exam.service.Impl.LoginSelectServiceImpl;
import cn.pc.exam.service.LoginSelectService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
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
    public String login(LoginPo loginPo, Model model) {
        model.addAttribute("loginPo",loginPo);
        return "index";
    }

    /**
     * 和上面一样,用来将项目部署到tomcat后自己输入网址查看
     * @param loginPo
     * @param model
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/login ")
//     public String login(LoginPo loginPo, Model model) {
//        model.addAttribute("loginPo",loginPo);
//        return "index";
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
    public String success(LoginPo loginPo, Model model)  throws  Exception{


          System.out.println(loginPo.getSelectWhoIn());
//          return "success";
        if(loginPo.getSelectWhoIn().equals("admin")) {
            Admin admin= loginSelectService.queryAdminIDAndPassWd(loginPo.getId());
            if(admin != null  && loginPo.getId().equals(admin.getAname()) && loginPo.getPassword().equals(admin.getApassword())) {
                model.addAttribute("admin1","this an admin");
                model.addAttribute("loginPo" ,loginPo);
                model.addAttribute("admin",admin);
                return "success";
            }else {
                model.addAttribute("no", "no");
                return "index";
            }
        }else if(loginPo.getSelectWhoIn().equals("teacher") ){
            Teacher teacher= loginSelectService.queryTeacher(loginPo.getId());
            if(teacher != null && loginPo.getId().equals(teacher.getTid()) && loginPo.getPassword().equals(teacher.getTpassword())) {
                model.addAttribute("teacher1","this is teacher");
                model.addAttribute("teacher",teacher);
                return "success";
            }else {
                model.addAttribute("no", "no");
                return "index";
            }
        }else {
            Student student = loginSelectService.queryStudent(loginPo.getId());
            if(student != null && loginPo.getId().equals(student.getSid()) && loginPo.getPassword().equals(student.getSid())) {
                model.addAttribute("student1","this is student");
                model.addAttribute("student",student);
                return "success";
            }else {
                model.addAttribute("no", "no");
                return "index";
            }
        }
    }

}
