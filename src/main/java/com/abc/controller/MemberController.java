package com.abc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abc.domain.Member;
import com.abc.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {

	// Service 객체 생성할 것
	// 인터페이스는 객체 생성은 불가능하지만 변수의 타입으로는 사용될 수 있음
	@Autowired
	private MemberService mService;
	
	// join.html 회원가입 페이지를 띄우는 기능
	@GetMapping("/join")
	public String join() {
		log.debug("join() 실행");
		return "memberView/join";	// templates의 memberView의 join.html
	}
	
	// 회원가입을 실행하는 기능
	@PostMapping("/join")
	public String join(Member member) {
		log.debug("member의 정보 : {}", member);
		
		// service에 Member 객체 전송
		int result = mService.insertMember(member);
		
		return "redirect:/";	// localhost:8888/ 회원가입이 성공하면 index 페이지로 감
	}
	
	// ID 중복체크 창을 띄우는 기능
	@GetMapping("/idCheck")
	public String idCheck() {
		log.debug("idCheck() 실행");
		return "memberView/idChkForm";
	}
	
	// ID 중복체크를 실행하는 기능
	@PostMapping("/idCheck")
	public String idCheck(String searchId, Model model) {
		log.debug("searchId : {}", searchId);
		
		// DB에서 ID를 조회한 결과
		Member searchedMember = mService.selectOneMember(searchId);
		
		log.debug("검색 결과 : {}", searchedMember);
		
		// 검색 결과가 없으면 true, 있으면 false
		boolean  result = searchedMember == null ? true : false;
		
		log.debug("id 사용가능여부 : {}", result);
		
		model.addAttribute("searchId", searchId);
		model.addAttribute("result", result);
		
		return "memberView/idChkForm";
	}
	
	// 로그인 폼을 띄우는 기능
	@GetMapping("/login")
	public String login() {
		log.debug("login() 실행");
		
		return "memberView/login";
	}
	
	// 실제 로그인 기능을 WebSecurityConfig 클래스에서 진행
	
	// 회원정보 수정 화면 띄우기
	@GetMapping("/update")
	public String updateInfo(@AuthenticationPrincipal UserDetails user, Model model) {
		
		// 사용자 식별자
		String memberId = user.getUsername();
		
		log.debug("memberId : {}", memberId);
		
		Member member = mService.selectOneMember(memberId);
		
		log.debug("member : {}", member);
	
		
		model.addAttribute("member", member);
		
		return "memberView/updateInfo";
	}
	
	

	// 회원정보 수정
	@PostMapping("/update")
	public String update(Member member, 
			@AuthenticationPrincipal UserDetails user) { // 지금 로그인 되어있는 객체를 가져오는 @AuthenticationPrincipal
	
		log.debug("member : {}" , member);
		log.debug("username : {}", user.getUsername());
		member.setMemberId(user.getUsername()); 
		int result = mService.updateMember(member);
		log.debug("result : {}", result);
		
		return "redirect:/";
	}
	
}
