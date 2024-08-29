package com.gyw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gyw.application.R;
import com.gyw.entity.LocalMusicBean;

import java.util.List;

public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.LocalMusicViewHolder> {

    Context context;
    List<LocalMusicBean> data;//音乐数据源

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //点击事件接口
    public interface OnItemClickListener{
        public void OnItemClick(View view,int position);
    }

    public LocalMusicAdapter() {
    }

    public LocalMusicAdapter(Context context, List<LocalMusicBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public LocalMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_local_music,parent,false);
        LocalMusicViewHolder holder = new LocalMusicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalMusicViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocalMusicBean musicBean = data.get(position);
        holder.idTv.setText(musicBean.getId());
        holder.titleTv.setText(musicBean.getTitle());
        holder.singerTv.setText(musicBean.getSinger());
        holder.timeTv.setText(musicBean.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(view,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalMusicViewHolder extends RecyclerView.ViewHolder{

        TextView idTv,titleTv,singerTv,timeTv;

        public LocalMusicViewHolder(View itemView) {
            super(itemView);
            //初始化控件
            idTv = itemView.findViewById(R.id.item_local_music_id);
            titleTv = itemView.findViewById(R.id.item_local_music_title);
            singerTv = itemView.findViewById(R.id.item_local_music_singer);
            timeTv = itemView.findViewById(R.id.item_local_music_time);

        }
    }

}
