package com.classy.daily.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.classy.daily.service.LookBook_ReplyService;
import com.classy.daily.vo.LookBook_RepleVO;

@Controller
public class LookBook_ReplyController {

	private static final Logger logger = LoggerFactory.getLogger(LookBook_ReplyController.class);
	
	@Autowired
	private LookBook_ReplyService service;
	
	
	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	@ResponseBody
	@RequestMapping(value = "/lookbookReple/insertLbReple", method = RequestMethod.POST)
	public int insertLbReply(LookBook_RepleVO reple) {
		
		logger.info("granted RepleVO : {}", reple);
		
		int result = 0;
		result = service.insertLbReple(reple);
			
		return result;
	}
	
	
	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	@ResponseBody
	@RequestMapping(value = "/lookbookReple/selectAllLbReple", method = RequestMethod.POST)
	public ArrayList<LookBook_RepleVO> selectAllLbReple(int lb_boardnum) {
		
		logger.info("granted lookbookboard_num : {}", lb_boardnum);
		
		ArrayList<LookBook_RepleVO> list = service.selectAllLbReple(lb_boardnum);
			
		return list;
	}
	
	
	// Ajax를 활용한 댓글 수정 (UPDATE)
	@ResponseBody
	@RequestMapping(value = "/lookbookReple/updateLbReple", method = RequestMethod.POST)
	public int updateLbReple(LookBook_RepleVO reple) {
		
		int result = 0;
		
		logger.info("granted RepleVO : {}", reple);
		
		result = service.updateLbReple(reple);
		
		return result;
	}
		
		
	// Ajax를 활용한 댓글 삭제 (DELETE)
	@ResponseBody
	@RequestMapping(value = "/lookbookReple/deleteLbReple", method = RequestMethod.POST)
	public int deleteLbReple(LookBook_RepleVO reple) {
		
		int result = 0;
		
		logger.info("granted reply For Delete  : {}", reple);
		
		result = service.deleteLbReple(reple);
		
		return result;
	}
	
	
	// Ajax를 활용한 댓글 개수 세어오기
	@ResponseBody
	@RequestMapping(value = "/lookbookReple/countReple", method = RequestMethod.POST)
	public int countReple(int lb_boardnum) {
		
		int result = 0;
		
		logger.info("granted lb_boardnum For countReple  : {}", lb_boardnum);
		
		result = service.countReple(lb_boardnum);
		
		return result;
	}
	
}
