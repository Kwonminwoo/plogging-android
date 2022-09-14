package com.example.plogging.statistics;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.plogging.notice.Notice;
import com.example.plogging.retrofit.RetrofitClient;
import com.example.plogging.retrofit.PloggingService;
import com.example.plogging.sqlite_database.MyPlogging;
import com.example.plogging.sqlite_database.MyPloggingData;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatisticsFragment extends Fragment {
    private RecyclerView recyclerView[] = new RecyclerView[12];
    private StatisticsAdapter adapter[] = new StatisticsAdapter[12];
    private List<Notice> noticeList[] = new List[12];

    private int[] recyclerViewId = {R.id.rec_statistics_1, R.id.rec_statistics_2, R.id.rec_statistics_3,
            R.id.rec_statistics_4, R.id.rec_statistics_5, R.id.rec_statistics_6,
            R.id.rec_statistics_7, R.id.rec_statistics_8, R.id.rec_statistics_9,
            R.id.rec_statistics_10, R.id.rec_statistics_11, R.id.rec_statistics_12, };

    private MyPlogging myPlogging;
    private List<MyPloggingData> ploggingList[] = new List[12];
    private Spinner spinner;
    private TextView userName;

    private int findYear = 2022;


    String[] items = {"2022", "2021", "2020"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for(int i = 0;i < ploggingList.length;i++){
            ploggingList[i] = new ArrayList<>();
        }
        setSQLite();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_statistics, container, false);

        setUser();

        userName = rootView.findViewById(R.id.statistics_user_name);
        spinner = rootView.findViewById(R.id.spinner1);


        addMyPlogging("2022-1-3", "대구");
        addMyPlogging("2022-2-5", "서울");
        addMyPlogging("2022-3-5", "부산");





        setSpinner(rootView);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        myPlogging.deleteAll();
    }

    private void setSpinner(ViewGroup rootView){

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
                ploggingList[0] = getPloggingData(findYear, 1);
                ploggingList[1] = getPloggingData(findYear, 2);
                ploggingList[2] = getPloggingData(findYear, 3);
                setRecyclerViewData();
                initRecyclerView(rootView);
                findYear = Integer.parseInt(items[position]);
            }

            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initRecyclerView(ViewGroup rootView) {

        for(int i = 0;i < recyclerView.length;i++){
            recyclerView[i] = rootView.findViewById(recyclerViewId[i]);
            recyclerView[i].setAdapter(adapter[i]);
            recyclerView[i].setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        }
    }

    // user DB 호출
    private void setUser(){
        Retrofit retrofit = RetrofitClient.getClient();
        PloggingService service = retrofit.create(PloggingService.class);

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
                System.out.println("caasdsasdadll.toString() = " + call.toString());
                Log.d("UserOnFail", "msg: " + t.getMessage());
            }
        });
    }

    private void setRecyclerViewData(){
        System.out.println(noticeList.length + "dasfasdasfsaad");
        // 월별 출력
        for(int i = 0;i < noticeList.length;i++){
            adapter[i] = new StatisticsAdapter(ploggingList[i]);
        }
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

    private void addMyPlogging(String date, String locat){
        myPlogging.inserData(date, locat);
    }
}