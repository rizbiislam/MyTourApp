package com.diu.mytour;

import com.google.firebase.database.ServerValue;

public class Plan {
    private String planID;
    private String planCaption;
    private String planName;
    private String planContact;
    private String planPrice;
    private String planContact2;
    private String planDescription;
    private String planImage;
    private Object planDate ;
    private Boolean planStatus;

    public Plan(String planCaption,String planName, String planContact, String planPrice, String planContact2, String planDescription, String planImage, Boolean planStatus) {
        this.planCaption = planCaption;
        this.planName = planName;
        this.planContact = planContact;
        this.planPrice = planPrice;
        this.planContact2 = planContact2;
        this.planDescription = planDescription;
        this.planImage = planImage;
        this.planDate = ServerValue.TIMESTAMP;
        this.planStatus = planStatus;
    }

    public Plan() {
    }

    public String getplanID() {
        return planID;
    }

    public void setplanID(String planID) {
        this.planID = planID;
    }

    public String getplanCaption() {
        return planCaption;
    }

    public String getplanName() {
        return planName;
    }

    public String getplanContact() {
        return planContact;
    }
    public String getplanPrice() {
        return planPrice;
    }

    public String getplanContact2() {
        return planContact2;
    }

    public String getplanDescription() {
        return planDescription;
    }

    public String getplanImage() {
        return planImage;
    }

    public Object getplanDate() {
        return planDate;
    }

    public Boolean getplanStatus() {
        return planStatus;
    }


    public void setplanCaption(String planCaption) {
        this.planCaption = planCaption;
    }

    public void setplanName(String planName) {
        this.planName = planName;
    }


    public void setplanContact(String planContact) {
        this.planContact = planContact;
    }
    public void setplanPrice(String planPrice) {
        this.planPrice = planPrice;
    }


    public void setplanContact2(String planContact2) {
        this.planContact2 = planContact2;
    }


    public void setplanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public void setplanImage(String planImage) {
        this.planImage = planImage;
    }

    public void setplanDate(Object planDate) {
        this.planDate = planDate;
    }


    public void setplanStatus(Boolean planStatus) {
        this.planStatus = planStatus;
    }

}
