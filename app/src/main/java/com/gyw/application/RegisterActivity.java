package com.gyw.application;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.gyw.db.UserDbHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    private SharedPreferences ap;        //聊天记录缓存
    private String chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //获取组件
        account = findViewById(R.id.re_edit_account);
        password = findViewById(R.id.re_edit_pwd);
        account.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        //小箭头返回
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //销毁该页面,不是跳转
                finish();
            }
        });
        //点击注册
        findViewById(R.id.re_btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = RegisterActivity.this.account.getText().toString();
                String pwd = password.getText().toString();

                if(TextUtils.isEmpty(account)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名和密码",Toast.LENGTH_SHORT).show();
                }else {
                    int row = UserDbHelper.getInstance(RegisterActivity.this).register(account, pwd, "暂无~~~");
                    if(row > 0){
                        chat = account + "_chat";
                        ap = getSharedPreferences(chat,MODE_PRIVATE);
                        ArrayList<HashMap<Integer, Object>> item = new ArrayList<>();
                        HashMap<Integer,Object> map = new HashMap<Integer,Object>();
                        map.put(0xb01,-10);
                        map.put(0xb02,"维塔斯客服很高兴为您服务！");
                        item.add(map);
                        Gson gson = new Gson();                             //将对象转化为json字符串
                        String itemJson = gson.toJson(item);
                        //存储字符串到SharePreference
                        SharedPreferences.Editor edit = ap.edit();
                        edit.putString("chat",itemJson);
                        edit.commit();
                        Toast.makeText(RegisterActivity.this,"注册成功，请登录",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

            }
        });


    }
}
