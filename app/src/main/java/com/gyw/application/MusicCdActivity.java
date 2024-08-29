package com.gyw.application;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gyw.application.fragment.MusicFragment;
import com.gyw.collector.FragmentCollector;
import com.gyw.collector.MediaPlayerCollector;
import com.gyw.db.MusicDbHelper;
import com.gyw.entity.LocalMusicBean;
import com.gyw.logcat.LogUtils;
import com.gyw.resource.MusicStatic;
import com.william.widget.RoundImageView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import kotlin.text.UStringsKt;

public class MusicCdActivity extends AppCompatActivity{

    private SeekBar seekBar;                      //进度条对象
    private LocalMusicBean musicBean;             //播放音乐对象
    private int currentPosition;                  //当前歌曲的位置
    private int time;                             //歌曲时间
    private int current;                          //音乐当前播放时间
    private boolean isPlay;                       //音乐是否在播放

    private List<LocalMusicBean> localMusicBeans;  //歌曲数据源
    private Bundle bundle;                        //包裹

    private TextView title;                       //歌曲标题
    private TextView singer;                      //歌曲唱者
    private RoundImageView image;                 //歌曲背景图片
    private ImageView pause;                      //暂停开关
    private TextView dynamicTime;                 //动态时间
    private TextView staticTime;                  //静态时间
    private Handler handler = new Handler();      //定时器
    private Timer timer;                          //定时器

    private MediaPlayer mediaPlayer;              //播放器对象
    private MusicFragment musicFragment;          //musicFragment

    private ObjectAnimator animator;              //背景图片动画
    private float currentRotation = 0.0f;         //记录旋转当前位置

