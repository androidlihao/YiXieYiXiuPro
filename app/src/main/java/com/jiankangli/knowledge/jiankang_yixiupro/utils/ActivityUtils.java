package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @date 2019-04-17 15:55
 * @description :
 */
public class ActivityUtils{


    private static ActivityUtils instance ;
    private static List<Activity> activityStack = new ArrayList<Activity>();

    public static ActivityUtils getInstance() {
        if (instance==null){
            synchronized (ActivityUtils.class){
                if (instance==null){
                    instance=new ActivityUtils();
                }
            }
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }

}
