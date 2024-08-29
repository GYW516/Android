package com.gyw.application.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gyw.adapter.LocalMusicAdapter;
import com.gyw.application.MusicCdActivity;
import com.gyw.application.R;
import com.gyw.collector.MediaPlayerCollector;
import com.gyw.db.MusicDbHelper;
import com.gyw.entity.LocalMusicBean;
import com.gyw.resource.MusicStatic;
import com.william.widget.RoundImageView;

import java.io.IOException;
import java.util.List;

public class MusicFragment extends Fragment {

    RecyclerView musicRv;
    List<LocalMusicBean> data; //歌曲数据源
    private LocalMusicAdapter adapter;//item适配器

    private TextView music_title;            //底部歌名
    private RoundImageView on_pause;        //底部音乐开关
    private RoundImageView img;            //img歌曲背景图
    private int state = 0;                 //记录音乐状态 0:关 1:开
    private boolean isRunning = false;             //标记任务是否在运行中
    private ObjectAnimator rotationAnimator;       //音乐图标旋转动画
    private RoundImageView cd;                     //音乐图标

    private int position;
    private int currentPlayPosition = 0;    //当前歌曲的位置,默认是0
    MediaPlayer mediaPlayer;                //播放器对象
    private LocalMusicBean musicBean;       //当前播放的音乐对象

    private Handler handler;                //多线程助手

    //公共方法，用于外部对象修改播放状态
    public void changeState(int state,boolean isRunning){
        this.state = state;
        this.isRunning = isRunning;
    }
    //公共方法，用于外部对象改变暂停按钮背景图片
    public void changeBG(boolean isRun){
        if(isRun){
            //正在播放就关掉
            on_pause.setImageResource(R.drawable.m_pause);
        }else {
            //暂停播放就开
            on_pause.setImageResource(R.drawable.m_on);
        }
    }
    //公共方法，用于外部对象调用，改变背景图片和歌名
    public void changeTitle(LocalMusicBean lb,int cd_position){
        musicBean = null;
        musicBean = lb;
        currentPlayPosition = cd_position;
        img.setImageResource(lb.getImg());
        music_title.setText(lb.getTitle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music, container, false);

        //SQlite数据源
        if(MusicDbHelper.getInstance(getActivity()).getAllMusic().size() < 1){
            data = MusicStatic.getLocalMusic();
        }else {
            data = MusicDbHelper.getInstance(getActivity()).getAllMusic();
        }
        //初始化控件
        musicBean = new LocalMusicBean("1","钢铁洪流进行曲","纯音乐","无","02:47", R.drawable.mc_1,R.raw.c1,false,false);
        mediaPlayer = MediaPlayer.create(getActivity(),R.raw.c1);
        //初始化先将MusicFragment加到共享fragment里
        MediaPlayerCollector.addMediaPlayer(mediaPlayer);
        musicRv = view.findViewById(R.id.music_list);
        music_title = view.findViewById(R.id.m_title);
        on_pause = view.findViewById(R.id.m_pause_on);
        img = view.findViewById(R.id.m_photo);
        cd = view.findViewById(R.id.CD);
        //创建适配器对象，管理每一个item
        adapter = new LocalMusicAdapter(getActivity(),data);
        musicRv.setAdapter(adapter);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        musicRv.setLayoutManager(layoutManager);

        //创建动画对象，设置旋转属性为rotation，旋转角度为360度,设置无限循环
        rotationAnimator = ObjectAnimator.ofFloat(cd,"rotation",0f, 360f);
        rotationAnimator.setDuration(3000);
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        handler = new Handler(Looper.getMainLooper());

        //歌曲名title的点击事件，跳转到cd界面
        view.findViewById(R.id.m_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传当前播放的音乐对象，以及播放器即可
                Bundle bundle = new Bundle();
                bundle.putSerializable("musicBean", musicBean);
                bundle.putInt("time",mediaPlayer.getDuration());
                bundle.putInt("current",mediaPlayer.getCurrentPosition());
                bundle.putBoolean("isPlay",mediaPlayer.isPlaying());
                bundle.putInt("currentPlayPosition",currentPlayPosition);
                //页面跳转
                Intent intent = new Intent(getActivity(), MusicCdActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_up,R.anim.fade_out);
            }
        });

        //开启一个线程,处理光盘动画
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(state == 1 && !isRunning){
                        //开始转动
                        isRunning = true;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                rotationAnimator.start();
                            }
                        });
                        System.out.println("转动");
                    }else if (state == 0 && isRunning){
                        //停止转动
                        isRunning = false;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                rotationAnimator.cancel();
                            }
                        });
                        System.out.println("m停止转动");
                    }
                }
            }
        }).start();

        // 设置监听器，当音乐播放完成时触发
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("播放结束");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        rotationAnimator.cancel();
                    }
                });
                on_pause.setImageResource(R.drawable.m_pause);
                isRunning = false;
                state = 0;
            }
        });

        //底部音乐开关按钮
        on_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state == 0){
                    state = 1;
                    on();
                }else if(state == 1){
                    state = 0;
                    pause();
                }
            }
        });

        //数据源变化，提示适配器更新
        adapter.notifyDataSetChanged();

        //设置每一项的点击事件
        setEventListener();

        return view;
    }

    //每一项点击事件
    private void setEventListener(){
        adapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                currentPlayPosition = position;
                musicBean = data.get(currentPlayPosition);
                music_title.setText(musicBean.getTitle());
                img.setImageResource(musicBean.getImg());
                state = 1;
                stopMusic();
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(getContext(),Uri.parse("android.resource://"+getContext().getPackageName()+"/"+musicBean.getPath()));
                    System.out.println(Uri.parse("android.resource://"+getContext().getPackageName()+"/"+musicBean.getPath()));
                    playMusic();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //暂停播放
    private void pause(){
        /*暂停播放*/
        if(mediaPlayer != null){
            mediaPlayer.pause();
            on_pause.setImageResource(R.drawable.m_pause);
        }
    }

    //继续播放
    private void on(){
        /*播放音乐*/
        if(mediaPlayer != null && !mediaPlayer.isPlaying()){
           mediaPlayer.start();
        }
        on_pause.setImageResource(R.drawable.m_on);
    }

    //播放音乐 再次点击会重头开始播放 （每一项点击）
    private void playMusic(){
        /*播放音乐*/
        if(mediaPlayer != null && !mediaPlayer.isPlaying()){
            try {
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        on_pause.setImageResource(R.drawable.m_on);
    }

    //停止音乐 进度条会重开降到0 （每一项点击）
    private void stopMusic(){
        /*停止音乐*/
        if(mediaPlayer != null){
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            on_pause.setImageResource(R.drawable.m_pause);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();//释放资源
        }
    }


}