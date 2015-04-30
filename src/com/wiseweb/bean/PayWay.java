package com.wiseweb.bean;

public class PayWay {
	private int imageId;
	private String name;
	private String desc;
	
	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "PayWay [imageId=" + imageId + ", name=" + name + ", desc="
				+ desc + "]";
	}
}
