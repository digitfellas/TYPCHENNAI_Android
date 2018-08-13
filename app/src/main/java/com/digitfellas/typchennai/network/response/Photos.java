package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 08/06/18.
 */

public class Photos implements Serializable {

    private String filename;

    private String filename_ext;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename_ext() {
        return filename_ext;
    }

    public void setFilename_ext(String filename_ext) {
        this.filename_ext = filename_ext;
    }
}
