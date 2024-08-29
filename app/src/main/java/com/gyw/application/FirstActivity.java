package com.gyw.application;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.gyw.application.fragment.HomeFragment;
import com.gyw.application.fragment.MineFragment;
import com.gyw.application.fragment.MusicFragment;
import com.gyw.collector.FragmentCollector;
import com.gyw.logcat.LogUtils;

public class FirstActivity extends AppCompatActivity {

    private int x1;
    private int x2;
    private int counter = 0;

    //三个碎片
    private HomeFragment homeFragment;    //首页面
    private MusicFragment musicFragment;  //音乐界面
    private MineFragment mineFragment;    //我的界面

    //底部导航栏
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        //初始化控件
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //点击导航栏监听
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    selectedFragment(0);
                    LogUtils.LogD("debug","首页");
                }else if(item.getItemId() == R.id.music){
                    selectedFragment(1);
                    LogUtils.LogD("debug","music");
                }else if(item.getItemId() == R.id.mine){
                    selectedFragment(2);
                    LogUtils.LogD("debug","我的");
                }
                return true;
            }
        });

        //默认选中首页
        selectedFragment(0);

    }

    //默认选中首页
    private void selectedFragment(int position){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = manager.beginTransaction();
        hideFragment(beginTransaction);

        if(position == 0){
            if(homeFragment == null){
                homeFragment = new HomeFragment();
                beginTransaction.add(R.id.content,homeFragment,"fragment-home");
            }else {
                beginTransaction.show(homeFragment);
            }
        }else if(position == 1){
            if(musicFragment == null){
                musicFragment = new MusicFragment();
                beginTransaction.add(R.id.content,musicFragment,"fragment-music");
                FragmentCollector.addFragment(musicFragment);
            }else {
                beginTransaction.show(musicFragment);
            }
        }else if(position == 2){
            if(mineFragment == null){
                mineFragment = new MineFragment();
                beginTransaction.add(R.id.content,mineFragment,"fragment-mine");
            }else {
                beginTransaction.show(mineFragment);
            }
        }
        beginTransaction.commit();

    }
    //隐藏
    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(homeFragment != null){
            fragmentTransaction.hide(homeFragment);
        }
        if(musicFragment != null){
            fragmentTransaction.hide(musicFragment);
        }
        if(mineFragment != null){
            fragmentTransaction.hide(mineFragment);
        }
    }


    //连续侧滑两次退出程序
    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = (int) event.getRawX();
                if (x2 - x1 > 200) { // 设定滑动的距离阈值
                  if(counter == 0){
                      Toast.makeText(this, "再次滑动退出程序", Toast.LENGTH_SHORT).show();
                      counter++;
                  }else if(counter == 1){
                      finish();
                      System.exit(0);
                  }
                }
                break;
        }

        return true;
    }


    }
