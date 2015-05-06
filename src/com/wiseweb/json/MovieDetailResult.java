package com.wiseweb.json;

public class MovieDetailResult {
	private String action;
	private String status;
	private MovieDetail movie;

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

	
	public MovieDetail getMovie() {
		return movie;
	}

	public void setMovie(MovieDetail movie) {
		this.movie = movie;
	}


	@Override
	public String toString() {
		return "MovieDetailResult [action=" + action + ", status=" + status
				+ ", movie=" + movie + "]";
	}


	public static class MovieDetail {
		public String actor;
		public String country;
		public String director;
		public Boolean expired; // 是否已经下映
		public int hot; // 热度
		public String intro; // 影片介绍
		public long movieId;
		public int movieLength;
		public String movieName;
		public String movieType;
		public String pathHorizonS;
		public String pathSquare;
		public String pathVerticalS;
		public int point; // 用户评分
		public String publishTime;
		public String score; // 编辑评分

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

		public Boolean getExpired() {
			return expired;
		}

		public void setExpired(Boolean expired) {
			this.expired = expired;
		}

		public int getHot() {
			return hot;
		}

		public void setHot(int hot) {
			this.hot = hot;
		}

		public String getIntro() {
			return intro;
		}

		public void setIntro(String intro) {
			this.intro = intro;
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

		public String getPathHorizonS() {
			return pathHorizonS;
		}

		public void setPathHorizonS(String pathHorizonS) {
			this.pathHorizonS = pathHorizonS;
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

		public int getPoint() {
			return point;
		}

		public void setPoint(int point) {
			this.point = point;
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
			return "MovieDetail [actor=" + actor + ", country=" + country
					+ ", director=" + director + ", expired=" + expired
					+ ", hot=" + hot + ", intro=" + intro + ", movieId="
					+ movieId + ", movieLength=" + movieLength + ", movieName="
					+ movieName + ", movieType=" + movieType
					+ ", pathHorizonS=" + pathHorizonS + ", pathSquare="
					+ pathSquare + ", pathVerticalS=" + pathVerticalS
					+ ", point=" + point + ", publishTime=" + publishTime
					+ ", score=" + score + "]";
		}

	}
}
