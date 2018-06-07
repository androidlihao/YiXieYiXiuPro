package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;



public class PersonalActivity extends BaseActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_personName_id)
    TextView tvPersonNameId;
    @BindView(R.id.tv_numberPhone)
    TextView tvNumberPhone;
    @BindView(R.id.RV_id)
    RecyclerView RVId;
    @BindView(R.id.tv_Exitlogin_id)
    TextView tvExitloginId;
    private CustomPopWindow photoPopWindow;

    final int RC_CAMERA_CODE=998;//相机权限申请
    final int RC_PHOTO_CODE=997;//相册权限申请
    final int OPEN_CAMERA_CODE=112;
    final int OPEN_PHOTOALBUM_CODE=113;
    private Uri uri;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "个人中心");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View view=View.inflate(this,R.layout.takephoto,null);
        photoPopWindow = new CustomPopWindow.PopupWindowBuilder(PersonalActivity.this)
                .setView(view)
                .setFocusable(true)
                .setOutsideTouchable(false)
                .setAnimationStyle(R.animator.scale_with_alpha)
                .create();
        view.findViewById(R.id.tv_cancel_id).setOnClickListener(this);
        view.findViewById(R.id.tv_gallery_id).setOnClickListener(this);
        view.findViewById(R.id.tv_takephoto_id).setOnClickListener(this);

    }

    @OnClick({R.id.profile_image, R.id.tv_Exitlogin_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                //弹出弹窗,选择状态
                photoPopWindow.showAtLocation(profileImage, Gravity.BOTTOM, 0, 100);
                break;
            case R.id.tv_Exitlogin_id:

                //清除sp的值
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel_id:
                photoPopWindow.dissmiss();
                break;
            case R.id.tv_takephoto_id:
                String[] perms={Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(this,perms)){
                      //直接开始拍照、
                    takephoto();
                }else{
                    //申请拍照需要的权限
                    EasyPermissions.requestPermissions(this,"拍照需要摄像头权限",RC_CAMERA_CODE,perms);
                }
                break;
            case R.id.tv_gallery_id:
                String[] perm={Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(this,perm)){

                }else{
                    EasyPermissions.requestPermissions(this,"读取相册需要读取SD卡权限",RC_PHOTO_CODE,perm);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    //权限获取成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i("TAG", "onPermissionsGranted: "+"获取权限成功");
        switch (requestCode){
            case RC_CAMERA_CODE:
                //执行拍照
                takephoto();
                break;
            case RC_PHOTO_CODE:

                break;
        }
    }
    //权限获取失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode){
            case RC_CAMERA_CODE:
                ToastUtils.showToast(getApplicationContext(),"拍照权限获取失败");
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UCrop.RESULT_ERROR){
            ToastUtils.showToast(this,"图片剪裁失败");
            Log.i("TAG", "errors: "+UCrop.getError(data));
            return;
        }
        Log.i("TAG", "onActivityResult: "+requestCode+resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPEN_CAMERA_CODE://拍照
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                         //开启裁剪功能
                          uri=FileProvider.getUriForFile(this,
                                 "com.jiankangli.knowledge.jiankang_yixiupro.fileprovider",file);
                         Log.i("TAG", "onActivityResult: "+uri);
                    } else {
                         //开启裁剪功能
                          uri=Uri.fromFile(file);
                    }
                    //开始裁剪功能
                    cropRawPhoto(uri);
                    break;
                case OPEN_PHOTOALBUM_CODE:
                    Log.i("TAG", "onActivityResult: " + data.getData());

                    break;
                case UCrop.REQUEST_CROP:
                    Log.i("TAG", "REQUEST_CROP: " + UCrop.getOutput(data));
                    //mCameraTv.setText(UCrop.getOutput(data) + "");
//                    Glide.with(this)
//                            .load(UCrop.getOutput(data))
//                            .crossFade()
//                            .into(mCameraImg);
                    break;
            }
        }
    }
    //图片的存储路径
    private String path= Environment.getExternalStorageDirectory()+"/yixiuPro/photos/";
    private String authoritys="com.jiankangli.knowledge.jiankang_yixiupro.fileprovider";
    //Android 7.0以上的拍照
    private void takephoto(){
        //存储对象
        file = new File(path,System.currentTimeMillis()+".png");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imguri;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){//7.0以上
            //获取对应的存储文件URI
             imguri= FileProvider.getUriForFile(this,
                    authoritys,file);
            //临时授权
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }else{
            imguri=Uri.fromFile(file);//得到file文件路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imguri);
        }
        startActivityForResult(intent,OPEN_CAMERA_CODE);
    }
    public void cropRawPhoto(Uri uri) {

        // 修改配置参数（我这里只是列出了部分配置，并不是全部）
        UCrop.Options options = new UCrop.Options();
        // 修改标题栏颜色
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        // 修改状态栏颜色
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        // 隐藏底部工具
        options.setHideBottomControls(true);
        // 图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
        // 如果不开启，用户不能拖动选框，只能缩放图片
        options.setFreeStyleCropEnabled(true);
        Uri desUri;
        File desfile=new File(path,System.currentTimeMillis()+".png");
        if (!desfile.getParentFile().exists()){
            desfile.getParentFile().mkdirs();
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){//
            desUri=FileProvider.getUriForFile(this,authoritys,desfile);
        }else{
            desUri=Uri.fromFile(desfile);
        }
        Log.i("TAG", "cropRawPhoto: "+desUri);
        Log.i("TAG", "Uri: "+uri);
        // 设置源uri及目标uri
        UCrop.of(uri,desUri)
                // 长宽比
                .withAspectRatio(1, 1)
                // 图片大小
                .withMaxResultSize(200, 200)
                // 配置参数
                .withOptions(options)
                .start(this);
    }

}
