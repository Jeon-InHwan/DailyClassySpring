package com.classy.daily.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classy.daily.dao.FreeboardDAO;
import com.classy.daily.vo.FreeboardVO;

@Service
public class FreeboardService {

	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private FreeboardDAO dao;
	
	
	// 자유게시판 게시글 작성 요청
	public String writeFreeboard(FreeboardVO freeboard) {
		
		String path = "";
		
		String user_id = (String)session.getAttribute("loginId");
		freeboard.setUser_id(user_id);
		
		int cnt = dao.writeFreeboard(freeboard);
		
		if(cnt < 1) {
			path = "redirect:/freeboard/writeFreeboardForm";
		} else {
			path = "redirect:/freeboard/listFreeboards";
		}
		
		return path;
	}

	
	// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(String searchItem, String searchWord) {
		
		Map<String, String> search = new HashMap<String, String>();
		search.put("searchItem", searchItem);
		search.put("searchWord", searchWord);
		
		int result = 0;
		result = dao.selectTotalCount(search);
		
		return result;
	}

	
	// 자유게시판 목록 조회
	public ArrayList<FreeboardVO> listFreeboards(String searchItem, String searchWord, int startRecord,
			int countPerPage) {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("searchItem", searchItem);
		search.put("searchWord", searchWord);
		return dao.listFreeboards(search, startRecord, countPerPage);
	}


	// Ajax를 활용하여 글 번호를 토대로 자유게시판 글 하나 가져오기 + 조회수 1 업데이트
	public FreeboardVO readFreeboard(int fb_boardnum) {
		
		FreeboardVO oneBoard = null;
		
		dao.updateHits(fb_boardnum);
		oneBoard = dao.readFreeboard(fb_boardnum);
		
		return oneBoard;
	}

	
	// Ajax를 활용한 자유게시판 글 하나 수정
	public int updateFreeboard(FreeboardVO freeboard) {
		
		String user_id = (String)session.getAttribute("loginId");
		int user_state = (int)session.getAttribute("user_state");
		freeboard.setUser_id(user_id);
		freeboard.setUser_state(user_state);
		
		int result = 0;
		result = dao.updateFreeboard(freeboard);
		
		return result;
	}


	// Ajax를 활용한 자유게시판 글 하나 삭제
	public int deleteFreeboard(FreeboardVO freeboard) {
		
		String user_id = (String)session.getAttribute("loginId");
		int user_state = (int)session.getAttribute("user_state");
		freeboard.setUser_id(user_id);
		freeboard.setUser_state(user_state);

		int result = 0;
		result = dao.deleteFreeboard(freeboard);
		
		return result;
	}

}
