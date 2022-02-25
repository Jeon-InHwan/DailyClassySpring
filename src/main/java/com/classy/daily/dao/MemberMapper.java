package com.classy.daily.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.classy.daily.vo.MemberVO;

public interface MemberMapper {
	
	
	// DB까지 도달하는 회원가입 요쳥
	public int join(MemberVO member);

	
	// ID 중복체크, 로그인 요청을 처리하기 위한 회원 조회
	public MemberVO selectMember(String user_id);


	// 회원정보 수정 요청
	public int updateMember(MemberVO member);


	// 회원탈퇴 요청
	public int deleteMember(String user_id);


	// 태그 검색 및 Link 검색에 따른 LinkHits 업데이트
	public int updateLinkHits(MemberVO member);

	
	// LinkHits 가져오기
	public int getLinkHits(String loginId);


	// 사용자의 user_state 업데이트
	public int updateUserState(String loginId);


	// paging 처리를 하기 위해 전체 회원 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(Map<String, String> search);


	// Member 목록 요청 by 관리자
	public ArrayList<MemberVO> listMembers(Map<String, Object> search, RowBounds rb);


	// 사용자 계정 비활성화
	public int deactivation(String user_id);


	// 사용자 계정 활성화
	public int activation(String user_id);


	// 사용자 계정 파트너 승격
	public int elevation(String user_id);

	
	// 사용자 계정 파트너 강등
	public int demotion(String user_id);

	
	
}
