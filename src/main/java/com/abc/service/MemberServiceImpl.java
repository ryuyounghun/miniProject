package com.abc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abc.dao.MemberDAO;
import com.abc.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	
	// 데이터베이스를 컨트롤하기 위한 MemberDAO 객체
	@Autowired
	private MemberDAO mDao;
	
	// SecurityConfig에 있는 비밀번호를 암호화하기 위한 객체
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public int insertMember(Member member) {
		
		log.debug("insertMember() 실행");
		log.debug("Member 객체 : {}", member);
		
		// Member 객체에 평문으로 들어있는 비번을 암호화 한다
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		// 암호화된 비번을 다시 Member객체에 설정
		member.setMemberPw(encodedPassword);
		
		return mDao.insertMember(member);
	}

	@Override
	public Member selectOneMember(String id) {
		
		log.debug("selectOneMember() 실행");
		log.debug("id : {}", id);
		
		return mDao.selectOneMember(id);
	}

	@Override
	public int updateMember(Member member) {
		// 현재 Member 객체의 비밀번호(평문)
		String pw = member.getMemberPw();
		
		String encodePw = passwordEncoder.encode(pw); // 원래 객체에 들어있는 평문을 넣어준다 패스워드인코더함수에?
		
		member.setMemberPw(encodePw); // pw는 평문이여서 그냥 넣어주면 안되고 인코드Pw를 넣으면 된다.
		
		// 비밀번호를 암호화한 member 객체 전송
		return mDao.updateMember(member); 
		
	}

}
