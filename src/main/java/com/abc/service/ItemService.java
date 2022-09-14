package com.abc.service;

import java.util.List;

import com.abc.domain.Item;

public interface ItemService {

	public int insertItem(Item item);
	public List<Item> selectAllItem(String itemCategory);
	public Item selectOneItem(int itemNum);
}
