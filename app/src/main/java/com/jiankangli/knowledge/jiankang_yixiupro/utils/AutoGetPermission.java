package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import java.io.File;

import static com.jiankangli.knowledge.jiankang_yixiupro.activity.PersonalActivity.CODE_CAMERA_REQUEST;
import static com.jiankangli.knowledge.jiankang_yixiupro.activity.PersonalActivity.CODE_GALLERY_REQUEST;
import static com.jiankangli.knowledge.jiankang_yixiupro.utils.RegexUtil.hasSdcard;

/**
 * Created by 李浩 on 2018/6/1.
 */
//自动获取权限类
public class AutoGetPermission {
    /**
     * 自动获取相机权限
     */
    public static final int CAMERA_PERMISSIONS_REQUEST_CODE = 3;//相机权限申请码
    public static final int STORAGE_PERMISSIONS_REQUEST_CODE = 4;//SD卡读取权限申请码
    public static void autoObtainCameraPermission(Activity context) {
        File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //权限没有获取,申请获取相机权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {
            //有权限直接调用系统相机拍照
            if (hasSdcard()) {
                Uri imageUri;
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(context,"com.headphoto.filePhone",fileUri);
                }else{
                    imageUri = Uri.fromFile(fileUri);
                }
                PhotoUtils.takePicture(context,imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showToast(context, "设备没有SD卡！");
            }
        }
    }
    /**
     * 自动获取sd卡权限
     */

    public static void autoObtainStoragePermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(context,CODE_GALLERY_REQUEST);
        }

    }
}
