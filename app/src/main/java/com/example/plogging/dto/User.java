package com.example.plogging.dto;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("post_num")
    private int postNum;

    public User(int userId, String userName, int postNum) {
        this.userId = userId;
        this.userName = userName;
        this.postNum = postNum;
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
}
