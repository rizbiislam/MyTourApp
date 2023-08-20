package com.diu.mytour;

public class AdminHelperClass {

    private String name;
    private String email;
    private String phone;
    private String password;
    private String joiningDate;
    private String leftDate;
    private String address;

    public AdminHelperClass() {
        // Default constructor required for Firebase
    }

    public AdminHelperClass(String name, String email, String phone, String password, String joiningDate, String leftDate, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.joiningDate = joiningDate;
        this.leftDate = leftDate;
        this.address = address;
    }

    // Getters and setters (required for Firebase)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getLeftDate() {
        return leftDate;
    }

    public void setLeftDate(String leftDate) {
        this.leftDate = leftDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

