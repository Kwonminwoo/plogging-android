package com.example.plogging.statistics;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plogging.R;
import com.example.plogging.dto.User;
import com.example.plogging.retrofit.RetrofitClient;
import com.example.plogging.retrofit.UserService;
import com.example.plogging.sqlite_database.MyPlogging;
import com.example.plogging.sqlite_database.MyPloggingData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatisticsFragment extends Fragment {
    private MyPlogging myPlogging;
    private List<MyPloggingData> ploggingList;
    private TextView userName;


    String[] items = {"2022", "2021", "2020"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ploggingList = new ArrayList<>();
        setSQLite();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_statistics, container, false);

//        RecyclerView recyclerViewParent = rootView.findViewById(R.id.recyclerview_statistics);
//        recyclerViewParent.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewParent.setAdapter(adapter);
        Spinner spinner = rootView.findViewById(R.id.spinner1);

        userName = rootView.findViewById(R.id.statistics_user_name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                //API에 만들어져 있는 R.layout.simple_spinner...를 씀
                getActivity(),android.R.layout.simple_spinner_item, items
        );

        //미리 정의된 레이아웃 사용
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // 스피너 객체에다가 어댑터를 넣어줌
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택했을 때 실행 코드
                //textView.setText(items[position]);
                //userName.setText(items[position]);
            }

            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //선택 되지 않았을 때 코드
                //2022
                //textView.setText("선택: ");
            }
        });

        testData("2022-1-3", "대구");
        testData("2022-1-5", "서울");

        ploggingList = getPloggingData(2022, 1);

        Toast.makeText(getContext(), ploggingList.get(1).getLocation(), Toast.LENGTH_SHORT).show();

        setUser();

        return rootView;
    }
    
    private void setUser(){
        Retrofit retrofit = RetrofitClient.getClient();
        UserService service = retrofit.create(UserService.class);

        Call<User> call = service.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Log.d("response", "msg: " + response.code());
                    return;
                }
                userName.setText(response.body().getUserName()); // 유저 이름 설정
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("onFail", "msg: " + t.getMessage());
            }
        });
    }
    
    // SQLite
    private void setSQLite(){
        myPlogging = new MyPlogging(getContext());
        myPlogging.createDB();
    }

    private List<MyPloggingData> getPloggingData(int year, int month){
        List<MyPloggingData> findList = new ArrayList<>();
        findList.addAll(myPlogging.getMyPlogging(year, month));
        return findList;
    }

    private void testData(String date, String locat){
        myPlogging.testSetData(date, locat);
    }
}