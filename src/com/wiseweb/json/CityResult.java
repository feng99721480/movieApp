package com.wiseweb.json;

import java.util.List;

public class CityResult {
	private String action;
	private String status;
	private List<City> citis;
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
	public List<City> getCitis() {
		return citis;
	}
	public void setCitis(List<City> citis) {
		this.citis = citis;
	}
	@Override
	public String toString() {
		return "CityResult [action=" + action + ", status=" + status
				+ ", citis=" + citis + "]";
	}
	
	
	
}
