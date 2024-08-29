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

public class MusicDbHelper extends SQLiteOpenHelper {

    private static MusicDbHelper musicDbHelper;
    private static final String DB_NAME = "music.db";
    private static final int VERSION = 1;

    public MusicDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建单例,供使用者调用该类的增删改查方法
    public synchronized static MusicDbHelper getInstance(Context context){
        if(musicDbHelper == null){
            musicDbHelper = new MusicDbHelper(context,DB_NAME,null,VERSION);
        }
        return musicDbHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建音乐数据表(专门存放音乐信息的)
        String sql = "CREATE TABLE music_table ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT,"
                + "singer TEXT,"
                + "album TEXT,"
                + "img INTEGER,"
                + "path INTEGER,"
                + "is_like INTEGER"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS music_table");
//        onCreate(db);
    }

    //增加一首歌曲
    public int addOneMusic(LocalMusicBean musicBean){
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("title", musicBean.getTitle());
        values.put("singer", musicBean.getSinger());
        values.put("album", musicBean.getAlbum());
        values.put("img", musicBean.getImg());
        values.put("path", musicBean.getPath());
        values.put("is_like", 0);//默认0,不喜欢
        String nullColumnHack = "values(null,?,?,?,?,?,?)";
        //执行语句
        int insert = (int) db.insert("music_table",nullColumnHack,values);
        db.close();
        return insert;
    }
    //一次性添加多首歌曲
    public void addMultipleMusic(List<LocalMusicBean> list){
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
                values.put("is_like", 0);//默认0,不喜欢
                db.insert("music_table",nullColumnHack,values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    //查询所有歌曲
    @SuppressLint("Range")
    public List<LocalMusicBean> getAllMusic(){
        List<LocalMusicBean> list = new ArrayList<>();
        //获取SQLiteDatabase实
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from music_table";
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
                musicBean.setIs_like(cursor.getInt(cursor.getColumnIndex("is_like")) == 1);
                list.add(musicBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }




}
