package cn.ssm.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
	public static Cookie getCookie(Cookie cookies[],String name) {
		if(cookies == null  || cookies.length==0) {
			return null;
		}
	
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(name)) {
				return cookie;
			}
		}
		
		return null;
	}

}
