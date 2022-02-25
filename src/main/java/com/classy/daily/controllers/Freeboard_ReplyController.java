package com.classy.daily.controllers;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classy.daily.service.Freeboard_ReplyService;
import com.classy.daily.vo.Freeboard_RepleVO;


@Controller
public class Freeboard_ReplyController {

	private static final Logger logger = LoggerFactory.getLogger(Freeboard_ReplyController.class);
	
	@Autowired
	private Freeboard_ReplyService service;
	
	
	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	@ResponseBody
	@RequestMapping(value = "/freeboardReple/insertReple", method = RequestMethod.POST)
	public int insertReply(Freeboard_RepleVO reple) {
		
		logger.info("granted RepleVO : {}", reple);
		
		int result = 0;
		result = service.insertReple(reple);
			
		return result;
	}
	
	
	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	@ResponseBody
	@RequestMapping(value = "/freeboardReple/selectAllReple", method = RequestMethod.POST)
	public ArrayList<Freeboard_RepleVO> selectAllReple(int fb_boardnum) {
		
		logger.info("granted freeboard_num : {}", fb_boardnum);
		
		ArrayList<Freeboard_RepleVO> list = service.selectAllReple(fb_boardnum);
			
		return list;
	}
	
	
	
	
	// Ajax를 활용한 댓글 수정 (UPDATE)
	@ResponseBody
	@RequestMapping(value = "/freeboardReple/updateReple", method = RequestMethod.POST)
	public int updateReple(Freeboard_RepleVO reply) {
		
		int result = 0;
		
		logger.info("granted ReplyVO : {}", reply);
		
		result = service.updateReple(reply);
		
		return result;
	}
	
	
	// Ajax를 활용한 댓글 삭제 (DELETE)
	@ResponseBody
	@RequestMapping(value = "/freeboardReple/deleteReple", method = RequestMethod.POST)
	public int deleteReple(Freeboard_RepleVO reply) {
		
		int result = 0;
		
		logger.info("granted reply For Delete  : {}", reply);
		
		result = service.deleteReple(reply);
		
		return result;
	}
	
	
	
	
	
	
}
