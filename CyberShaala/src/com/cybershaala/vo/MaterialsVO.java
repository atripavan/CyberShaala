
package com.cybershaala.vo;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MaterialsVO {
    private List<String> materialLinkList = new ArrayList<String>();
    private List<String> materialNamesList = new ArrayList<String>();
    private List<String> materialDescList = new ArrayList<String>();
   // private List materialIDArr;
    private String phonenumber;
    private String reputation;
    private String interests;
    

/*    public void setUserId(String userid) {
        this.userid = userid;
    }
    
    public String getUserId() {
        return userid;
    }
    */
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
   
}
