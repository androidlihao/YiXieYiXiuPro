package com.jiankangli.knowledge.jiankang_yixiupro.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;


import com.jiankangli.knowledge.jiankang_yixiupro.utils.log.NetWorkLog;

import java.io.IOException;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * @author : lihao
 * @date : 2019-01-16 16:17
 * @description :Log拦截器代码
 */
public class LogInterceptor implements Interceptor {

    private static final String TAG = "okhttp";
    private static final String POST = "POST";
    private static final String GET = "GET";

    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Log.w(TAG, "request:" + request.toString());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        Log.w(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        assert response.body() != null;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();


//        writLog(request, content);


        Log.w(TAG, "response body:" + content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    /**
     * 编写网络请求日志
     *
     * @param request
     * @param result
     */
    private void writLog(Request request, String result) throws IOException {
        String method = request.method();
        StringBuilder params = new StringBuilder();
        if (POST.equalsIgnoreCase(method)) {

            if (request.body() instanceof FormBody && ((FormBody) request.body()).size() > 0) {
                FormBody formBody = (FormBody) request.body();

                for (int i = 0; i < formBody.size(); i++) {
                    params.append(formBody.encodedValue(i));
                    if (i != formBody.size() - 1) {
                        params.append(",");

                    }
                }
            }
        }
        NetWorkLog netWorkLog = NetWorkLog.getInstance();
        if (!TextUtils.isEmpty(method)) {
            netWorkLog.writeH1("RequestMethod:" + method);
        }
        netWorkLog.writeContent(request.url().toString(), params.toString(), result);


    }
}
