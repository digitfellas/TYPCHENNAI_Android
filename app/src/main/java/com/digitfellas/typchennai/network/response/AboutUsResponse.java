package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 07/06/18.
 */

public class AboutUsResponse implements Serializable {

    private String status;

    private Content content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
