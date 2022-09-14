package com.abc.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abc.domain.Item;
import com.abc.domain.Member;
import com.abc.domain.Order;
import com.abc.service.ItemService;
import com.abc.service.MemberService;
import com.abc.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("item")
@Controller
public class ItemController {

	@Autowired
	private ItemService iService;

	@Autowired
	private OrderService oService;
	
	@Autowired
	private MemberService mService;
	
	// 상품정보등록 창 호출
	@GetMapping("/inputItem")
	public String inputItem() {

		return "/itemView/inputItem";
	}

	// 상품정보등록 DB 저장
	@PostMapping("/inputItem")
	public String inputItem(Item item) {
		log.debug("inputItem() 실행");

		
		// item 객체를 insert 쿼리에 등록
		int result = iService.insertItem(item);

		return "redirect:/";
	}

	
	// 배달하기 창 호출
	@GetMapping("/buyItem")
	public String buyItem(Model model, String category,
			@AuthenticationPrincipal UserDetails user) {

		List<Item> iList = null;
		
		// 등록 되어 있는 모든 정보를 리스트에 등록
		if (category == null) {
			iList = iService.selectAllItem(category);
			
		} else {
			iList = iService.selectAllItem(category);
		}
		
		String id = user.getUsername();
		
		Member member = mService.selectOneMember(id);

		log.debug("category 번호 : {}", category);
		
		log.debug("iList() size : {}", iList.size());
		
		model.addAttribute("member", member);
		
		model.addAttribute("category", category);
		
		model.addAttribute("itemList", iList);

		return "/itemView/buyItem";
	}

	// 상품 정보 주문 창에 DB 저장
	@PostMapping("/buyItem")
	public String buyItem(Order order,
			@AuthenticationPrincipal UserDetails user) {

		log.debug("buyItem() 시작");
		
		order.setMemberId(user.getUsername());
		
		log.debug("order : {}", order);
		
		int result = oService.insertOrder(order);
		
		return "redirect:./endOrder";
		//return "redirect:./endOrder";
	}

	
	// 주문(카트) 창 호출
	@GetMapping("/orderItem")
	public String orderItem(Model model,
			@AuthenticationPrincipal UserDetails user) {

		log.debug("orderItem 실행");
		
		String id = user.getUsername();
		
		// order List에 주문 정보 다 등록하기
		List<Order> oList = oService.selectAllOrder(id);

		log.debug("oList : {}", oList);

		// html에서 orderList를 보여주기 위해 model에 등록
		model.addAttribute("orderList", oList);

		// 만약 주문(카트) 창이 비어있지 않다면 합계금액 등록하기
		if (!oList.isEmpty()) {
			int result = oService.paymentOrder(id);
			
			// html에서 합계금액 출력하기 위해 model에 등록
			model.addAttribute("allPay", result);
			log.debug("총액 : {}", result);
		} 


		return "/itemView/orderItem";
	}
	
	// 배달하기 창에서 주문 추가하기
	@GetMapping("/plusUp")
	public String plusUp(int orderNum,
			@AuthenticationPrincipal UserDetails user) {
		log.debug("plusUpdate() 시작");
		
		String id = user.getUsername();
		
		List<Order> oList = oService.selectAllOrder(id);
		
		log.debug("oList의 : {}", oList);
		
		Order order;
		
		for (int i = 0; i < oList.size(); i++) {
			log.debug("for문 시작");
			if (oList.get(i).getOrderNum() == orderNum) {
				order = oList.get(i);
				log.debug("order체크 : {}", order);
				
				int result = oService.plusOrder(order);
			} 
		}
		
		// order 객체 쿼리에 넣어서 주문 상품 개수 추가하기
		
		return "redirect:./endOrder";
	}
	
