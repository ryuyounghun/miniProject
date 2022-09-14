package com.abc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.NoticeDAO;
import com.abc.domain.Notice;
import com.abc.util.PageNavigator;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDAO nDao;
	
	@Override
	public int insertNotice(Notice notice) {
		return nDao.insertNotice(notice);
	}

	@Override
	public int updateNotice(Notice notice) {
		return nDao.updateNotice(notice);
	}

	@Override
	public int deleteNotice(int noticeNum) {
		// TODO Auto-generated method stub
		return nDao.deleteNotice(noticeNum);
	}

	@Override
	public Notice selectOneNotice(int noticeNum) {
		return nDao.selectOneNotice(noticeNum);
	}

	@Override
	public int updateViewCount(int noticeNum) {
		return nDao.updateViewCount(noticeNum);
	}
	
	@Override
	public List<Notice> selectAllNotices(PageNavigator navi, String type, String searchKeyword) {
		
		// mapper에 파라미터로 보내줄 map 생성
		Map<String, String> map = new HashMap<String, String>();
		
		// 검색조건이 있으면 map에 넣을 것이다.
		map.put("type", type);
		map.put("searchKeyword", searchKeyword);
		
		// 시작 레코드부터 한 페이지의 글 단위(10개)만큼 선택
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		
		return nDao.selectAllNotices(map, rb);
	}

	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String keyword) {
		
		// map은 인터페이스, hashmap 클래스 객체
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("type", type);
		map.put("searchKeyword", keyword);
		
		int total = nDao.countNotices(map);
		
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
		
		return navi;
	}

}
