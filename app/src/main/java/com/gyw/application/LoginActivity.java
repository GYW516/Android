package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.gyw.collector.ActivityCollector;
import com.gyw.db.UserDbHelper;
import com.gyw.entity.UserInfo;

/*
MainActivity就是登录界面
*/

public class LoginActivity extends AppCompatActivity {

    private EditText et_account; //用户名
    private EditText et_pwd;     //密码
    private CheckBox checkBox;   //记住密码
    private SharedPreferences sp;
    private boolean is_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        //获取sharePreference
        sp = getSharedPreferences("user",MODE_PRIVATE);
        //相关联的控件
        et_account = (EditText) findViewById(R.id.login_edit_account);
        et_pwd = (EditText) findViewById(R.id.login_edit_pwd);
        et_account.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        checkBox = findViewById(R.id.check_rm);

        //是否勾选了记住密码(就是之前登录给个信息缓存)
        is_login = sp.getBoolean("is_login", false);
        if(is_login){
            String account = sp.getString("account", null);
            String password = sp.getString("password", null);
            et_account.setText(account);
            et_pwd.setText(password);
        }

        //点击注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册页面
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //点击登录
        findViewById(R.id.login_btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.获取用户输入
                String account = et_account.getText().toString().trim();
                String password = et_pwd.getText().toString().trim();
                //2.判断输入是否为空
                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                    //2.1如果为空Toast提示用户不能为空
                    Toast.makeText(LoginActivity.this,"用户名密码不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    if(account.equals(new String("200388")) && password.equals(new String("200388"))){
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("is_login", false);
                        edit.putString("account",account);
                        edit.putString("password",password);
                        edit.commit();
                        //登录成功
                        Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                        startActivity(intent);
                        ActivityCollector.finishAll();
                    }else {
                        System.out.println("非管理员登录");
                        UserInfo login = UserDbHelper.getInstance(LoginActivity.this).login(account);
                        if(login != null){
                            if(account.equals(login.getAccount())&&password.equals(login.getPassword())){
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putBoolean("is_login", LoginActivity.this.is_login);
                                edit.putString("account",account);
                                edit.putString("password",password);
                                edit.commit();
                                //登录成功
                                Intent intent = new Intent(LoginActivity.this,FirstActivity.class);
                                startActivity(intent);
                                ActivityCollector.finishAll();
                            }else {
                                Toast.makeText(LoginActivity.this,"用户名密码错误",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,"该账号未注册",Toast.LENGTH_SHORT).show();
                        }

                    }


                }
            }
        });

        //记住密码
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LoginActivity.this.is_login = b;
            }
        });


        Log.d("MainActivity", "debug");
    }



}