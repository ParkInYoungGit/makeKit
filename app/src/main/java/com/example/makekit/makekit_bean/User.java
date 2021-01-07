package com.example.makekit.makekit_bean;

import java.util.ArrayList;

public class User {

    String email;
    String name;
    String pw;
    String address;
    String addressdetail;
    String tel;
    String birth;

    //////////////////////////////////////////////////////////////////////////////
    // 1/7 kyeongmi 추가
    // seller constructor
    public User(String email, String name, String address, String addressdetail) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.addressdetail = addressdetail;
    }
    //////////////////////////////////////////////////////////////////////////////

    public User(String email, String name, String pw, String address, String addressdetail, String tel, String birth) {
        this.email = email;
        this.name = name;
        this.pw = pw;
        this.address = address;
        this.addressdetail = addressdetail;
        this.tel = tel;
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressdetail() {
        return addressdetail;
    }

    public void setAddressdetail(String addressdetail) {
        this.addressdetail = addressdetail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
