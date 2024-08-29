package com.gyw.logcat;

import android.util.Log;
/*
logcat包装类
*/
public class LogUtils {
    private static boolean openLog = true;

    //调试
    public static void LogD(String tag,String msg){
        if(openLog){
            Log.d(tag, msg);
        }
    }
    //错误
    public static void LogE(String tag,String msg){
        if(openLog){
            Log.e(tag, msg);
        }
    }
    //详细
    public static void LogV(String tag,String msg){
        if(openLog){
            Log.v(tag, msg);
        }
    }
    //警告
    public static void LogW(String tag,String msg){
        if(openLog){
            Log.w(tag, msg);
        }
    }
    //信息
    public static void LogI(String tag,String msg){
        if(openLog){
            Log.i(tag, msg);
        }
    }


}
