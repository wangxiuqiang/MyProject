package cn.ssm.utils;

import java.util.Set;

import cn.ssm.model.Products;

public class ProductUtils {

	
	public static Products findProduct(Set<Products> set,String id) {
		
		for(Products products : set) {
			if(products.getId().equals(id)) {
			
				return products;
				
			}
		}
		
		return null;
	}
}
