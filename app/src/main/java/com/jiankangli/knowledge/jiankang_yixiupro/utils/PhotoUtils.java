package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by 李浩 on 2018/6/6.
 */

public class PhotoUtils {
    // 全局变量
    public static final int RESULT_CODE_1 = 201;
    // 7.0 以上的uri
    private Uri mProviderUri;
    // 7.0 以下的uri
    private Uri mUri;
    // 图片路径
    private String mFilepath = Environment.getExternalStorageState()+"photos";

    /**
     * 拍照
     * */
    private void camera(Context context){
        File file=new File(mFilepath,System.currentTimeMillis()+"jpg");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Android 7.0以上的URI
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            //通过FileProVider创建一个content类型的uri
            FileProvider.getUriForFile(context,"com.yixie.pro",
                    file);

        }
    }
}
