package com.cybershaala.data.model;

import java.sql.Timestamp;

public class Comment {
	
	private String MaterialURL;
	private String comment;
	private String userId;
	private Timestamp dateTime;
	
	public String getMaterialURL() {
		return MaterialURL;
	}
	public void setMaterialURL(String materialURL) {
		MaterialURL = materialURL;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
}
