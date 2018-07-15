package cn.ssm.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import cn.ssm.model.Products;
import cn.ssm.service.ProductService;
import cn.ssm.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class CartController {
	@Autowired
	private ProductService service;
	//显示购物车页面
	@RequestMapping("showCart")
	public String showCart() {
		
		return "cart";
	}
	
	//加入购物车
	@RequestMapping("addCart")
	@ResponseBody
	public String addCart(String id,HttpServletRequest request) {
		//获取到要加入购物车的商品
		//Products product = service.findProductById(null);
		Products product = null;
		System.out.println(id+ "-------------");
		
		HttpSession session =request.getSession();
		//先从session中获取购物车对象
		Map<Products,Integer> cart = (Map<Products, Integer>) session.getAttribute("cart");
		if(cart == null) {
			
			//定义一个mapper集合来作为我们的购物车对象
			cart = new HashMap<Products,Integer>();
			product = service.findProductById(id);
			cart.put(product, 1);
			
		}
		else {
			
			//判断加入购物车的商品在购物车中是否存在
			Set<Products> KeySet = cart.keySet();
			product = ProductUtils.findProduct(KeySet, id);
			
			if(product == null) {
				product = service.findProductById(id);
				cart.put(product, 1);
			}
			else {
			cart.put(product, cart.get(product) + 1);
			}
			
		}
	
		//将购物车对象放到session
		
		session.setAttribute("cart", cart);
		System.out.println(session.getAttribute("cart"));
		return "{\"msg\":\"true\"}";
	}
	//修改购物车信息
	@RequestMapping("updateCart")
	public String updateCart(String id,Integer count,HttpServletRequest request) {
		//获取到购物车对象
		
		HttpSession session = request.getSession();
		
		Map<Products,Integer> cart = (Map<Products, Integer>) session.getAttribute("cart");
		
		Set<Products> keySet = cart.keySet();
		
		Products product = ProductUtils.findProduct(keySet, id);
		
		cart.put(product, count);
		
		if(count == 0) {
			
			cart.remove(product);
			
		}
		session.setAttribute("cart", cart);
		return"redirect:showCart";
		
	}
}
