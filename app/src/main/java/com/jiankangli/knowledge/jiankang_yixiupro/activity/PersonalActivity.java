package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

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
    final int RC_CAMERA_CODE=998;
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
                String[] perms={Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(this,perms)){
                      //直接开始拍照、

                }else{
                    //申请拍照需要的权限
                    EasyPermissions.requestPermissions(this,"拍照需要摄像头权限",RC_CAMERA_CODE,perms);
                }
                break;
            case R.id.tv_gallery_id:

                break;
        }
    }
    //权限获取成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode){
            case RC_CAMERA_CODE:
                //执行拍照
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
}
