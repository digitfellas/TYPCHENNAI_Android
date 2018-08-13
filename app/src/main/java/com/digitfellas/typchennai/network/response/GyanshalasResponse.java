package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ubuntu on 8/6/18.
 */

public class GyanshalasResponse implements Serializable {

    private String status;
    private List<Gyanshalas> gyanshalas;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Gyanshalas> getGyanshalas() {
        return gyanshalas;
    }

    public void setGyanshalas(List<Gyanshalas> gyanshalas) {
        this.gyanshalas = gyanshalas;
    }
}
