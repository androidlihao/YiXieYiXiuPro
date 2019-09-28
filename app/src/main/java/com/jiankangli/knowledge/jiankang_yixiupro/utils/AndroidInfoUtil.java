package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

/**
 * @author : lihao
 * @date: 2019-01-17 10:13
 * @description : android信息相关工具类
 */
public class AndroidInfoUtil {

    /**
     * 获取基本设备信息
     *
     * @return 设备基本信息字符串
     */
    public static String getDeviceInfo() {
        StringBuffer infoBuffer = new StringBuffer();
        appendLineInfo(infoBuffer, "----- 设备信息 -----");
        //BOARD 主板
        appendLineInfo(infoBuffer, " BOARD: " + android.os.Build.BOARD);
        appendLineInfo(infoBuffer, " BOOTLOADER: " + android.os.Build.BOOTLOADER);
        //BRAND 运营商
        appendLineInfo(infoBuffer, " BRAND: " + android.os.Build.BRAND);
        appendLineInfo(infoBuffer, " CPU_ABI: " + android.os.Build.CPU_ABI);
        appendLineInfo(infoBuffer, " CPU_ABI2: " + android.os.Build.CPU_ABI2);
        //DEVICE 驱动
        appendLineInfo(infoBuffer, " DEVICE: " + android.os.Build.DEVICE);
        //DISPLAY Rom的名字 例如 Flyme 1.1.2（魅族rom） &nbsp;JWR66V（Android nexus系列原生4.3rom）
        appendLineInfo(infoBuffer, " DISPLAY: " + android.os.Build.DISPLAY);
        //指纹
        appendLineInfo(infoBuffer, " FINGERPRINT: " + android.os.Build.FINGERPRINT);
        //HARDWARE 硬件
        appendLineInfo(infoBuffer, " HARDWARE: " + android.os.Build.HARDWARE);
        appendLineInfo(infoBuffer, " HOST: " + android.os.Build.HOST);
        appendLineInfo(infoBuffer, " ID: " + android.os.Build.ID);
        //MANUFACTURER 生产厂家
        appendLineInfo(infoBuffer, " MANUFACTURER: " + android.os.Build.MANUFACTURER);
        //MODEL 机型
        appendLineInfo(infoBuffer, " MODEL: " + android.os.Build.MODEL);
        appendLineInfo(infoBuffer, " PRODUCT: " + android.os.Build.PRODUCT);
        appendLineInfo(infoBuffer, " RADIO: " + android.os.Build.RADIO);
        appendLineInfo(infoBuffer, " RADITAGSO: " + android.os.Build.TAGS);
        appendLineInfo(infoBuffer, " TIME: " + android.os.Build.TIME);
        appendLineInfo(infoBuffer, " TYPE: " + android.os.Build.TYPE);
        appendLineInfo(infoBuffer, " USER: " + android.os.Build.USER);
        //VERSION.RELEASE 固件版本
        appendLineInfo(infoBuffer, " VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE);
        appendLineInfo(infoBuffer, " VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME);
        //VERSION.INCREMENTAL 基带版本
        appendLineInfo(infoBuffer, " VERSION.INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL);
        //VERSION.SDK SDK版本
        appendLineInfo(infoBuffer, " VERSION.SDK: " + android.os.Build.VERSION.SDK);
        appendLineInfo(infoBuffer, " VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT);
        appendLineInfo(infoBuffer, "----- --------- -----");
        return infoBuffer.toString();
    }

    /**
     * 获取当前应用信息
     *
     * @return app基本信息（包名+ versionName + versionCode）
     */
    public static String getAppInfo(Context context) {
        StringBuffer infoBuffer = new StringBuffer();
        appendLineInfo(infoBuffer, "PackageName：" + context.getApplicationInfo().packageName);
        try {
            appendLineInfo(infoBuffer,"AppName:"+context.getResources()
                    .getString(context.getPackageManager().getPackageInfo(context.getPackageName(),0)
                            .applicationInfo.labelRes));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appendLineInfo(infoBuffer, "VersionName：" + getVersionName(context));
        appendLineInfo(infoBuffer, "VersionCode：" + getAppVersionCode(context));
        return infoBuffer.toString();
    }

    /**
     * 获取版本名称
     *
     * @param context 上下文对象
     * @return app VersionName
     */
    public static String getVersionName(Context context) {
        if (context == null) {
            return "context is null";
        }
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "get version name failed";
        }
        if (pi == null) {
            return "get version name failed";
        }
        String versionName = pi.versionName;
        if (versionName == null) {
            return "app not set version name";
        }
        return versionName;
    }

    /**
     * 获取当前应用版本号
     *
     * @param context 上下文对象
     * @return app VersionCode
     */
    public static String getAppVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "get version code failed";
        }
        if (pi == null) {
            return "get version code failed";
        }
        int versionCode = pi.versionCode;
        return String.valueOf(versionCode);
    }

    private static void appendLineInfo(StringBuffer infoBuffer, String str) {
        infoBuffer.append(str).append("\r\n");
    }
    /**
     * 获取AndroidId
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
