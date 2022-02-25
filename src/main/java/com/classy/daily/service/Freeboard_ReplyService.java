package com.classy.daily.service;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.classy.daily.dao.Freeboard_ReplyDAO;
import com.classy.daily.vo.Freeboard_RepleVO;


@Service
public class Freeboard_ReplyService {

	@Autowired
	private Freeboard_ReplyDAO dao;
	
	@Autowired
	private HttpSession session;
	
	
	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	public int insertReple(Freeboard_RepleVO reple) {
		
		int result = 0;
		
		result = dao.insertReple(reple);
		
		return result;
	}
	
	
	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	public ArrayList<Freeboard_RepleVO> selectAllReple(int fb_boardnum) {
		
		ArrayList<Freeboard_RepleVO> list = dao.selectAllReple(fb_boardnum);
		
		return list;
	}


	// Ajax를 활용한 댓글 수정 (UPDATE)
	public int updateReple(Freeboard_RepleVO reple) {
		
		String user_id = (String)session.getAttribute("loginId");
		int user_state = (int)session.getAttribute("user_state");
		reple.setUser_id(user_id);
		reple.setUser_state(user_state);
		
		int result = 0;
		
		result = dao.updateReple(reple);
		
		return result;
	}


	// Ajax를 활용한 댓글 삭제 (DELETE)
	public int deleteReple(Freeboard_RepleVO reple) {
		
		String user_id = (String)session.getAttribute("loginId");
		int user_state = (int)session.getAttribute("user_state");
		reple.setUser_id(user_id);
		reple.setUser_state(user_state);
		
		int result = 0;
		
		result = dao.deleteReple(reple);
		
		return result;
	}
	
	

	
	
}
