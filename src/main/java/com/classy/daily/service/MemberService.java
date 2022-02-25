package com.classy.daily.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.classy.daily.dao.MemberDAO;
import com.classy.daily.vo.MemberVO;


@Service
public class MemberService {

	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private HttpSession session;
	
	
	// DB까지 도달하는 회원가입 요쳥  (ID 중복 확인 포함)
	public String join(MemberVO member) {
		
		String path = "";
		
		MemberVO searchedMember = dao.selectMember(member.getUser_id());
		
		if(searchedMember != null) {
			return "redirect:/member/joinForm";
		}
		
		int cnt = dao.join(member);
		
		if(cnt < 1) {
			path = "redirect:/member/joinForm";
		} else {
			path = "redirect:/";
		}
		
		return path;
	}


	// 로그인 요청
	public String login(MemberVO member) {
		
		String path = "";
		
		MemberVO searchedMember = dao.selectMember(member.getUser_id());
		
		// 사용자가 입력한 id를 토대로, 데이터베이스에서 일치하는 데이터를 가져오지 못한 경우
		if(searchedMember == null) {
			System.out.println("ID가 틀린 상황");
			path = "redirect:/";
		} else {
			
			// 이미 탈퇴 신청이 완료된 회원인 경우
			if(searchedMember.getUser_state() == -1) {
				System.out.println("이미 탈퇴한 회원");
				path = "redirect:/";
				return path;
			}
			
			// 사용자가 입력한 비밀번호와 조회한 데이터의 비밀번호가 같은지 확인
			if(member.getUser_pw().equals(searchedMember.getUser_pw())){
				// 일치하는 경우이므로, 로그인 처리를 해주어야 함 => 세션 스코프에 로그인 정보 저장
				session.setAttribute("loginId", searchedMember.getUser_id());
				session.setAttribute("loginNm", searchedMember.getUser_name());
				session.setAttribute("user_state", searchedMember.getUser_state());
				session.setAttribute("kakao", "none");
				path = "redirect:/member/afterLogin";
			} else {
				// 비밀번호만 틀린 상황 (ID는 바르게 입력한 상황)
				System.out.println("PW가 틀린 상황");
				path = "redirect:/";
				}
			}
		
			return path;
	}


	// 로그아웃 요청 처리
	public int logout() {
		
		// 세션 스코프에 저장되어 있는 로그인 정보 삭제
		session.removeAttribute("loginId");
		session.removeAttribute("loginNm");
		session.removeAttribute("user_state");

		
		if(session.getAttribute("kakao").equals("kakao")) {
			return 2;
		} else {
			return 1;
		}
		
	}


	
	// 회원정보 수정 요청을 위한 회원 조회
	public MemberVO forMemberUpdate() {
		
		String user_id = (String)session.getAttribute("loginId");
		
		MemberVO searchedMember = dao.selectMember(user_id);
		
		return searchedMember;
	}


	
	// 회원정보 수정 요청
	public String updateMember(MemberVO member) {

		String path = "";
		
		if(member.getUser_id() == null) {
			String user_id = (String)session.getAttribute("loginId");
			member.setUser_id(user_id);
		}
		
		int cnt = dao.updateMember(member);
		
		if(cnt < 1) {
			path = "redirect:/member/updateMemberForm";
		} else {
			session.removeAttribute("loginId");
			session.removeAttribute("loginNm");
			session.removeAttribute("manager");
			path = "redirect:/";
		}
		
		return path;
	}


	// 회원탈퇴 요청
	public String withdrawalMember(MemberVO member) {
		
		String path = "";
		int cnt = 0;
		
		String user_id = (String)session.getAttribute("loginId");

		MemberVO searchedMember = dao.selectMember(user_id);
		
		if(member.getUser_id().equals(searchedMember.getUser_id())) {
			if (member.getUser_pw().equals(searchedMember.getUser_pw())) {
				cnt = dao.deleteMember(searchedMember.getUser_id());
			}
		} else {
			path = "redirect:/member/withdrawalMemberForm";
		}
		
		if(cnt < 1) {
			path = "redirect:/member/withdrawalMemberForm";
		} else {
			session.removeAttribute("loginId");
			session.removeAttribute("loginNm");
			session.removeAttribute("manager");
			path = "redirect:/";
		}
		
		return path;
	}


	// 태그 검색 및 Link 검색에 따른 LinkHits 업데이트
	public int updateLinkHits(String user_id) {
		
		MemberVO member = new MemberVO();
		
		String loginId = (String)session.getAttribute("loginId");
		
		member.setUser_id(user_id);
		member.setUser_name(loginId);
		
		int cnt = dao.updateLinkHits(member);
		
		return cnt;
	}


	// LinkHits 가져오기
	public int getLinkHits() {
		
		String loginId = (String)session.getAttribute("loginId");
		
		int cnt = dao.getLinkHits(loginId);
		
		return cnt;
		
	}


	// 사용자의 user_state 업데이트
	public int updateUserState() {
		
		String loginId = (String)session.getAttribute("loginId");
		
		int cnt = dao.updateUserState(loginId);
		
		return cnt;
	
	}


	// paging 처리를 하기 위해 전체 회원 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(String searchItem, String searchStatus, String searchWord) {
		
		Map<String, String> search = new HashMap<String, String>();
		search.put("searchItem", searchItem);
		search.put("searchStatus", searchStatus);
		search.put("searchWord", searchWord);
		
		int result = 0;
		
		result = dao.selectTotalCount(search);
		
		return result;
	}


	// Member 목록 요청 by 관리자
	public ArrayList<MemberVO> listMembers(String searchItem, String searchWord, String searchStatus, int startRecord,
			int countPerPage) {
		
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("searchItem", searchItem);
		search.put("searchStatus", searchStatus);
		search.put("searchWord", searchWord);
		
		ArrayList<MemberVO> list = dao.listMembers(search, startRecord, countPerPage);
		
		return list;
	}


	// 사용자 계정 비활성화
	public int deactivation(String user_id) {
		
		int cnt = 0;
		
		cnt = dao.deactivation(user_id);
		
		return cnt;
		
	}


	// 사용자 계정 활성화
	public int activation(String user_id) {
		
		int cnt = 0;
		
		cnt = dao.activation(user_id);
		
		return cnt;
	}
	
	
	// 사용자 계정 파트너 승격
	public int elevation(String user_id) {
		
		int cnt = 0;
		
		cnt = dao.elevation(user_id);
		
		return cnt;
	}


	// 사용자 계정 파트너 강등
	public int demotion(String user_id) {
		
		int cnt = 0;
		
		cnt = dao.demotion(user_id);
		
		return cnt;
	}


	// 회원가입 시 ID 중복 검사
	public MemberVO idCheck(String user_id) {
		
		MemberVO searchedMember = dao.selectMember(user_id);
		
		return searchedMember;
	}


	
	
	
	
}
