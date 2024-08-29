package com.gyw.resource;

import com.gyw.application.R;
import com.gyw.entity.LocalMusicBean;

import java.util.ArrayList;
import java.util.List;

public class MusicStatic {
    public static List<LocalMusicBean> getLocalMusic(){
        List<LocalMusicBean> data = new ArrayList<>();
        /*加载本地存储当中的音乐mp3文件到集合当中*/
        data.add(new LocalMusicBean("1","钢铁洪流进行曲","纯音乐","无","02:47", R.drawable.mc_1,R.raw.c1,false,false));
        data.add(new LocalMusicBean("2","Cat's Eye","杏里(Anri)","无","03:17",R.drawable.mr_1,R.raw.r1,false,false));
        data.add(new LocalMusicBean("3","ちゃんと言わないと愛せない","石川小百合","无","03:11",R.drawable.mr_2,R.raw.r2,false,false));
        data.add(new LocalMusicBean("4","黑夜问白天","纯音乐","无","01:00",R.drawable.mc_2,R.raw.c2,false,false));
        data.add(new LocalMusicBean("5","Lotta Lovin","ENGLISH","无","02:06",R.drawable.me_1,R.raw.e1,false,false));
        data.add(new LocalMusicBean("6","Speak Softly Love","纯音乐","无","04:37",R.drawable.mc_3,R.raw.c3,false,false));
//        data.add(new LocalMusicBean("7","天涯","C少爷","无","00:18",R.drawable.mz_1,R.raw.z1,false));
//        data.add(new LocalMusicBean("8","谁料黄榜中状元","韩再芬","无","03:40",R.drawable.mz_2,R.raw.z2,false));
//        data.add(new LocalMusicBean("9","Hit the Road Jack","Piano","无","02:24",R.drawable.mc_4,R.raw.c4,false));
//        data.add(new LocalMusicBean("10","三傻大闹宝莱坞","印度群星","无","04:27",R.drawable.mc_5,R.raw.y1,false));
//        data.add(new LocalMusicBean("11","美丽的神话","成龙——金喜善","无","04:50",R.drawable.mz_3,R.raw.z3,false));
//        data.add(new LocalMusicBean("12","Nunca Es Suficiente","Natalia","无","03:57",R.drawable.mz_4,R.raw.z4,false));
//        data.add(new LocalMusicBean("13","缘之空","纯音乐","无","03:00",R.drawable.mc_6,R.raw.c6,false));
//        data.add(new LocalMusicBean("14","风的季节","soler","无","04:10",R.drawable.mz_5,R.raw.z5,false));
//        data.add(new LocalMusicBean("15","心臓を差し出せ！","Linked-Horizon","无","05:41",R.drawable.mr_3,R.raw.r3,false));
//        data.add(new LocalMusicBean("16","空中散步-哈儿的移动城堡","月爱汐","无","02:14",R.drawable.mc_7,R.raw.c7,false));
        return data;
    }

    //歌单1
    public static List<LocalMusicBean> getListOne(){
        List<LocalMusicBean> one = new ArrayList<>();
        one.add(new LocalMusicBean("1","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mc_1,0,false,false));
        one.add(new LocalMusicBean("2","天空之城(钢琴版)","纯音乐","无","0",R.drawable.l_c1,0,false,false));
        one.add(new LocalMusicBean("3","夜空的寂静(钢琴曲)","纯音乐","无","0",R.drawable.l_c2,0,false,false));
        one.add(new LocalMusicBean("4","四小天鹅舞曲","纯音乐","无","0",R.drawable.l_c3,0,false,false));
        one.add(new LocalMusicBean("5","少女的祈祷(钢琴曲)","纯音乐","无","0",R.drawable.l_c4,0,false,false));
        one.add(new LocalMusicBean("6","梦中的婚礼","纯音乐","无","0",R.drawable.l_c5,0,false,false));
        one.add(new LocalMusicBean("7","瞬间的永恒","纯音乐","无","0",R.drawable.l_c6,0,false,false));
        one.add(new LocalMusicBean("8","千与千寻(钢琴纯音乐版)","纯音乐","无","0",R.drawable.l_c7,0,false,false));
        one.add(new LocalMusicBean("9","匈牙利第五区(钢琴曲)","纯音乐","无","0",R.drawable.l_c8,0,false,false));
        one.add(new LocalMusicBean("10","致爱丽丝","纯音乐","无","0",R.drawable.l_c9,0,false,false));
        return one;
    }

    //歌单2
    public static List<LocalMusicBean> getListTwo(){
        List<LocalMusicBean> two = new ArrayList<>();
        two.add(new LocalMusicBean("1","红莲的弓矢","Linked Horizon","无","0",R.drawable.l_j1,0,false,false));
        two.add(new LocalMusicBean("2","自由之翼","Linked Horizon","无","0",R.drawable.l_j2,0,false,false));
        two.add(new LocalMusicBean("3","献出心脏吧！","Linked Horizon","无","0",R.drawable.l_j3,0,false,false));
        two.add(new LocalMusicBean("4","Red Swan","YOSHIKI feat.HYDE","无","0",R.drawable.l_j4,0,false,false));
        two.add(new LocalMusicBean("5","憧憬与屍之道","Linked Horizon","无","0",R.drawable.l_j5,0,false,false));
        two.add(new LocalMusicBean("6","我的战争","神圣放逐乐队","无","0",R.drawable.l_j6,0,false,false));
        two.add(new LocalMusicBean("7","The Rumbling","SiM","无","0",R.drawable.l_j7,0,false,false));
        two.add(new LocalMusicBean("8","美丽而残酷的世界","日笠阳子","无","0",R.drawable.l_j8,0,false,false));
        two.add(new LocalMusicBean("9","great escape","cinema staff","无","0",R.drawable.l_j9,0,false,false));
        two.add(new LocalMusicBean("10","黄昏之鸟","神圣放逐乐队","无","0",R.drawable.l_j10,0,false,false));
        return two;
    }

    //歌单3
    public static  List<LocalMusicBean> getListThree(){
        List<LocalMusicBean> three = new ArrayList<>();
        three.add(new LocalMusicBean("1","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("2","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("3","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("4","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("5","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("6","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("7","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("8","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("9","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        three.add(new LocalMusicBean("10","钢铁洪流进行曲","纯音乐","无","0",R.drawable.mz_5,0,false,false));
        return three;
    }




}
