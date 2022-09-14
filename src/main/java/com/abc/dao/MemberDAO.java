package com.abc.dao;

import org.apache.ibatis.annotations.Mapper;

import com.abc.domain.Member;

@Mapper
public interface MemberDAO {

	public int insertMember(Member member);
	// memberMapper.xml에서 parameterType으로 쓴 이름과 동일하게!
	public Member selectOneMember(String memberId);
	
	public int updateMember(Member member);
}
