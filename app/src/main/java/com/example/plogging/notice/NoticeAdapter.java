package com.example.plogging.notice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plogging.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    List<Notice> items = new ArrayList<>();
    private OnItemClickListener listener = null;

    interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.notice_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notice notice = items.get(position);
        holder.setItem(notice);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setList(List<Notice> list){
        items = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userName;
        TextView location;
        TextView date;
        TextView personNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            personNum = itemView.findViewById(R.id.person_num);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (listener != null) {
                            listener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
        public void setItem(Notice item){
//            Bitmap bitmap = StringBase64.StringToBitmap(item.getImage());
//            userImage.setImageBitmap(bitmap);
            userImage.setImageAlpha(R.drawable.ic_launcher_background);
            userName.setText("null");
            location.setText(item.getLocation());
            date.setText(item.getDate());
//            personNum.setText(item.getUserListSize());
        }
    }
}
