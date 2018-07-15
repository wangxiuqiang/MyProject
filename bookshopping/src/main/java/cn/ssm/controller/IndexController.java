package cn.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ssm.model.PageBean;
import cn.ssm.model.Products;
import cn.ssm.service.ProductService;

@Controller
public class IndexController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("showIndex")
	public String showIndex(Model model,Integer page,String type) {
		
		
//		//查询商品
//		
//		List<Products> productsList = productService.findProductList();
//		//将查询到的商品在首页面中展示
//		
//		model.addAttribute("productsList", productsList);
		
//		return "index";
		return showIndex1(model,page,type);
		
		
	}
	@RequestMapping("/")
	public String showIndex1(Model model,Integer page,String type) {
		
		
		//查询商品
		
		//获取商品的总记录数
		
		Integer count = productService.findCount();
		
		//创建pageBean的对象
		
		PageBean pageBean = new PageBean(8,page,count);
		
		model.addAttribute("pageBean", pageBean);
		
		//调用具有分页功能的页面
		
		List<Products> productsList = productService.findProductListPage(pageBean);
		
		//将查询到的商品在首页面中展示
		model.addAttribute("productsList", productsList);
		
		
		
		return "index";
		
		
	}
	
	
	
	
	
	//打开后台管理页面
	@RequestMapping("showAdminIndex")
	public String showAdminIndex() {
		
		
		return "admin/index";
		
	}
	
	
	
	
}
