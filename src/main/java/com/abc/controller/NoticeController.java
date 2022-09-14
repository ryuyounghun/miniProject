package com.abc.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.abc.domain.Notice;
import com.abc.service.NoticeService;
import com.abc.util.FileService;
import com.abc.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("notice")
@Controller

public class NoticeController {
	
	// service 객체 호출
	@Autowired
	private NoticeService nService;
	
	// 첨부파일 업로드 패스 지정
	@Value("${spring.servlet.multipart.location}")		// 설정파일(application 파일)의 속성을 가지고 오고 싶을떄 사용할 수 있는 annotation(spring)
	private String uploadPath;
	
	// 페이지 당 글 수
	@Value("${user.notice.page}")
	private int countPerPage;
	
	// 그룹당 페이지 수
	@Value("${user.notice.group}")
	private int pagePerGroup;
	
	// 브랜드 소개 화면 표시
	@GetMapping("/brandInfo")
	public String brandInfo() {
		
		log.debug("brandInfo() 실행");
		
		return "noticeView/brandInfo";
	}

	// 공지사항 글 목록 호출
	// 배열 안에 여러개 주소 삽입하기
	@GetMapping({"/", "/list"})
	public String list(String type, String searchWord, 
						@RequestParam(name = "page", defaultValue = "1") int page, 
						Model model) {
		log.debug("list() 실행");
		
		// Service 객체에서 글 목록 가져오기
		List<Notice> nList = null;	// 결과를 담을 리스트
		PageNavigator navi = 
				nService.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);
		
		log.debug("type : {}, searchWord : {}", type, searchWord);
		
		if(searchWord == null || type == null) {
			// 검색어 또는 카테고리가 없다면 전체 검색
			nList = nService.selectAllNotices(navi, null, null);
		} else {
			// 검색어 또는 카테고리가 있다면 조건 검색
			nList = nService.selectAllNotices(navi, type, searchWord);
			// 검색어와 타입을 model 추가하여 사용자가 확인할 수 있게 함
			model.addAttribute("type", type);
			model.addAttribute("searchWord", searchWord);
		}
		
		model.addAttribute("navi", navi);
		model.addAttribute("noticeList", nList);
		
		return "noticeView/list";
	}

	// 공지사항 글 쓰기(화면 호출)
	@GetMapping("/write")
    public String write() {
       log.debug("write() 실행");
       return "noticeView/write";
    }
	
	// 공지사항 글 쓰기(DB저장)
	// @RequestParam 이란? write라는 주소를 호출할 때 
	// 요청에 파라미터로 특정 값을 넣겠다는 의미(Notice 객체 set으로 가지고 올 수 없는 특별한 경우)
	@PostMapping("/write")
	public String write(Notice notice, @RequestParam MultipartFile file) {
		
		log.debug("write notice : {}", notice);
		// 첨부된 오리지널 파일의 파일명 출력 
		log.debug("write file : {}", file.getOriginalFilename());
		
		// 파일이 첨부되어 있으면 파일 정보를 Notice 객체에 저장
		if(!file.isEmpty()) {
			// 저장할 파일명 생성
			String savedFile = FileService.saveFile(file, uploadPath);
			log.debug("savedFuke : {}", savedFile);
			notice.setSavedFile(savedFile);
			notice.setOriginalFile(file.getOriginalFilename());
			
		}
		
		nService.insertNotice(notice);
		
		return "redirect:./";		// ./list 잊지 말자
	}
	 
	// 공지사항 글 읽기
	// 받아오고자 하는 값이 기본자료형일때 아무것도 넘겨주지 않으면
	// null이 들어오려고 해서 예외가 발생함
	@GetMapping("/read")
	public String read(int num, Model model) {
		log.debug("read() num : {}", num);
		
		// service 호출
		// 조회수를 먼저 올린 다음
		nService.updateViewCount(num);
		// 해당 매퍼 실행하자!
		Notice currentNotice = nService.selectOneNotice(num);
		
		model.addAttribute("notice", currentNotice);
		
		return "noticeView/read";
	}
	
	// 공지사항 글 수정하기(화면 호출)
	@GetMapping("/update")
	public String update(int num, Model model) {
		log.debug("update() num : {}", num);
		
		// service 호출
		Notice currentNotice = nService.selectOneNotice(num);
		
		log.debug("update() notice : {}", currentNotice);
		
		model.addAttribute("notice", currentNotice);
		
		return "noticeView/update";
	}
	
	// 공지사항 글 수정하기(DB 저장)
	@PostMapping("/update")
	public String update(Notice notice) {
		log.debug("update() notice : {}", notice);
		
		nService.updateNotice(notice);
		
		return "redirect:./";
	}
	
	// 공지사항 글 삭제하기
	@GetMapping("/delete")
	public String delete(int num) {
		log.debug("delete() num : {}", num);
		
		nService.deleteNotice(num);
		
		return "redirect:./";
	}
	
	// 첨부파일 다운로드 창 띄우기
	// HttpServletRequest와 HttpServletResponse가 서로 첨부파일을 주고받기 위해 요청, 응답(서버에서 돌려주는 응답)을 한다.
	@GetMapping("/download")
	public String downloadFile(int num, Model model, HttpServletResponse response) {
		
		log.debug("num : {}", num);
		
		// 이 번호에 해당하는 Notice 객체를 가져온다.
		Notice notice = nService.selectOneNotice(num);
		
		// 원래 파일명
		String originalFile = notice.getOriginalFile();
		
		try {
			// 여기 첨부파일 있어요!! 이름은 originalFile이에요!!
			response.setHeader(
					"Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(originalFile, "UTF-8")
					);
		} catch(UnsupportedEncodingException e) {
			// 지원하지 않은 인코딩방식을 사용했다면 알려주세요
			e.printStackTrace();
		}
		
		// 저장된 파일 경로 가져오기
		String fullPath = uploadPath + "/" + notice.getSavedFile(); 
		
		// 서버의 첨부파일을 읽을 입력 스트림 & 클라이언트에 출력할 출력스트림
		FileInputStream fileIn = null;
		ServletOutputStream fileOut = null;
		
		try {
			
			// fullPath에 해당하는 주소의 파일을 읽어옴
			fileIn = new FileInputStream(fullPath);
			// 읽어온 파일을 집어넣을 출력 공간을 열어 놓음
			fileOut = response.getOutputStream();
			
			// Spring 파일 관련 유틸 이용해서 출력
			// fileIn에 들어있떤 FileInputStream 내용을 복사하여 fileOut에 붙여넣어주세요
			FileCopyUtils.copy(fileIn, fileOut);
			
			fileIn.close();
			fileOut.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
	// 게시판에서 이미지 불러오기 기능
	@GetMapping("/display")
	public ResponseEntity<Resource> display(int num) {
		
		log.debug("num : {}", num);
		
		Notice notice = nService.selectOneNotice(num);
		
		Resource resource 
			= new FileSystemResource(uploadPath + "/" + notice.getSavedFile());
	
		// 파일이 존재하지 않을때
		if(!resource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		} 
		
		HttpHeaders header = new HttpHeaders();
		
		Path filePath = null;
		
		try {
			filePath = Paths.get(uploadPath + "/" + notice.getSavedFile());
			
			// response의 header에
			// 제가 첨부한 내용의 타입은 파일이에요
			header.add("Content-type", Files.probeContentType(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	
	// 이벤트 페이지 호출
		@GetMapping("/event")
		public String event() {
			return "noticeView/event";
		}
		
}
