package cn.graduate.subject.controller;

import cn.graduate.subject.pojo.College;
import cn.graduate.subject.pojo.Grade;
import cn.graduate.subject.pojo.Subject;
import cn.graduate.subject.pojo.User;
import cn.graduate.subject.service.AdminService;
import cn.graduate.subject.utils.StatusUtils;
import cn.graduate.subject.vo.UserAndSuject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/admin" ,produces = "application/json;charset=utf-8")
public class AdminController {

    @Autowired
    AdminService adminService;
    /**
     * 对选题的操作
     */
    /**
     * 添加
     *
     * @param subject
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addSubject")
    @ResponseBody
    @RequiresRoles(value = "admin")
    public String addSubject(Subject subject) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        if (subject == null) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.IS_NULL);
            return JSON.toJSONString(map);
        }
        int result = adminService.addSubject(subject);
        if (result > 0) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_SUCCESS);
        } else {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_FAILURE);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除一个题目
     *
     * @param sid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delSubject")
    @ResponseBody
    @RequiresRoles(value = "admin")
    public String delSubject(int sid) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        int result = adminService.delSubject(sid);
        if (result > 0) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.DEL_SUCCESS);
        } else {
            map.put(StatusUtils.STATUSCODE, StatusUtils.DEL_FAILURE);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 更新一个题目
     *
     * @param subject
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateSubject")
    @ResponseBody
    @RequiresRoles(value = "admin")
    public String updateSuject(Subject subject) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        int result = adminService.updateSubject(subject);
        if (result > 0) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_SUCCESS);
        } else {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_FAILURE);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 查找全部的题目信息
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectAllSubject/{page}")
    @ResponseBody
    @RequiresRoles(value = {"admin","user"} , logical = Logical.OR )
    public String selectAllSubject(@PathVariable int page) throws Exception {
        PageHelper.startPage(page, StatusUtils.PAGE_SIZE);

        List<Subject> subjects = adminService.selectAllSubject();

        PageInfo<Subject> pageInfo = new PageInfo<Subject>(subjects);
        if (subjects != null && subjects.size() > 0) {
            //不返回多余的
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            HashMap<String, Integer> map = new HashMap<>();
            map.put(StatusUtils.STATUSCODE, StatusUtils.FIND_FAILURE);
            return JSON.toJSONString(map);
        }

    }

    /**
     * 根据编号查找指定的一个题目
     *
     * @param sid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectSubjectById")
    @ResponseBody
    @RequiresRoles(value = {"admin","user"} , logical = Logical.OR )
    public String selectSubjectById(int sid) throws Exception {

        Subject subject = adminService.selectSubjectById(sid);
        if (subject == null) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put(StatusUtils.STATUSCODE, StatusUtils.FIND_FAILURE);
            return JSON.toJSONString(map);
        } else {
            return JSON.toJSONString(subject);
        }
    }

    /**
     * 根据名称 模糊查找题目
     * @param sname
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectSubjectByName/{page}")
    @ResponseBody
    @RequiresRoles(value = {"admin","user"} , logical = Logical.OR )
    public String selectSubjectByName(String sname, @PathVariable int page) throws Exception {
//使用page插件 ,进行封装
        PageHelper.startPage(page, StatusUtils.PAGE_SIZE);
        List<Subject> subjects = adminService.selectSubjectByName(sname);
        PageInfo<Subject> pageInfo = new PageInfo<Subject>(subjects);
        if (subjects != null && subjects.size() > 0) {
            return JSON.toJSONString(pageInfo);
        } else {
            HashMap<String, Integer> map = new HashMap<>();
            map.put(StatusUtils.STATUSCODE, StatusUtils.FIND_FAILURE);
            return JSON.toJSONString(map);
        }
    }

    /**
     * 添加用户
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addUser")
    @ResponseBody
    @RequiresRoles(value = "admin")
    public String addUser( User user) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        if( user == null ) {
            map.put( StatusUtils.STATUSCODE , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        int result = adminService.addUser( user );
        if( result > 0 ) {
            map.put( StatusUtils.STATUSCODE , StatusUtils.UPDATE_SUCCESS );
        } else {
            map.put( StatusUtils.STATUSCODE , StatusUtils.UPDATE_FAILURE );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 删除用户 ,根据用户的编号
     * @param uid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delUser")
    @ResponseBody
    @RequiresRoles(value = "admin")
    public String delUser( int uid ) throws Exception {

        HashMap<String, Integer> map = new HashMap<>();

        int result = adminService.delUser( uid );
        if( result > 0 ) {
            map.put( StatusUtils.STATUSCODE , StatusUtils.DEL_SUCCESS );
        } else {
            map.put( StatusUtils.STATUSCODE , StatusUtils.DEL_FAILURE );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 更新用户
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateUser")
    @ResponseBody
    @RequiresRoles(value = "admin")
    public String updateUser( User user ) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        if( user == null ) {
            map.put( StatusUtils.STATUSCODE , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        if( user.getUname().equals("") ) {
            user.setUname(null);
        }
        if( user.getUaccount().equals("") ) {
            user.setUaccount(null);
        }
        if( user.getUsex().equals("") ) {
            user.setUsex(null);
        }
        if( user.getUemail().equals("") ) {
            user.setUemail(null);
        }
        int result = adminService.updateUser( user );
        if( result > 0 ) {
            map.put( StatusUtils.STATUSCODE , StatusUtils.UPDATE_SUCCESS );
        } else {
            map.put( StatusUtils.STATUSCODE , StatusUtils.UPDATE_FAILURE );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 根据多种条件查学生信息和学生的选课信息
     * @param user
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectUser/{page}")
    @ResponseBody
    @RequiresRoles(value = {"admin","user"} , logical = Logical.OR )
    public String selectUser( User user , @PathVariable int page) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        if( user == null ) {
            map.put( StatusUtils.STATUSCODE , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        //使用page插件 ,进行封装
        PageHelper.startPage(page, StatusUtils.PAGE_SIZE);
        List<UserAndSuject> userAndSujects = adminService.selectUserByMoreWays( user );

        PageInfo<UserAndSuject> pageInfo = new PageInfo<UserAndSuject>(userAndSujects);

        return JSON.toJSONString( pageInfo );
    }

    /**
     * 根据学院的cid来查找  班级的内容,用来做联动
     */
    @RequestMapping(value = "/selectGradeByCid")
    @ResponseBody
    @RequiresRoles(value = {"admin","user"} , logical = Logical.OR )

    public String selectGradeByCid( int cid ) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        List<Grade> grades = adminService.selectGradeByCid( cid );
        if(grades != null && grades.size() > 0 ) {
            return JSON.toJSONString( grades );
        }
         map.put( StatusUtils.STATUSCODE , StatusUtils.FIND_FAILURE );
        return JSON.toJSONString( map );
    }

    /**
     * 查找所有的专业
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectCollege")
    @ResponseBody
    @RequiresRoles(value = {"admin","user"} , logical = Logical.OR )
    public String selectCollege(  ) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        List<College> colleges = adminService.selectCollege();
        if(colleges != null && colleges.size() > 0 ) {
            return JSON.toJSONString( colleges );
        }
        map.put( StatusUtils.STATUSCODE , StatusUtils.FIND_FAILURE );
        return JSON.toJSONString( map );
    }

    @RequestMapping(value = "/sendEmail")
    @ResponseBody
    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    public String sendEmail( String content , String uaccount ) throws Exception {

        adminService.selectAndsendEmail( content , uaccount );
        HashMap<String, Integer> map = new HashMap<>();
        map.put( StatusUtils.STATUSCODE , StatusUtils.FIND_SUCCESS );
        return JSON.toJSONString( map );
    }


}
