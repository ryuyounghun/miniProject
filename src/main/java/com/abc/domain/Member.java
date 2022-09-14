package com.abc.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {

	private String memberId; 	// 사용자 ID(필수)
	private String memberPw;	// 사용자 비번(필수)
	private String memberName;	// 사용자 이름(필수)
	private String phone;		// 전화번호(필수)
	private String birth;		// 생년월일(선택)
	private String sex;			// 성별(선택)
	private String address;		// 주소(선택)
	
	// Spring Security를 위한 필드 지정
	private boolean enabled;	// 계정 상태가 유효한지 확인하기 위한 변수 1 이면 유효, 0 이면 유효X
	private String roleName;	// 사용자 계정 권한을 구분, ROLE_USER와 ROLE_ADMIN이 있음
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public String getPassword() {
		return this.memberPw;
	}
	@Override
	public String getUsername() {
		return this.memberId;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	
	
}
