package cn.ssm.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ssm.model.Users;
import cn.ssm.service.UsersService;
import cn.ssm.utils.DateUtils;
import cn.ssm.utils.Md5Utils;
import cn.ssm.utils.UUIDUtils;

@Controller
public class UsersController {
	@Resource
	private UsersService usersService;

	@RequestMapping("showReg")
	public String showReg(String type, Model model) {

		if (type != null && type.equals("1")) {
			model.addAttribute("msg", "两次密码不一致");
		} else if (type != null && type.equals("2")) {
			model.addAttribute("msg", "验证码错误");
		} else if (type != null && type.equals("3")) {
			model.addAttribute("msg", "注册失败");
		}

		return "reg";
	}

	@RequestMapping("reg")
	public String reg(Users user, String repassword, String checkcode, HttpServletRequest request) {
		user.setRole("user");

		user.setUpdatetime(DateUtils.format(new Date()));
		// service б

		// 对密码和确认密码进行比对
		String password = user.getPassword();
		if (!password.equals(repassword)) {
			return "redirect:showReg.action?type=1";
		}

		// 使用md5对密码加密
		user.setPassword(Md5Utils.md5(password));
		user.setState(0);// 0表示没有激活，1激活

		// 生成激活码
		String uuid = UUIDUtils.getUUID();
		user.setActivecode(uuid);

		// 验证验证吗是否正确
		// 从session中获取生成的验证吗

		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");

		// 拿用户输入的验证码与生成的验证码进行比较

		if (!checkcode_session.equals(checkcode)) {
			return "redirect:showReg.action?type=2";
		}
		int num = usersService.saveUsers(user);
		if (num == 0) {
			return "redirect:showReg.action?type=3";
		}
		// usersService.saveUsers(user);
		return "redirect:showLogin.action?type=1";
	}

	@RequestMapping("showLogin")
	public String showLogin(String type, Model model) {
		if ("1".equals(type)) {
			model.addAttribute("msg", "注册成功，请登录");
		} else if ("2".equals(type)) {
			model.addAttribute("msg", "用户名或密码错误");
		}
		else if("3".equals(type)) {
			model.addAttribute("msg", "没有登录，请登录");
		}
		return "login";
	}

	// 激活账户的方法
	@RequestMapping("activation")
	public String activation(String code) {
		// 根据激活码查询账户
		Users user = usersService.findUserByCode(code);
		// 将用户状态扎转为1
		user.setState(1);
		usersService.updateUsers(user);
		return "login";

	}

	@RequestMapping("checkName")
	@ResponseBody
	public String checkName(String username) {
		// 根据用户名查询是否存在该用户名
		System.out.println(username);
		Users user = usersService.findUserByName(username);
		// 当对象不为空 说明用户名存在
		if (user != null) {
			// 返回flase 表示用户名存在
			return "{\"msg\":\"false\"}";
		}
		return "{\"msg\":\"true\"}";

	}

	@RequestMapping("login")
	public String login(Users user, String remember, String autologin, HttpServletResponse resp,HttpServletRequest request) throws UnsupportedEncodingException {
		// 加密
		
			user.setPassword(Md5Utils.md5(user.getPassword()));

			Users users = usersService.login(user);

			if (users == null) {
				return "redirect:showLogin?type=2";
			}
			// 记录用户
			// 判断用户是否勾选记住用户
			if (remember != null && remember.equals("on")) {
				// 将用户名以cookie的形式发送到客户端
				Cookie cookie;
				cookie = new Cookie("usersname", URLEncoder.encode(users.getUsername(), "utf-8"));
				cookie.setMaxAge(60 * 60 * 24 * 7);
				resp.addCookie(cookie);
				Cookie cookie1 = new Cookie("save", "on");
				cookie1.setMaxAge(60 * 60 * 24 * 7);
				resp.addCookie(cookie1);
			} 
			else {
				Cookie cookie2 = new Cookie("usersname", "");
				// 设置cookie的存活时间，单位s 存活7天
				cookie2.setMaxAge(0);
				resp.addCookie(cookie2);
				Cookie cookie3 = new Cookie("save", "");
				cookie3.setMaxAge(0);
				resp.addCookie(cookie3);
			}

			// 自动登录
			if (autologin != null && autologin.equals("on")) {
				Cookie cookie = new Cookie("autologin",URLEncoder.encode(users.getUsername(), "utf-8")+"-"+users.getPassword());
				cookie.setMaxAge(60 * 60 * 24 * 7);
				resp.addCookie(cookie);
			}
			//将登录的用户存放到session中
		

		// 设置cookie的存活时间，单位s 存活7天
			request.getSession().setAttribute("user", users);
			//判断登录用户色
			//如果是admin则跳转到管理员界面
			if(users.getRole().equals("admin")) {
				
				return "redirect:showAdminIndex";
			}
		return "redirect:showIndex";
	}
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,HttpServletResponse resp) {
		HttpSession session = request.getSession();
		//删除session中存放的登陆对象
		//session.removeAttribute("user");
		
		//销毁session
		session.invalidate();
		
		//去除自动登陆的功能
		//将自动登录cookie中的信息清空
		Cookie cookie = new Cookie("autologin","");
		
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
	
		return"redirect:showIndex";
	}
}
