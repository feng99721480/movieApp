package com.wiseweb.json;

import java.util.List;

public class MovieResult {
	private String action;
	private String status;
	private List<Movie> movies;

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

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "MovieResult [action=" + action + ", status=" + status
				+ ", movies=" + movies + "]";
	}

	public static class Movie {
		public String actor;
		public String country;
		public String director;
		public Boolean has2D;
		public Boolean has3D;
		public Boolean hasImax;
		public int hot;
		public int hot_planCount;
		public int hot_priority;
		public String minPrice;
		public String minVipPrice;
		public long movieId;
		public int movieLength;
		public String movieName;
		public String movieType;
		public String pathHorizonB;
		public String pathSquare;
		public String pathVerticalS;
		public String posterPath;
		public String publishTime;
		public String score;

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

		public int getHot_planCount() {
			return hot_planCount;
		}

		public void setHot_planCount(int hot_planCount) {
			this.hot_planCount = hot_planCount;
		}

		public int getHot_priority() {
			return hot_priority;
		}

		public void setHot_priority(int hot_priority) {
			this.hot_priority = hot_priority;
		}

		public String getMinPrice() {
			return minPrice;
		}

		public void setMinPrice(String minPrice) {
			this.minPrice = minPrice;
		}

		public String getMinVipPrice() {
			return minVipPrice;
		}

		public void setMinVipPrice(String minVipPrice) {
			this.minVipPrice = minVipPrice;
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

		public String getPathHorizonB() {
			return pathHorizonB;
		}

		public void setPathHorizonB(String pathHorizonB) {
			this.pathHorizonB = pathHorizonB;
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

		public String getPosterPath() {
			return posterPath;
		}

		public void setPosterPath(String posterPath) {
			this.posterPath = posterPath;
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
			return "Movie [actor=" + actor + ", country=" + country
					+ ", director=" + director + ", has2D=" + has2D
					+ ", has3D=" + has3D + ", hasImax=" + hasImax + ", hot="
					+ hot + ", hot_planCount=" + hot_planCount
					+ ", hot_priority=" + hot_priority + ", minPrice="
					+ minPrice + ", minVipPrice=" + minVipPrice + ", movieId="
					+ movieId + ", movieLength=" + movieLength + ", movieName="
					+ movieName + ", movieType=" + movieType
					+ ", pathHorizonB=" + pathHorizonB + ", pathSquare="
					+ pathSquare + ", pathVerticalS=" + pathVerticalS
					+ ", posterPath=" + posterPath + ", publishTime="
					+ publishTime + ", score=" + score + "]";
		}

	}
}
