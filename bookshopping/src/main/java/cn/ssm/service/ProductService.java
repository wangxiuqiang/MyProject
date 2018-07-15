package cn.ssm.service;
import java.util.List;

import cn.ssm.model.PageBean;
import cn.ssm.model.Products;

public interface ProductService {
	
	
		public int saveProduct(Products pro) ;
		public int updateProduct(Products pro);
		public int delProduct(String id);
		public Products findProductById(String id);
		public List<Products> findProductList();
		public List<Products> findProductListByType(String type, PageBean page);
		//商品总记录数
		public Integer findCount();
		public List<Products> findProductListPage(PageBean page);
		public int findCountByType(String type);
		
	}

