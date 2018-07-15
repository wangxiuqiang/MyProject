package cn.ssm.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ssm.dao.UsersMapper;
import cn.ssm.model.Users;
import cn.ssm.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.sun.glass.ui.Application;

/**
 * filter过滤器 每一个过滤器都应该实现filter接口 可以使用过滤器拦截所有的请求，对请求进行验证，如果请求合法，就放行，否则拦截
 * 
 * @author Administrator
 * 权限的控制，登陆认证，日志
 *
 */
public class AutoLoginFilter implements Filter {

	// private UsersService usersService = new UsersServiceImpl();

	private UsersMapper userMapper;

	//在对象被销毁时调用的方法
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// 验证代码
		System.out.println("拦截到用户的请求");
		// 向下转型
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 获取用户的请求路径
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();

		// 对于登录的请求，注册的请求不需要自动登录
		if (uri.indexOf("login") == -1 && uri.indexOf("Login") == -1 && uri.indexOf("reg") == -1
				&& uri.indexOf("Reg") == -1) {
			// 获取session
			HttpSession session = request.getSession();
			// 获取session中登录的对象
			Users users = (Users) session.getAttribute("user");
			System.out.println(users);

			// 判断用户是否登录
			// 如果users为null，说明没有登录
			if (users == null) {
				// 自动登录
				//获取所有 的cookie
				Cookie[] cookies = request.getCookies();
				//获取需要使用的cookie
				Cookie cookie = CookieUtils.getCookie(cookies, "autologin");
				//不为null  说明有登陆的信息
				if (cookie != null) {
					//获取cookie中用户的信息
					String autoLogin = cookie.getValue();
					// 判断cookie是否有信息
					if (autoLogin != null && !autoLogin.equals("")) {
						//以-为分隔符来获取用户名和密码
						String[] names = autoLogin.split("-");
						//将登陆信息封装到对象中
						Users user = new Users();
						user.setUsername(URLDecoder.decode(names[0], "utf-8"));
						
						user.setPassword(names[1]);
						// 调用Service进行登录
						// Users user2 = usersService.login(user);
						Users users2 = userMapper.login(user);
						// 将登录的对象存放到session
						session.setAttribute("user", users2);
					}
				}
				else {
					//如果请求的是与订单相关的请求，要求用户必须登录
					if(uri.indexOf("order")!=-1||uri.indexOf("Order")!=-1) {
						//跳转到登录页面
						//重定向的方式
						response.sendRedirect("showLogin?type=3");
					//请求转发
					//request.getRequestDispatcher("").forward(request, response);
					//forword与Redirect
						
					
					}
					
				}
			}
		}
		// 对请求放行
		chain.doFilter(req, resp);
	}
    //创建对象时会自动调用init方法对对象初始化
	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//获取到spring容器
		//serveletcontext代表整个web应用
		ServletContext context = config.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
		userMapper = ac.getBean(UsersMapper.class);
	}

}
