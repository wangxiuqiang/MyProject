package cn.ssm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.ssm.model.PageBean;
import cn.ssm.model.Products;

public interface ProductMapper {
	public int saveProduct(Products pro) ;
	public int updateProduct(Products pro);
	public int delProduct(String id);
	public Products findProductById(String id);
	public List<Products> findProductList();
	public List<Products> findProductListByType(@Param(value = "type") String type, @Param(value = "page") PageBean page);
	public Integer findCount();
	public List<Products> findProductListPage(PageBean page);
	public int findCountByType(String type);
	
}
