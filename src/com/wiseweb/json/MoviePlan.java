package com.wiseweb.json;

public class MoviePlan {
	public long cinemaId; // 影院id
	public String featureTime; // 开场时间
	public String hallName; // 厅名
	public String language; // 场次语言
	public long movieId; // 影片id
	public long planId; // 场次ID
	public String price; // 场次售价
	public String screenType; // 场次屏幕类型
	public String standardPrice; // 影院水牌价
	public String subtitle; // 场次字幕

	public long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getFeatureTime() {
		return featureTime;
	}

	public void setFeatureTime(String featureTime) {
		this.featureTime = featureTime;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}

	public String getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@Override
	public String toString() {
		return "MoviePlan [cinemaId=" + cinemaId + ", featureTime="
				+ featureTime + ", hallName=" + hallName + ", language="
				+ language + ", movieId=" + movieId + ", planId=" + planId
				+ ", price=" + price + ", screenType=" + screenType
				+ ", standardPrice=" + standardPrice + ", subtitle=" + subtitle
				+ "]";
	}

}
