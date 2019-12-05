package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import android.text.TextUtils;

/**
 * @author : lihao
 * @date: 2019-05-20 14:56
 * Description: 实体父类
 * */
public class BaseEntity<T> {
    public String code;
    public String time;
    public String msg;
    public T data;

    public boolean isSuccess(){
        if (code!=null){
            if (TextUtils.equals("200",code)||TextUtils.equals("success",code)) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
