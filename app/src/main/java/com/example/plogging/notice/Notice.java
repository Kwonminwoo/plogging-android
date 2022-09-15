package com.example.plogging.notice;

import com.example.plogging.dto.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Notice implements Serializable {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("post_id")
    private int postId;
    @SerializedName("location")
    private String location;
    private String date;
    private String kit;
    private String image;
    private String content;

    private List<User> userList = new ArrayList<>();

    public Notice() {

    }

    public Notice(int userId, int postId, String location, String date, String kit, String image, String content) {
        this.userId = userId;
        this.postId = postId;
        this.location = location;
        this.date = date;
        this.kit = kit;
        this.image = image;
        this.content = content;
    }

    public Notice(int userId, String location, String date, String kit, String image, String content) {
        this.userId = userId;
        this.location = location;
        this.date = date;
        this.kit = kit;
        this.image = image;
        this.content = content;
    }

    public Notice(String location, String date, String kit, String image, String content) {
        this.location = location;
        this.date = date;
        this.kit = kit;
        this.image = image;
        this.content = content;
    }

    public Notice(String location, String date) {
        this.location = location;
        this.date = date;
    }



    public void addUser(User user){
        userList.add(user);
    }

    public int getUserNum(){
        return userList.size();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}