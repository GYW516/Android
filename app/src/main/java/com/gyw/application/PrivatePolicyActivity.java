package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/*
有改动
这里是我喜欢的音乐收藏列表
*/

public class PrivatePolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_policy);

        //返回到我的界面
        findViewById(R.id.policy_privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}