    //格式化时间
    private String ToFormat(int mm){
        //将毫秒转化为分钟和秒
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mm);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(mm) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d",minutes,seconds);
    }

    //更新时间线程
    private Runnable updateCurrentTime = new Runnable() {
        @Override
        public void run() {
            if(!mediaPlayer.isPlaying()){
                //播放器是关着的,不更新时间
                return;
            }
            int currentTime = mediaPlayer.getCurrentPosition();
            //播放器是开着的，更新时间
            int newTime = currentTime + 1000;
            String formatTime = ToFormat(newTime);
            dynamicTime.setText(formatTime);
            //每秒更新一次
            handler.postDelayed(this,1000);
        }
    };

    //进度条更新方法
    private void updateProgressBar(MediaPlayer mp){
        int currentPosition = mp.getCurrentPosition();
        int progress = (int)(((double)currentPosition / time) * 100);
        seekBar.setProgress(progress);
    }

    //开启进度条
    private void startProgressBar(){
        if(timer == null){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    updateProgressBar(mediaPlayer);
                }
            },0,1000);//每秒更新进度
        }
    }

    //暂停进度条
    private void pauseProgressBar(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    //切换音乐封装
    private void playMusicInMusicBean(LocalMusicBean mBean){
        title.setText(mBean.getTitle());                     //音乐名称更新
        singer.setText(mBean.getSinger());                   //歌唱者或类型更新
        image.setImageResource(mBean.getImg());              //背景图片更新
        dynamicTime.setText("00:00");
        musicFragment.changeState(1,false);   //碎片状态更新
        musicFragment.changeBG(false);                 //碎片状态更新
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        mediaPlayer.stop();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://com.gyw.application/"+mBean.getPath()));
            if(mediaPlayer != null && !mediaPlayer.isPlaying()){
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        time = mediaPlayer.getDuration();
        String format = ToFormat(mediaPlayer.getDuration());
        staticTime.setText(format);
        pause.setImageResource(R.drawable.navi_pause);
        handler.post(updateCurrentTime);                    //动态时间随时更新
        //为了保持旋转角度，再开启动画的话需要重新new一个动画对象
        if(animator != null){
            //说明刚开是进入到这个界面
            animator.start();
        }else {
            animator = ObjectAnimator.ofFloat(image,"rotation",currentRotation,currentRotation+360);
            animator.setDuration(10000);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.start();
        }
        startProgressBar();                                //进度条更新
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_cd);

        //SQlite数据源
        if(MusicDbHelper.getInstance(MusicCdActivity.this).getAllMusic().size() < 1){
            localMusicBeans = MusicStatic.getLocalMusic();
        }else {
            localMusicBeans = MusicDbHelper.getInstance(MusicCdActivity.this).getAllMusic();
        }

        //获取musicFragment
        List<MusicFragment> fragments = FragmentCollector.fragments;
        musicFragment = fragments.get(0);

        //获取播放器对象
        List<MediaPlayer> players = MediaPlayerCollector.mediaPlayers;
        mediaPlayer = players.get(0);

        //获取包裹（页面之间的数据传送）
        bundle = getIntent().getExtras();
        if(bundle != null){
            musicBean = (LocalMusicBean) bundle.getSerializable("musicBean");
            time = bundle.getInt("time");
            current = bundle.getInt("current");
            isPlay = bundle.getBoolean("isPlay");
            currentPosition = bundle.getInt("currentPlayPosition");
        }

        //初始化组件
        seekBar = findViewById(R.id.cd_seekBar);
        title = findViewById(R.id.cd_title);
        singer = findViewById(R.id.cd_singer);
        image = findViewById(R.id.cd_image);
        pause = findViewById(R.id.cd_pause);
        dynamicTime = findViewById(R.id.cd_dynamic);
        staticTime = findViewById(R.id.cd_static);
        //初始化进度条
        updateProgressBar(mediaPlayer);

        //创建动画对象，设置旋转属性为rotation，旋转角度为360度,设置无限循环
        animator = ObjectAnimator.ofFloat(image,"rotation",0f, 360f);
        animator.setDuration(10000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);

        //动画对象监听,时刻获取转动的位置
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator Animator) {
                currentRotation = (float) Animator.getAnimatedValue();//记录当前旋转角度
            }
        });

        //时长初始化操作,  页面渲染初始化
        String currentTime = ToFormat(current);
        String allTime = ToFormat(time);
        staticTime.setText(allTime);
        dynamicTime.setText(currentTime);

        title.setText(musicBean.getTitle());
        singer.setText(musicBean.getSinger());
        image.setImageResource(musicBean.getImg());
        if(mediaPlayer.isPlaying()){
            //播放器是开着的
            handler.post(updateCurrentTime);
            pause.setImageResource(R.drawable.navi_pause);
            animator.start();
            startProgressBar();
        }else {
            //播放器是关着的
            pause.setImageResource(R.drawable.navi_pause_on);
            animator.cancel();
        }

        //开关按钮
        findViewById(R.id.cd_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    //正在播放的话点击后停止
                    handler.removeCallbacks(updateCurrentTime);
                    mediaPlayer.pause();
                    pause.setImageResource(R.drawable.navi_pause_on);
                    musicFragment.changeState(0,true);
                    musicFragment.changeBG(true);
                    animator.cancel();
                    pauseProgressBar();
                    //用于释放动画对象内存，垃圾回收
                    animator = null;
                }else {
                    //不播放就开
                    handler.post(updateCurrentTime);
                    mediaPlayer.start();
                    pause.setImageResource(R.drawable.navi_pause);
                    musicFragment.changeState(1,false);
                    musicFragment.changeBG(false);
                    //为了保持旋转角度，再开启动画的话需要重新new一个动画对象
                    if(animator != null){
                        //说明刚开是进入到这个界面
                        animator.start();
                    }else {
                        animator = ObjectAnimator.ofFloat(image,"rotation",currentRotation,currentRotation+360);
                        animator.setDuration(10000);
                        animator.setRepeatCount(ObjectAnimator.INFINITE);
                        animator.start();
                    }
                    startProgressBar();

                }
            }
        });

        // 设置监听器，当音乐播放完成时触发,开关改为暂停按钮
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handler.removeCallbacks(updateCurrentTime);
                pause.setImageResource(R.drawable.navi_pause_on);
                musicFragment.changeState(0,true);
                musicFragment.changeBG(true);
                animator.cancel();
                pauseProgressBar();
            }
        });

        //设置上一首点击监听
        findViewById(R.id.cd_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition == 0){
                    Toast.makeText(MusicCdActivity.this, "已经是第一首了，没有上一曲", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPosition = currentPosition - 1;
                musicBean = null;//原来的bean对象无引用，垃圾回收
                musicBean = localMusicBeans.get(currentPosition);
                musicFragment.changeTitle(musicBean,currentPosition);
                playMusicInMusicBean(musicBean);
            }
        });

        //设置下一首点击监听
        findViewById(R.id.cd_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition == (localMusicBeans.size()-1)){
                    Toast.makeText(MusicCdActivity.this, "已经是最后一首了，没有下一曲", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPosition = currentPosition + 1;
                musicBean = null;
                musicBean = localMusicBeans.get(currentPosition);
                musicFragment.changeTitle(musicBean,currentPosition);
                playMusicInMusicBean(musicBean);

            }
        });

        //返回到音乐界面
        findViewById(R.id.cd_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 设置 SeekBar 的监听器（拖动）有待实现
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 如果是用户拖动 SeekBar 导致的进度改变，则执行相应操作
                if (fromUser) {
                    // 在这里处理音乐播放器的进度改变操作
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 用户开始拖动 SeekBar 时调用，可以在这里暂停音乐播放器
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 用户停止拖动 SeekBar 时调用，可以在这里继续音乐播放器
            }
        });



    }


    //销毁操作，释放不必要的资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁时停止更新
        handler.removeCallbacks(updateCurrentTime);
        pauseProgressBar();
    }
}