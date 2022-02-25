package com.classy.daily.controllers;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.classy.daily.service.LookBookService;
import com.classy.daily.util.MediaUtils;
import com.classy.daily.util.PageNavigator;
import com.classy.daily.vo.LinkVO;
import com.classy.daily.vo.LookBookVO;
import com.classy.daily.vo.PictureVO;
import com.classy.daily.vo.TagVO;
import org.apache.commons.io.IOUtils;



@Controller
public class LookBookController {

	
	private static final Logger logger = LoggerFactory.getLogger(LookBookController.class);
	
	@Autowired
	private LookBookService service;
	
	private final int countPerPage = 10;
	private final int pagePerGroup = 5;
	
	private static final String UPLOAD_PATH = "/LookBookUploadFiles";
	
	
	// 룩북 게시판 상세 읽기
	@RequestMapping(value = "/lookbook/readLookBook", method = RequestMethod.GET)
	public String readLookBook(int lb_boardnum, Model model) {
		
		logger.info("lb_boardnum for ReadLookBook : {}", lb_boardnum);
		
		// 룩북게시판 하나 조회
		LookBookVO oneLookBook = service.listLookBookboards(lb_boardnum);
		
		model.addAttribute("lookbook", oneLookBook);
		
		logger.info("ReadLookBook VO : {}", oneLookBook);
		
		return "lookbook/readLookBook";
	}
	
	
	// LookBook 작성 페이지로 이동
	@RequestMapping(value = "/lookbook/writeLookBookForm", method = RequestMethod.GET)
	public String writeLookBookForm() {
		return "lookbook/writeLookBookForm";
	}
	
	
	// LookBook 게시글 리스트 페이지로 이동
	@RequestMapping(value = "/lookbook/listLookBooks", method = RequestMethod.GET)
	public String listLookBooks(Model model, 
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchItem", defaultValue = "lb_title") String searchItem,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord) {
		
		// paging 처리를 하기 위해 전체 request 수를 DB에서 조회해오기 (검색한 경우도 고려해야함)
		int totalCount = service.selectTotalCount(searchItem, searchWord);
		
		logger.info("select Total freeboard Count : {}", totalCount);
		logger.info("searchItem : {}", searchItem);
		logger.info("searchWord : {}", searchWord);
		
		// paging 처리를 위한 객체 생성
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, totalCount);
		
		
		// 룩북게시판 목록 조회
		ArrayList<LookBookVO> list = service.listLookBookboards(searchItem, searchWord, navi.getStartRecord(), navi.getCountPerPage());
		
		logger.info("select all from lookbookBoard : {}", list);
		logger.info("navi : {}", navi);
		
		model.addAttribute("list", list);
		model.addAttribute("navi", navi);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("searchItem", searchItem);
		
		return "lookbook/listLookBooks";
	
	}
	
	
	// LookBook 게시글 INSERT 요청
	@ResponseBody
	@RequestMapping(value = "/lookbook/writeLookBook", method = RequestMethod.POST)
	public int writeLookBook
		(@RequestPart(value = "lookbook") LookBookVO lookbook, @RequestPart(value = "article_file") List<MultipartFile> multipartFile) {
		
		logger.info("granted LookBookVO : {}", lookbook);
		logger.info("granted multipartFile : {}", multipartFile);
		logger.info("granted LookBookVO for multipartFile : {}", lookbook);
		
		return service.writeLookBook(lookbook, multipartFile);
		
	}
	

	// Ajax를 활용한 룩북게시판 태그 가져오기
	@ResponseBody
	@RequestMapping(value = "/lookbook/readTag", method = RequestMethod.POST)
	public ArrayList<TagVO> readTag(int lb_boardnum) {
		
		ArrayList<TagVO> list = service.readTag(lb_boardnum);
		
		logger.info("granted ArrayList<TagVO> : {}", list);
		
		return list;
	}
	
	
	// Ajax를 활용한 룩북게시판 링크 가져오기
	@ResponseBody
	@RequestMapping(value = "/lookbook/readLink", method = RequestMethod.POST)
	public ArrayList<LinkVO> readLink(int lb_boardnum) {
		
		ArrayList<LinkVO> list = service.readLink(lb_boardnum);
		
		logger.info("granted ArrayList<LinkVO> : {}", list);
		
		return list;
	}
	
	
	// Ajax를 활용한 룩북게시판 사진 가져오기
	@ResponseBody
	@RequestMapping(value = "/lookbook/readPicture", method = RequestMethod.POST)
	public ArrayList<PictureVO> readPicture(int lb_boardnum) {
		
		ArrayList<PictureVO> list = service.readPicture(lb_boardnum);
		
		logger.info("granted ArrayList<LinkVO> : {}", list);
		
		return list;
	}
	
	
	// 업로드된 파일 처리
	@RequestMapping(value = "/lookbook/display", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(@RequestParam("name") String fileName)throws Exception{
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		logger.info("FILE NAME : " + fileName);
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(UPLOAD_PATH + "/" + fileName);
			
			//step: change HttpHeader ContentType
			if(mType != null) {
				//image file(show image)
				headers.setContentType(mType);
			}else {
				//another format file(download file)
				fileName = fileName.substring(fileName.indexOf("_")+1);//original file Name
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
			return entity;
		
	}
	
	
	// Ajax를 활용한 룩북게시판 추천수 올리기
	@ResponseBody
	@RequestMapping(value = "/lookbook/recommendThis", method = RequestMethod.POST)
	public int recommendThis(int lb_boardnum) {
		
		int cnt = service.recommendThis(lb_boardnum);
		
		return cnt;
	}

	
	// Ajax를 활용한 룩북게시판 글 수 가져오기
	@ResponseBody
	@RequestMapping(value = "/lookbook/countLookBook", method = RequestMethod.POST)
	public int countLookBook() {
		
		int cnt = service.countLookBook();
		
		logger.info("granted LookBookCount : {}", cnt);
		
		return cnt;
	}
	
	
	// 게시글 삭제 요청
	@RequestMapping(value = "lookbook/deleteLookBook", method = RequestMethod.GET)
	public String deleteLookBook(int lb_boardnum) {
		
		return service.deleteLookBook(lb_boardnum);
		
	}
	
	
	
	
}
