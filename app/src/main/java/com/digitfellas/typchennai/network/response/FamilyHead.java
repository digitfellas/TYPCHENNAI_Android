package com.digitfellas.typchennai.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FamilyHead implements Serializable {

    private String name;

    private String surname;

    private String res_std_code;

    private String off_std_code;

    @SerializedName("native")
    private String native_loc;

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

    public String getRes_std_code() {
        return res_std_code;
    }

    public void setRes_std_code(String res_std_code) {
        this.res_std_code = res_std_code;
    }

    public String getOff_std_code() {
        return off_std_code;
    }

    public void setOff_std_code(String off_std_code) {
        this.off_std_code = off_std_code;
    }

    public String getNative_loc() {
        return native_loc;
    }

    public void setNative_loc(String native_loc) {
        this.native_loc = native_loc;
    }
}
