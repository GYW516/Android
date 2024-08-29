package com.gyw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gyw.application.R;
import com.gyw.db.CollectDbHelper;
import com.gyw.db.HotPushDbHelper;
import com.gyw.entity.LocalMusicBean;
import com.gyw.logcat.LogUtils;
import com.william.widget.RoundImageView;

import java.util.List;

public class HotMusicAdapter extends RecyclerView.Adapter<HotMusicAdapter.HotListViewHolder> {

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

    public HotMusicAdapter() {
    }

    public HotMusicAdapter(Context context, List<LocalMusicBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public HotListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot_song,parent,false);
        HotListViewHolder holder = new HotListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocalMusicBean musicBean = data.get(position);
        holder.imgHot.setImageResource(musicBean.getImg());
        holder.titleHot.setText(musicBean.getTitle());
        holder.singerHot.setText(musicBean.getSinger());
        if(musicBean.getIs_collect()){
            holder.collect.setBackgroundResource(R.drawable.c_yes);
        }else {
            holder.collect.setBackgroundResource(R.drawable.c_no);
        }

        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.collect.getBackground().getConstantState().equals(context.getResources().getDrawable(R.drawable.c_yes).getConstantState())){
                    holder.collect.setBackgroundResource(R.drawable.c_no);
                    //更新推送端的数据
                    int row = HotPushDbHelper.getInstance(context).updateIsCollect(Integer.parseInt(musicBean.getId()), 0);
                    if(row > 0){
                        CollectDbHelper.getInstance(context).deleteOneMusic(musicBean.getTitle());
                        Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                        LogUtils.LogD("Debug","取消收藏");
                    }
                }else {
                    holder.collect.setBackgroundResource(R.drawable.c_yes);
                    //更新推送端信息
                    int row = HotPushDbHelper.getInstance(context).updateIsCollect(Integer.parseInt(musicBean.getId()), 1);
                    if(row > 0){
                        //本地加一首音乐
                        CollectDbHelper.getInstance(context).CollectOneMusic(musicBean);
                        Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                        LogUtils.LogD("Debug","收藏成功");
                    }else {
                        Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });
        //整个大框子的事件监听
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

    class HotListViewHolder extends RecyclerView.ViewHolder{

        TextView titleHot,singerHot;
        RoundImageView imgHot;
        ImageView collect;

        public HotListViewHolder(View itemView) {
            super(itemView);
            //初始化控件
            imgHot = itemView.findViewById(R.id.item_hot_img);
            titleHot = itemView.findViewById(R.id.item_hot_title);
            singerHot = itemView.findViewById(R.id.item_hot_singer);
            collect = itemView.findViewById(R.id.item_hot_collect);

        }
    }

}
