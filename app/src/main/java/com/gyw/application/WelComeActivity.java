package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class WelComeActivity extends AppCompatActivity {

    private TextView tvCountdown;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 4000;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);
        //获取sharePreference
        sp = getSharedPreferences("user",MODE_PRIVATE);
        //初始化控件
        tvCountdown = findViewById(R.id.countDown);
        tvCountdown.setBackgroundColor(Color.TRANSPARENT);

        startCountdown();

    }


    //倒计时
    private void startCountdown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                int secondRemaining = (int) (l/1000);
                tvCountdown.setText(secondRemaining + " s");
            }

            //如果已经检测到登录，直接跳转到主页面
            @Override
            public void onFinish() {
                String account = sp.getString("account", "noAccount");
                if(account == "noAccount"){
                    //跳转到登录页面
                    startActivity(new Intent(WelComeActivity.this, LoginActivity.class));
                }else {
                    //直接跳转到主页面
                    Intent intent = new Intent(WelComeActivity.this,FirstActivity.class);
                    startActivity(intent);
                }

                //倒计时结束后的操作
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }



}