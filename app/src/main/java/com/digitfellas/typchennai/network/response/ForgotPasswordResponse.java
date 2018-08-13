package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 09/06/18.
 */

public class ForgotPasswordResponse implements Serializable {

    private String status;

    private String message;

    private String member_id;

    private String member_type;

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

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }
}
