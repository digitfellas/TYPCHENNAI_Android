package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 31/01/18.
 */

public class RegistrationResponse implements Serializable {

    private String status;

    private String message;

    private String member_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
