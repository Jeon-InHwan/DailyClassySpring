package com.classy.daily.vo;

import lombok.Data;

@Data
public class LookBookVO {

	private int lb_boardnum;
	private String user_id;
	private String lb_title;
	private String lb_content;
	private int lb_likes;
	private int lb_hitcount;
	private String lb_regdate;
	private int user_state;
	
	private int tag_num;
	private String[] tag;
	
	private int link_num;
	private String[] link_add;
	
	private int pic_num;
	private String[] originalfile;
	private String[] savedfile;
	
	
	private String original;
	private String saved;
	
	
}


