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
}