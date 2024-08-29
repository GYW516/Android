package com.gyw.collector;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*
页面收藏
*/

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    //实现在主界面侧滑退出到桌面以及侧滑两次退出
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

}
