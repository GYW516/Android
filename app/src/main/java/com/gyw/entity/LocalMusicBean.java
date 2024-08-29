package com.gyw.entity;

import java.io.Serializable;

public class LocalMusicBean implements Serializable {

    private String id;            //歌曲id
    private String title;      //歌曲名字
    private String singer;     //歌曲歌手
    private String album;      //歌曲专辑
    private String time;       //歌曲时长
    private int img;           //歌曲图片路径
    private int path;       //mp3路径 raw
    private Boolean is_like;   //歌曲是喜欢
    private Boolean is_collect; //歌曲收藏

    public LocalMusicBean() {
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public LocalMusicBean(String id, String title, String singer, String album, String time, int img, int path, Boolean is_like, Boolean is_collect) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.album = album;
        this.time = time;
        this.img = img;
        this.path = path;
        this.is_like = is_like;
        this.is_collect = is_collect;
    }

    public Boolean getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(Boolean is_collect) {
        this.is_collect = is_collect;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public Boolean getIs_like() {
        return is_like;
    }

    public void setIs_like(Boolean is_like) {
        this.is_like = is_like;
    }
}
