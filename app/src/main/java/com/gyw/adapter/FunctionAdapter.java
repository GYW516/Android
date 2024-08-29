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
import com.gyw.entity.FunctionItem;

import java.util.List;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder>{

    Context context;
    List<FunctionItem> data;//文字数据源

    //无参构造
    public FunctionAdapter() {
    }

    //有参构造
    public FunctionAdapter(Context context, List<FunctionItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ability, parent, false);
        FunctionViewHolder holder = new FunctionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FunctionItem functionItem = data.get(position);
        holder.item_id.setText(functionItem.getId());
        holder.item_fun.setText(functionItem.getFunctionItem());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /*每个item信息初始化*/
    class FunctionViewHolder extends RecyclerView.ViewHolder{
        TextView item_id,item_fun;

        public FunctionViewHolder(View itemView){
            super(itemView);
            //初始化控件
            item_id = itemView.findViewById(R.id.item_function_id);
            item_fun = itemView.findViewById(R.id.item_function);
        }


    }

}
