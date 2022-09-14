package com.abc.service;

import com.abc.domain.Member;

public interface MemberService {

	public int insertMember(Member member);
	public Member selectOneMember(String id);
	public int updateMember(Member member);
}
