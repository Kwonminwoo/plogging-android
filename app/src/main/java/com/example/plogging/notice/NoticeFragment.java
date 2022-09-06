package com.example.plogging.notice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.plogging.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_notice, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);

        NoticeAdapter adapter = new NoticeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        List<Notice> notices = new ArrayList<>();
        for(int i = 0; i < 10; i ++){
            notices.add(new Notice(R.drawable.ic_launcher_background, "11", "11", "22", "33" + i));
        }
        adapter.setList(notices);

        adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

}