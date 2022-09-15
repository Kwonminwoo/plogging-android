package com.example.plogging.retrofit;

import com.example.plogging.dto.User;
import com.example.plogging.notice.Notice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PloggingService {
    @GET("/findUser")
    Call<User> getUser();

    @GET("/findAllPost")
    Call<List<Notice>> getPosts();

    @GET("/findAllPost/user")
    Call<List<User>> getPostUser();

    @GET("/getRank")
    Call<List<User>> getRank();


    @FormUrlEncoded
    @POST("/upload")
    Call<Notice> addPost(Notice notice);
}