package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.LocalePicAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ActivityUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lihao
 * @date 2019-09-19 21:55
 * @description :生成维修工单
 */
public class CreateWxOrderActivity extends BaseActivity implements View.OnClickListener, TextWatcher {


    @BindView(R.id.tv_next_id)
    TextView tvNextId;
    @BindView(R.id.rl_head_id)
    RelativeLayout rlHeadId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.tv_count_id)
    TextView tvCountId;
    @BindView(R.id.rc_photo_id)
    RecyclerView rcPhotoId;
    private LocalePicAdapter localePicAdapter;
    private View mFootView;
    private int orderId;

    @Override
    protected int getLayoutId() {
        return R.layout.input_order_1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeUi();
        initView();
        initData();
        initListener();
        ActivityUtils.getInstance().addActivity(this);
    }

    private void changeUi() {
        orderId = getIntent().getIntExtra("OrderId", -1);
        addMiddleTitle(this, "生成维修工单");
        etId.setHint("请输入故障描述,不超过2000字");
    }

    private void initListener() {
        etId.addTextChangedListener(this);
        tvNextId.setOnClickListener(this);
        tvNextId.setText("提交");
    }


    private void initData() {

    }


    private void initView() {
        rcPhotoId.setLayoutManager(new GridLayoutManager(this, 4));
        localePicAdapter = new LocalePicAdapter(R.layout.item_locale_pic);
        rcPhotoId.setAdapter(localePicAdapter);
        mFootView = LayoutInflater.from(this).inflate(R.layout.layout_pic_adapter_foot, null);
        localePicAdapter.addFooterView(mFootView);
        mFootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(CreateWxOrderActivity.this); // where this is an Activity instance
                rxPermissions.requestEach(
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                try {
                                    if (permission.granted) {
                                        //打开
                                        selectphoto();
                                    } else if (permission.shouldShowRequestPermissionRationale) {

                                    } else {
                                        ToastUtil.showShortSafe("请进入设置界面打开读取本地文件权限", getApplicationContext());
                                    }
                                } catch (Exception e) {
                                    Log.i("TAG", "ISPermission: " + e);
                                }
                            }
                        });
            }
        });
        localePicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_close) {
                    localePicAdapter.remove(position);
                    if (localePicAdapter.getFooterLayoutCount() == 0) {
                        localePicAdapter.addFooterView(mFootView);
                    }
                }
            }
        });
    }

    public void selectphoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMedia(localePicAdapter.getData())
                .maxSelectNum(5)
                .forResult(PictureConfig.TYPE_IMAGE);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tvCountId.setText(s.length() + "/2000");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next_id:
                //先上传图片
                if (TextUtils.isEmpty(etId.getText().toString().trim())) {
                    ToastUtil.showShortSafe("请输入故障描述",getApplicationContext());
                    return;
                }
                commonLoading.show();
                if (localePicAdapter.getData().size() != 0) {
                    uploadPic();
                } else {
                    createOrder(new JsonArray());
                }
                break;
        }
    }

    /**
     * 开始生成工单信息
     *
     * @param jsonElements
     */
    private void createOrder(JsonArray jsonElements) {
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("id",orderId);
            jsonObject.add("orderPicVos", jsonElements);
            jsonObject.addProperty("remark",etId.getText().toString().trim());
            jsonObject.addProperty("userId", SPUtils.get(this, "userId", -1 + "")+"");
            String string = BaseJsonUtils.Base64String(new JSONObject(jsonObject.toString()));
            RetrofitManager.create(ApiService.class)
                    .orderConversion(string)
                    .compose(RxSchedulers.<BaseEntity>io2main())
                    .as(AutoDispose.<BaseEntity>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new RxSubscriber<BaseEntity>() {
                        @Override
                        public void _onNext(BaseEntity baseEntity) {
                            if (baseEntity.isSuccess()){
                                ToastUtil.showShortSafe("维修工单成功生成!",getApplicationContext());
                                finish();
                            }else {
                                ToastUtil.showShortSafe(baseEntity.msg,getApplicationContext());
                            }
                            commonLoading.dismiss();
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            ToastUtil.showShortSafe(msg,getApplicationContext());
                            commonLoading.dismiss();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传签名图片，然后再提交数据到服务器
     */
    private void uploadPic() {
        final JsonArray jsonElements = new JsonArray();
        Observable.fromIterable(localePicAdapter.getData())
                .map(new Function<LocalMedia, JsonArray>() {
                    @Override
                    public JsonArray apply(LocalMedia localMedia) throws Exception {
                        String picUrl = localMedia.getPath();
                        File file = new File(picUrl);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("data", file.getName() + ".png", requestBody);
                        RetrofitManager.create(ApiService.class)
                                .uploadImage(body)
                                .subscribe(new RxSubscriber<BaseEntity<PicUrlBean>>() {
                                    @Override
                                    public void _onNext(BaseEntity<PicUrlBean> picUrlBeanBaseEntity) {
                                        JsonObject jsonObject = new JsonObject();
                                        if (picUrlBeanBaseEntity.isSuccess()) {
                                            jsonObject.addProperty("picUrl", picUrlBeanBaseEntity.data.getUrl());
                                        } else {
                                            jsonObject.addProperty("picUrl", "");
                                        }
                                        jsonElements.add(jsonObject);
                                    }

                                    @Override
                                    public void _onError(Throwable e, String msg) {
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty("picUrl", "");
                                        jsonElements.add(jsonObject);
                                    }

                                });
                        return jsonElements;
                    }
                })
                .compose(RxSchedulers.<JsonArray>io2main())
                .as(AutoDispose.<JsonArray>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<JsonArray>() {
                    @Override
                    public void _onNext(JsonArray jsonElements1) {

                    }

                    @Override
                    public void _onError(Throwable e, String msg) {

                    }

                    @Override
                    public void onComplete() {
                        if (jsonElements.size() == localePicAdapter.getData().size()) {
                            //开始执行保存操作
                            createOrder(jsonElements);
                        }
                        super.onComplete();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //返回的图片集合
        if (requestCode == PictureConfig.TYPE_IMAGE && resultCode == RESULT_OK) {
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            localePicAdapter.setNewData(localMedia);
            if (localMedia.size() == 5) {
                localePicAdapter.removeFooterView(mFootView);
            }
        } else if (resultCode == RESULT_CANCELED) {

        }

    }


    @Override
    public void onBackPressedSupport() {
        DialogUtil.showPositiveDialog(this, "警告", "关闭后，您填写的内容将会丢失", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

}
