package com.example.plogging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;
import com.example.plogging.home.HomeFragment;
import com.example.plogging.notice.NoticeFragment;
import com.example.plogging.statistics.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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

        // Log.d("hash", Utility.INSTANCE.getKeyHash(this));
        KakaoSdk.init(this, "DXXvygIO5LMISCyadU1ENwjjOE8");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containers, homeFragment).commit();

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