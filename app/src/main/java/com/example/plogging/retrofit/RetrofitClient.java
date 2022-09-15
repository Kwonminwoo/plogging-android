package com.example.plogging.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String SERVER_IP = "http://192.168.1.14:8080";
    private static final String SERVER_IP = "http://10.2.17.36:8080";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(){
        if(retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_IP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
