package com.abc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abc.domain.Item;
import com.abc.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("product")
@Controller
public class ProductController {

	@Autowired
	private ItemService iService;
	
	
	// 메뉴창 띄우는 기능
	@GetMapping("/menu")
	public String menu(Model model, String category) {
		log.debug("menu() 실행");
		
		List<Item> item = iService.selectAllItem(category);
		
		model.addAttribute("item", item);
		
		return "productView/menu";	// templates의 productView의 menu.html
	}
	
	// 특정버거창 띄우는 기능
	@GetMapping("/menuDetail")
	public String menuDetail(Model model, int num) {
		log.debug("menuDetail() 실행");
		
		Item item = iService.selectOneItem(num);
		model.addAttribute(item);
		
		return "productView/menuDetail";	// templates의 productView의 menuDetail.html
	}
	
}
