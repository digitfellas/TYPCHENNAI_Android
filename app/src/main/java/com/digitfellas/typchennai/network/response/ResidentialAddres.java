package com.digitfellas.typchennai.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResidentialAddres implements Serializable {

    private String res_address_1;

    private String res_address_2;

    private String res_address_3;

    private String state_id;

    private String res_std_code;

    private String res_phone;

    private String res_pin_code;

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

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getRes_std_code() {
        return res_std_code;
    }

    public void setRes_std_code(String res_std_code) {
        this.res_std_code = res_std_code;
    }

    public String getRes_phone() {
        return res_phone;
    }

    public void setRes_phone(String res_phone) {
        this.res_phone = res_phone;
    }

    public String getRes_pin_code() {
        return res_pin_code;
    }

    public void setRes_pin_code(String res_pin_code) {
        this.res_pin_code = res_pin_code;
    }
}
