package com.example.plogging.notice;

import android.graphics.Bitmap;
import android.media.Image;

public class Notice {
    private int image;
    private String userName;
    private String location;
    private String date;
    private String personNum;

    public Notice(int image, String userName, String location, String date, String personNum) {
        this.image = image;
        this.userName = userName;
        this.location = location;
        this.date = date;
        this.personNum = personNum;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }
}
