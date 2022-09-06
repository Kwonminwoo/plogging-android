package com.example.plogging.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.plogging.R;

public class NoticePost extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_post);
        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", -1);
        Toast.makeText(this, "" + postId, Toast.LENGTH_SHORT).show();
    }
}