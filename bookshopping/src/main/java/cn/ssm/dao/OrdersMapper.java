package cn.ssm.dao;


import java.util.List;

import cn.ssm.model.Orders;

public interface OrdersMapper {

	public int saveOrder(Orders order);

	public List<Orders> findOrdersList(Integer id);
	
	
}
