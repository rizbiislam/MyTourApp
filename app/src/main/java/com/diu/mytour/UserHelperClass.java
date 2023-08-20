package com.diu.mytour;

public class UserHelperClass {
    String name, phone, image, password;
    public UserHelperClass() {
    }
    public UserHelperClass(String name, String phone, String image, String password) {
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
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
}