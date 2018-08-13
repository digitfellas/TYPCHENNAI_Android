package com.digitfellas.typchennai.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administrator on 08/06/18.
 */

public class GetGalleryResponse implements Serializable {

    private String status;

    private String image_path;

    private List<PhotoAlbum> photoalbum;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public List<PhotoAlbum> getPhotoalbum() {
        return photoalbum;
    }

    public void setPhotoalbum(List<PhotoAlbum> photoalbum) {
        this.photoalbum = photoalbum;
    }
}
