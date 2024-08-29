package com.gyw.application.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyw.adapter.HotMusicAdapter;
import com.gyw.adapter.RankListAdapter;
import com.gyw.application.MusicListActivity;
import com.gyw.application.R;
import com.gyw.db.HotPushDbHelper;
import com.gyw.db.MusicDbHelper;
import com.gyw.entity.BannerData;
import com.gyw.entity.LocalMusicBean;
import com.gyw.resource.MusicStatic;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Banner mbanner;
    private List<BannerData> bannerList = new ArrayList<BannerData>();

    private RecyclerView rank_list;                //听歌排行
    private RecyclerView hot_list;                 //热门歌推荐
    List<LocalMusicBean> data;                     //歌曲数据源
    List<LocalMusicBean> hot;                      //热门歌曲推送数据源
    private RankListAdapter adapter;               //排行榜item适配器
    private HotMusicAdapter hotMusicAdapter;       //热门歌推荐item适配器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        //初始化数据源
        data = MusicStatic.getLocalMusic();
        //热门歌曲推送数据源
        if(HotPushDbHelper.getInstance(getActivity()).getAllCollect().size() < 1){
            hot = MusicStatic.getLocalMusic();
        }else {
            hot = HotPushDbHelper.getInstance(getActivity()).getAllCollect();
        }

        //初始化控件
        mbanner = view.findViewById(R.id.banner);
        rank_list = view.findViewById(R.id.ranking_list);
        hot_list = view.findViewById(R.id.hot_list);

        //模拟数据（静态）
        bannerList.add(new BannerData(R.drawable.b1,"图1"));
        bannerList.add(new BannerData(R.drawable.b2,"图2"));
        bannerList.add(new BannerData(R.drawable.b3,"图3"));
        bannerList.add(new BannerData(R.drawable.b4,"图4"));

        mbanner.setAdapter(new BannerImageAdapter<BannerData>(bannerList) {
            @Override
            public void onBindView(BannerImageHolder holder, BannerData data, int position, int size) {
                //设置数据
                holder.imageView.setImageResource(data.getImg());
            }
        }).addBannerLifecycleObserver(getActivity()).setIndicator(new CircleIndicator(getActivity()));

        //纯音乐歌单点击监听
        view.findViewById(R.id.sheet_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传递当前是哪个歌单
                Bundle bundle = new Bundle();
                bundle.putInt("numb",1);
                //页面跳转
                Intent intent = new Intent(getActivity(), MusicListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //进击的巨人歌单点击监听
        view.findViewById(R.id.sheet_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传递当前是哪个歌单
                Bundle bundle = new Bundle();
                bundle.putInt("numb",2);
                //页面跳转
                Intent intent = new Intent(getActivity(),MusicListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        //国产音乐歌单点击监听
        view.findViewById(R.id.sheet_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传递当前是哪个歌单
                Bundle bundle = new Bundle();
                bundle.putInt("numb",3);
                //页面跳转
                Intent intent = new Intent(getActivity(),MusicListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager lM = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rank_list.setLayoutManager(layoutManager);
        hot_list.setLayoutManager(lM);

        //创建适配器对象，管理每一个item;
        adapter = new RankListAdapter(getActivity(),data);
        rank_list.setAdapter(adapter);

        //创建热门歌推荐适配器
        hotMusicAdapter = new HotMusicAdapter(getActivity(),hot);
        hot_list.setAdapter(hotMusicAdapter);

        //数据源变化，提示适配器更新
        adapter.notifyDataSetChanged();
        hotMusicAdapter.notifyDataSetChanged();

        return view;
    }
}