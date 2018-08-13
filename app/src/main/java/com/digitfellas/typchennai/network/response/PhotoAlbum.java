package com.digitfellas.typchennai.network.response;

import java.io.Serializable;

/**
 * Created by administrator on 08/06/18.
 */

public class PhotoAlbum implements Serializable {

    private String id;

    private String albumtype;

    private String title;

    private String cover_photo;

    private String cover_photo_ext;

    private int photo_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumtype() {
        return albumtype;
    }

    public void setAlbumtype(String albumtype) {
        this.albumtype = albumtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getCover_photo_ext() {
        return cover_photo_ext;
    }

    public void setCover_photo_ext(String cover_photo_ext) {
        this.cover_photo_ext = cover_photo_ext;
    }

    public int getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(int photo_count) {
        this.photo_count = photo_count;
    }
}
