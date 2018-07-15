package cn.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ssm.dao.OrdersMapper;
import cn.ssm.model.OrderItem;
import cn.ssm.model.Orders;
import cn.ssm.service.OrderItemService;
import cn.ssm.service.OrdersService;
@Service
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrdersMapper mapper;
	@Autowired
	private OrderItemService service;

	@Override
	public int saveOrder(Orders order) {
		int num = mapper.saveOrder(order);
		//保存订单的订单项
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			service.saveOrderItem(orderItem);
		}
		return num;
	}

	@Override
	public List<Orders> findOrdersList(Integer id) {
		return mapper.findOrdersList(id);
	}


}
