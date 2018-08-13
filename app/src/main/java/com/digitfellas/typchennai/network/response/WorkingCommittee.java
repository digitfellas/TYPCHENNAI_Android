package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 07/06/18.
 */

public class WorkingCommittee implements Serializable{

    private String name;

    private String role;

    private String mobile_no;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
