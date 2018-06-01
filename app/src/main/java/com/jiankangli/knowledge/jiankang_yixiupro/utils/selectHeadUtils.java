package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;

import static com.jiankangli.knowledge.jiankang_yixiupro.utils.RegexUtil.hasSdcard;

/**
 * Created by 李浩 on 2018/6/1.
 * 因为每个项目都需要用到这个功能，特意封装一个工具类
 */

public class selectHeadUtils extends RxAppCompatActivity{

       public static final int PHONE_CAMERA=5;
       public static final int PHONE_OPEN_Gallery=6;
       public static final int CAMERA_PERMISSIONS_REQUEST_CODE = 3;
       public static final int STORAGE_PERMISSIONS_REQUEST_CODE = 4;

    //处理拍照
       private  void CreamPic(Context context){
           //存储拍照的图片
           File outputfile=new File(context.getExternalCacheDir(),"output.png");
           try {
               if (outputfile.exists()){
                   outputfile.delete();
               }
               outputfile.createNewFile();
           }catch (Exception e){
                e.printStackTrace();
           }
           Uri imageuri;
           if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
               imageuri = FileProvider.getUriForFile(context, "com.headphoto.filePhone", outputfile);
           }else{
               imageuri=Uri.fromFile(outputfile);
           }
           //启动相机
           Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
           intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
           startActivityForResult(intent,PHONE_CAMERA);
       }
      //处理相册
       private void openCallery(){
           Intent intent =new Intent(Intent.ACTION_GET_CONTENT);
           intent.setType("image*//**//*");
           startActivityForResult(intent,PHONE_OPEN_Gallery);
       }
      //剪切功能
    /**
     * @param activity    当前activity
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param requestCode 剪裁图片的请求码
     */
    public static void cropImageUri(Activity activity, Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat",Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }
      //返回给需要用到的页面
    //适配Android 6.0和7.0，动态权限申请
    private void autoObtainCameraPermissin(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                    ToastUtils.showToast(getApplicationContext(),"您已经拒绝过一次");
                }
                ActivityCompat.requestPermissions(this,new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},CAMERA_PERMISSIONS_REQUEST_CODE);

        }else{//权限已经有了，直接开始拍照
              if (hasSdcard()){
                    CreamPic(this);
              }else{
                  ToastUtils.showToast(getApplicationContext(),"设备没有SD卡");
              }

        }
    }
    //权限申请回调方法

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        CreamPic(this);
                    } else {
                        ToastUtils.showToast(this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showToast(this, "请允许打开相机！！");
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCallery();
                } else {
                    ToastUtils.showToast(this, "请允许打操作SDCard！！");
                }
                break;
        }
    }
    //@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PHONE_CAMERA://拍照完成回调
//                    cropImageUri = Uri.fromFile(fileCropUri);
//                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
//                    break;
//                case CODE_GALLERY_REQUEST://访问相册完成回调
//                    if (hasSdcard()) {
//                        cropImageUri = Uri.fromFile(fileCropUri);
//                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
//                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
//                    } else {
//                        ToastUtils.showShort(this, "设备没有SD卡！");
//                    }
//                    break;
//                case CODE_RESULT_REQUEST:
//                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
//                    if (bitmap != null) {
//                        showImages(bitmap);
//                    }
//                    break;
//            }
//        }
//    }
}
