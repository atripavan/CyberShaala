
package com.cybershaala.vo;

import java.sql.Timestamp;
//import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class MaterialsVO {
    private List<String> materialLinkList = new ArrayList<String>();
    private List<String> materialNamesList = new ArrayList<String>();
    private List<String> materialDescList = new ArrayList<String>();
    private List<String> mats3pdfList = new ArrayList<String>();
    private List<String> mats3vidList = new ArrayList<String>();
    private List<String> matyoutubevidList = new ArrayList<String>();
    private List<String> matyoutubeNames = new ArrayList<String>();
    private List<String> matyoutubeDesc = new ArrayList<String>();
    private List<String> mats3vidNames = new ArrayList<String>();
    private List<String> mats3vidDesc = new ArrayList<String>();
    private List<String> mats3pdfNames = new ArrayList<String>();
    private List<String> mats3pdfDesc = new ArrayList<String>();

	private int materialId;
	private String materialUrl;
	private String materialName;
	private String materialDesc;
	private String userId;
	private String tags;
	private String type;
	private Timestamp dateTime;

	public void setMats3pdfList(List<String> mats3pdfList) {
	   this.mats3pdfList = mats3pdfList;
	}

    public List<String> getMats3pdfList() {
	       return mats3pdfList;
	}
    
	public void setMatyoutubeNamesList(List<String> matyoutubeNames) {
		   this.matyoutubeNames = matyoutubeNames;
		}

	public List<String> getMatyoutubeNamesList() {
	       return matyoutubeNames;
	}
	public void setMatyoutubeDescList(List<String> matyoutubeDesc) {
		   this.matyoutubeDesc = matyoutubeDesc;
	}
	
	public List<String> getMatyoutubeDescList() {
	       return matyoutubeDesc;
	}
	public void setMats3vidNamesList(List<String> mats3vidNames) {
		   this.mats3vidNames = mats3vidNames;
	}
	
	public List<String> getMats3vidNamesList() {
	       return mats3vidNames;
	}
	public void setMats3vidDescList(List<String> mats3vidDesc) {
		   this.mats3vidDesc = mats3vidDesc;
	}
	
	public List<String> getMats3vidDescList() {
	    return mats3vidDesc;
	}
	
	public void setMats3pdfNamesList(List<String> mats3pdfNames) {
		   this.mats3pdfNames = mats3pdfNames;
	}
	
	public List<String> getMats3pdfNamesList() {
	    return mats3pdfNames;
	}
	public void setMats3pdfDescList(List<String> mats3pdfDesc) {
		   this.mats3pdfDesc = mats3pdfDesc;
	}
	
	public List<String> getMats3pdfDescList() {
	 return mats3pdfDesc;
	}
    
	public void setMatyoutubevidList(List<String> matyoutubevidList) {
	   this.matyoutubevidList = matyoutubevidList;
	}

    public List<String> getMatyoutubevidList() {
	       return matyoutubevidList;
	 }

	 public void setMats3vidList(List<String> mats3vidList) {
	        this.mats3vidList = mats3vidList;
	 }

	public List<String> getMats3vidList() {
	    return mats3vidList;
	}
	
   public void setMaterialLinkList(List<String> materialLinkArr) {
        this.materialLinkList = materialLinkArr;
    }

    public List<String> getMaterialLinkList() {
        return materialLinkList;
    }
    
    public void setMaterialNamesList(List<String> materialNamesList) {
        this.materialNamesList = materialNamesList;
    }

    public List<String> getMaterialNamesList() {
        return materialNamesList;
    }
    public void setMaterialDescList(List<String> materialDescList) {
        this.materialDescList = materialDescList;
    }

    public List<String> getMaterialDescList() {
        return materialDescList;
    }
   
    /*******************************************************Integrated **********************************************/
    
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
