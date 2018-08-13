package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administrator on 07/06/18.
 */

public class WorkingCommitteeResponse implements Serializable {

    private String status;

    private List<WorkingCommittee> working_committee;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WorkingCommittee> getWorking_committee() {
        return working_committee;
    }

    public void setWorking_committee(List<WorkingCommittee> working_committee) {
        this.working_committee = working_committee;
    }
}
