package com.example.plogging.retrofit;

import com.example.plogging.dto.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("/findUser")
    Call<User> getUser();
}
