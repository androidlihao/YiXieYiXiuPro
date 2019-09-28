package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenxin
 * @date: 2019-03-03 14:54
 * Description:
 */
public class PermissionUtil {

    public static final int GOTO_SETTING_CODE = 152;

    /**
     * 判断是否有权限
     *
     * @param context
     * @param perms
     * @return
     */
    public static boolean hasPermissions(@NonNull Context context, @Size(min = 1) @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 申请权限
     */
    public static void requestPermissions(@NonNull Activity activity, int requestCode, String[] permissions) {

        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        //将List转为数组
        String[] permissionsArray = permissionList.toArray(new String[permissionList.size()]);
        if (!permissionList.isEmpty()) {
            //不可能为空
            ActivityCompat.requestPermissions(activity, permissionsArray, requestCode);
            //返回结果onRequestPermissionsResult
        }
    }


    /**
     * 申请权限的回调
     *
     * @param requestCode  请求权限时传入的请求码，用于区别是哪一次请求的
     * @param permissions  所请求的所有权限的数组
     * @param grantResults 权限授予结果，和 permissions 数组参数中的权限一一对应，元素值为两种情况，如下:
     *                     授予: PackageManager.PERMISSION_GRANTED
     *                     拒绝: PackageManager.PERMISSION_DENIED
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults, @NonNull PermissionCallbacks callBack) {
        //授予的权限。
        List<String> granted = new ArrayList<>();

        //拒绝的权限
        List<String> denied = new ArrayList<>();


        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        if (denied.isEmpty()) {
            callBack.onPermissionsAllGranted(requestCode, granted);
        } else {
            callBack.onPermissionsDenied(requestCode, granted, denied);
        }


    }

    /**
     * 用户是否拒绝权限，并检查“不要提醒”。
     *
     * @param activity activity
     * @param perms    检查权限
     * @return 返回用户是否拒绝权限的结果
     */
    public static boolean somePermissionPermanentlyDenied(Activity activity, @NonNull List<String> perms) {
        for (String deniedPermission : perms) {
            if (permissionPermanentlyDenied(activity, deniedPermission)) {
                return true;
            }
        }

        return false;
    }

    public static boolean permissionPermanentlyDenied(Activity activity, @NonNull String perms) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, perms);
    }


    public static void showDialogGoToAppSettting(final Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage("去设置界面开启权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting(activity);
                    }
                }).setCancelable(false).show();
    }


    /**
     * 跳转到应用设置界面
     */
    public static void goToAppSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, GOTO_SETTING_CODE);
    }

    public static void showPermissionReason(final int requestCode, final Activity activity, final String[] permission, String s) {
        new AlertDialog.Builder(activity)
                .setMessage(s)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(activity, requestCode, permission);
                    }
                })
                .setCancelable(false).show();
    }


    public interface PermissionCallbacks {

        /**
         * 是否获取所有的请求权限
         *
         * @param requestCode 请求码
         * @param perms       返回所有允许的权限
         */
        void onPermissionsAllGranted(int requestCode, List<String> perms);

        /**
         * 请求权限返回
         *
         * @param requestCode  请求码
         * @param grantedPerms 通过的权限
         * @param deniedPerms  拒绝的权限
         */
        void onPermissionsDenied(int requestCode, List<String> grantedPerms, List<String> deniedPerms);

    }

}
