package com.digitfellas.typchennai.network.response;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by administrator on 08/06/18.
 */

public class DownloadResponse implements Serializable {

    private String status;

    private String pdf_path;

    private List<Pdf> pdfs;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPdf_path() {
        return pdf_path;
    }

    public void setPdf_path(String pdf_path) {
        this.pdf_path = pdf_path;
    }


    public List<Pdf> getPdfs() {
        return pdfs;
    }

    public void setPdfs(List<Pdf> pdfs) {
        this.pdfs = pdfs;
    }
}
