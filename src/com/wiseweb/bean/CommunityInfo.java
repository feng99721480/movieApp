package com.wiseweb.bean;

public class CommunityInfo {
	private String communityName;
	private String outline;
	private int imgId;
	private int newPostNum;
	public CommunityInfo(String communityName, String outline, int imgId,
			int newPostNum) {
		super();
		this.communityName = communityName;
		this.outline = outline;
		this.imgId = imgId;
		this.newPostNum = newPostNum;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getOutline() {
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public int getNewPostNum() {
		return newPostNum;
	}
	public void setNewPostNum(int newPostNum) {
		this.newPostNum = newPostNum;
	}
	@Override
	public String toString() {
		return "CommunityInfo [communityName=" + communityName + ", outline="
				+ outline + ", imgId=" + imgId + ", newPostNum=" + newPostNum
				+ "]";
	}
	
}
