package com.gyw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gyw.application.R;
import com.gyw.entity.LocalMusicBean;
import com.william.widget.RoundImageView;

import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.CollectViewHolder> {

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

    public CollectAdapter() {
    }

    public CollectAdapter(Context context, List<LocalMusicBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CollectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_collect_list,parent,false);
        CollectViewHolder holder = new CollectViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CollectViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocalMusicBean musicBean = data.get(position);
        holder.collectBk.setImageResource(musicBean.getImg());
        holder.titleTv.setText(musicBean.getTitle());
        holder.singerTv.setText(musicBean.getSinger());

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

    class CollectViewHolder extends RecyclerView.ViewHolder{

        RoundImageView collectBk;
        TextView titleTv,singerTv;
        ImageView like,cancel;

        public CollectViewHolder(View itemView) {
            super(itemView);
            //初始化控件
            collectBk = itemView.findViewById(R.id.item_collect_img);
            titleTv = itemView.findViewById(R.id.item_collect_title);
            singerTv = itemView.findViewById(R.id.item_collect_singer);
            like = itemView.findViewById(R.id.collect_like);
            cancel = itemView.findViewById(R.id.collect_cancel);

        }
    }

}
