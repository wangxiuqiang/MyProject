package cn.ssm.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import cn.ssm.model.PageBean;
import cn.ssm.model.Products;
import cn.ssm.service.ProductService;
import cn.ssm.utils.UUIDUtils;
import cn.ssm.utils.UploadUtils;

@Controller

public class ProductController {
	//打开商品管理界面
	@Autowired
	private ProductService service;
	@RequestMapping("showProductList")
	public String showProductList(Model model) {
		//查询所有的商品
		List<Products> productsList = service.findProductList();
		
		//将查询到的商品传递到页面中展示
		//想当于request
		model.addAttribute("productsList", productsList);
		
		return "admin/product";
	}
	//打开添加商品页面
	@RequestMapping("showAddProduct")
	public String showAddProduct() {
		return "admin/addProduct";
	}
	//添加商品
	@RequestMapping("saveProduct")
	public String saveProduct(Products pro,MultipartFile imgpic) throws IOException {
		//将上传的文件上传到服务器
		
		//获取上传文件的路径
		
		//将路径保存到数据库
		
		String upload = UploadUtils.upload(imgpic);
		//jpg,png,gif
		//将路径保存到数据库
		pro.setId(UUIDUtils.getUUID());
		pro.setImgurl(upload);
		pro.setState(1);
		service.saveProduct(pro);
	
		System.out.println(pro);
		System.out.println(imgpic);
		
		return "redirect:showProductList";
	}
	//删除商品
	@RequestMapping("delProduct")
	public String delProduct(String id) {
		System.out.println(id);
		
		//调用service进行删除
		
		service.delProduct(id);
		
		return "redirect:showProductList";
	}

	//打开修改商品页面
	@RequestMapping("showeditProduct")
	public String showeditProduct(String id,Model model) {
		//查询要修改的商品
		Products product = service.findProductById(id);
		
		//将商品传到页面展示数据
		model.addAttribute("product", product);
		return "admin/editproduct";
	}
	//保存修改商品
	@RequestMapping("updateProduct")
	public String updateProduct(Products pro,MultipartFile imgpic) throws IOException {
		
		//判断是否有上传文件
		
		
		if(!imgpic.getOriginalFilename().equals("")&& imgpic.getOriginalFilename()!=null) {
			
			String upload = UploadUtils.upload(imgpic);
			
			pro.setImgurl(upload);
		}
		
		service.updateProduct(pro);
		
		return "redirect:showProductList";
	}
	//打开商品展示的页面
	@RequestMapping("showproductkinds")
	public String showproductkinds(String type,Model model,Integer page) {
		
		int count = service.findCountByType(type);
		
		PageBean pageBean = new PageBean(8,page,count);
		
		
		//查询要显示的类型的商品
		List<Products> productsList = service.findProductListByType(type,pageBean);
		
		System.out.println(productsList);
		
		//将商品传递到页面中展示
		model.addAttribute("productsList",productsList);
		
		model.addAttribute("type",type);
		
		model.addAttribute("pageBean",pageBean);
		
		return "productkinds";
	}
	//查看商品详细
	@RequestMapping("showproductinfo")
	
	public String showproductinfo(String id,Model model) {
		
		Products product = service.findProductById(id);
		
		model.addAttribute("product",product);
		
		return"productinfo";
	}
}
