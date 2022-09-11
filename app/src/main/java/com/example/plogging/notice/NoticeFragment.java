package com.example.plogging.notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.plogging.MainActivity;
import com.example.plogging.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {
    Button addNoticeBtn;
    RecyclerView recyclerView;
    ActivityResultLauncher<Intent> startActivityResult;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    String text;
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if( result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                            switch (result.getData().getStringExtra("intentName")){
                                case "noticePost":
                                    text = result.getData().getStringExtra("register");
                                    break;
                                case "noticeForm":
                                    text = result.getData().getStringExtra("recruitment");
                                    break;
                            }
                        }
                    };
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_notice, container, false);


        init(rootView);
        showPost();
        addNotice(addNoticeBtn);

        return rootView;
    }

    private void init(ViewGroup rootView){
        addNoticeBtn = rootView.findViewById(R.id.add_notice_button);
        recyclerView = rootView.findViewById(R.id.recyclerview_notice);
    }

    private void  showPost(){
        NoticeAdapter adapter = setRecyclerView();
        adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), NoticePost.class);
                intent.putExtra("postId", position);
                startActivityResult.launch(intent);
            }
        });
    }
    private NoticeAdapter setRecyclerView(){
        NoticeAdapter adapter = new NoticeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        List<Notice> notices = new ArrayList<>();
        for(int i = 0; i < 10; i ++){
            // test
            notices.add(new Notice(R.drawable.ic_launcher_background, "11", "11", "22", "33" + i));
        }
        adapter.setList(notices);
        return adapter;
    }

    private void addNotice(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NoticeForm.class);
                startActivityResult.launch(intent);
            }
        });
    }






}