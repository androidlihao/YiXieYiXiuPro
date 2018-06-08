package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.util.Base64;

import org.json.JSONObject;


/**
 * Created by 李浩 on 2018/5/17.
 */

public class BaseJsonUtils {
    public static String Base64String(JSONObject json){
        return Base64.encodeToString(json.toString().getBytes(), Base64.NO_WRAP);
    }
}
