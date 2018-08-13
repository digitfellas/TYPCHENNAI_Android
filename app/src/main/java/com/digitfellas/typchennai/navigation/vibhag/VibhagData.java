package com.digitfellas.typchennai.navigation.vibhag;

import java.io.Serializable;

/**
 * Created by ubuntu on 7/6/18.
 */

public class VibhagData implements Serializable {

    public String name;
    public String urlContent;
    public String urlContact;
    public String mGalleryName;
    public int image;

    public VibhagData(String name,String url,String urlContact,int image){
        this.name = name;
        this.urlContent = url;
        this.urlContact = urlContact;
        this.image = image;
    }

    public VibhagData() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlContent() {
        return urlContent;
    }

    public void setUrlContent(String urlContent) {
        this.urlContent = urlContent;
    }

    public String getUrlContact() {
        return urlContact;
    }

    public void setUrlContact(String urlContact) {
        this.urlContact = urlContact;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getmGalleryName() {
        return mGalleryName;
    }

    public void setmGalleryName(String mGalleryName) {
        this.mGalleryName = mGalleryName;
    }
}
