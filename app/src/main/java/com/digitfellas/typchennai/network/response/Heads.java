package com.digitfellas.typchennai.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Heads implements Serializable {

    private String id;

    private String name;

    private String surname;

    private String res_address_1;

    private String res_address_2;

    private String res_address_3;

    private String mobile_no;

    private String city_name;

    @SerializedName("native")
    private String mNative;

    private String res_pin_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getmNative() {
        return mNative;
    }

    public void setmNative(String mNative) {
        this.mNative = mNative;
    }

    public String getRes_pin_code() {
        return res_pin_code;
    }

    public void setRes_pin_code(String res_pin_code) {
        this.res_pin_code = res_pin_code;
    }

    public String getRes_address_1() {
        return res_address_1;
    }

    public void setRes_address_1(String res_address_1) {
        this.res_address_1 = res_address_1;
    }

    public String getRes_address_2() {
        return res_address_2;
    }

    public void setRes_address_2(String res_address_2) {
        this.res_address_2 = res_address_2;
    }

    public String getRes_address_3() {
        return res_address_3;
    }

    public void setRes_address_3(String res_address_3) {
        this.res_address_3 = res_address_3;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
