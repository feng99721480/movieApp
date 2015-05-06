package com.wiseweb.bean;

import android.graphics.Bitmap;

public class FilmInfo{

	private String filmName; // 
	private String genre; // 类型
	private String director; // 
	private String starring; // 
	private String region; // 
	private String duration; // 
	private String outline;  //
	private String summary; // 
	private String actionTime; // 
	private Bitmap imgId;         //
	private String score;      //
	private String iMax;       //iMax3D,iMax2D,3D
	private boolean newMovie;   // 
	private String show;       //
	
	/*
	 * constructor
	 */
	public FilmInfo(){
		
	}
	public FilmInfo(String filmName, String genre, String director,
			String starring, String region, String duration, String outline,
			String summary, String actionTime, Bitmap imgId, String score,
			String iMax, boolean newMovie, String show) {
		super();
		this.filmName = filmName;
		this.genre = genre;
		this.director = director;
		this.starring = starring;
		this.region = region;
		this.duration = duration;
		this.outline = outline;
		this.summary = summary;
		this.actionTime = actionTime;
		this.imgId = imgId;
		this.score = score;
		this.iMax = iMax;
		this.newMovie = newMovie;
		this.show = show;
	}
	
	public FilmInfo(String filmName, String outline, Bitmap imgId, String score,
			String show) {
		super();
		this.filmName = filmName;
		this.outline = outline;
		this.imgId = imgId;
		this.score = score;
		this.show = show;
	}

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStarring() {
		return starring;
	}

	public void setStarring(String starring) {
		this.starring = starring;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getActionTime() {
		return actionTime;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}

	/**
	 * @return the outline
	 */
	public String getOutline() {
		return outline;
	}

	/**
	 * @param outline the outline to set
	 */
	public void setOutline(String outline) {
		this.outline = outline;
	}

	/**
	 * @return the imgId
	 */
	public Bitmap getImgId() {
		return imgId;
	}

	/**
	 * @param imgId the imgId to set
	 * 参数为Bitmap类型
	 */
	public void setImgId(Bitmap imgId) {
		this.imgId = imgId;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	public String getiMax() {
		return iMax;
	}

	public void setiMax(String iMax) {
		this.iMax = iMax;
	}

	public boolean isNewMovie() {
		return newMovie;
	}

	public void setNewMovie(boolean newMovie) {
		this.newMovie = newMovie;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	@Override
	public String toString() {
		return "FilmInfo [filmName=" + filmName + ", genre=" + genre
				+ ", director=" + director + ", starring=" + starring
				+ ", region=" + region + ", duration=" + duration
				+ ", outline=" + outline + ", summary=" + summary
				+ ", actionTime=" + actionTime + ", imgId=" + imgId
				+ ", score=" + score + ", iMax=" + iMax + ", newMovie="
				+ newMovie + ", show=" + show + "]";
	}	
}
