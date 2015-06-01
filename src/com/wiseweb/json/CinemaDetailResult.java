package com.wiseweb.json;

import java.util.Arrays;
import java.util.List;

public class CinemaDetailResult {
	private String action;
	private String status;
	private CinemaDetail cinema;

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
	
	

	public CinemaDetail getCinema() {
		return cinema;
	}

	public void setCinema(CinemaDetail cinema) {
		this.cinema = cinema;
	}



	@Override
	public String toString() {
		return "CinemaDetailResult [action=" + action + ", status=" + status
				+ ", cinema=" + cinema + "]";
	}



	public static class CinemaDetail {
		private String cinemaAddress;// 影院地址
		private int cinemaId;// 影院id
		private String cinemaIntro;// 影院介绍
		private String cinemaName;// 影院名称
		private String cinemaTel;// 影院电话
		private int cityId;// 城市id
		private String cityName;// 城市名称
		private int districtId;// 区域id
		private String districtName;// 区域名称
		private String drivePath;// 乘车线路
		private String[] galleries;// 影院图片集合
		private int hot;// 热度
		private String latitude;// 纬度
		private String logo;// 影院logo
		private String longitude;// 经度
		private int machineType;// 取票机类型
		private String openTime;// 营业时间
		private String[] score;// 评分
		private String visualEffect;//视听效果
		private String cinemaEnvrionment;//影院环境
		private String surrondingRestaurants;//周边餐饮
		private String fetchTicket;// 取票
		private String imax;// imax
		private String glass;// 3d眼镜
		private String park;// 停车
		private String lovers;// 情侣座
		private String children;// 小孩优惠
		private String card;// 刷卡
		private String wifi;// 无线网
		private String rest;// 休息区
		private String refund;// 退票退款
		private String coupon;// 优惠信息

		public String getVisualEffect() {
			return visualEffect;
		}

		public void setVisualEffect(String visualEffect) {
			this.visualEffect = visualEffect;
		}

		public String getCinemaEnvrionment() {
			return cinemaEnvrionment;
		}

		public void setCinemaEnvrionment(String cinemaEnvrionment) {
			this.cinemaEnvrionment = cinemaEnvrionment;
		}

		public String getSurrondingRestaurants() {
			return surrondingRestaurants;
		}

		public void setSurrondingRestaurants(String surrondingRestaurants) {
			this.surrondingRestaurants = surrondingRestaurants;
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

		public String getCinemaIntro() {
			return cinemaIntro;
		}

		public void setCinemaIntro(String cinemaIntro) {
			this.cinemaIntro = cinemaIntro;
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

		public String getDrivePath() {
			return drivePath;
		}

		public void setDrivePath(String drivePath) {
			this.drivePath = drivePath;
		}

		public String[] getGalleries() {
			return galleries;
		}

		public void setGalleries(String[] galleries) {
			this.galleries = galleries;
		}

		public int getHot() {
			return hot;
		}

		public void setHot(int hot) {
			this.hot = hot;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLogo() {
			return logo;
		}

		public void setLogo(String logo) {
			this.logo = logo;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public int getMachineType() {
			return machineType;
		}

		public void setMachineType(int machineType) {
			this.machineType = machineType;
		}

		public String getOpenTime() {
			return openTime;
		}

		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}

		

		public String[] getScore() {
			return score;
		}

		public void setScore(String[] score) {
			this.score = score;
		}

		public String getFetchTicket() {
			return fetchTicket;
		}

		public void setFetchTicket(String fetchTicket) {
			this.fetchTicket = fetchTicket;
		}

		public String getImax() {
			return imax;
		}

		public void setImax(String imax) {
			this.imax = imax;
		}

		public String getGlass() {
			return glass;
		}

		public void setGlass(String glass) {
			this.glass = glass;
		}

		public String getPark() {
			return park;
		}

		public void setPark(String park) {
			this.park = park;
		}

		public String getLovers() {
			return lovers;
		}

		public void setLovers(String lovers) {
			this.lovers = lovers;
		}

		public String getChildren() {
			return children;
		}

		public void setChildren(String children) {
			this.children = children;
		}

		public String getCard() {
			return card;
		}

		public void setCard(String card) {
			this.card = card;
		}

		public String getWifi() {
			return wifi;
		}

		public void setWifi(String wifi) {
			this.wifi = wifi;
		}

		public String getRest() {
			return rest;
		}

		public void setRest(String rest) {
			this.rest = rest;
		}

		public String getRefund() {
			return refund;
		}

		public void setRefund(String refund) {
			this.refund = refund;
		}

		public String getCoupon() {
			return coupon;
		}

		public void setCoupon(String coupon) {
			this.coupon = coupon;
		}

		@Override
		public String toString() {
			return "CinemaDetail [cinemaAddress=" + cinemaAddress
					+ ", cinemaId=" + cinemaId + ", cinemaIntro=" + cinemaIntro
					+ ", cinemaName=" + cinemaName + ", cinemaTel=" + cinemaTel
					+ ", cityId=" + cityId + ", cityName=" + cityName
					+ ", districtId=" + districtId + ", districtName="
					+ districtName + ", drivePath=" + drivePath
					+ ", galleries=" + Arrays.toString(galleries) + ", hot="
					+ hot + ", latitude=" + latitude + ", logo=" + logo
					+ ", longitude=" + longitude + ", machineType="
					+ machineType + ", openTime=" + openTime + ", score="
					+ Arrays.toString(score) + ", visualEffect=" + visualEffect
					+ ", cinemaEnvrionment=" + cinemaEnvrionment
					+ ", surrondingRestaurants=" + surrondingRestaurants
					+ ", fetchTicket=" + fetchTicket + ", imax=" + imax
					+ ", glass=" + glass + ", park=" + park + ", lovers="
					+ lovers + ", children=" + children + ", card=" + card
					+ ", wifi=" + wifi + ", rest=" + rest + ", refund="
					+ refund + ", coupon=" + coupon + "]";
		}

		

		

	}
}
