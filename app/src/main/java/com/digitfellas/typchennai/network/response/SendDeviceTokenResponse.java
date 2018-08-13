package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 08/06/18.
 */

public class SendDeviceTokenResponse implements Serializable {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
