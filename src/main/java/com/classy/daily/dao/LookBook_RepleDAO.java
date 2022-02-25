package com.classy.daily.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.classy.daily.vo.LookBook_RepleVO;


@Repository
public class LookBook_RepleDAO {


	@Autowired
	private SqlSession session;
	
	
	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	public ArrayList<LookBook_RepleVO> selectAllLbReple(int lb_boardnum) {
		
		ArrayList<LookBook_RepleVO> list = null;
		
		try {
			 LookBook_RepleMapper mapper = session.getMapper(LookBook_RepleMapper.class);
			 list = mapper.selectAllLbReple(lb_boardnum);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return list;
	}


	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	public int insertLbReple(LookBook_RepleVO reple) {
		
		int result = 0;
		 
		try {
			 LookBook_RepleMapper mapper = session.getMapper(LookBook_RepleMapper.class);
			 result = mapper.insertLbReple(reple);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}


	// Ajax를 활용한 댓글 수정 (UPDATE)
	public int updateLbReple(LookBook_RepleVO reple) {
		
		int result = 0;
		 
		try {
			 LookBook_RepleMapper mapper = session.getMapper(LookBook_RepleMapper.class);
			 result = mapper.updateLbReple(reple);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}


	// Ajax를 활용한 댓글 삭제 (DELETE)
	public int deleteLbReple(LookBook_RepleVO reple) {
		
		int result = 0;
		 
		try {
			 LookBook_RepleMapper mapper = session.getMapper(LookBook_RepleMapper.class);
			 result = mapper.deleteLbReple(reple);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}


	// Ajax를 활용한 댓글 개수 세어오기
	public int countReple(int lb_boardnum) {
		
		int result = 0;
		 
		try {
			 LookBook_RepleMapper mapper = session.getMapper(LookBook_RepleMapper.class);
			 result = mapper.countReple(lb_boardnum);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}
	
	
	

}
