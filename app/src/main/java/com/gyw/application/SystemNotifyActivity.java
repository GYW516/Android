package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gyw.adapter.CollectAdapter;
import com.gyw.db.CollectDbHelper;
import com.gyw.entity.LocalMusicBean;
import com.gyw.logcat.LogUtils;

import java.util.List;

/*
有改动
现在这是我的收藏处理类
*/
public class SystemNotifyActivity extends AppCompatActivity {

    RecyclerView collect;
    CollectAdapter adapter;
    List<LocalMusicBean> collects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notify);

        //初始化组件
        collect = findViewById(R.id.collect);

        collects = CollectDbHelper.getInstance(SystemNotifyActivity.this).getAllCollect();
        if(collects.size()>0){
            System.out.println(collects.get(0).getTitle());
        }else {
            LogUtils.LogD("Debug","无记录!");
        }

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        collect.setLayoutManager(layoutManager);

        adapter = new CollectAdapter(this,collects);
        collect.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //返回到我的界面
        findViewById(R.id.notify_system).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }



}