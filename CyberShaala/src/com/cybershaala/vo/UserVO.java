
package com.cybershaala.vo;


import java.sql.Date;

public class UserVO {
    private java.lang.String userid;
    private String emailid;
    private String address;
    private String phonenumber;
    private String fblink;
    private String linkedinlink;
    private String githublink;
    private String firstname;
    private String lastname;
    private Date joindate;
    private String reputation;
    private String interests;
    private String[] interestslist;

    public void setUserId(String userid) {
        this.userid = userid;
    }
    
    public String getUserId() {
        return userid;
    }
    
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    
    public String getFirstName() {
        return firstname;
    }
    
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }
    
    public String getLastName() {
        return lastname;
    }
    
    public void setReputation(String reputation) {
        this.reputation = reputation;
    }
    
    public String getReputation() {
        return reputation;
    }
    
    public void setEmailID(String emailid) {
        this.emailid = emailid;
    }
    
    public String getEmailID() {
        return emailid;
    }
    
    public void setJoinDate(Date joindate) {
        this.joindate = joindate;
    }

    public Date getJoinDate() {
        return joindate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setFBLink(String fblink) {
        this.fblink = fblink;
    }

    public String getFBLink() {
        return fblink;
    }

    public void setLinkedInLink(String linkedinlink) {
        this.linkedinlink = linkedinlink;
    }

    public String getLinkedInLink() {
        return linkedinlink;
    }

    public void setGitHubLink(String githublink) {
        this.githublink = githublink;
    }

    public String getGitHubLink() {
        return githublink;
    }
    
    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getInterests() {
        return interests;
    }
    
    public void setInterestsList(String[] interestslist) {
        this.interestslist = interestslist;
    }

    public String[] getInterestsList() {
        return interestslist;
    }

}
