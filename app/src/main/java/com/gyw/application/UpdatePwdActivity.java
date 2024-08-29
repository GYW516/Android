package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gyw.collector.ActivityCollector;
import com.gyw.db.UserDbHelper;
import com.gyw.entity.UserInfo;

public class UpdatePwdActivity extends AppCompatActivity {

    private EditText up_et_account;
    private EditText up_et_newPwd;
    private EditText up_et_confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        //初始化控件
        up_et_account = findViewById(R.id.up_et_account);
        up_et_newPwd = findViewById(R.id.up_et_newPwd);
        up_et_confirm = findViewById(R.id.up_et_confirm);

        //修改密码点击事件
        findViewById(R.id.up_bt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = up_et_account.getText().toString();
                String new_pwd = up_et_newPwd.getText().toString();
                String confirm = up_et_confirm.getText().toString();

                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(new_pwd) || TextUtils.isEmpty(confirm)){
                    Toast.makeText(UpdatePwdActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                }else if(!new_pwd.equals(confirm)){
                    Toast.makeText(UpdatePwdActivity.this, "新密码和确认密码不一致", Toast.LENGTH_SHORT).show();
                }else {
                    UserInfo userInfo = UserDbHelper.getInstance(UpdatePwdActivity.this).login(account);
                    if(userInfo != null){
                        //用户存在，修改密码
                        int row = UserDbHelper.getInstance(UpdatePwdActivity.this).updatePwd(userInfo.getAccount(), new_pwd);
                        if(row > 0){
                            Toast.makeText(UpdatePwdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                            //回传的时候要用startActivityForResult启动一个页面，并且在该页面要设置setResult
//                            setResult(1000);
                            ActivityCollector.finishAll();
                            finish();
                            Intent intent = new Intent(UpdatePwdActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(UpdatePwdActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //用户不存在
                        Toast.makeText(UpdatePwdActivity.this, "该用户不存在，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //返回到我的界面
        findViewById(R.id.change_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




}