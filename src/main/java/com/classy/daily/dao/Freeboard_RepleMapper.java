package com.classy.daily.dao;

import java.util.ArrayList;

import com.classy.daily.vo.Freeboard_RepleVO;

public interface Freeboard_RepleMapper {

	
	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	public int insertReple(Freeboard_RepleVO reply);

	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	public ArrayList<Freeboard_RepleVO> selectAllReple(int fb_boardnum);

	// Ajax를 활용한 댓글 수정 (UPDATE)
	public int updateReple(Freeboard_RepleVO reple);

	// Ajax를 활용한 댓글 삭제 (DELETE)
	public int deleteReple(Freeboard_RepleVO reple);
	
	
	

}
