package com.wiseweb.json;

import java.util.List;

public class MovieComingResult {
	private String action;     //接口名称
	private String status;     //状态码
	private List<MovieComing> movies;   //结果列表
 
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

	public List<MovieComing> getMovies() {
		return movies;
	}

	public void setMovies(List<MovieComing> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "MovieComingResult [action=" + action + ", status=" + status
				+ ", movies=" + movies + "]";
	}

	public static class MovieComing {
		public String actor; // 演员
		public String country; // 国家
		public String director; // 导演
		public Boolean has2D;
		public Boolean has3D;
		public Boolean hasImax;
		public int hot; // 热度
		public long movieId; // 影片ID
		public int movieLength; // 影片类型
		public String movieName; // 影片名
		public String movieType; // 影片类型
		public String pathSquare; // 海报地址
		public String pathVerticalS; // 海报地址
		public String publishTime; // 影片上映时间
		public String score; // 评分

		public String getActor() {
			return actor;
		}

		public void setActor(String actor) {
			this.actor = actor;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getDirector() {
			return director;
		}

		public void setDirector(String director) {
			this.director = director;
		}

		public Boolean getHas2D() {
			return has2D;
		}

		public void setHas2D(Boolean has2d) {
			has2D = has2d;
		}

		public Boolean getHas3D() {
			return has3D;
		}

		public void setHas3D(Boolean has3d) {
			has3D = has3d;
		}

		public Boolean getHasImax() {
			return hasImax;
		}

		public void setHasImax(Boolean hasImax) {
			this.hasImax = hasImax;
		}

		public int getHot() {
			return hot;
		}

		public void setHot(int hot) {
			this.hot = hot;
		}

		public long getMovieId() {
			return movieId;
		}

		public void setMovieId(long movieId) {
			this.movieId = movieId;
		}

		public int getMovieLength() {
			return movieLength;
		}

		public void setMovieLength(int movieLength) {
			this.movieLength = movieLength;
		}

		public String getMovieName() {
			return movieName;
		}

		public void setMovieName(String movieName) {
			this.movieName = movieName;
		}

		public String getMovieType() {
			return movieType;
		}

		public void setMovieType(String movieType) {
			this.movieType = movieType;
		}

		public String getPathSquare() {
			return pathSquare;
		}

		public void setPathSquare(String pathSquare) {
			this.pathSquare = pathSquare;
		}

		public String getPathVerticalS() {
			return pathVerticalS;
		}

		public void setPathVerticalS(String pathVerticalS) {
			this.pathVerticalS = pathVerticalS;
		}

		public String getPublishTime() {
			return publishTime;
		}

		public void setPublishTime(String publishTime) {
			this.publishTime = publishTime;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		@Override
		public String toString() {
			return "MovieComing [actor=" + actor + ", country=" + country
					+ ", director=" + director + ", has2D=" + has2D
					+ ", has3D=" + has3D + ", hasImax=" + hasImax + ", hot="
					+ hot + ", movieId=" + movieId + ", movieLength="
					+ movieLength + ", movieName=" + movieName + ", movieType="
					+ movieType + ", pathSquare=" + pathSquare
					+ ", pathVerticalS=" + pathVerticalS + ", publishTime="
					+ publishTime + ", score=" + score + "]";
		}

	}
}
