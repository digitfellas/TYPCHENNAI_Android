package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administrator on 09/06/18.
 */

public class VideoResponse implements Serializable {

    private String status;

    private List<Video> videos;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
