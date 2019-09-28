package com.jiankangli.knowledge.jiankang_yixiupro.Base;

import android.content.Context;

/**
 * @author : chenxin
 * @date : 2019-01-16 16:17
 * @description : view 基类
 */
public interface BaseView {

    Context getContext();

    /**
     * 显示进度对话框
     *
     * @param msg
     */
    void showProgressDialog(String msg);

    /**
     * 隐藏进度对话框
     */
    void dismissProgressDialog();

    /**
     * 显示提示信息对话框
     *
     * @param msg
     */
    void showMessageDialog(String msg);
}
