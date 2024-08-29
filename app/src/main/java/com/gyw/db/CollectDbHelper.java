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

public class CollectDbHelper extends SQLiteOpenHelper {

    private static CollectDbHelper collectDbHelper;
    private static final String DB_NAME = "collect.db";
    private static final int VERSION = 1;

    public CollectDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建单例,供使用者调用该类的增删改查方法
    public synchronized static CollectDbHelper getInstance(Context context){
        if(collectDbHelper == null){
            collectDbHelper = new CollectDbHelper(context,DB_NAME,null,VERSION);
        }
        return collectDbHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建音乐数据表(专门存放音乐信息的)
        String sql = "CREATE TABLE collect_table ("
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

    //收藏一首
    public int CollectOneMusic(LocalMusicBean musicBean){
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("title", musicBean.getTitle());
        values.put("singer", musicBean.getSinger());
        values.put("album", musicBean.getAlbum());
        values.put("img", musicBean.getImg());
        values.put("path", musicBean.getPath());
        values.put("is_like", 0);//默认0,已经收藏
        String nullColumnHack = "values(null,?,?,?,?,?,?)";
        //执行语句
        int insert = (int) db.insert("collect_table",nullColumnHack,values);
        db.close();
        return insert;
    }
    //一次性收藏多首歌曲
    public void CollectMultipleMusic(List<LocalMusicBean> list){
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
                db.insert("collect_table",nullColumnHack,values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    //查询所有收藏的歌曲
    @SuppressLint("Range")
    public List<LocalMusicBean> getAllCollect(){
        List<LocalMusicBean> list = new ArrayList<>();
        //获取SQLiteDatabase实
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from collect_table";
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

    //删除一首收藏的音乐
    public void deleteOneMusic(String title){
        SQLiteDatabase db = getWritableDatabase();
        String selection = "title=?";
        String[] selectionArgs = {title};
        db.delete("collect_table",selection,selectionArgs);
        db.close();
    }




}
