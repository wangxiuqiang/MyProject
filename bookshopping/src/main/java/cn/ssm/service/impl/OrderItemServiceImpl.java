package cn.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ssm.dao.OrderItemMapper;
import cn.ssm.model.OrderItem;
import cn.ssm.service.OrderItemService;
@Service
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
	private OrderItemMapper mapper;
	@Override
	public int saveOrderItem(OrderItem item) {
		return mapper.saveOrderItem(item);
	}

}
