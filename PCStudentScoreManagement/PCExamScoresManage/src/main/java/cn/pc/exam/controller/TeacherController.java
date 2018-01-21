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

import javax.servlet.http.HttpServletRequest;
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

    /**
     *  通过老师的id查出来老师教授的班级和课程的List泛型,输出在下拉菜单中
     * @param model
     * @param Tid
     * @return
     * @throws Exception
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

    /**
     *
     * @param Tid   根据老师的id 调用上面insertScores的函数查出老师教授的课程和班级
     *
     * @param model
     * @param Gid
     * @param Cid cid和gid表示从页面传过来的课程和班级编号,通过这两个值
     *            查询某个班选择了该课程的同学
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectStudent/{Tid}")
    public String selectStudent(@PathVariable String Tid,
                                Model model, String Gid,String Cid) throws Exception{
        insertScores(model,Tid);
        model.addAttribute("studentList",teacherManagerService.selectStudent(Cid,Gid));
        model.addAttribute("gid",Gid);
        model.addAttribute("cid",Cid);
        return "/teacher/TeacherWriteScore";
    }

    /**
     *
     * @param model
     * @param Tid
     * @param Sid
     * @param cid
     * @param gid
     * @param score
     * 这五个值都由页面的url传过来,表示老师编号,学生,课程和班级
     *               编号,调用上面的函数用tid,cid,gid查出来课程和班级的LIst用于下拉菜单
     *               用sid ,cid作为条件将score作为值更新到学生的分数表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/writeSuccess/{Tid}/{Sid}/{cid}/{gid}")
    public String writeSuccess(Model model, @PathVariable String Tid
    , @PathVariable String Sid , @PathVariable String cid
            , @PathVariable String gid, String score) throws Exception{
        float s = 0;
        if(score != ""){
            s = Float.parseFloat(score);
        }else{
            s = 0;
        }

        teacherManagerService.insertScore(s,Sid,cid);
        //重新选择出来修改后页面显示出来
        selectStudent(Tid,model,gid,cid);
        return "teacher/TeacherWriteScore";
    }

    /**
     *    通过复选框进行多个的修改,使用for循环
     * @param model
     * @param Tid
     * @param cid
     * @param gid
     * @param array
     * @param score
     * @return
     * @throws Exception
     */
    @RequestMapping("/writeSuccessSome/{Tid}/{cid}/{gid}")
    public String writeSuccessSome(Model model,@PathVariable String Tid
            ,@PathVariable String cid
            , @PathVariable String gid,String[] array , String[] score) throws Exception{
        float[] s = {0};
        for (int i = 0; i < score.length; i++){
            s[i] = Float.parseFloat(score[i]);
            teacherManagerService.insertScore(s[i],array[i],cid);
        }
        selectStudent(Tid,model,gid,cid);
        return "/teacher/TeacherWriteScore";
    }

    /**
     * 查询前的页面选择
     * @param model
     * @param Tid
     * @return
     * @throws Exception
     */
    @RequestMapping("/noPassSelect/{Tid}")
    public String noPassSelect(Model model, @PathVariable String Tid) throws Exception{
        insertScores(model,Tid);
        model.addAttribute("Tid",Tid);
        return "/teacher/TeacherSelectNoPass";
    }

    /**
     * 不合格的查询
     * @param Tid
     * @param model
     * @param Cid
     * @return
     * @throws Exception
     */
    @RequestMapping("/noPassSelectResult/{Tid}")
    public String noPassSelectResult(@PathVariable String Tid, Model model,String Cid) throws  Exception{
        insertScores(model,Tid);
        model.addAttribute("studentNoPass" ,teacherManagerService.selectNoPassStudent(Cid));
        return "/teacher/TeacherSelectNoPass";
    }


}
