package com.abc.service;

import java.util.List;

import com.abc.domain.Notice;
import com.abc.util.PageNavigator;

public interface NoticeService {
	
	public int insertNotice(Notice notice);
	public int updateNotice(Notice notice);
	public int deleteNotice(int noticeNum);
	public Notice selectOneNotice(int noticeNum);
	public int updateViewCount(int noticeNum);
	
	// 페이징을 적용한 전체 글 가져오기
	public List<Notice> selectAllNotices(PageNavigator navi, String type, String searchKeyword);
	
	// PageNavigator 객체 가져오기(페이징에 대한 정보)
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String keyword);
}
