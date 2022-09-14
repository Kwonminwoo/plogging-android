package com.example.plogging.statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plogging.R;
import com.example.plogging.notice.Notice;
import com.example.plogging.sqlite_database.MyPloggingData;

import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    private List<MyPloggingData> datas;

    public StatisticsAdapter(List<MyPloggingData> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.statistics_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyPloggingData notice = datas.get(position);
        holder.location.append(notice.getLocation());
        holder.date.append(notice.getDate());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView location;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.statistics_location);
            date = itemView.findViewById(R.id.statistics_date);
        }

        public void setItem(Notice notice){
            location.setText(notice.getLocation());
            date.setText(notice.getDate());
        }
    }
}
