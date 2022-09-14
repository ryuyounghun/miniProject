package com.abc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.abc.domain.Notice;

@Mapper
public interface NoticeDAO {

	public int insertNotice(Notice notice);
	public int updateNotice(Notice notice);
	public int deleteNotice(int noticeNum);
	public List<Notice> selectAllNotices(Map<String, String> map, RowBounds rb);
	public int countNotices(Map<String, String> map);
	
	
	public Notice selectOneNotice(int noticeNum);
	public int updateViewCount(int noticeNum);
	
}
