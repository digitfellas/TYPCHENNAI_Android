package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 08/06/18.
 */

public class Pdf implements Serializable {

    private String title;

    private String filename;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
