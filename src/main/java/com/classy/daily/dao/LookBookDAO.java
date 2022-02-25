package com.classy.daily.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.classy.daily.vo.LinkVO;
import com.classy.daily.vo.LookBookVO;
import com.classy.daily.vo.PictureVO;
import com.classy.daily.vo.TagVO;


@Repository
public class LookBookDAO {

	@Autowired
	private SqlSession session;
	
	private static final Logger logger = LoggerFactory.getLogger(LookBookDAO.class);
	
	
	// LookBook 게시글 INSERT 요청
	@Transactional(rollbackFor = {Exception.class})
	public int writeLookBook(LookBookVO lookbook) throws Exception {
		
		int cnt1 = 0;
		
		
		String[] temp1 = lookbook.getOriginalfile();
		String[] temp2 = lookbook.getSavedfile();
		List<LookBookVO> list = new ArrayList<LookBookVO>(); 
		
		for(int i = 0; i < temp1.length; i++) {
			LookBookVO vo = new LookBookVO();
			vo.setOriginal(temp1[i]);
			vo.setSaved(temp2[i]);
			list.add(vo);
		}
		
		
		logger.info("granted ArrayList<LookBookVO> : {}", list);
		
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			cnt1 = mapper.writeLookBook(lookbook);
			mapper.tagInsert(lookbook);
			
			if(lookbook.getLink_add().length >= 1) {
				mapper.linkInsert(lookbook);
			}
			
			mapper.picInsert(list);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		
		return cnt1;
	}


	// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
	public int selectTotalCount(Map<String, String> search) {
		
		int result = 0;
		 
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			 result = mapper.selectTotalCount(search);
			} catch(Exception e) {
			 e.printStackTrace();
		}
			return result;
	}


	// 룩북게시판 목록 조회
	public ArrayList<LookBookVO> listLookBookboards(Map<String, Object> search, int startRecord, int countPerPage) {
		
		ArrayList<LookBookVO> list = null;
		
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			RowBounds rb = new RowBounds(startRecord, countPerPage);
			list = mapper.listLookBookboards(search, rb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	// 글 번호를 토대로 룩북 게시판 조회수 1 업데이트
	public void updateHits(int lb_boardnum) {
		
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			 mapper.updateHits(lb_boardnum);
		} catch(Exception e) {
			 e.printStackTrace();
		}
	}


	// 룩북게시판 하나 조회
	public LookBookVO readLookBookboard(int lb_boardnum) {
		
		LookBookVO oneLookBook = new LookBookVO();
		
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			oneLookBook = mapper.readLookBookboard(lb_boardnum);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return oneLookBook;
	}


	// Ajax를 활용한 룩북게시판 태그 가져오기
	public ArrayList<TagVO> readTag (int lb_boardnum) {
		
		ArrayList<TagVO> list = new ArrayList<TagVO>();
		
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			list = mapper.readTag(lb_boardnum);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return list;
	
	}


	// Ajax를 활용한 룩북게시판 링크 가져오기
	public ArrayList<LinkVO> readLink(int lb_boardnum) {

		ArrayList<LinkVO> list = new ArrayList<LinkVO>();
		
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			list = mapper.readLink(lb_boardnum);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return list;
	}


	// Ajax를 활용한 룩북게시판 사진 가져오기
	public ArrayList<PictureVO> readPicture(int lb_boardnum) {

		ArrayList<PictureVO> list = new ArrayList<PictureVO>();
		
		try {
			LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			list = mapper.readPicture(lb_boardnum);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return list;
		
	}


	// Ajax를 활용한 룩북게시판 추천수 올리기
	public int recommendThis(LookBookVO lookbook) {
		
		int cnt = 0;
		
		try {
			 LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			 cnt = mapper.recommendThis(lookbook);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return cnt;
	}


	// Ajax를 활용한 룩북게시판 글 수 가져오기
	public int countLookBook(String user_id) {

		int cnt = 0;
		
		try {
			 LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			 cnt = mapper.countLookBook(user_id);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return cnt;
	}


	// 인기글 가져오기 
	public ArrayList<LookBookVO> getPopular() {

		ArrayList<LookBookVO> list = null;
		
		try {
			 LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			 RowBounds rb = new RowBounds(0, 5);
			 list = mapper.getPopular(rb);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return list;
	}


	// 게시글 삭제 요청
	@Transactional(rollbackFor = {Exception.class})
	public int deleteLookBook(LookBookVO lookbook) throws Exception {
		
		int cnt = 0;
		
		try {
			 LookBookMapper mapper = session.getMapper(LookBookMapper.class);
			 cnt = mapper.deleteLookBook(lookbook);
		} catch(Exception e) {
			 e.printStackTrace();
		}
		
		return cnt;
		
	}

	
	
	
	
}
