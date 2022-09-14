package com.abc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.OrderDAO;
import com.abc.domain.Item;
import com.abc.domain.Member;
import com.abc.domain.Order;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderDAO oDao;
	

	@Override
	public int insertOrder(Order order) {
		// TODO Auto-generated method stub
		return oDao.insertOrder(order);
	}


	@Override
	public List<Order> selectAllOrder(String id) {
		// TODO Auto-generated method stub
		return oDao.selectAllOrder(id);
	}


	@Override
	public int paymentOrder(String id) {
		// TODO Auto-generated method stub
		return oDao.paymentOrder(id);
	}


	@Override
	public int plusOrder(Order order) {
		
		return oDao.plusOrder(order);
	}

	@Override
	public int minusOrder(Order order) {
		// TODO Auto-generated method stub
		return oDao.minusOrder(order);
	}

	@Override
	public Order selectOneOrder(int orderNum) {
		// TODO Auto-generated method stub
		return oDao.selectOneOrder(orderNum);
	}


	@Override
	public int deleteOrder(Order order) {
		// TODO Auto-generated method stub
		return oDao.deleteOrder(order);
	}





}
