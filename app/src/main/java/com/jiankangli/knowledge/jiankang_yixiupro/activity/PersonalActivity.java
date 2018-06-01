package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.AutoGetPermission;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.PhotoUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jiankangli.knowledge.jiankang_yixiupro.utils.AutoGetPermission.STORAGE_PERMISSIONS_REQUEST_CODE;
import static com.jiankangli.knowledge.jiankang_yixiupro.utils.AutoGetPermission.CAMERA_PERMISSIONS_REQUEST_CODE;

public class PersonalActivity extends BaseActivity {

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

    private static final int CODE_RESULT_REQUEST = 8;
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri cropImageUri;
    //创建文件的路径名
    public File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    public static final int CODE_GALLERY_REQUEST = 6;
    public static final int CODE_CAMERA_REQUEST = 7;
    public static Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "个人中心");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @OnClick({R.id.profile_image, R.id.tv_Exitlogin_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                AutoGetPermission.autoObtainStoragePermission(PersonalActivity.this);//获取相机权限并打开相机
                //弹出弹窗
                break;
            case R.id.tv_Exitlogin_id:
                //清除sp的值
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        //创建原图的存储路径
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            imageUri = FileProvider.getUriForFile(this, "com.headphoto.filePhone", fileUri);//通过FileProvider创建一个content类型的Uri
                        }else{
                            imageUri = Uri.fromFile(fileUri);
                        }
                        PhotoUtils.takePicture(this,imageUri,CODE_CAMERA_REQUEST);//开始执行拍照过程
                    } else {
                        ToastUtils.showToast(this, "设备没有SD卡！");
                    }
                } else {
                    ToastUtils.showToast(this, "请允许打开相机！！");
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this,CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.showToast(this, "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG", "onActivityResult: "+resultCode);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    Log.i("TAG", "onActivityResult: ");
                    cropImageUri = Uri.fromFile(fileCropUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        imageUri = FileProvider.getUriForFile(this, "com.headphoto.filePhone", fileUri);//通过FileProvider创建一个content类型的Uri
                    }else{
                        imageUri = Uri.fromFile(fileUri);
                    }
                    Log.i("TAG", "onActivityResult: "+imageUri);
                    PhotoUtils.cropImageUri(PersonalActivity.this, imageUri, cropImageUri,
                            1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    Log.i("TAG", "GALLERY: ");
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this,"com.zz.fileprovider",new File(data.getData().getPath()));
                        }else{
                            newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        }
                        Log.i("TAG", "onActivityResult: "+newUri);
                       // PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.showToast(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Log.i("TAG", "onActivityResult: "+cropImageUri);
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);//最后的结果
                    }
                    break;
                default:
            }
        }
    }

    private void showImages(Bitmap bitmap) {
        // photo.setImageBitmap(bitmap);
        profileImage.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
