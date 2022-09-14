package com.example.plogging.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plogging.R;

import java.io.Serializable;

public class NoticePost extends AppCompatActivity {
    TextView userName;
    TextView location;
    TextView personNum;
    TextView date;
    TextView kits;
    ImageView image;
    TextView content;

    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_post);

        init();

        Button noticeBtn =  findViewById(R.id.notice_btn);
        Button backBtn = findViewById(R.id.back_btn);
        setPostData();


        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = findViewById(R.id.notice_user_name);
                intent.putExtra("recruitment", text.getText().toString());
                intent.putExtra("intentName", "noticePost");


                setResult(RESULT_OK, intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void init(){
        userName = findViewById(R.id.notice_user_name);
        location = findViewById(R.id.notice_location);
        personNum = findViewById(R.id.notice_person_num);
        date = findViewById(R.id.notice_date);
        kits = findViewById(R.id.notice_kits);
        image = findViewById(R.id.notice_image);
        content = findViewById(R.id.notice_introduction);
    }

    private void setPostData(){
        Notice notice = (Notice) getIntent().getSerializableExtra("select_post");
        location.setText(notice.getLocation());
        date.setText(notice.getDate());
        kits.append(notice.getKit());
        image.setImageURI(null);
        content.append(notice.getContent());
    }


}