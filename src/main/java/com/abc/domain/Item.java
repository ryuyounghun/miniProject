package com.abc.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Item {

	private int itemNum;		// 상품번호
	private String itemName;	// 상품이름
	private String itemCategory;// 상품 카테고리
	private int itemPrice;		// 상품가격
	private String itemImage;	// 상품 이미지
	private String itemContent;	// 상품 설명
}
