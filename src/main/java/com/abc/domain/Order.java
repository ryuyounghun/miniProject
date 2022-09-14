package com.abc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Order {

	private int orderNum;		// 주문번호
	private String orderName;	// 주문 상품이름
	private int orderPrice;		// 주문 상품 가격
	private String orderImage;	// 주문 상품 이미지
	private int quantity;		// 주문 개수
	private String memberId;
}
