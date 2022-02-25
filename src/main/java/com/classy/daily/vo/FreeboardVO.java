package com.classy.daily.vo;

import lombok.Data;

@Data
public class FreeboardVO {

	public int fb_boardnum;
	public String user_id;
	public String fb_title;
	public String fb_content;
	public int fb_likes;
	public int fb_hitcount;
	public String fb_regdate;
	public int user_state;
	
}
