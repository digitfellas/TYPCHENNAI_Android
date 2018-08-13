package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.util.List;

public class GetDirectoryResponse implements Serializable {

    private String status;

    private List<Heads> heads;

    private List<Heads> members;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Heads> getHeads() {
        return heads;
    }

    public void setHeads(List<Heads> heads) {
        this.heads = heads;
    }

    public List<Heads> getMembers() {
        return members;
    }

    public void setMembers(List<Heads> members) {
        this.members = members;
    }
}
