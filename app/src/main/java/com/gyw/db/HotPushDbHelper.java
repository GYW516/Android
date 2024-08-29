package com.gyw.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.gyw.entity.LocalMusicBean;

import java.util.ArrayList;
import java.util.List;

public class HotPushDbHelper extends SQLiteOpenHelper {

    private static HotPushDbHelper hotPushDbHelper;
    private static final String DB_NAME = "hotPush.db";
    private static final int VERSION = 1;

    public HotPushDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建单例,供使用者调用该类的增删改查方法
    public synchronized static HotPushDbHelper getInstance(Context context){
        if(hotPushDbHelper == null){
            hotPushDbHelper = new HotPushDbHelper(context,DB_NAME,null,VERSION);
        }
        return hotPushDbHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建音乐数据表(专门存放音乐信息的)
        String sql = "CREATE TABLE hot_table ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT,"
                + "singer TEXT,"
                + "album TEXT,"
                + "img INTEGER,"
                + "path INTEGER,"
                + "is_collect INTEGER"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS music_table");
//        onCreate(db);
    }

    //推送一首热门歌
    public int PushOneMusic(LocalMusicBean musicBean){
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("title", musicBean.getTitle());
        values.put("singer", musicBean.getSinger());
        values.put("album", musicBean.getAlbum());
        values.put("img", musicBean.getImg());
        values.put("path", musicBean.getPath());
        values.put("is_collect", 0);//默认0,没有收藏
        String nullColumnHack = "values(null,?,?,?,?,?,?)";
        //执行语句
        int insert = (int) db.insert("hot_table",nullColumnHack,values);
        db.close();
        return insert;
    }
    //一次性推送多首热门歌
    public void PushMultipleMusic(List<LocalMusicBean> list){
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        String nullColumnHack = "values(null,?,?,?,?,?,?)";
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (LocalMusicBean musicBean : list) {
                //填充占位符
                values.put("title", musicBean.getTitle());
                values.put("singer", musicBean.getSinger());
                values.put("album", musicBean.getAlbum());
                values.put("img", musicBean.getImg());
                values.put("path", musicBean.getPath());
                values.put("is_collect", 0);//默认0,不喜欢
                db.insert("hot_table",nullColumnHack,values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    //查询所有推送的热门歌
    @SuppressLint("Range")
    public List<LocalMusicBean> getAllCollect(){
        List<LocalMusicBean> list = new ArrayList<>();
        //获取SQLiteDatabase实
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from hot_table";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            do {
                LocalMusicBean musicBean = new LocalMusicBean();
                musicBean.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                musicBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                musicBean.setSinger(cursor.getString(cursor.getColumnIndex("singer")));
                musicBean.setAlbum(cursor.getString(cursor.getColumnIndex("album")));
                musicBean.setImg(cursor.getInt(cursor.getColumnIndex("img")));
                musicBean.setPath(cursor.getInt(cursor.getColumnIndex("path")));
                musicBean.setIs_collect(cursor.getInt(cursor.getColumnIndex("is_collect")) == 1);
                list.add(musicBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    //更新热门歌的状态（是否已收藏）
    public int updateIsCollect(int id,int isCollect){
        //获取SQLiteDataBase实例
        SQLiteDatabase db = getWritableDatabase();
        //填充占位符
        ContentValues values = new ContentValues();
        values.put("is_collect",isCollect);
        String[] selectionArgs = {String.valueOf(id)};
        //执行sql
        int update = db.update("hot_table",values,"id=?",selectionArgs);
        db.close();
        return update;
    }




}
