package com.wiseweb.bean;

public class CinemaInfo {
	private String cinemaName; // 
	private boolean preferential; // 
	private boolean imax; // 
	private boolean seat; // 
	private boolean groupPurchase; // 
	private double lowestPrice; //
	private String cinemaAddress; //
	private String distance; // 

	/*
	 * constructor
	 */
	public CinemaInfo(){
		
	}
	public CinemaInfo(String cinemaName, boolean preferential, boolean imax,
			boolean seat, boolean groupPurchase, double lowestPrice,
			String cinemaAddress, String distance) {
		super();
		this.cinemaName = cinemaName;
		this.preferential = preferential;
		this.imax = imax;
		this.seat = seat;
		this.groupPurchase = groupPurchase;
		this.lowestPrice = lowestPrice;
		this.cinemaAddress = cinemaAddress;
		this.distance = distance;
	}

	// Getters and Setters
	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
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

	public String getCinemaAddress() {
		return cinemaAddress;
	}

	public void setCinemaAddress(String cinemaAddress) {
		this.cinemaAddress = cinemaAddress;
	}
	
	
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "CinemaInfo [cinemaName=" + cinemaName + ", preferential="
				+ preferential + ", imax=" + imax + ", seat=" + seat
				+ ", groupPurchase=" + groupPurchase + ", lowestPrice="
				+ lowestPrice + ", cinemaAddress=" + cinemaAddress
				+ ", distance=" + distance + "]";
	}

	
}
