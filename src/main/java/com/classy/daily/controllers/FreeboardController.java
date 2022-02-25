package com.classy.daily.controllers;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.classy.daily.service.FreeboardService;
import com.classy.daily.util.PageNavigator;
import com.classy.daily.vo.FreeboardVO;




@Controller
public class FreeboardController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(FreeboardController.class);
	
	@Autowired
	private FreeboardService service;
	
	private final int countPerPage = 10;
	private final int pagePerGroup = 5;
	
	// 자유게시판 작성 Form 요청
	@RequestMapping(value = "/freeboard/writeFreeboardForm", method = RequestMethod.GET)
	public String writeFreeboardForm() {
		return "freeboard/writeFreeboardForm";
	}
	
	
	// 자유게시판 작성 요청
	@RequestMapping(value = "/freeboard/writeFreeboard", method = RequestMethod.POST)
	public String writeFreeboard(FreeboardVO freeboard) {
		
		logger.info("granted FreeboardVO : {}", freeboard);
		return service.writeFreeboard(freeboard);
	}
	
	
	// 자유게시판 jsp로 이동 요청
	@RequestMapping(value = "/freeboard/listFreeboards", method = RequestMethod.GET)
	public String listFreeboards(Model model, 
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchItem", defaultValue = "fb_title") String searchItem,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord) {
		
		
		// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
		int totalCount = service.selectTotalCount(searchItem, searchWord);
		
		logger.info("select Total freeboard Count : {}", totalCount);
		logger.info("searchItem : {}", searchItem);
		logger.info("searchWord : {}", searchWord);
		
		// paging 처리를 위한 객체 생성
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, totalCount);
		
		
		// 자유게시판 목록 조회
		ArrayList<FreeboardVO> list = service.listFreeboards(searchItem, searchWord, navi.getStartRecord(), navi.getCountPerPage());
		
		logger.info("select all from freeboard : {}", list);
		logger.info("navi : {}", navi);
		
		model.addAttribute("list", list);
		model.addAttribute("navi", navi);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("searchItem", searchItem);
		
		return "/freeboard/listFreeboards";
	}
	
	
	// Ajax를 활용한 자유게시판 글 하나 읽기
	@ResponseBody
	@RequestMapping(value = "/freeboard/readFreeboard", method = RequestMethod.POST)
	public FreeboardVO readFreeboard(int fb_boardnum) {
		FreeboardVO oneBoard = service.readFreeboard(fb_boardnum);
		return oneBoard;
	}
	
	
	// Ajax를 활용한 자유게시판 글 하나 수정
	@ResponseBody
	@RequestMapping(value = "/freeboard/updateFreeboard", method = RequestMethod.POST)
	public int updateFreeboard(FreeboardVO freeboard) {
		
		int result = 0;
		result = service.updateFreeboard(freeboard);
		
		return result;
	}
	
	
	// Ajax를 활용한 자유게시판 글 하나 삭제
	@ResponseBody
	@RequestMapping(value = "/freeboard/deleteFreeboard", method = RequestMethod.POST)
	public int deleteFreeboard(FreeboardVO freeboard) {
			
		int result = 0;
		result = service.deleteFreeboard(freeboard);
			
		return result;
	}
	
	
}
