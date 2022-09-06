package com.example.plogging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.plogging.home.HomeFragment;
import com.example.plogging.notice.NoticeFragment;
import com.example.plogging.statistics.StatisticsFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private NoticeFragment noticeFragment;
    private StatisticsFragment statisticsFragment;

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
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, homeFragment).commit();
                    case R.id.notice:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, noticeFragment).commit();
                    case R.id.satistics:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, statisticsFragment).commit();
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

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this,"Date: "+dateMessage, Toast.LENGTH_SHORT).show();
    }
}