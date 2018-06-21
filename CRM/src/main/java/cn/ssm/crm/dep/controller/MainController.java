package cn.ssm.crm.dep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
  
	@RequestMapping("/")
	public String login()  {
		return "/pages/login";
	}
	
	@RequestMapping( value = "/frame")
	public String frame() {
		return "/pages/frame";
	}
}
