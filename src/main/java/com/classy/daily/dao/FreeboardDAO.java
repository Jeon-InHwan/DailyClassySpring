package com.classy.daily.dao;

import java.util.ArrayList;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.classy.daily.vo.FreeboardVO;



@Repository
public class FreeboardDAO {

	
	@Autowired
	private SqlSession session;
	
	
	// 자유게시판 게시글 작성 요청
	public int writeFreeboard(FreeboardVO freeboard) {
		
		int cnt = 0;
		
		try {
			FreeboardMapper mapper = session.getMapper(FreeboardMapper.class);
			cnt = mapper.writeFreeboard(freeboard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}

	
	// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(Map<String, String> search) {
		
		int result = 0;
		 
		try {
			 FreeboardMapper mapper = session.getMapper(FreeboardMapper.class);
			 result = mapper.selectTotalCount(search);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}

	
	// 자유게시판 목록 조회
	public ArrayList<FreeboardVO> listFreeboards(Map<String, Object> search, int startRecord, int countPerPage) {
		
		ArrayList<FreeboardVO> list = null;
		
		try {
			FreeboardMapper mapper = session.getMapper(FreeboardMapper.class);
			RowBounds rb = new RowBounds(startRecord, countPerPage);
			list = mapper.listFreeboards(search, rb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	// Ajax를 활용하여 글 번호를 토대로 조회수 1 업데이트
	public void updateHits(int fb_boardnum) {
		
		try {
			FreeboardMapper mapper = session.getMapper(FreeboardMapper.class);
			 mapper.updateHits(fb_boardnum);
			} catch(Exception e) {
			 e.printStackTrace();
		}
		
	}

	
	// Ajax를 활용하여 글 번호를 토대로 자유게시판 글 하나 가져오기
	public FreeboardVO readFreeboard(int fb_boardnum) {
		
		FreeboardVO board = null;
		
		try {
			FreeboardMapper mapper = session.getMapper(FreeboardMapper.class);
			 board = mapper.readFreeboard(fb_boardnum);
			} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return board;
	}


	// Ajax를 활용한 자유게시판 글 하나 수정
	public int updateFreeboard(FreeboardVO freeboard) {
		
		int result = 0;
		 
		try {
			FreeboardMapper mapper = session.getMapper(FreeboardMapper.class);
			 result = mapper.updateFreeboard(freeboard);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
			
	}


	// Ajax를 활용한 자유게시판 글 하나 삭제
	public int deleteFreeboard(FreeboardVO freeboard) {
		
		int result = 0;
		 
		try {
			FreeboardMapper mapper = session.getMapper(FreeboardMapper.class);
			 result = mapper.deleteFreeboard(freeboard);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}

	
	
	
}
