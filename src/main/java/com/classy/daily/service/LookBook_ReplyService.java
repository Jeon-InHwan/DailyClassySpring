package com.classy.daily.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.classy.daily.dao.LookBook_RepleDAO;
import com.classy.daily.vo.LookBook_RepleVO;

@Service
public class LookBook_ReplyService {

	
	@Autowired
	private LookBook_RepleDAO dao;
	
	@Autowired
	private HttpSession session;
	
	
	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	public ArrayList<LookBook_RepleVO> selectAllLbReple(int lb_boardnum) {
		
		ArrayList<LookBook_RepleVO> list = dao.selectAllLbReple(lb_boardnum);
		
		return list;
	}


	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	public int insertLbReple(LookBook_RepleVO reple) {
		
		int result = 0;
		
		result = dao.insertLbReple(reple);
		
		return result;
	}


	// Ajax를 활용한 댓글 수정 (UPDATE)
	public int updateLbReple(LookBook_RepleVO reple) {
		
		String user_id = (String)session.getAttribute("loginId");
		int user_state = (int)session.getAttribute("user_state");
		reple.setUser_id(user_id);
		reple.setUser_state(user_state);
		
		int result = 0;
		
		result = dao.updateLbReple(reple);
		
		return result;
	}


	// Ajax를 활용한 댓글 삭제 (DELETE)
	public int deleteLbReple(LookBook_RepleVO reple) {
		
		String user_id = (String)session.getAttribute("loginId");
		int user_state = (int)session.getAttribute("user_state");
		reple.setUser_id(user_id);
		reple.setUser_state(user_state);
		
		int result = 0;
		
		result = dao.deleteLbReple(reple);
		
		return result;
	}


	// Ajax를 활용한 댓글 개수 세어오기
	public int countReple(int lb_boardnum) {
		
		int result = 0;
		
		result = dao.countReple(lb_boardnum);
		
		return result;
	}

}
