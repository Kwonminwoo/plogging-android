package com.example.plogging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.plogging.home.HomeFragment;
import com.example.plogging.notice.NoticeFragment;
import com.example.plogging.statistics.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private NoticeFragment noticeFragment;
    private StatisticsFragment statisticsFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        Log.d("hash", Utility.INSTANCE.getKeyHash(this));
        KakaoSdk.init(this, "07fc38cf333014fea26061bbbfab19b8");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containers, noticeFragment).commit();

        NavigationBarView naviView = findViewById(R.id.bottom_nav);
        naviView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.notice:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, noticeFragment).commit();
                        return true;
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.satistics:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, statisticsFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        noticeFragment = new NoticeFragment();
        statisticsFragment = new StatisticsFragment();
    }

    public void Login(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void accountLogin(){
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this,(oAuthToken, error) -> {
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
            }
            return null;
        });
    }

    Function2<OAuthToken, Throwable, Unit> callback = ((oAuthToken, throwable) -> {
        if(oAuthToken != null){
            Login();
        }
        if(throwable != null){
            if(throwable.toString().contains("statusCode=302")){
                accountLogin();
            }
        }
        return null;
    });
}