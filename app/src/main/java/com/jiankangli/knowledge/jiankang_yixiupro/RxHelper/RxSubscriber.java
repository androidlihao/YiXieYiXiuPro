package com.jiankangli.knowledge.jiankang_yixiupro.RxHelper;


import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 *
 * @author 李浩 封装Subscriber
 * @date 2019/1/16
 * @deprecated 封装，只需要管返回的数据的成功和失败，进度条可以展示出来
 */
public abstract class RxSubscriber<T> implements Observer<T> {

    String msg;
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        msg=e.toString();
        if (e instanceof InterruptedException) {
            // 服务器异常
            msg ="请求中断...";
        } else if (e instanceof UnknownHostException) {
            msg = "没有网络...";
        } else if (e instanceof SocketTimeoutException) {
            // 超时
            msg = "访问服务器超时...";
        }else{
            msg = "请求失败，请稍后重试...";
        }
         _onError(e,msg);
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onSubscribe(Disposable d) {
        //开始准备
    }

    @Override
    public void onComplete() {
        
        //结束准备
    }

    public abstract void _onNext(T t);

    public abstract void _onError(Throwable e, String msg);

}

