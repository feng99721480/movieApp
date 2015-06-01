package com.wiseweb.bean;

public class CinemaTicketInfo {
	private int imageDel;
	private int imageid;
	private String cinemaTicket;
	private String ticketCount;
	private String ticketPrice;
	private int state;
	
	public CinemaTicketInfo() {
	}

	public CinemaTicketInfo(int imageDel ,int imageid, String cinemaTicket,
			String ticketCount, String ticketPrice ,int state) {
		this.imageDel = imageDel;
		this.imageid = imageid;
		this.cinemaTicket = cinemaTicket;
		this.ticketCount = ticketCount;
		this.ticketPrice = ticketPrice;
		this.state = state;
	}
	
	public int getImageid() {
		return imageid;
	}

	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	public String getCinemaTicket() {
		return cinemaTicket;
	}

	public void setCinemaTicket(String cinemaTicket) {
		this.cinemaTicket = cinemaTicket;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	public int getImageDel() {
		return imageDel;
	}

	public void setImageDel(int imageDel) {
		this.imageDel = imageDel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "TicketInfo [imageDel=" + imageDel +",imageid=" + imageid + ", cinemaTicket="
				+ cinemaTicket + ", ticketCount=" + ticketCount
				+ ", ticketPrice=" + ticketPrice + "]";
	}

}
