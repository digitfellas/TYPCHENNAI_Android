package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 09/06/18.
 */

public class Video implements Serializable{

    private String title;

    private String filename;

    private String video_banner;

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

    public String getVideo_banner() {
        return video_banner;
    }

    public void setVideo_banner(String video_banner) {
        this.video_banner = video_banner;
    }
}
