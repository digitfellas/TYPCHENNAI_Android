package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 07/06/18.
 */

public class Content implements Serializable {

    private String title;

    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
