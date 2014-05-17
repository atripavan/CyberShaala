package com.cybershaala.data.model;

import java.sql.Timestamp;


public class QA {

	private String materialUrl;
	private String question;
	private String answer;
	private String text;
	private String userId;
	private String voteUp;
	private String voteDown;
	private Timestamp dateTime;
	
	public String getMaterialUrl() {
		return materialUrl;
	}
	public void setMaterialUrl(String materialUrl) {
		this.materialUrl = materialUrl;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVoteUp() {
		return voteUp;
	}
	public void setVoteUp(String voteUp) {
		this.voteUp = voteUp;
	}
	public String getVoteDown() {
		return voteDown;
	}
	public void setVoteDown(String voteDown) {
		this.voteDown = voteDown;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
}
