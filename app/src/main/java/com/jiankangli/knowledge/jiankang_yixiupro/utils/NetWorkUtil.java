package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by chenxin on 2017/3/20.
 */

public class NetWorkUtil {
    public static final String TAG = "NetWorkUtil";
    /**
     * 无网络连接
     */
    public static final int NET_WORK_TYPE_INVALID = 0;

    /**
     * 移动网络
     */
    public static final int NET_WORK_TYPE_MOBILE = 1;
    /**
     * wifi网络
     */
    public static final int NET_WORK_TYPE_WIFI = 2;
    private NetWorkUtil() {
    }



    /**
     * 检查当前网络状态
     */
    public static int checkNetWorkType(Context context) {
        int netWorkType = 0;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if (typeName.equalsIgnoreCase("WIFI")) {
                netWorkType = NET_WORK_TYPE_WIFI;
            } else if (typeName.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                netWorkType = NET_WORK_TYPE_MOBILE;
            }

        } else {
            netWorkType = NET_WORK_TYPE_INVALID;
        }

        return netWorkType;
    }

    /**
     * 检查当前版本
     * @throws IOException
     */
  //  public static void checkVersion(final Context context, final VersionCallback callback) throws Exception {
//        boolean isNewAppliction = false;
////        DownloaderUtils.getInstance().getXml(DownloaderConstant.VERSION_CONFIG,callback);
//        Request request = new Request.Builder().url(DownloaderConstant.VERSION_CONFIG).build();
//        OkHttpClient client = new OkHttpClient();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String xml = response.body().string();
//                try {
//                    VersionConfigBean configBean = VersionParser.xml2Bean(xml);
//                    String versionName = BuildConfig.VERSION_NAME;
//                    if (versionName.equals(configBean.getVersion())){
//                        callback.onEqual();
//                    }else{
//                        callback.onUnEqual(configBean);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

  //  }


}
