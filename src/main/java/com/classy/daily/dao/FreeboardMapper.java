package com.classy.daily.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.classy.daily.vo.FreeboardVO;

public interface FreeboardMapper {

	
	// 자유게시판 게시글 작성 요청
	public int writeFreeboard(FreeboardVO freeboard);

	// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(Map<String, String> search);

	// 자유게시판 목록 조회
	public ArrayList<FreeboardVO> listFreeboards(Map<String, Object> search, RowBounds rb);

	// Ajax를 활용하여 글 번호를 토대로 조회수 1 업데이트
	public void updateHits(int fb_boardnum);

	// Ajax를 활용하여 글 번호를 토대로 자유게시판 글 하나 가져오기
	public FreeboardVO readFreeboard(int fb_boardnum);

	// Ajax를 활용한 자유게시판 글 하나 수정
	public int updateFreeboard(FreeboardVO freeboard);

	// Ajax를 활용한 자유게시판 글 하나 삭제
	public int deleteFreeboard(FreeboardVO freeboard);
	
	
	

}
