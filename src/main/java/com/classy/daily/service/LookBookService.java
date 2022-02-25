package com.classy.daily.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.classy.daily.dao.LookBookDAO;
import com.classy.daily.util.FileService;
import com.classy.daily.vo.LinkVO;
import com.classy.daily.vo.LookBookVO;
import com.classy.daily.vo.PictureVO;
import com.classy.daily.vo.TagVO;

@Service
public class LookBookService {

	@Autowired
	private LookBookDAO dao;
	
	@Autowired
	private HttpSession session;
	
	private static final Logger logger = LoggerFactory.getLogger(LookBookService.class);

	private static final String UPLOAD_PATH = "/LookBookUploadFiles";
	
	
	// LookBook 게시글 INSERT 요청
	public int writeLookBook(LookBookVO lookbook, List<MultipartFile> multipartFile) {
				
		String user_id = (String)session.getAttribute("loginId");
		lookbook.setUser_id(user_id);
		
		int realCount = 0;
		int cnt = 0;
		
		int size = multipartFile.size();
		
		String originalfilename[] = new String[size];
		String savedfilename[] = new String[size];
		
		
		// upload라는 이름의 첨부파일이 있을 경우 파일 저장 실행
		try {
			
			if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
				
				for(MultipartFile file:multipartFile) {
					
					String original = file.getOriginalFilename();
					String saved = FileService.saveFile(file, UPLOAD_PATH);
					
					originalfilename[realCount] = original;
					savedfilename[realCount] = saved;
					
					realCount = realCount + 1;
			
				}
				
				lookbook.setOriginalfile(originalfilename);
				lookbook.setSavedfile(savedfilename);
		
			}
			
		} catch (Exception e) {
			
			System.out.println("오류 발생");
			return cnt;

		}
		
		try {
			cnt = dao.writeLookBook(lookbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}


	// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(String searchItem, String searchWord) {
		
		Map<String, String> search = new HashMap<String, String>();
		search.put("searchItem", searchItem);
		search.put("searchWord", searchWord);
		
		int result = 0;
		result = dao.selectTotalCount(search);
		
		return result;
	}


	// 룩북게시판 목록 조회
	public ArrayList<LookBookVO> listLookBookboards(String searchItem, String searchWord, int startRecord,
			int countPerPage) {
		
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("searchItem", searchItem);
		search.put("searchWord", searchWord);
		return dao.listLookBookboards(search, startRecord, countPerPage);
		
	}

	
	// 룩북게시판 하나 조회
	public LookBookVO listLookBookboards(int lb_boardnum) {
		
		LookBookVO oneLookBook = new LookBookVO();
		
		dao.updateHits(lb_boardnum);
		oneLookBook = dao.readLookBookboard(lb_boardnum);
		
		return oneLookBook;
		
	}


	// Ajax를 활용한 룩북게시판 태그 가져오기
	public ArrayList<TagVO> readTag(int lb_boardnum) {
		
		ArrayList<TagVO> list = new ArrayList<TagVO>();
		
		list = dao.readTag(lb_boardnum);
		
		return list;
	}


	// Ajax를 활용한 룩북게시판 링크 가져오기
	public ArrayList<LinkVO> readLink(int lb_boardnum) {

		ArrayList<LinkVO> list = new ArrayList<LinkVO>();
		
		list = dao.readLink(lb_boardnum);
		
		return list;
	}


	// Ajax를 활용한 룩북게시판 사진 가져오기
	public ArrayList<PictureVO> readPicture(int lb_boardnum) {
		
		ArrayList<PictureVO> list = new ArrayList<PictureVO>();
		
		list = dao.readPicture(lb_boardnum);
		
		return list;
	
	}


	// Ajax를 활용한 룩북게시판 추천수 올리기
	public int recommendThis(int lb_boardnum) {
		
		int cnt = 0;
		
		LookBookVO lookbook = new LookBookVO();
		
		String user_id = (String)session.getAttribute("loginId");
		
		lookbook.setUser_id(user_id);
		lookbook.setLb_boardnum(lb_boardnum);
		
		cnt = dao.recommendThis(lookbook);
		
		return cnt;
		
	}


	// Ajax를 활용한 룩북게시판 글 수 가져오기
	public int countLookBook() {
		
		String user_id = (String)session.getAttribute("loginId");
		
		int cnt = dao.countLookBook(user_id);
		
		return cnt;
		
	}


	// 인기글 가져오기 
	public ArrayList<LookBookVO> getPopular() {
		
		ArrayList<LookBookVO> list = dao.getPopular();
		
		return list;
		
		
	}


	// 게시글 삭제 요청
	public String deleteLookBook(int lb_boardnum) {
		
		String path = "";
		LookBookVO lookbook = new LookBookVO();
		
		ArrayList<PictureVO> list = dao.readPicture(lb_boardnum);
		ArrayList<String> savedfile = new ArrayList<String>();
		
		String user_id = (String)session.getAttribute("loginId");
		int user_state = (int)session.getAttribute("user_state");
		
		lookbook.setUser_id(user_id);
		lookbook.setLb_boardnum(lb_boardnum);
		lookbook.setUser_state(user_state);

		logger.info("ArrayList PictureVO : " + list);
		
		
		if(list != null) {
			for(int i = 0; i < list.size(); i++) {
				savedfile.add((list.get(i)).getSavedfile());   
			}
		}
		
		
		logger.info("ArrayList savedfile : " + savedfile);
		
		
		int cnt = 0;
		
		try {
			cnt = dao.deleteLookBook(lookbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(cnt > 0) {
			
			path = "redirect:/lookbook/listLookBooks";
			
			String fullPath = "";
			
			// 첨부파일이 있을 때만 삭제한다!
			
			for(int i = 0; i < savedfile.size(); i++) {
				String savedName = savedfile.get(i);
				fullPath = UPLOAD_PATH + "/" + savedName;
			
			
				if(savedfile != null) {
					boolean check = FileService.deleteFile(fullPath);
					
					if(check) {
						System.out.println("첨부파일 삭제 성공");
					} else {
						System.out.println("첨부파일 삭제 실패");
					}
				}
			}
		
			
		} else {
			path = "redirect:/lookbook/readLookBook?lb_boardnum=" + lb_boardnum;
		}
		
		return path;
		
	}


	
	
}

