package com.abc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abc.domain.Member;
import com.abc.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

	@Autowired
	private MemberService mService;
	
	
	// 메인화면(index.html) 열기
		@GetMapping("/")
		public String index(Model model) {
			log.debug("index() 실행");
			
			
			return "index";
		}
	
	// 매장 찾기 호출
	@GetMapping("/findStore")
	public String findStore(Model model,
			@AuthenticationPrincipal UserDetails user) {
		
		if (user != null) {
			String id = user.getUsername();
			
			Member member = mService.selectOneMember(id);
			
			model.addAttribute("member", member);
		}
		
		
		return "findStore";
	}
	
	// 관리자 페이지 호출
	@GetMapping("/manager")
	public String manager() {
		
		return "manager";
	}
	
	// 마이 페이지 호출
	@GetMapping("/myPage")
	public String myPage() {
		
		return "myPage";
	}
	
	
}
