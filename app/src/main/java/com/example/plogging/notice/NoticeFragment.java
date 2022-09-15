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

import com.example.plogging.R;
import com.example.plogging.dto.User;
import com.example.plogging.retrofit.PloggingService;
import com.example.plogging.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeFragment extends Fragment {
    Button addNoticeBtn;
    RecyclerView recyclerView;
    ActivityResultLauncher<Intent> startActivityResult;
    List<Notice> posts = new ArrayList<>();
    List<User> postsUser = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPosts();
        getPostUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_notice, container, false);

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
                                    Notice notice = (Notice) result.getData().getSerializableExtra("new_notice");
                                    System.out.println();
                                    break;
                                case "noticeForm":
                                    text = result.getData().getStringExtra("recruitment");
                                    break;
                            }
                        }
                    };
                });


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
                Notice notice = posts.get(position);
                User user = postsUser.get(position);
                intent.putExtra("select_post", notice); // 선택된 포스트를 넘김
                intent.putExtra("select_post_user", user);
                startActivityResult.launch(intent);
            }
        });
    }
    private NoticeAdapter setRecyclerView(){
        NoticeAdapter adapter = new NoticeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setList(posts, postsUser);
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

    private void getPostUserInfo(){
        Retrofit retrofit = RetrofitClient.getClient();
        PloggingService service = retrofit.create(PloggingService.class);

        Call<List<User>> call = service.getPostUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(!response.isSuccessful()){
                    Log.d("response", "msg: " + response.code());
                    return;
                }
                List<User> findUserList = response.body();
                postsUser = findUserList;
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("PostUserOnFail", "msg: " + t.getMessage());
            }
        });

    }

    // DB 리스트 가져오기
    private void getPosts(){
        Retrofit retrofit = RetrofitClient.getClient();
        PloggingService service = retrofit.create(PloggingService.class);

        Call<List<Notice>> call = service.getPosts();
        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                if(!response.isSuccessful()){
                    Log.d("response", "msg: " + response.code());
                    return;
                }
                List<Notice> findList = response.body();
                posts = findList;
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Log.d("PostOnFail", "msg: " + t.getMessage());
            }
        });
    }
}