package com.abc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.domain.Member;
import com.abc.domain.Order;

@Mapper
public interface OrderDAO {

	public int insertOrder(Order order); 
	
	public List<Order> selectAllOrder(String id);
	public Order selectOneOrder(int orderNum);
	public int paymentOrder(String id);
	
	public int plusOrder(Order order);
	public int minusOrder(Order order);
	
	public int deleteOrder(Order order);
}
