package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.gyw.db.HotPushDbHelper;
import com.gyw.db.MusicDbHelper;
import com.gyw.entity.LocalMusicBean;

import java.util.ArrayList;
import java.util.List;

/*管理员操作*/

public class AdminActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //获取sharePreference
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);

        //退出登录
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AdminActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("确定要退出登录吗?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //删除缓存里的东西
                                SharedPreferences.Editor edit = sp.edit();
                                edit.remove("account");
                                edit.remove("password");
                                edit.remove("is_login");
                                edit.commit();
                                //退出登录
                                finish();
                                //打开登录页面
                                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        //增加音乐
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("增");
                List<LocalMusicBean> data = new ArrayList<>();
                /*加载本地存储当中的音乐mp3文件到集合当中*/
                data.add(new LocalMusicBean("1","钢铁洪流进行曲","纯音乐","无","02:47", R.drawable.mc_1,R.raw.c1,false,false));
                data.add(new LocalMusicBean("2","Cat's Eye","杏里(Anri)","无","03:17",R.drawable.mr_1,R.raw.r1,false,false));
                data.add(new LocalMusicBean("3","ちゃんと言わないと愛せない","石川小百合","无","03:11",R.drawable.mr_2,R.raw.r2,false,false));
                data.add(new LocalMusicBean("4","黑夜问白天","纯音乐","无","01:00",R.drawable.mc_2,R.raw.c2,false,false));
                data.add(new LocalMusicBean("5","Lotta Lovin","ENGLISH","无","02:06",R.drawable.me_1,R.raw.e1,false,false));
                data.add(new LocalMusicBean("6","Speak Softly Love","纯音乐","无","04:37",R.drawable.mc_3,R.raw.c3,false,false));
                data.add(new LocalMusicBean("7","天涯","C少爷","无","00:18",R.drawable.mz_1,R.raw.z1,false,false));
                data.add(new LocalMusicBean("8","谁料黄榜中状元","韩再芬","无","03:40",R.drawable.mz_2,R.raw.z2,false,false));
                data.add(new LocalMusicBean("9","Hit the Road Jack","Piano","无","02:24",R.drawable.mc_4,R.raw.c4,false,false));
                data.add(new LocalMusicBean("10","三傻大闹宝莱坞","印度群星","无","04:27",R.drawable.mc_5,R.raw.y1,false,false));
                data.add(new LocalMusicBean("11","美丽的神话","成龙——金喜善","无","04:50",R.drawable.mz_3,R.raw.z3,false,false));
                data.add(new LocalMusicBean("12","Nunca Es Suficiente","Natalia","无","03:57",R.drawable.mz_4,R.raw.z4,false,false));
                data.add(new LocalMusicBean("13","缘之空","纯音乐","无","03:00",R.drawable.mc_6,R.raw.c6,false,false));
                data.add(new LocalMusicBean("14","风的季节","soler","无","04:10",R.drawable.mz_5,R.raw.z5,false,false));
                data.add(new LocalMusicBean("15","心臓を差し出せ！","Linked-Horizon","无","05:41",R.drawable.mr_3,R.raw.r3,false,false));
                data.add(new LocalMusicBean("16","空中散步-哈儿的移动城堡","月爱汐","无","02:14",R.drawable.mc_7,R.raw.c7,false,false));
                MusicDbHelper.getInstance(AdminActivity.this).addMultipleMusic(data);
            }
        });

        //获取音乐
        findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("获取");
                List<LocalMusicBean> musicList = MusicDbHelper.getInstance(AdminActivity.this).getAllMusic();
                if (musicList != null){
                    for (LocalMusicBean musicBean : musicList) {
                        System.out.println(musicBean.getId());
                        System.out.println(musicBean.getTitle());
                        System.out.println(musicBean.getSinger());
                        System.out.println(musicBean.getImg());
                        System.out.println(musicBean.getPath());
                        System.out.println(musicBean.getIs_like());
                    }
                }
            }
        });

        //推送音乐
        findViewById(R.id.push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("推送");
                List<LocalMusicBean> push_data = new ArrayList<>();
                push_data.add(new LocalMusicBean("1","钢铁洪流进行曲","纯音乐","无","02:47", R.drawable.mc_1,R.raw.c1,false,false));
                push_data.add(new LocalMusicBean("2","Cat's Eye","杏里(Anri)","无","03:17",R.drawable.mr_1,R.raw.r1,false,false));
                push_data.add(new LocalMusicBean("3","ちゃんと言わないと愛せない","石川小百合","无","03:11",R.drawable.mr_2,R.raw.r2,false,false));
                push_data.add(new LocalMusicBean("4","黑夜问白天","纯音乐","无","01:00",R.drawable.mc_2,R.raw.c2,false,false));
                push_data.add(new LocalMusicBean("5","Lotta Lovin","ENGLISH","无","02:06",R.drawable.me_1,R.raw.e1,false,false));
                push_data.add(new LocalMusicBean("6","Speak Softly Love","纯音乐","无","04:37",R.drawable.mc_3,R.raw.c3,false,false));
                push_data.add(new LocalMusicBean("7","天涯","C少爷","无","00:18",R.drawable.mz_1,R.raw.z1,false,false));
                HotPushDbHelper.getInstance(AdminActivity.this).PushMultipleMusic(push_data);
            }
        });

        //获取推送音乐的信息
        findViewById(R.id.all_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("获取推送音乐信息");
                List<LocalMusicBean> data = HotPushDbHelper.getInstance(AdminActivity.this).getAllCollect();
                if(data != null){
                    for (LocalMusicBean bean : data) {
                        System.out.println(bean.getTitle());
                    }
                }
            }
        });


    }
}