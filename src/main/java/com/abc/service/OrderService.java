package com.abc.service;

import java.util.List;

import com.abc.domain.Item;
import com.abc.domain.Member;
import com.abc.domain.Order;

public interface OrderService {

	public int insertOrder(Order order);
	public List<Order> selectAllOrder(String id);
	public int paymentOrder(String id);
	public int plusOrder(Order order);
	public int minusOrder(Order order);
	public Order selectOneOrder(int orderNum);
	public int deleteOrder(Order order);
}
