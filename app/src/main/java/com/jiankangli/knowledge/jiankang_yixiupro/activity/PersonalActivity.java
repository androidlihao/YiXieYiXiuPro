package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.HeadPicUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.yalantis.ucrop.UCrop;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
    private HeadPicUtils headPicUtils;


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
        headPicUtils = new HeadPicUtils(this);
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
                    headPicUtils.takephoto();
                }else{
                    //申请拍照需要的权限
                    EasyPermissions.requestPermissions(this,"拍照需要摄像头权限",RC_CAMERA_CODE,perms);
                }
                photoPopWindow.dissmiss();
                break;
            case R.id.tv_gallery_id:
                String[] perm={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(this,perm)){
                    headPicUtils.seletPhoto();
                }else{
                    EasyPermissions.requestPermissions(this,"读取相册需要读取SD卡权限",RC_PHOTO_CODE,perm);
                }
                photoPopWindow.dissmiss();
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
        switch (requestCode){
            case RC_CAMERA_CODE:
                //执行拍照
                headPicUtils.takephoto();
                break;
            case RC_PHOTO_CODE:
                headPicUtils.seletPhoto();
                break;
        }
    }
    //权限获取失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode){
            case RC_CAMERA_CODE:
                ToastUtils.showToast(this,"拍照权限获取失败");
                break;
            case RC_PHOTO_CODE:
                ToastUtils.showToast(this,"相册读取权限获取失败");
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UCrop.RESULT_ERROR){
            ToastUtils.showToast(this,"图片剪裁失败");
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPEN_CAMERA_CODE://拍照
                    //开始裁剪功能
                    headPicUtils.cropRawPhoto(headPicUtils.getFileProviderUri());
                    break;
                case OPEN_PHOTOALBUM_CODE:
                    headPicUtils.cropRawPhoto(data.getData());
                    break;
                case UCrop.REQUEST_CROP:
                    //显示出来，并提交服务器
                    Uri Cropuri=UCrop.getOutput(data);
                    Log.i("TAG", "onActivityResult: "+Cropuri);
                    Bitmap bitmap=BitmapFactory.decodeFile(Cropuri.getPath());
                    profileImage.setImageBitmap(bitmap);
                    File file=new File(Cropuri.getPath());
                    //提交服务器
                    submithead(file);
                    break;
            }
        }
    }

    private void submithead(File file) {
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId", SharePreferenceUtils.get(this,"userId",-1+""));
            String jsonString= BaseJsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class)
                    .submitHead(jsonString,file)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<String>bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            Log.i("TAG", "onNext: "+s);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("TAG", "onError: "+e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
