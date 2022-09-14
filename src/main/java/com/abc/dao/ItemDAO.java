package com.abc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.domain.Item;

@Mapper
public interface ItemDAO {

	public int insertItem(Item item);
	public List<Item> selectAllItem(String itemCategory);
	public Item selectOneItem(int itemNum);
}
