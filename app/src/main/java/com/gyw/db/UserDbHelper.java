package com.gyw.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.gyw.entity.UserInfo;

public class UserDbHelper extends SQLiteOpenHelper {

    private static UserDbHelper userDbHelper;
    private static final String DB_MAME = "android.db";
    private static final int VERSION = 1;

    public UserDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    //创建单例，供使用调用该类的增删该查方法
    public synchronized static UserDbHelper getInstance(Context context){
        if(null == userDbHelper){
            userDbHelper = new UserDbHelper(context,DB_MAME,null,VERSION);
        }
        return userDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建user_table表
        db.execSQL("create table user_table(user_id integer primary key autoincrement,"+
                "account text,"+
                "password text,"+
                "nickname text"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /*
    注册
    */
    public int register(String account,String password,String nickname){
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("account",account);
        values.put("password",password);
        values.put("nickname",nickname);
        String nullColumnHack = "values(null,?,?,?)";
        //执行语句
        int insert = (int) db.insert("user_table", nullColumnHack, values);
        db.close();
        return insert;
    }

    /*
    修改用户密码
    */
    public int updatePwd(String account,String password) {
        //获取SQLiteDdataBase实例
        SQLiteDatabase db = getWritableDatabase();
        //填充占位符
        ContentValues values = new ContentValues();
        values.put("password",password);
        //执行sql
        int update = db.update("user_table", values, "account=?", new String[]{account});
        //关闭数据库连接
        db.close();
        return update;
    }

    /*
    登录 / 根据用户名查询单个用户信息
    */
    @SuppressLint("Range")
    public UserInfo login(String account){
        SQLiteDatabase db = getWritableDatabase();
        UserInfo userInfo = null;
        String sql = "select * from user_table where account=?";
        String[] selectionArgs = {account};//查询条件
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if(cursor.moveToNext()){
            int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
            String name = cursor.getString(cursor.getColumnIndex("account"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            userInfo = new UserInfo(user_id,name,password,nickname);
        }
        cursor.close();
        db.close();
        return userInfo;
    }



}
