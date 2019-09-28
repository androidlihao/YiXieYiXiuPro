package com.jiankangli.knowledge.jiankang_yixiupro.utils;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.AliasBean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author 王恩钊
 * @time 2017/9/28 15:33
 */

public class DicUtil {

    /**
     * 字段名称转换
     * @param str
     * @return
     */
    public static String getKeyOrValue(String str, Context context) {
        String result = str;
        AliasBean aliasBean = parseAlias(context);
        for (AliasBean.AliasBeanBean aliasBeanBean : aliasBean.getAliasBean()) {
            if (str.equals(aliasBeanBean.getKey())) {
                result = aliasBeanBean.getValue();
                break;
            } else if (str.equals(aliasBeanBean.getValue())) {
                result = aliasBeanBean.getKey();
                break;
            }
        }
        return result;
    }

    /**
     * 字段名称转换
     * @param str
     * @return
     */
    public static String getKeyOrValue(String str, Map<String, String> map) {
        String result = str;
        for (String key : map.keySet()) {
            if (str.equals(key)) {
                result = map.get(key);
                break;
            }else if (str.equals(map.get(key))){
                result = key;
                break;
            }
        }

        return result;
    }
    public static AliasBean parseAlias(Context context) {
        InputStream is = AssetUtil.getIsFromAssets("alias.json",context);
        JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
        return new Gson().fromJson(jsonReader, AliasBean.class);
    }


}
