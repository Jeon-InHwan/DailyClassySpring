package com.classy.daily.dao;

import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.classy.daily.vo.Freeboard_RepleVO;



@Repository
public class Freeboard_ReplyDAO {

	@Autowired
	private SqlSession session;
	
	
	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	public int insertReple(Freeboard_RepleVO reply) {
		
		int result = 0;
		 
		try {
			 Freeboard_RepleMapper mapper = session.getMapper(Freeboard_RepleMapper.class);
			 result = mapper.insertReple(reply);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}

	
	
	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	public ArrayList<Freeboard_RepleVO> selectAllReple(int fb_boardnum) {

		ArrayList<Freeboard_RepleVO> list = null;
		
		try {
			Freeboard_RepleMapper mapper = session.getMapper(Freeboard_RepleMapper.class);
			 list = mapper.selectAllReple(fb_boardnum);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return list;
	}



	// Ajax를 활용한 댓글 수정 (UPDATE)
	public int updateReple(Freeboard_RepleVO reple) {
		
		int result = 0;
		 
		try {
			 Freeboard_RepleMapper mapper = session.getMapper(Freeboard_RepleMapper.class);
			 result = mapper.updateReple(reple);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}



	// Ajax를 활용한 댓글 삭제 (DELETE)
	public int deleteReple(Freeboard_RepleVO reple) {
		
		int result = 0;
		 
		try {
			 Freeboard_RepleMapper mapper = session.getMapper(Freeboard_RepleMapper.class);
			 result = mapper.deleteReple(reple);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}



	
	
	
}
