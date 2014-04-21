package com.cybershaala.data.model;

import java.sql.Timestamp;

public class Materials {
	
	private int materialId;
	private String materialUrl;
	private String materialName;
	private String materialDesc;
	private String userId;
	private String tags;
	private String type;
	private Timestamp dateTime;
	
	public int getMaterialId() {
		return materialId;
	}
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
	public String getMaterialUrl() {
		return materialUrl;
	}
	public void setMaterialUrl(String materialUrl) {
		this.materialUrl = materialUrl;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
}
