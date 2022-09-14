package com.abc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 공지사항 정보를 가지고 있는 VO(DTO)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Notice {
	
	private int noticeNum;			// 공지글 번호
	private String title;			// 제목
	private String content;			// 내용
	private int viewCount;			// 조회수
	private String inputDate;		// 작성일
	
	// 첨부파일용 필드
	private String originalFile;	 
	private String savedFile;		
}
