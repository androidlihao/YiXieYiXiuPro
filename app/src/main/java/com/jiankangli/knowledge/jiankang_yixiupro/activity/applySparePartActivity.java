package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lihao
 * @date 2019-09-27 20:10
 * @description :
 */
public class applySparePartActivity extends BaseActivity {

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.tv_workNumber_id)
    TextView tvWorkNumberId;
    @BindView(R.id.et_orderNo_id)
    EditText etOrderNoId;
    @BindView(R.id.et_accessoryName_id)
    EditText etAccessoryNameId;
    @BindView(R.id.et_accessoryNo_id)
    EditText etAccessoryNoId;
    @BindView(R.id.et_serialNum_id)
    EditText etSerialNumId;
    @BindView(R.id.et_number_id)
    EditText etNumberId;
    @BindView(R.id.cb_foreignAid_id)
    CheckBox cbForeignAidId;
    @BindView(R.id.et_commodityLink_id)
    EditText etCommodityLinkId;
    @BindView(R.id.et_supplier_id)
    EditText etSupplierId;
    @BindView(R.id.tv_needTime_id)
    TextView tvNeedTimeId;
    @BindView(R.id.tv_arrivalTime_id)
    TextView tvArrivalTimeId;
    @BindView(R.id.et_unitPrice_id)
    EditText etUnitPriceId;
    @BindView(R.id.et_returnNumber_id)
    EditText etReturnNumberId;
    @BindView(R.id.et_accRemark_id)
    EditText etAccRemarkId;
    @BindView(R.id.et_remark_id)
    EditText etRemarkId;
    @BindView(R.id.tv_count_id)
    TextView tvCountId;
    @BindView(R.id.rc_photo_id)
    RecyclerView rcPhotoId;
    @BindView(R.id.btn_submit_id)
    Button btnSubmitId;
    private LocalePicAdapter localePicAdapter;
    private View mFootView;
    private TimePickerView pvTime;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_part_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //让布局向上移来显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "申请备件");
        initView();
        initDialog();
    }

    private void initView() {
        tvWorkNumberId.setText(SPUtil.getInstance(getApplicationContext()).getString("workNumber"));
        if (getIntent().getExtras()!=null) {
            String orderNo = getIntent().getExtras().getString("orderNo");
            etOrderNoId.setText(orderNo);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setAutoMeasureEnabled(true);
        rcPhotoId.setLayoutManager(gridLayoutManager);
        rcPhotoId.setHasFixedSize(true);
        rcPhotoId.setNestedScrollingEnabled(false);
        localePicAdapter = new LocalePicAdapter(R.layout.item_locale_pic);
        rcPhotoId.setAdapter(localePicAdapter);
        mFootView = LayoutInflater.from(this).inflate(R.layout.layout_pic_adapter_foot, null);
        localePicAdapter.addFooterView(mFootView);
        mFootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(applySparePartActivity.this); // where this is an Activity instance
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

    @OnClick({R.id.tv_needTime_id, R.id.tv_arrivalTime_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_needTime_id:
                pvTime.show(view);
                break;
            case R.id.tv_arrivalTime_id:
                pvTime.show(view);
                break;
        }
    }
    private void initDialog() {
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                ((TextView) v).setText(TimeUtil.getTimeFormatParse(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年","月","日","时","分","秒")
                .setType(new boolean[]{true, true, true, true, true, true})
                .isCyclic(true)
                .build();
    }
    public void selectphoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMedia(localePicAdapter.getData())
                .maxSelectNum(5)
                .forResult(PictureConfig.TYPE_IMAGE);

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

    @OnClick(R.id.btn_submit_id)
    public void onViewClicked() {
        //提交申请
        final String OrderNo = etOrderNoId.getText().toString();
        if (TextUtils.isEmpty(OrderNo)) {
            ToastUtil.showShortSafe("请输入申请单号",this);
            return;
        }
        final String accessoryName = etAccessoryNameId.getText().toString();
        if (TextUtils.isEmpty(accessoryName)) {
            ToastUtil.showShortSafe("请输入备件名称",this);
            return;
        }
        final String number = etNumberId.getText().toString();
        if (TextUtils.isEmpty(number)) {
            ToastUtil.showShortSafe("请输入数量",this);
            return;
        }
        String remark = etRemarkId.getText().toString();
        if (TextUtils.isEmpty(remark)) {
            ToastUtil.showShortSafe("请输入故障描述",this);
            return;
        }
        commonLoading.show();
        final List<LocalMedia> data = localePicAdapter.getData();
        final JsonArray jsonElements = new JsonArray();
        Observable.fromIterable(data)
                .map(new Function<LocalMedia, File>() {
                    @Override
                    public File apply(LocalMedia localMedia) throws Exception {
                        return new File(localMedia.getPath());
                    }
                }).flatMap(new Function<File, ObservableSource<BaseEntity<PicUrlBean>>>() {
            @Override
            public ObservableSource<BaseEntity<PicUrlBean>> apply(File file) throws Exception {
                RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("data",file.getName()+".png",requestBody);
                return  RetrofitManager.create(ApiService.class)
                        .uploadImage(body);
            }
        }).compose(RxSchedulers.<BaseEntity<PicUrlBean>>io2main())
                .as(AutoDispose.<BaseEntity<PicUrlBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<PicUrlBean>>() {
                    @Override
                    public void _onNext(BaseEntity<PicUrlBean> picUrlBeanBaseEntity) {
                        JsonObject jsonObject = new JsonObject();
                        if (picUrlBeanBaseEntity.isSuccess()){
                            jsonObject.addProperty("picUrl",picUrlBeanBaseEntity.data.getUrl());
                        }else {
                            jsonObject.addProperty("picUrl","");
                        }
                        jsonElements.add(jsonObject);
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("picUrl","");
                        jsonElements.add(jsonObject);
                    }

                    @Override
                    public void onComplete() {
                        if (jsonElements.size()==data.size()){
                            //图片上传完成，开始执行下一步
                            String accessoryNo=etAccessoryNoId.getText().toString();
                            String serialNum = etSerialNumId.getText().toString();
                            String foreignAid = cbForeignAidId.isChecked() ? "1" : "0";
                            String commodityLink = etCommodityLinkId.getText().toString();
                            String supplier = etSupplierId.getText().toString();
                            String needTime = tvNeedTimeId.getText().toString();
                            String arrivalTime = tvArrivalTimeId.getText().toString();
                            String unitPrice = etUnitPriceId.getText().toString();
                            String returnNumber = etReturnNumberId.getText().toString();
                            String accRemark = etAccRemarkId.getText().toString();
                            String applyName = (String) SPUtils.get(getApplicationContext(), "name","");
                            String operatorId=SPUtil.getInstance(getApplicationContext()).getString("userId");
                            String remark = etRemarkId.getText().toString();
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("orderNo",OrderNo);
                            jsonObject.addProperty("accessoryName",accessoryName);
                            jsonObject.addProperty("accessoryNo",accessoryNo);
                            jsonObject.addProperty("serialNum",serialNum);
                            jsonObject.addProperty("number",number);
                            jsonObject.addProperty("foreignAid",foreignAid);
                            jsonObject.addProperty("commodityLink",commodityLink);
                            jsonObject.addProperty("supplier",supplier);
                            jsonObject.addProperty("needTime",needTime);
                            jsonObject.addProperty("arrivalTime",arrivalTime);
                            jsonObject.addProperty("unitPrice",unitPrice);
                            jsonObject.addProperty("returnNumber",returnNumber);
                            jsonObject.addProperty("operatorId",operatorId);
                            jsonObject.addProperty("accRemark",accRemark);
                            jsonObject.addProperty("remark",remark);
                            jsonObject.addProperty("applyName",applyName);
                            jsonObject.add("accessoryPicVos", jsonElements);
                            addSparePart(jsonObject);
                        }
                        super.onComplete();
                    }
                });

    }

    private void addSparePart(JsonObject jsonObject) {
        try {
            RetrofitManager.create(ApiService.class)
                    .addSparePart(BaseJsonUtils.Base64String(new JSONObject(jsonObject.toString())))
                    .compose(RxSchedulers.<BaseEntity>io2main())
                    .as(AutoDispose.<BaseEntity>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new RxSubscriber<BaseEntity>() {
                        @Override
                        public void _onNext(BaseEntity baseEntity) {
                            ToastUtil.showShortSafe(baseEntity.msg,getApplicationContext());
                            if (baseEntity.isSuccess()){
                                try {
                                    Thread.sleep(2000);
                                    finish();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            ToastUtil.showShortSafe(msg,getApplicationContext());
                        }

                        @Override
                        public void onComplete() {
                            commonLoading.dismiss();
                            super.onComplete();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeDialog();
                break;
        }
        return true;
    }
    private void closeDialog() {
        DialogUtil.showPositiveDialog(this, "警告", "关闭后，您填写的内容将会丢失", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }
    @Override
    public void onBackPressedSupport() {
        closeDialog();
    }
}
