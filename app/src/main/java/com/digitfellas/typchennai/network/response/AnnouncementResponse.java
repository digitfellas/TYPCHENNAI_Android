package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.util.List;

public class AnnouncementResponse implements Serializable{

    private String status;

    public List<Announcements> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcements> announcements) {
        this.announcements = announcements;
    }

    private List<Announcements> announcements;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
