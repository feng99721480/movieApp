package com.wiseweb.json;

import java.util.List;

import com.wiseweb.json.MovieResult.Movie;

public class CinemaResult {
	private String action;
	private String status;
	private List<Cinema> cinemas;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<Cinema> getCinemas() {
		return cinemas;
	}
	public void setCinemas(List<Cinema> cinemas) {
		this.cinemas = cinemas;
	}
	
	@Override
	public String toString() {
		return "CinemaResult [action=" + action + ", status=" + status
				+ ", cinemas=" + cinemas + "]";
	}

	public static class Cinema{
		private String cinemaAddress;//影院地址
		private int cinemaId;//影院id
		private String cinemaName;//影院名字
		private String cinemaTel;//影院电话
		private int cityId;//城市id
		private String cityName;//城市名称
		private int districtId;//区域id
		private String districtName;//区域名称
		private String latitude;//纬度
		private String longitude;//经度
		private int platform;//平台id
		private boolean preferential;//新用户专享
		private boolean imax;//imax
		private boolean seat;//座
		private boolean groupPurchase;//团购
		private double lowestPrice;//价格
		private String distance;//距离
		
		public Cinema(){
			
		}
		public Cinema(String cinemaAddress, int cinemaId, String cinemaName,
				String cinemaTel, int cityId, String cityName, int districtId,
				String districtName, String latitude, String longitude,
				int platform, boolean preferential, boolean imax, boolean seat,
				boolean groupPurchase, double lowestPrice, String distance) {
			super();
			this.cinemaAddress = cinemaAddress;
			this.cinemaId = cinemaId;
			this.cinemaName = cinemaName;
			this.cinemaTel = cinemaTel;
			this.cityId = cityId;
			this.cityName = cityName;
			this.districtId = districtId;
			this.districtName = districtName;
			this.latitude = latitude;
			this.longitude = longitude;
			this.platform = platform;
			this.preferential = preferential;
			this.imax = imax;
			this.seat = seat;
			this.groupPurchase = groupPurchase;
			this.lowestPrice = lowestPrice;
			this.distance = distance;
		}
		public String getCinemaAddress() {
			return cinemaAddress;
		}
		public void setCinemaAddress(String cinemaAddress) {
			this.cinemaAddress = cinemaAddress;
		}
		public int getCinemaId() {
			return cinemaId;
		}
		public void setCinemaId(int cinemaId) {
			this.cinemaId = cinemaId;
		}
		public String getCinemaName() {
			return cinemaName;
		}
		public void setCinemaName(String cinemaName) {
			this.cinemaName = cinemaName;
		}
		public String getCinemaTel() {
			return cinemaTel;
		}
		public void setCinemaTel(String cinemaTel) {
			this.cinemaTel = cinemaTel;
		}
		public int getCityId() {
			return cityId;
		}
		public void setCityId(int cityId) {
			this.cityId = cityId;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		public int getDistrictId() {
			return districtId;
		}
		public void setDistrictId(int districtId) {
			this.districtId = districtId;
		}
		public String getDistrictName() {
			return districtName;
		}
		public void setDistrictName(String districtName) {
			this.districtName = districtName;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public int getPlatform() {
			return platform;
		}
		public void setPlatform(int platform) {
			this.platform = platform;
		}
		public boolean isPreferential() {
			return preferential;
		}
		public void setPreferential(boolean preferential) {
			this.preferential = preferential;
		}
		public boolean isImax() {
			return imax;
		}
		public void setImax(boolean imax) {
			this.imax = imax;
		}
		public boolean isSeat() {
			return seat;
		}
		public void setSeat(boolean seat) {
			this.seat = seat;
		}
		public boolean isGroupPurchase() {
			return groupPurchase;
		}
		public void setGroupPurchase(boolean groupPurchase) {
			this.groupPurchase = groupPurchase;
		}
		public double getLowestPrice() {
			return lowestPrice;
		}
		public void setLowestPrice(double lowestPrice) {
			this.lowestPrice = lowestPrice;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		@Override
		public String toString() {
			return "Cinema [cinemaAddress=" + cinemaAddress + ", cinemaId="
					+ cinemaId + ", cinemaName=" + cinemaName + ", cinemaTel="
					+ cinemaTel + ", cityId=" + cityId + ", cityName="
					+ cityName + ", districtId=" + districtId
					+ ", districtName=" + districtName + ", latitude="
					+ latitude + ", longitude=" + longitude + ", platform="
					+ platform + ", preferential=" + preferential + ", imax="
					+ imax + ", seat=" + seat + ", groupPurchase="
					+ groupPurchase + ", lowestPrice=" + lowestPrice
					+ ", distance=" + distance + "]";
		}
		
		
	}
	
}
