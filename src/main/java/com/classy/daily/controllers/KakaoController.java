package com.classy.daily.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.classy.daily.service.LookBookService;
import com.classy.daily.service.MemberService;
import com.classy.daily.vo.LookBookVO;
import com.classy.daily.vo.MemberVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



@Controller
public class KakaoController {
	
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private LookBookService lb_service;
	
	@Autowired
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
	

	@RequestMapping(value = "/login/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(
		HttpServletRequest request) throws Exception {
		String reqUrl = 
			"https://kauth.kakao.com/oauth/authorize"
			+ "?client_id=a2b8378625907d2264657e120498342c"
			+ "&redirect_uri=http://localhost:8888/login/oauth_kakao"
			+ "&response_type=code";
		
		return reqUrl;
	}
	
	// 카카오 연동정보 조회
	@RequestMapping(value = "/login/oauth_kakao")
	public String oauthKakao(
			@RequestParam(value = "code", required = false) String code
			, Model model, HttpSession session) throws Exception {
		
		// 랜덤 문자열을 생성
		String uid = UUID.randomUUID().toString();
		
		String result = "";
		String redirect = "";
		int count = -1;
		
		// 인기글 가져오기 
		ArrayList<LookBookVO> list = lb_service.getPopular();
		model.addAttribute("list", list);

		System.out.println("#########" + code);
        String access_Token = getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);
        
        
        HashMap<String, Object> userInfo = getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        
        String id = "";
        
        String temp = (String)userInfo.get("email");
        
        	
        int subst = temp.lastIndexOf("@");
        id = temp.substring(0, subst);
        System.out.println(id);
        count++;
   
       
        JSONObject kakaoInfo =  new JSONObject(userInfo);
        //model.addAttribute("kakaoInfo", kakaoInfo);
  
        MemberVO searchedMeber = new MemberVO();
        searchedMeber = service.idCheck(id);
        
        MemberVO updateMember = new MemberVO();
        MemberVO joinMember = new MemberVO();
        
        if(searchedMeber == null) {
        	joinMember.setUser_id(id);
        	joinMember.setUser_name((String)userInfo.get("nickname"));
        	joinMember.setUser_pw(uid);
        	result = service.join(joinMember);
        	System.out.println(joinMember);
        	if(redirect.equals("redirect:/member/joinForm")) {
        		return "index";
        	} else {
            	session.setAttribute("user_state", 0);
        		session.setAttribute("loginId", id);
                session.setAttribute("loginNm", userInfo.get("nickname"));
                session.setAttribute("kakao", "kakao");
                session.setAttribute("access_token", access_Token);
        		return "member/afterLogin";
        	}
        } else {
        	updateMember.setUser_id(id);
        	System.out.println("두 번째 : " + id);
        	System.out.println("두 번째 : " + updateMember);
        	updateMember.setUser_pw(uid);
        	updateMember.setUser_name((String)userInfo.get("nickname"));
        	redirect = service.updateMember(updateMember);
        	System.out.println("문제 : " + updateMember);
        	if(redirect.equals("redirect:/member/updateMemberForm")) {
        		return "index";
        	} else {
        		int user_state = searchedMeber.getUser_state();
            	session.setAttribute("user_state", user_state);
        		session.setAttribute("loginId", id);
                session.setAttribute("loginNm", userInfo.get("nickname"));
                session.setAttribute("kakao", "kakao");
                session.setAttribute("access_token", access_Token);
        		return "member/afterLogin";
        	}
        	
        }
        
        
        //본인 원하는 경로 설정
	}
	
    //토큰발급
	public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=a2b8378625907d2264657e120498342c");  //본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:8888/login/oauth_kakao");     // 본인이 설정해 놓은 경로
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            
            
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
       
            e.printStackTrace();
        }

        return access_Token;
    }
	
    //유저정보조회
    public HashMap<String, Object> getUserInfo (String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
         
            e.printStackTrace();
        }

        return userInfo;
    }

	private JsonElement parse(String result) {
		
		return null;
	}
	
	@RequestMapping(value="/kakao/logout")
	public String access(HttpSession session) throws IOException {
		
		httpConnection conn = httpConnection.getInstance();
		
		logger.info("/kakao/logout : " + conn);
		
		String access_token = (String)session.getAttribute("access_token");
		
		logger.info("/kakao/logout access_token : " + access_token);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Authorization", "Bearer "+ access_token);
		
		System.out.println("map : " + map);
		
		// String temp = "https://kauth.kakao.com/oauth/logout?client_id=" + access_token + "&logout_redirect_uri=http://localhost:8888";
		
		
		String result2 = conn.HttpPostConnection("https://kapi.kakao.com/v1/user/unlink", map).toString();
		String result = conn.HttpPostConnection("https://kapi.kakao.com/v1/user/logout", map).toString();
		
		System.out.println(result2);
		System.out.println(result);
		
		
		/*
		Cookie[] cookies = request.getCookies();
		
		logger.info("cookies : " + cookies);
		
		for(Cookie cookie: cookies) {
			System.out.println("쿠키 명 : " + cookie.getName());
			System.out.println("쿠키 값 : " + cookie.getValue());
		}
		*/
		
		
		return "redirect:/";
	}
	
 }
