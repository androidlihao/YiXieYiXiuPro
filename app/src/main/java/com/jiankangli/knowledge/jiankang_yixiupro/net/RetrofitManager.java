package com.jiankangli.knowledge.jiankang_yixiupro.net;

import com.jiankangli.knowledge.jiankang_yixiupro.Constant.constant;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;

/**
 * Created by 李浩 on 2018/5/17.
 */
import okhttp3.OkHttpClient;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lw on 2017-04-01.
 */

public class RetrofitManager {
    private static long CONNECT_TIMEOUT = 60L;
    private static long READ_TIMEOUT = 10L;
    private static long WRITE_TIMEOUT = 10L;
    private static volatile OkHttpClient mOkHttpClient;
    /**
     * 获取Service
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.REQUEST_BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
    /**
     * 获取OkHttpClient实例
     * 设置请求时长
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                //Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

}
