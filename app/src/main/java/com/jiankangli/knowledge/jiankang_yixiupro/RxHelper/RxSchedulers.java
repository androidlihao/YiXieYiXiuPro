package com.jiankangli.knowledge.jiankang_yixiupro.RxHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : lihao
 * @date: 2019/1/16 15:05
 * @description : rxjava 响应式线程切换
 */

public class RxSchedulers {

    /**
     * 通用线程（IO Thread==> Main Thread）
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io2main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {

                return upstream.subscribeOn(Schedulers.io())

                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
