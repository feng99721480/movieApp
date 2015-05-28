package com.wiseweb.json;

import java.util.List;

public class City {
	private String tab;
	private String text;
	private List<Group> group;   //城市信息
	
	
	
	public String getTab() {
		return tab;
	}



	public void setTab(String tab) {
		this.tab = tab;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public List<Group> getGroup() {
		return group;
	}



	public void setGroup(List<Group> group) {
		this.group = group;
	}



	@Override
	public String toString() {
		return "City [tab=" + tab + ", text=" + text + ", group=" + group + "]";
	}



	public static class Group{
		public List<CityList> list;
		
		
		public List<CityList> getList() {
			return list;
		}


		public void setList(List<CityList> list) {
			this.list = list;
		}
		

		@Override
		public String toString() {
			return "Group [list=" + list + "]";
		}


		public static class CityList{
			public String cityId;
			public String first;
			public String cityName;
			public String getCityId() {
				return cityId;
			}
			public void setCityId(String cityId) {
				this.cityId = cityId;
			}
			public String getFirst() {
				return first;
			}
			public void setFirst(String first) {
				this.first = first;
			}
			public String getCityName() {
				return cityName;
			}
			public void setCityName(String cityName) {
				this.cityName = cityName;
			}
			@Override
			public String toString() {
				return "CityList [cityId=" + cityId + ", first=" + first
						+ ", cityName=" + cityName + "]";
			}
			
		}
	}
}
