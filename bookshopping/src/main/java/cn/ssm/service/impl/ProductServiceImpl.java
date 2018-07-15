package cn.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ssm.dao.ProductMapper;
import cn.ssm.dao.UsersMapper;
import cn.ssm.model.PageBean;
import cn.ssm.model.Products;
import cn.ssm.model.Users;
import cn.ssm.service.ProductService;
import cn.ssm.service.UsersService;
import cn.ssm.utils.MailUtils;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper productMapper;

	@Override
	public int saveProduct(Products pro) {
		// TODO Auto-generated method stub
		return productMapper.saveProduct(pro);
	}

	@Override
	public int updateProduct(Products pro) {
		// TODO Auto-generated method stub
		return productMapper.updateProduct(pro);
	}

	@Override
	public int delProduct(String id) {
		// TODO Auto-generated method stub
		return productMapper.delProduct(id);
	}

	@Override
	public Products findProductById(String id) {
		// TODO Auto-generated method stub
		return productMapper.findProductById(id);
	}

	
	@Override
	public List<Products> findProductList() {
		// TODO Auto-generated method stub
		return productMapper.findProductList();
	}

	@Override
	public List<Products> findProductListByType(String type,PageBean page) {
		// TODO Auto-generated method stub
		return productMapper.findProductListByType(type,page);
	}

	@Override
	public Integer findCount() {
		// TODO Auto-generated method stubs
		return productMapper.findCount();
	}

	@Override
	public List<Products> findProductListPage(PageBean page) {
		// TODO Auto-generated method stub
		return productMapper.findProductListPage(page);
	}

	@Override
	public int findCountByType(String type) {
		// TODO Auto-generated method stub
		return productMapper.findCountByType(type);
	}
	
	

}
