package cn.pc.exam.controller;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Grade;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.Impl.AdminManagerServiceImpl;
import cn.pc.exam.service.Impl.TeacherManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Target;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    AdminManagerServiceImpl adminManagerService;

    @Autowired
    TeacherManagerServiceImpl teacherManagerService;

    /**
     * 写入前的一步,将信息查出来,
     * @param model
     * @param id  传递需要的id,表示写入的哪个教师的
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePassword/{id}")
    public String updatePassword(Model model, @PathVariable String id) throws Exception{
        model.addAttribute("flag","update");
        TeacherExtend teacher = adminManagerService.queryTeacherForOne(id);
        model.addAttribute("teacherEx",teacher);
        return "/teacher/TeacherIndex";
    }

    /**
     *
     * @param id   id用来做条件
     * @param model
     * @param teacherExtend  页面传过来的类参数,需要写入的内容
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePasswordSuccess/{id}")
    public String updatePasswordSuccess(@PathVariable String id
            , Model model,TeacherExtend teacherExtend) throws Exception{
           if(teacherExtend.getTpassword().equals(teacherExtend.getNewPassword())){
               teacherManagerService.updatePassword(teacherExtend);
           }
           model.addAttribute("success","success");
           return "/teacher/TeacherPasswordSuccess";
    }


    /* 首先要找出所有和教师有关的信息,需要差多个表,比如,一个老师的授课表中有他
    *  教授的班级和课程号,可以根据这个查出这个班级的人,这个课程的名字,然后下拉菜单中出现的是这个
    *  老师教授的班级的列表,不会出现别的班级,课程方面也是出现教授的课程名,
    *  班级的数量和课程的数量不能给定,所以,需要从数据库中取出来,然后去添加,用List可以
    *  首先取出班级 ,返回值用扩展的teacherExtend类, 里面有list<tc> ,有list<course>
    *      有list<Grade> ,查出来的list可以赋值给teacherExtend中的list 用list表示这个老师可能教授多个课程或多个班,在tc中
    *      有多条记录,从而在课程和班级中就有多个
    *       select * from tc where teacherId = #{value} 传来String,传出TC  ,这里面的内容
    *       不会是完全一样的
    *       通过tc查course  ,但是查course使,可能出现条件中的id相同,要消去相同的行
    *       select * from course WHERE Cid In <foreach var="" item = "" start="" end="" >#{?.?}
    *      </foreach>
    *      tc查出来给下面查course和grade  ,然后返回这两个list,将其输出,达到可以通过班级或学科进行,成绩的录入
    *      尝试实现根据这两者进行成绩录入
    */
    @RequestMapping(value = "/insertScores/{Tid}")
    public String insertScores(Model model ,@PathVariable String Tid) throws Exception{
      List<Course> courseList =  teacherManagerService.selectTcAndCourse(Tid);
      List<Grade> gradeList = teacherManagerService.selectTcAndGrade(Tid);
      model.addAttribute("grade",gradeList);
      model.addAttribute("course",courseList);
      model.addAttribute("Tid",Tid);
        return "/teacher/TeacherWriteScore";
    }

    @RequestMapping(value = "/selectStudent/{Tid}")
    public String selectStudent(@PathVariable String Tid, Model model, String Gid,String Cid) throws Exception{
        insertScores(model,Tid);
        model.addAttribute("studentList",teacherManagerService.selectStudent(Cid,Gid));
        model.addAttribute("gid",Gid);
        model.addAttribute("cid",Cid);
        return "/teacher/TeacherWriteScore";
    }
}
