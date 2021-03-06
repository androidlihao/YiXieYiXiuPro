package com.jiankangli.knowledge.jiankang_yixiupro.utils;

/**
 * @author lihao
 * @date 2019-03-18 12:01
 * @description :
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by tgvincent on 2018/6/25.
 * 国内手机厂商白名单跳转工具类
 *
 * @author tgvincent
 * @version 1.0
 */
public class UWhiteListSettingUtil {
    public static void enterWhiteListSetting(Context context) {
        try {
            context.startActivity(getSettingIntent());
        } catch (Exception e) {
            context.startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }

    private static Intent getSettingIntent() {
        ComponentName componentName = null;
        String brand = Build.BRAND;
        switch (brand.toLowerCase()) {
            case "samsung":
                componentName = new ComponentName("com.samsung.android.sm",
                        "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity");
                break;
            case "huawei":
            case "honor":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    componentName=new ComponentName("com.huawei.systemmanager",
                            "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
                }else if (Build.VERSION.SDK_INT>=23&& Build.VERSION.SDK_INT<26){
                    componentName = new ComponentName("com.huawei.systemmanager",
                            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                }else{
                    componentName =new ComponentName("com.huawei.systemmanager",
                            "com.huawei.systemmanager.com.huawei.permissionmanager.ui.MainActivity");
                }

                break;
            case "xiaomi":
                componentName = new ComponentName("com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity");
                break;
            case "vivo":
                componentName = new ComponentName("com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity");
                break;
            case "oppo":
                componentName = new ComponentName("com.coloros.oppoguardelf",
                        "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity");
                break;
            case "360":
                componentName = new ComponentName("com.yulong.android.coolsafe",
                        "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity");
                break;
            case "meizu":
                componentName = new ComponentName("com.meizu.safe",
                        "com.meizu.safe.permission.SmartBGActivity");
                break;
            case "oneplus":
                componentName = new ComponentName("com.oneplus.security",
                        "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");
                break;
            default:
                break;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (componentName != null) {
            intent.setComponent(componentName);
        } else {
            intent.setAction(Settings.ACTION_SETTINGS);
        }
        return intent;
    }
}
