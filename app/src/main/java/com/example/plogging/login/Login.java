package com.example.plogging.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plogging.MainActivity;
import com.example.plogging.R;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class Login extends AppCompatActivity {
    public Login(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        KakaoSdk.init(this, "07fc38cf333014fea26061bbbfab19b8");

        ImageButton kakaoLoginBtn = findViewById(R.id.kakaoBtn);
        kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountLogin();
            }
        });
    }

    public void AppLogin(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void accountLogin(){ // 사용
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void getUserInfo(){
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                System.out.println("로그인 완료");
                Log.i(TAG, user.toString());
                {

                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: "+user.getId() +
                            "\n이메일: "+user.getKakaoAccount().getEmail());
                }
                Account user1 = user.getKakaoAccount();
                System.out.println("사용자 계정" + user1);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            return null; //로그인 함수 마지막(정보 호출)
        });
    }

    Function2<OAuthToken, Throwable, Unit> callback = ((oAuthToken, throwable) -> {
        if(oAuthToken != null){
            AppLogin();
        }
        if(throwable != null){
            if(throwable.toString().contains("statusCode=302")){
                accountLogin();
            }
        }
        return null;
    });
}
