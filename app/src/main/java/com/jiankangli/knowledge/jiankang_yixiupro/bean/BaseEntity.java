package com.jiankangli.knowledge.jiankang_yixiupro.bean;

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
        if (code.contentEquals("200")||code.contentEquals("success")) {
            return true;
        }else {
            return false;
        }
    }
}
