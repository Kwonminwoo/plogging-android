package com.example.plogging.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("post_num")
    private int postNum;
    @SerializedName("user_image")
    private String userImage;


    public User(String userName, String userImage) {
        this.userName = userName;
        this.userImage = userImage;
    }

    public User(int userId, String userName, int postNum, String userImage) {
        this.userId = userId;
        this.userName = userName;
        this.postNum = postNum;
        this.userImage = userImage;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getPostNum() {
        return postNum;
    }

    @Override
    public int compareTo(User user) {
        if(this.postNum > user.postNum){
            return 1;
        }else if(this.postNum == user.postNum){
            return 0;
        }else
            return -1;
    }
}
