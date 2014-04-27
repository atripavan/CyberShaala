package com.cybershaala.data.model;

public class Feedback {

	private String materialUrl;
	private int starRating;
	private boolean qOne;
	private boolean qTwo;
	private boolean qThree;
	private boolean qFour;
	private boolean qFive;
	private int finalScore;
	private int views;
	
	public String getMaterialUrl() {
		return materialUrl;
	}
	public void setMaterialUrl(String materialUrl) {
		this.materialUrl = materialUrl;
	}
	public int getStarRating() {
		return starRating;
	}
	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}
	public boolean isqOne() {
		return qOne;
	}
	public void setqOne(boolean qOne) {
		this.qOne = qOne;
	}
	public boolean isqTwo() {
		return qTwo;
	}
	public void setqTwo(boolean qTwo) {
		this.qTwo = qTwo;
	}
	public boolean isqThree() {
		return qThree;
	}
	public void setqThree(boolean qThree) {
		this.qThree = qThree;
	}
	public boolean isqFour() {
		return qFour;
	}
	public void setqFour(boolean qFour) {
		this.qFour = qFour;
	}
	public boolean isqFive() {
		return qFive;
	}
	public void setqFive(boolean qFive) {
		this.qFive = qFive;
	}
	public int getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}	
}
