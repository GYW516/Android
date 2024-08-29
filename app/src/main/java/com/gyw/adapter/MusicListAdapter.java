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
import com.gyw.logcat.LogUtils;
import com.william.widget.RoundImageView;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

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

    public MusicListAdapter() {
    }

    public MusicListAdapter(Context context, List<LocalMusicBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sheet_list,parent,false);
        MusicListViewHolder holder = new MusicListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocalMusicBean musicBean = data.get(position);
        holder.idSh.setText(musicBean.getId());
        holder.imgSh.setImageResource(musicBean.getImg());
        holder.titleSh.setText(musicBean.getTitle());
        holder.singerSh.setText(musicBean.getSinger());

        //“喜欢”监听事件
        holder.likeSh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.LogD("Debug","点击了");
                if(holder.likeSh.getBackground().getConstantState().equals(context.getResources().getDrawable(R.drawable.navi_like_no).getConstantState())){
                    LogUtils.LogD("Debug","已添加到喜欢列表里");
                    holder.likeSh.setBackgroundResource(R.drawable.navi_like_yes);
                }else {
                    LogUtils.LogD("Debug","取消喜欢");
                    holder.likeSh.setBackgroundResource(R.drawable.navi_like_no);
                }
            }
        });
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

    class MusicListViewHolder extends RecyclerView.ViewHolder{

        TextView idSh,titleSh,singerSh;
        RoundImageView imgSh;
        ImageView addSh,likeSh,moreSh;

        public MusicListViewHolder(View itemView) {
            super(itemView);
            //初始化控件
            idSh = itemView.findViewById(R.id.item_sheet_id);
            titleSh = itemView.findViewById(R.id.item_sheet_title);
            singerSh = itemView.findViewById(R.id.item_sheet_singer);
            imgSh = itemView.findViewById(R.id.item_sheet_img);
            addSh = itemView.findViewById(R.id.add_sheet);
            likeSh = itemView.findViewById(R.id.sheet_like);
            moreSh = itemView.findViewById(R.id.more_vert);
        }
    }

}
