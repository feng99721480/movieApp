package com.wiseweb.constant;

public class Constant {
	//
	public static final int BTN_FLAG_FILM = 0x01;
	public static final int BTN_FLAG_CINEMA = 0x01 << 1;
	public static final int BTN_FLAG_COMMUNITY = 0x01 << 2;
	public static final int BTN_FLAG_MINE = 0x01 << 3;

	// Fragment
	public static final String FRAGMENT_FLAG_FILM = "电影";
	public static final String FRAGMENT_FLAG_CINEMA = "影院";
	public static final String FRAGMENT_FLAG_COMMUNITY = "社区";
	public static final String FRAGMENT_FLAG_MINE = "我的";
	public static final String FRAGMENT_FLAG_SIMPLE = "simple";
	//
	public static final String[] FILM_TYPE = { "剧情", "喜剧", "爱情", "动画", "动作",
			"恐怖", "惊悚", "悬疑", "冒险", "科幻", "犯罪", "战争", "纪录片", "其它" };
	public static final String[] FILM_AREA = { "美国", "大陆", "港台", "法国", "英国",
			"德国", "日本", "韩国", "印度", "̩泰国", "其它" };
//	public static final String baseURL = "http://192.168.0.141:4000/appAPI?";
//	public static final String orderURL = "http://192.168.0.203:4000/appAPI?";
	public static final String baseURL = "http://192.168.0.203:4000/appAPI?";
	public static final String[] CONTENT = new String[] { "3月1日", "3月1日",
			"3月1日", "3月1日", "3月1日", "3月1日", "3月1日", "3月1日" };
}
