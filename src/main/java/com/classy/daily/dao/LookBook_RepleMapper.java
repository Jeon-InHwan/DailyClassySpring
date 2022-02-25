package com.classy.daily.dao;

import java.util.ArrayList;

import com.classy.daily.vo.LookBook_RepleVO;

public interface LookBook_RepleMapper {

	
	// Ajax를 활용한 모든 댓글 가져오기 (SELECT)
	public ArrayList<LookBook_RepleVO> selectAllLbReple(int lb_boardnum);

	// Ajax를 활용한 댓글 하나 작성 (INSERT INTO)
	public int insertLbReple(LookBook_RepleVO reple);

	// Ajax를 활용한 댓글 수정 (UPDATE)
	public int updateLbReple(LookBook_RepleVO reple);

	// Ajax를 활용한 댓글 삭제 (DELETE)
	public int deleteLbReple(LookBook_RepleVO reple);

	// Ajax를 활용한 댓글 개수 세어오기
	public int countReple(int lb_boardnum);

}
