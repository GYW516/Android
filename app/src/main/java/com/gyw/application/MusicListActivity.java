package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gyw.adapter.MusicListAdapter;
import com.gyw.entity.LocalMusicBean;
import com.gyw.resource.MusicStatic;

import java.util.List;

public class MusicListActivity extends AppCompatActivity {

    List<LocalMusicBean> data;      //歌单的数据源
    private int numb;               //歌单编号
    private TextView title;         //歌单名称
    private Bundle bundle;          //包裹
    private RecyclerView sheetList;
    private MusicListAdapter adapter;   //歌单适配器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        //初始化组件
        sheetList = findViewById(R.id.sheet_list);
        title = findViewById(R.id.sheet_title);

        //获取包裹
        bundle = getIntent().getExtras();
        if(bundle != null){
            numb = bundle.getInt("numb",0);
        }
        //初始化歌单数据源
        if(numb == 1){
            data = MusicStatic.getListOne();
            title.setText("纯音乐歌单");
        }else if(numb == 2){
            data = MusicStatic.getListTwo();
            title.setText("进击的巨人歌单");
        }else {
            data = MusicStatic.getListThree();
            title.setText("国产原风歌单");
        }


        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        sheetList.setLayoutManager(layoutManager);

        //创建适配器对象
        adapter = new MusicListAdapter(this,data);
        sheetList.setAdapter(adapter);

        //点击导航栏返回到原界面
        findViewById(R.id.sheet_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}