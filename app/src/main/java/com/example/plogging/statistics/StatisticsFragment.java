package com.example.plogging.statistics;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.plogging.R;

public class StatisticsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_statistics, container, false);

        //RecyclerView recyclerViewParent = rootView.findViewById(R.id.recyclerview_statistics);
        //recyclerViewParent.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerViewParent.setAdapter(adapter);



        return rootView;
    }
}