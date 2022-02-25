package com.classy.daily.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classy.daily.service.LookBookService;
import com.classy.daily.service.MemberService;
import com.classy.daily.util.PageNavigator;
import com.classy.daily.vo.LookBookVO;
import com.classy.daily.vo.MemberVO;



@Controller
public class MemberController {

	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private LookBookService lb_service;
	
	private final int countPerPage = 10;
	private final int pagePerGroup = 5;
	
	
	// 회원가입 Form으로 이동하는 요청
	@RequestMapping(value = "/member/joinForm", method = RequestMethod.GET)
	public String joinForm() {
		return "member/joinForm";
	}
	
	
	// 로그인 후 화면으로 이동하는 요청
	@RequestMapping(value = "/member/afterLogin", method = RequestMethod.GET)
	public String afterLogin(Model model) {
		
		// 인기글 가져오기 
		ArrayList<LookBookVO> list = lb_service.getPopular();
		model.addAttribute("list", list);
		
		return "member/afterLogin";
	}
	
	
	
	// DB까지 도달하는 회원가입 요청  (ID 중복 확인 포함)
	@RequestMapping(value = "/member/join", method = RequestMethod.POST)
	public String join(MemberVO member) {
		
		logger.info("granted MemberVO for join : {}", member);
		
		return service.join(member);
		
	}
	
	
	// 로그인 요청
	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
	public String login(MemberVO member) {
		return service.login(member);
	}
	
	
	// 로그아웃 요청 처리
	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout() {
		
		int cnt = service.logout();
		
		if(cnt == 2) {
			return "redirect:/kakao/logout";
		} else {
			return "redirect:/";
		}
		
	}
	
	
	// 회원정보 수정 Form 요청
	@RequestMapping(value = "/member/updateMemberForm", method = RequestMethod.GET)
	public String updateMemberForm(Model model) {
		
		MemberVO searchedMember = service.forMemberUpdate();
		
		model.addAttribute("member", searchedMember);
		
		return "member/updateMemberForm";
	}
	
	
	// 회원정보 수정 요청
	@RequestMapping(value = "/member/updateMember", method = RequestMethod.POST)
	public String updateMember(MemberVO member) {
		logger.info("granted MemberVo for updateMember : {}", member);
		return service.updateMember(member);
	}
	
	
	// 회원탈퇴 폼 요청
	@RequestMapping(value = "/member/withdrawalMemberForm", method = RequestMethod.GET)
	public String withdrawalMemberForm() {
		return "member/withdrawalMemberForm";
	}
	
	
	// 회원탈퇴 요청
	@RequestMapping(value = "/member/withdrawalMember", method = RequestMethod.POST)
	public String  withdrawalMember(MemberVO member) {
		logger.info("granted MemberVO for deleteMember : {}", member);
		return service.withdrawalMember(member);
	}
	
	
	// 태그 검색 및 Link 검색에 따른 LinkHits 업데이트
	@ResponseBody
	@RequestMapping(value = "/member/updateLinkHits", method = RequestMethod.POST)
	public int updateLinkHits(String user_id) {
		logger.info("granted user_id for updateLinkHits : {}", user_id);
		return service.updateLinkHits(user_id);
	}
	
	
	// 포인트 조회 및 파트너 신청 페이지로 이동
	@RequestMapping(value = "/member/checkMyInfo", method = RequestMethod.GET)
	public String  checkMyInfo(Model model) {
		
		MemberVO searchedMember = service.forMemberUpdate();
		
		model.addAttribute("member", searchedMember);
		
		return "member/checkMyInfo";
	}
	
	
	// LinkHits 가져오기
	@ResponseBody
	@RequestMapping(value = "/member/getLinkHits", method = RequestMethod.POST)
	public int getLinkHits() {
		return service.getLinkHits();
	}
	
	
	// 사용자의 user_state 업데이트
	@ResponseBody
	@RequestMapping(value = "/member/updateUserState", method = RequestMethod.POST)
	public int updateUserState() {
		return service.updateUserState();
	}
	
	
	// Member 목록 요청 by 관리자
	@RequestMapping(value = "/manager/listMembers", method = RequestMethod.GET)
	public String MemberList(Model model,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchItem", defaultValue = "user_id") String searchItem,
			@RequestParam(value = "searchStatus", defaultValue = "all") String searchStatus,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord) {
		
		// paging 처리를 하기 위해 전체 회원 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
		int totalCount = service.selectTotalCount(searchItem, searchStatus, searchWord);
		
		logger.info("granted selectTotalCount : {}", totalCount);
		
		// paging 처리를 위한 객체 생성
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, totalCount);
		
		// Member 목록 요청 by 관리자
		ArrayList<MemberVO> list = service.listMembers(searchItem, searchWord, searchStatus, navi.getStartRecord(), navi.getCountPerPage());
		
		model.addAttribute("list", list);
		model.addAttribute("navi", navi);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("searchStatus", searchStatus);
		model.addAttribute("searchItem", searchItem);
		
		
		return "manager/listMembers";
	}
	
	
	// 사용자 계정 비활성화
	@ResponseBody
	@RequestMapping(value = "/manager/deactivation", method = RequestMethod.POST)
	public int deactivation(String user_id) {
		return service.deactivation(user_id);
	}
	
	
	// 사용자 계정 활성화
	@ResponseBody
	@RequestMapping(value = "/manager/activation", method = RequestMethod.POST)
	public int activation(String user_id) {
		return service.activation(user_id);
	}
	
	
	// 사용자 계정 파트너 승격
	@ResponseBody
	@RequestMapping(value = "/manager/elevation", method = RequestMethod.POST)
	public int elevation(String user_id) {
		return service.elevation(user_id);
	}
	
	
	// 사용자 계정 파트너 강등
	@ResponseBody
	@RequestMapping(value = "/manager/demotion", method = RequestMethod.POST)
	public int demotion(String user_id) {
		return service.demotion(user_id);
	}
	
	
	// 회원가입 시 ID 중복 검사
	@ResponseBody
	@RequestMapping(value = "/member/idCheck", method = RequestMethod.POST)
	public String idCheck(String user_id) {
		MemberVO searchedMember = service.idCheck(user_id);
		if(searchedMember == null) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	
	
}
