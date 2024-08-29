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
import com.william.widget.RoundImageView;

import java.util.List;

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.RankListViewHolder> {

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

    public RankListAdapter() {
    }

    public RankListAdapter(Context context, List<LocalMusicBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RankListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rank_list,parent,false);
        RankListViewHolder holder = new RankListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocalMusicBean musicBean = data.get(position);
        holder.idRk.setText(musicBean.getId());
        holder.imgRk.setImageResource(musicBean.getImg());
        holder.titleRk.setText(musicBean.getTitle());
        holder.singerRk.setText(musicBean.getSinger());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onItemClickListener.OnItemClick(view,position);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RankListViewHolder extends RecyclerView.ViewHolder{

        TextView idRk,titleRk,singerRk;
        RoundImageView imgRk;

        public RankListViewHolder(View itemView) {
            super(itemView);
            //初始化控件
            idRk = itemView.findViewById(R.id.item_rank_id);
            imgRk = itemView.findViewById(R.id.item_rank_img);
            titleRk = itemView.findViewById(R.id.item_rank_title);
            singerRk = itemView.findViewById(R.id.item_rank_singer);

        }
    }

}
