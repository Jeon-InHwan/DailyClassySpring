package com.classy.daily.vo;

import lombok.Data;

@Data
public class MemberVO {

	private String user_id;
	private String user_pw;
	private String user_name;
	private int user_state;
	private String user_regdate;
	private int link_hits;
	
}

