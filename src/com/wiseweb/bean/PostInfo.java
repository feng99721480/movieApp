package com.wiseweb.bean;

public class PostInfo {
	private String postFilmName;
	private String postTitle;
	private String postContent;
	private String postLatest;
	private int    postReplyNum;
	public PostInfo(String postFilmName, String postTitle, String postContent,
			String postLatest, int postReplyNum) {
		super();
		this.postFilmName = postFilmName;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postLatest = postLatest;
		this.postReplyNum = postReplyNum;
	}
	public String getPostFilmName() {
		return postFilmName;
	}
	public void setPostFilmName(String postFilmName) {
		this.postFilmName = postFilmName;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getPostLatest() {
		return postLatest;
	}
	public void setPostLatest(String postLatest) {
		this.postLatest = postLatest;
	}
	public int getPostReplyNum() {
		return postReplyNum;
	}
	public void setPostReplyNum(int postReplyNum) {
		this.postReplyNum = postReplyNum;
	}
	@Override
	public String toString() {
		return "PostInfo [postFilmName=" + postFilmName + ", postTitle="
				+ postTitle + ", postContent=" + postContent + ", postLatest="
				+ postLatest + ", postReplyNum=" + postReplyNum + "]";
	}
	
}