	// 주문(카트) 창에서 상품 추가하기
	@GetMapping("/plusUpdate")
	public String plusUpdate(int orderNum,
			@AuthenticationPrincipal UserDetails user) {
		log.debug("plusUpdate() 시작");
		
		String id = user.getUsername();
		
		List<Order> oList = oService.selectAllOrder(id);
		
		log.debug("oList의 : {}", oList);
		
		Order order;
		
		for (int i = 0; i < oList.size(); i++) {
			log.debug("for문 시작");
			if (oList.get(i).getOrderNum() == orderNum) {
				order = oList.get(i);
				log.debug("order체크 : {}", order);
				
				oService.plusOrder(order);
			} 
		}
		
		return "redirect:./orderItem";
	}

	// 주문(카트) 창에서 상품 마이너스하기
	@GetMapping("/minusUpdate")
	public String minusUpdate(int orderNum,
			@AuthenticationPrincipal UserDetails user) {
		log.debug("minusUpdate() 실행");
		
		String id = user.getUsername();
		
		
		List<Order> oList = oService.selectAllOrder(id);
		
		log.debug("oList의 : {}", oList);
		
		Order order;
		
		for (int i = 0; i < oList.size(); i++) {
			log.debug("for문 시작");
			if (oList.get(i).getOrderNum() == orderNum) {
				order = oList.get(i);
				log.debug("order체크 : {}", order);
				
				if (order.getQuantity() > 1) {
					oService.minusOrder(order);
				}
			} 
		}
		
		return "redirect:./orderItem";
	}
	
	// 주문(카트) 창에 같은 상품 있는지 체크하기 창 호출
	@GetMapping("/itemCheck")
	public String itemCheck(Model model, int num,
			@AuthenticationPrincipal UserDetails user) {
		
		// 상품 번호 받아와서 item 객체에 등록
		Item itCk = iService.selectOneItem(num);
		
		log.debug("itemCheck 의 결과 : {}", itCk);
		
		// html에서 사용해야 하니 model에 등록
		model.addAttribute("itCk", itCk);
		
		String id = user.getUsername();
		
		List<Order> oList = oService.selectAllOrder(id);
		
		log.debug("oList의 : {}", oList);
		
		Order order = null;
		
		for (int i = 0; i < oList.size(); i++) {
			log.debug("for문 시작");
			if (oList.get(i).getOrderNum() == num) {
				order = oList.get(i);
				log.debug("order체크 : {}", order);
			}
		}
		model.addAttribute("checkOrders", order);
		
		
		return "/itemView/itemCheck";
	}
	
	// 주문(카트) 창에서 상품 삭제하기
	@GetMapping("/deleteOrder")
	public String deleteOrder(int orderNum,
			@AuthenticationPrincipal UserDetails user) {
		log.debug("deleteOrder() 실행");
		
		String id = user.getUsername();
		
		List<Order> oList = oService.selectAllOrder(id);
		
		log.debug("oList의 : {}", oList);
		
		Order order;
		
		for (int i = 0; i < oList.size(); i++) {
			log.debug("for문 시작");
			if (oList.get(i).getOrderNum() == orderNum) {
				order = oList.get(i);
				log.debug("order체크 : {}", order);
				
				oService.deleteOrder(order);
			} 
		}
		
		
		return "redirect:./orderItem";
	}
	
	// 주문 확인창 이후 호출창
	@GetMapping("/endOrder")
	public String endOrder() {
		return "/itemView/endOrder";
	}
	
	// 결제창 호출
	@GetMapping("/finalOrder")
	public String finalOrder(Model model,
			@AuthenticationPrincipal UserDetails user) {
		
		String id = user.getUsername();
		
		log.debug("id : {}", id);
		
		Member member = mService.selectOneMember(id);
		
		log.debug("member : {}", member);
		model.addAttribute("member", member);
		
		List<Order> oList = oService.selectAllOrder(id);
		
		model.addAttribute("oList", oList);
		
		
		if (!oList.isEmpty()) {
			int result = oService.paymentOrder(id);
			
			// html에서 합계금액 출력하기 위해 model에 등록
			model.addAttribute("allPay", result);
			log.debug("총액 : {}", result);
		} 
		
		return "/itemView/finalOrder";
	}
	
	// 결제 완료 팝업
	@GetMapping("/endPay")
	public String endPay() {
		
		return "/itemView/endPay";
	}
	
}
