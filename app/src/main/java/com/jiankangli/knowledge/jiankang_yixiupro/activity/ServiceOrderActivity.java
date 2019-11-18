package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.LocalePicAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;

import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.ElectronOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ActivityUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author lihao
 * @date 2019-09-19 21:55
 * @description :服务原因
 */
public class ServiceOrderActivity extends BaseActivity implements View.OnClickListener, TextWatcher {


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
    private int type;
    private RepairOrder order;
    private ElectronOrderBean electronOrderBean;

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
        order = (RepairOrder) getIntent().getSerializableExtra("order");
        type = getIntent().getIntExtra("type", 2);
        String title = null;
        switch (type) {
            case 2:
                title = "服务原因";
                break;
            case 3:
                title = "服务内容";
                break;
            case 4:
                title = "服务结果";
                break;
            case 5:
                title = "服务建议";
                rcPhotoId.setVisibility(View.GONE);
                break;
        }
        addMiddleTitle(this, title);
        etId.setHint("请输入" + title + "，不超过2000字");
    }

    private void initListener() {
        etId.addTextChangedListener(this);
        tvNextId.setOnClickListener(this);
    }

    /**
     * 获取回显的工单信息
     */
    private void getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("workOrderId", order.getId());
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        RetrofitManager.create(ApiService.class)
                .showElectronOrder(js)
                .compose(RxSchedulers.<BaseEntity<ElectronOrderBean>>io2main())
                .subscribe(new RxSubscriber<BaseEntity<ElectronOrderBean>>() {
                    @Override
                    public void _onNext(BaseEntity<ElectronOrderBean> electronOrderBeanBaseEntity) {
                        //如果有回显消息
                        if (electronOrderBeanBaseEntity.isSuccess()) {
                            electronOrderBean = electronOrderBeanBaseEntity.data;
                            etId.setText(electronOrderBean.getServiceReasons());
                        } else {
                            electronOrderBean = new ElectronOrderBean();
                            electronOrderBean.setWorkOrderId(order.getId());
                            ToastUtil.showShortSafe(electronOrderBeanBaseEntity.msg, getApplicationContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg, getApplicationContext());
                    }

                    @Override
                    public void onComplete() {
                        initPhoto();
                        super.onComplete();
                    }
                });
    }

    private void initData() {
        //当界面可见的时候
        if (type != 2) {
            String order = SPUtil.getInstance(this).getString("order");
            electronOrderBean = GsonUtils.jsonTobean(order, ElectronOrderBean.class);

        } else {
            getData();
        }
        switch (type) {
            case 3:
                etId.setText(electronOrderBean.getServiceContent());
                initPhoto();
                break;
            case 4:
                etId.setText(electronOrderBean.getServiceResults());
                initPhoto();
                break;
            case 5:
                etId.setText(electronOrderBean.getServiceAdvice());
                initPhoto();
                break;
        }


    }

    private void initPhoto() {
        List<LocalMedia> localMedias = new LinkedList<>();
        if (electronOrderBean.getOrderPicVos()==null){
            return;
        }
        for (ElectronOrderBean.OrderPicVosBean orderPicVo : electronOrderBean.getOrderPicVos()) {
            //填充
            if (orderPicVo.getType()==type){
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(orderPicVo.getPicUrl());
                localMedias.add(localMedia);
            }
        }
        localePicAdapter.setNewData(localMedias);
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
                RxPermissions rxPermissions = new RxPermissions(ServiceOrderActivity.this); // where this is an Activity instance
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
                    setPic(localePicAdapter.getData());
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
                //设置服务原因
                switch (type) {
                    case 2:
                        electronOrderBean.setServiceReasons(etId.getText().toString());
                        break;
                    case 3:
                        electronOrderBean.setServiceContent(etId.getText().toString());
                        break;
                    case 4:
                        electronOrderBean.setServiceResults(etId.getText().toString());
                        break;
                    case 5:
                        electronOrderBean.setServiceAdvice(etId.getText().toString());
                        break;
                }
                String s = GsonUtils.beanTojson(electronOrderBean);
                SPUtil.getInstance(this).putString("order", s);
                //下一步
                if (type != 5) {
                    Intent intent = new Intent(this, ServiceOrderActivity.class);
                    intent.putExtra("type", type + 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, saveOrderActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //返回的图片集合
        if (requestCode == PictureConfig.TYPE_IMAGE && resultCode == RESULT_OK) {
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            localePicAdapter.setNewData(localMedia);
            setPic(localMedia);
            if (localMedia.size() == 5) {
                localePicAdapter.removeFooterView(mFootView);
            }
        } else if (resultCode == RESULT_CANCELED) {

        }

    }

    /**
     * 设置需要保存的图片
     *
     * @param localMedia
     */
    private void setPic(List<LocalMedia> localMedia) {
        List<ElectronOrderBean.OrderPicVosBean> orderPicVos = electronOrderBean.getOrderPicVos() == null ? new ArrayList<ElectronOrderBean.OrderPicVosBean>() : electronOrderBean.getOrderPicVos();
        //清除当前的所有的图片，然后重新添加
        for (int i = 0; i < orderPicVos.size(); i++) {
            ElectronOrderBean.OrderPicVosBean orderPicVosBean = orderPicVos.get(i);
            if (orderPicVosBean.getType() == type) {
                orderPicVos.remove(orderPicVosBean);
                i--;
            }
        }
        for (LocalMedia media : localMedia) {
            ElectronOrderBean.OrderPicVosBean orderPicVosBean = new ElectronOrderBean.OrderPicVosBean();
            orderPicVosBean.setPicUrl(media.getPath());
            orderPicVosBean.setType(type);
            orderPicVos.add(orderPicVosBean);
        }
        electronOrderBean.setOrderPicVos(orderPicVos);
    }

    @Override
    protected void onDestroy() {
        //退出编辑，清除
        if (type == 2) {
            SPUtil.getInstance(this).remove("order");
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (type == 2) {
            DialogUtil.showPositiveDialog(this, "警告", "关闭后，您填写的内容将会丢失", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        } else {
            super.onBackPressedSupport();
        }
    }
}
