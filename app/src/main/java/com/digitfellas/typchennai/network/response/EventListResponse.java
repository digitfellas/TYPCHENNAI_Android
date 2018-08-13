package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.util.List;

public class EventListResponse implements Serializable {

    private String status;

    private List<Events> events;

    private Events event;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }
}
