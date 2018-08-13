package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

public class OfficeAddress implements Serializable {

    private String off_address_1;

    private String off_address_2;

    private String off_address_3;

    private String state_id;

    private String off_std_code;

    private String off_phone;

    private String off_pin_code;

    public String getOff_address_1() {
        return off_address_1;
    }

    public void setOff_address_1(String off_address_1) {
        this.off_address_1 = off_address_1;
    }

    public String getOff_address_2() {
        return off_address_2;
    }

    public void setOff_address_2(String off_address_2) {
        this.off_address_2 = off_address_2;
    }

    public String getOff_address_3() {
        return off_address_3;
    }

    public void setOff_address_3(String off_address_3) {
        this.off_address_3 = off_address_3;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getOff_std_code() {
        return off_std_code;
    }

    public void setOff_std_code(String off_std_code) {
        this.off_std_code = off_std_code;
    }

    public String getOff_phone() {
        return off_phone;
    }

    public void setOff_phone(String off_phone) {
        this.off_phone = off_phone;
    }

    public String getOff_pin_code() {
        return off_pin_code;
    }

    public void setOff_pin_code(String off_pin_code) {
        this.off_pin_code = off_pin_code;
    }
}
