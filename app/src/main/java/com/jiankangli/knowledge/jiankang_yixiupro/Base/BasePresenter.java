package com.jiankangli.knowledge.jiankang_yixiupro.Base;

/**
 * @author : chenxin
 * @date : 2019-01-16 16:17
 * @description : presenter基类
 */
public interface BasePresenter<V extends BaseView> {
    /**
     * 获取View
     *
     * @param view
     */
    void bindView(V view);

    /**
     * 解绑View
     */
    void unbindView();


}
