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
    private Object planDate;
    private String expireDate;
    private Boolean planStatus;
    private String spinnerData; // Added for storing Spinner data
    private String approvedBy; // Added for storing the approvedby data

    public Plan() {
        // Default constructor required for Firebase
    }

    public Plan(String planCaption, String planName, String planContact, String planPrice, String planContact2,
                String planDescription, String planImage, String expireDate, Boolean planStatus, String spinnerData) {
        this.planCaption = planCaption;
        this.planName = planName;
        this.planContact = planContact;
        this.planPrice = planPrice;
        this.planContact2 = planContact2;
        this.planDescription = planDescription;
        this.planImage = planImage;
        this.planDate = ServerValue.TIMESTAMP;
        this.expireDate = expireDate;
        this.planStatus = planStatus;
        this.spinnerData = spinnerData;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getPlanCaption() {
        return planCaption;
    }

    public void setPlanCaption(String planCaption) {
        this.planCaption = planCaption;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanContact() {
        return planContact;
    }

    public void setPlanContact(String planContact) {
        this.planContact = planContact;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    public String getPlanContact2() {
        return planContact2;
    }

    public void setPlanContact2(String planContact2) {
        this.planContact2 = planContact2;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getPlanImage() {
        return planImage;
    }

    public void setPlanImage(String planImage) {
        this.planImage = planImage;
    }

    public Object getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Object planDate) {
        this.planDate = planDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Boolean getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Boolean planStatus) {
        this.planStatus = planStatus;
    }

    public String getSpinnerData() {
        return spinnerData;
    }

    public void setSpinnerData(String spinnerData) {
        this.spinnerData = spinnerData;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
