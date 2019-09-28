package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author : linjian
 * @date : 2019-01-16 16:21
 * @description :设备信息工具类
 */
public class DeviceUtil {

    /**
     * 设备是否为手机（不推荐）
     * 实际是判断是否可以打电话
     *
     * @param context 上下文对象
     * @return {@code true}是手机<br>{@code false} 不是手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 判断当前设备是手机还是平板（推荐）
     * Google I/O App for Android
     *
     * @param context 上下文对象
     * @return {@code true}是平板<br>{@code false} 不是平板
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取手机设备唯一标识码（MacCode）IMEI
     * 只有手机才有IMEI 标识码
     *
     * @param context 上下文对象
     * @return {@code 是手机}设备唯一标识码<br>{@code 不是手机} null
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (isPhone(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return telephonyManager.getImei();
            } else {
                return telephonyManager.getDeviceId();
            }
        }
        return null;
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    public static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalIpAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    public static InetAddress getLocalIpAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = null;

            en_netInterface = NetworkInterface.getNetworkInterfaces();
            //是否还有元素
            while (en_netInterface.hasMoreElements()) {
                //得到下一个元素
                NetworkInterface ni = en_netInterface.nextElement();
                //得到一个ip地址的列举
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")){
                        break;
                    }
                    else{
                        ip = null;
                    }
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位) 读取失败为"0000000000000000"
     */
    public static String getCPUSerial() {
        String str = null;
        String strCPU = null;
        String cpuAddress = "0000000000000000";
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat/proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.contains("Serial")) {
                        // 提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        // 去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cpuAddress;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context 上下文对象
     * @return 屏幕分辨率(eg : " 1080 * 1920 ")
     */
    public static String getResolution(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int width;
        int height;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        return width + "*" + height;
    }


    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键高度(如果没有虚拟键盘，得到的高度值和屏幕高度一样）
     *
     * @param context 上下文对象
     * @return 屏幕原始高度(单位 ： px)
     */
    public static int getOriginalHeight(Context context) {
        int screenHeightPx = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            screenHeightPx = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenHeightPx;
    }

    /**
     * 获取虚拟按键的高度
     *
     * @param context 上下文对象
     * @return (单位 ： px)
     */
    public static int getVirtualKeyHeight(Context context) {
        int totalHeight = getOriginalHeight(context);
        int contentHeight = getScreenHeightPx(context);
        return totalHeight - contentHeight;
    }

    /**
     * 获取标题栏高度
     *
     * @param activity 屏幕窗口对象
     * @return 获取到的top值（单位 ： px）
     */
    public static int getTitleHeight(AppCompatActivity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文对象
     * @return 状态栏高度值（单位：px）
     */
    public static int getStatusHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    /**
     * 获得屏幕高度（可展示区域的高度值）
     *
     * @param context 上下文对象
     * @return 屏幕高度值，不包含虚拟按键的高度（单位：px）
     */
    public static int getScreenHeightPx(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获得屏幕高度（可展示区域的高度值）
     *
     * @param context 上下文对象
     * @return 屏幕高度值，不包含虚拟按键的高度（单位：dp）
     */
    public static int getScreenHeightDp(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) (dm.heightPixels / dm.density);
    }


    /**
     * 获得屏幕宽度
     *
     * @param context 上下文对象
     * @return 屏幕宽度值（单位：px）
     */
    public static int getScreenWidthPx(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context 上下文对象
     * @return 屏幕宽度值（单位：dp）
     */
    public static int getScreenWidthDp(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels / dm.density);
        return width;
    }

    /**
     * 获取屏幕的宽高比
     *
     * @param context 上下文对象
     * @return 屏幕宽高比（宽 * 高 eg:360 * 640）
     */
    public static String getScreenProperty(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels / dm.density);
        int height = (int) (dm.heightPixels / dm.density);
        return width + "*" + height;
    }

    /**
     * 是否使屏幕常亮
     *
     * @param activity 当前Activity
     * @param isOpenLight true:保持长亮; false:取消长亮
     */
    public static void keepScreenLongLight(Activity activity, boolean isOpenLight){
        if (isOpenLight) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
