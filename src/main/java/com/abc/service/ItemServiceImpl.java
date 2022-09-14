package com.abc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.ItemDAO;
import com.abc.domain.Item;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDAO iDao;
	
	@Override
	public int insertItem(Item item) {

		
		return iDao.insertItem(item);
	}

	@Override
	public List<Item> selectAllItem(String itemCategory) {
		// TODO Auto-generated method stub
		return iDao.selectAllItem(itemCategory);
	}

	@Override
	public Item selectOneItem(int itemNum) {
		
		
		return iDao.selectOneItem(itemNum);
	}


}
