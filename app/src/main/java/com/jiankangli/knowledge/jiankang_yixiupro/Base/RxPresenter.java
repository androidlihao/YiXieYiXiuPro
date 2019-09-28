package com.jiankangli.knowledge.jiankang_yixiupro.Base;


import android.util.Log;


import com.jiankangli.knowledge.jiankang_yixiupro.utils.LogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ThrowableUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by 李浩 on 2018/3/2.
 */

public class RxPresenter<V extends BaseView> implements BasePresenter<V> {

    protected V mView;

    protected CompositeDisposable mCompositeDisposable;

    /**
     * 添加订阅者进行管理
     *
     * @param subscription 订阅者
     */
    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 取消订阅
     */
    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    @Override
    public void bindView(V view) {
        this.mView = view;
    }

    @Override
    public void unbindView() {
        this.mView = null;
        unSubscribe();
    }

    /**
     *
     * @param throwable 异常
     * @param filepath  写入的地址
     */
    protected void acceptThrowable(Throwable throwable, String filepath) {
        mView.dismissProgressDialog();
        LogUtil.e("accept", Log.getStackTraceString(throwable));
        ThrowableUtil.writeLog(throwable,filepath);
    }
}
