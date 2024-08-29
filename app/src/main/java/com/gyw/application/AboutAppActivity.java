package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gyw.adapter.FunctionAdapter;
import com.gyw.entity.FunctionItem;
import com.gyw.resource.FunctionItemStatic;

import java.util.List;

public class AboutAppActivity extends AppCompatActivity {

    private List<FunctionItem> data;//功能文字数据源
    private RecyclerView item_list; //控件
    private FunctionAdapter adapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        //数据源初始化
        data = FunctionItemStatic.getFunctionItem();
        //控件初始化
        item_list = findViewById(R.id.item_ability_list);
        //创建适配器对象，管理每一个item
        adapter = new FunctionAdapter(this,data);
        item_list.setAdapter(adapter);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        item_list.setLayoutManager(layoutManager);

        //数据源变化，提示适配器更新
        adapter.notifyDataSetChanged();

        //返回到我的界面
        findViewById(R.id.app_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}