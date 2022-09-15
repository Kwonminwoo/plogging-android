package com.example.plogging.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plogging.R;
import com.example.plogging.dto.User;
import com.example.plogging.retrofit.PloggingService;
import com.example.plogging.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {
    private TextView userName[] = new TextView[10];
    private TextView userPostNum[] = new TextView[10];
    private ImageView userImage[] = new ImageView[3];

    private int[] nameId = {R.id.user_name_1, R.id.user_name_2, R.id.user_name_3,
            R.id.user_name_4, R.id.user_name_5, R.id.user_name_6, R.id.user_name_7,
            R.id.user_name_8, R.id.user_name_9, R.id.user_name_10};

    private int[] postNumId = {R.id.user_postNum_1, R.id.user_postNum_2, R.id.user_postNum_3,
            R.id.user_postNum_4, R.id.user_postNum_5, R.id.user_postNum_6, R.id.user_postNum_7,
            R.id.user_postNum_8, R.id.user_postNum_9, R.id.user_postNum_10};

    private int[] imageId = {R.id.user_image_1, R.id.user_image_2, R.id.user_image_3};
    private TextView myRank;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);
        init(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getUserData();
    }

    private void init(ViewGroup rootView){
        for(int i = 0;i < userName.length; i++){
            userName[i] = rootView.findViewById(nameId[i]);
            userPostNum[i] = rootView.findViewById(postNumId[i]);
        }
        for(int i = 0;i < userImage.length; i++){
            userImage[i] = rootView.findViewById(imageId[i]);
        }
        myRank = rootView.findViewById(R.id.my_rank);
    }



    private void getUserData(){
        Retrofit retrofit = RetrofitClient.getClient();
        PloggingService service = retrofit.create(PloggingService.class);

        Call<List<User>> call = service.getRank();

        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(!response.isSuccessful()){
                    Log.d("response", "msg: " + response.code());
                    return;
                }
                List<User> findList = response.body();
                setRank(findList);
                return;
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("RankOnFail", "msg: " + t.getMessage());
            }
        });
    }

    private void setRank(List<User> list){
        Collections.sort(list, Collections.reverseOrder());
        for(int i = 0;i < list.size();i++){
            userName[i].setText(list.get(i).getUserName());
            userPostNum[i].setText(list.get(i).getPostNum()+ "회");
        }
        for(int i = 0;i < ((list.size() < userImage.length) ? list.size():userImage.length);i++){
            userImage[i].setImageAlpha(R.drawable.ic_launcher_foreground);
        }

        myRank.setText("1");
    }
}