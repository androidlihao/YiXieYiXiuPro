package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * Created by 李浩 on 2018/6/8.
 * 适配7.0以上的头像更替功能
 * 剪切用到了UCrop,并且用到了EasyPermissions做动态权限申请
 *
 * 重点注意：path的路径需要和file_path中的path属性一致
 */

public class HeadPicUtils {
    //图片的存储路径
    final public static String path= Environment.getExternalStorageDirectory()+"/yixiuPro/photos/";
    final public static String authoritys="com.jiankangli.knowledge.jiankang_yixiupro.fileprovider";
    public static File file;
    Activity context;
    final int OPEN_CAMERA_CODE=112;
    final int OPEN_PHOTOALBUM_CODE=113;
    public HeadPicUtils(Activity context){
        this.context=context;
    }
    //拍照
    //Android 7.0以上的拍照
    public void takephoto(){
        //存储对象
        file = new File(path,System.currentTimeMillis()+".png");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imguri=getFileProviderUri();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){//7.0以上
            //临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
        context.startActivityForResult(intent,OPEN_CAMERA_CODE);
    }
    //相册
    public void seletPhoto() {
        if (hasSdcard()){
            Intent mOpenGalleryIntent=new Intent(Intent.ACTION_PICK);
            mOpenGalleryIntent.setType("image/*");
            context.startActivityForResult(mOpenGalleryIntent,OPEN_PHOTOALBUM_CODE);
        }else{
            ToastUtils.showToast(context,"设备没有SD卡");
        }
    }
    //7.0以上的地址获取Uri
    public Uri getFileProviderUri(){
        Uri uri;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            uri= FileProvider.getUriForFile(context,authoritys,file);
        }else{
            uri=Uri.fromFile(file);
        }
        return uri;
    }
    //剪切
    public void cropRawPhoto(Uri uri) {
        // 修改配置参数（我这里只是列出了部分配置，并不是全部）
        UCrop.Options options = new UCrop.Options();
        // 修改标题栏颜色
        options.setToolbarColor(context.getResources().getColor(R.color.colorPrimary));
        // 修改状态栏颜色
        options.setStatusBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
        // 隐藏底部工具
        options.setHideBottomControls(true);
        // 图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
        // 如果不开启，用户不能拖动选框，只能缩放图片
        options.setFreeStyleCropEnabled(true);

        Uri desUri=Uri.fromFile(new File(path,"CropHeadPicture"+".png"));//剪切后的图片存储地址
        Log.i("TAG", "desUri: "+desUri);
        Log.i("TAG", "uri: "+uri);
        // 设置源uri及目标uri
        UCrop.of(uri,desUri)
                // 长宽比
                .withAspectRatio(1, 1)
                // 图片大小
                .withMaxResultSize(200, 200)
                // 配置参数
                .withOptions(options)
                .start(context);
    }
    //验证是否有SD卡
    public static boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
}
