package com.classy.daily.dao;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.classy.daily.vo.LinkVO;
import com.classy.daily.vo.LookBookVO;
import com.classy.daily.vo.PictureVO;
import com.classy.daily.vo.TagVO;

public interface LookBookMapper {

	// LookBook 게시글 INSERT 요청
	public int writeLookBook(LookBookVO lookbook);

	// LookBook 게시글 TAG INSERT 요청
	public int tagInsert(LookBookVO lookbook);

	// LookBook 게시글 Link INSERT 요청
	public void linkInsert(LookBookVO lookbook);

	// 다중 파일 업로드 
	public void picInsert(List<LookBookVO> list);

	// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(Map<String, String> search);

	// 룩북게시판 목록 조회
	public ArrayList<LookBookVO> listLookBookboards(Map<String, Object> search, RowBounds rb);

	// 글 번호를 토대로 룩북 게시판 조회수 1 업데이트
	public void updateHits(int lb_boardnum);

	// 룩북게시판 하나 조회
	public LookBookVO readLookBookboard(int lb_boardnum);

	// Ajax를 활용한 룩북게시판 태그 가져오기
	public ArrayList<TagVO> readTag(int lb_boardnum);

	// Ajax를 활용한 룩북게시판 링크 가져오기
	public ArrayList<LinkVO> readLink(int lb_boardnum);

	// Ajax를 활용한 룩북게시판 사진 가져오기
	public ArrayList<PictureVO> readPicture(int lb_boardnum);

	// Ajax를 활용한 룩북게시판 추천수 올리기
	public int recommendThis(LookBookVO lookbook);

	// Ajax를 활용한 룩북게시판 글 수 가져오기
	public int countLookBook(String user_id);

	// 인기글 가져오기 
	public ArrayList<LookBookVO> getPopular(RowBounds rb);

	// 게시글 삭제 요청
	public int deleteLookBook(LookBookVO lookbook);

	
	
	
}
