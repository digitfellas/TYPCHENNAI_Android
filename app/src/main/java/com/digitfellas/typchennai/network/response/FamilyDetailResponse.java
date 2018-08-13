package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.util.List;

public class FamilyDetailResponse implements Serializable {

    private String status;

    private FamilyHead head;

    private ResidentialAddres res_address;

    private String res_state;

    private String res_district;

    private String res_city;

    private OfficeAddress off_address;

    private String off_state;

    private String off_district;

    private String off_city;

    private List<Members> members;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FamilyHead getHead() {
        return head;
    }

    public void setHead(FamilyHead head) {
        this.head = head;
    }

    public ResidentialAddres getRes_address() {
        return res_address;
    }

    public void setRes_address(ResidentialAddres res_address) {
        this.res_address = res_address;
    }

    public String getRes_state() {
        return res_state;
    }

    public void setRes_state(String res_state) {
        this.res_state = res_state;
    }

    public String getRes_district() {
        return res_district;
    }

    public void setRes_district(String res_district) {
        this.res_district = res_district;
    }

    public String getRes_city() {
        return res_city;
    }

    public void setRes_city(String res_city) {
        this.res_city = res_city;
    }

    public OfficeAddress getOff_address() {
        return off_address;
    }

    public void setOff_address(OfficeAddress off_address) {
        this.off_address = off_address;
    }

    public String getOff_state() {
        return off_state;
    }

    public void setOff_state(String off_state) {
        this.off_state = off_state;
    }

    public String getOff_district() {
        return off_district;
    }

    public void setOff_district(String off_district) {
        this.off_district = off_district;
    }

    public String getOff_city() {
        return off_city;
    }

    public void setOff_city(String off_city) {
        this.off_city = off_city;
    }

    public List<Members> getMembers() {
        return members;
    }

    public void setMembers(List<Members> members) {
        this.members = members;
    }
}

