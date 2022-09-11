package com.example.plogging.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.plogging.R;

public class NoticePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_post);

        Button noticeBtn =  findViewById(R.id.notice_btn);

        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = findViewById(R.id.notice_user_name);
                Intent intent = new Intent();
                intent.putExtra("recruitment", text.getText().toString());
                intent.putExtra("intentName", "noticePost");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


}