package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.ElectronOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ActivityUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;

import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.squareup.picasso.Picasso;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lihao
 * @date 2019-09-26 11:16
 * @description :提交工单
 */
public class saveOrderActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.cb_isNormal_id)
    CheckBox cbIsNormalId;
    @BindView(R.id.tv_select_date_id)
    TextView tvSelectDateId;
    @BindView(R.id.et_go_id)
    EditText etGoId;
    @BindView(R.id.et_back_id)
    EditText etBackId;
    @BindView(R.id.tv_signature_id)
    TextView tvSignatureId;
    @BindView(R.id.iv_signature_id)
    ImageView ivSignatureId;
    @BindView(R.id.btn_save_order_id)
    Button btnSaveOrderId;
    private TimePickerView pvTime;
    private ElectronOrderBean electronOrderBean;
    private String leaveTime;
    private String travelToTime;
    private String travelBackTime;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_save_order_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "提交工单");
        initDialog();
        initListener();
        ActivityUtils.getInstance().addActivity(this);
        String order = SPUtil.getInstance(this).getString("order");
        electronOrderBean = GsonUtils.jsonTobean(order, ElectronOrderBean.class);
        if (electronOrderBean.getServiceEnd1() == 1) {
            cbIsNormalId.setChecked(true);
        }
        tvSelectDateId.setText(electronOrderBean.getLeaveTime() == null ? "" : electronOrderBean.getLeaveTime());
        etGoId.setText(electronOrderBean.getTravelToTime() == 0 ? "" : electronOrderBean.getTravelToTime() + "");
        etBackId.setText(electronOrderBean.getTravelBackTime() == 0 ? "" : electronOrderBean.getTravelBackTime() + "");
        if (electronOrderBean.getOrderPicVos() != null) {
            for (ElectronOrderBean.OrderPicVosBean orderPicVo : electronOrderBean.getOrderPicVos()) {
                if (orderPicVo.getType() == 5) {
                    Picasso.get().load(orderPicVo.getPicUrl()).error(R.drawable.load_error).placeholder(R.drawable.loading)
                            .into(ivSignatureId);
                }
            }
        }
    }

    private void initDialog() {
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvSelectDateId.setText(TimeUtil.getTimeFormatParseMinute(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, false})
                .isCyclic(true)
                .build();
    }

    private void initListener() {
        tvSelectDateId.setOnClickListener(this);
        tvSignatureId.setOnClickListener(this);
        btnSaveOrderId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_select_date_id:
                pvTime.show();
                break;
            case R.id.tv_signature_id:
                //跳转到签字面板
                Intent intent = new Intent(this, signActivity.class);
                startActivityForResult(intent, 201);
                break;
            case R.id.btn_save_order_id:
                //开始上传代码
                saveOrder();
                break;
        }
    }

    private void saveOrder() {
        final List<ElectronOrderBean.OrderPicVosBean> orderPicVos = electronOrderBean.getOrderPicVos();
        boolean isSign = false;
        if (orderPicVos != null) {
            for (ElectronOrderBean.OrderPicVosBean orderPicVo : orderPicVos) {
                if (orderPicVo.getType() == 5) {
                    isSign = true;
                }
            }
        }
        if (!isSign) {
            ToastUtil.showShortSafe("请签字", this);
            return;
        }
        leaveTime = tvSelectDateId.getText().toString();
        if (TextUtils.isEmpty(leaveTime)) {
            ToastUtil.showShortSafe("请输入离开场地时间", this);
            return;
        }
        travelToTime = etGoId.getText().toString();
        if (TextUtils.isEmpty(travelToTime)) {
            ToastUtil.showShortSafe("请输入差旅时间", this);
            return;
        }
        travelBackTime = etBackId.getText().toString();
        if (TextUtils.isEmpty(travelBackTime)) {
            ToastUtil.showShortSafe("请输入差旅时间", this);
            return;
        }
        DialogUtil.showPositiveDialog(this, "提示", "是否提交工单数据", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                commonLoading.show();
                final JsonArray jsonElements = new JsonArray();
                if (orderPicVos == null) {
                    saveOrderInfo(jsonElements);
                    return;
                }
                Observable.fromIterable(orderPicVos)
                        .filter(new Predicate<ElectronOrderBean.OrderPicVosBean>() {
                            @Override
                            public boolean test(ElectronOrderBean.OrderPicVosBean orderPicVosBean) throws Exception {
                                if (orderPicVosBean.getPicUrl().startsWith("http://")) {
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("picUrl", orderPicVosBean.getPicUrl());
                                    jsonObject.addProperty("type", orderPicVosBean.getType());
                                    jsonElements.add(jsonObject);
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        })
                        .map(new Function<ElectronOrderBean.OrderPicVosBean, JsonArray>() {
                            @Override
                            public JsonArray apply(final ElectronOrderBean.OrderPicVosBean orderPicVosBean) throws Exception {
                                String picUrl = orderPicVosBean.getPicUrl();
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
                                                    jsonObject.addProperty("type", orderPicVosBean.getType());
                                                } else {
                                                    jsonObject.addProperty("picUrl", "");
                                                    jsonObject.addProperty("type", orderPicVosBean.getType());
                                                }
                                                jsonElements.add(jsonObject);
                                            }

                                            @Override
                                            public void _onError(Throwable e, String msg) {
                                                JsonObject jsonObject = new JsonObject();
                                                jsonObject.addProperty("picUrl", "");
                                                jsonObject.addProperty("type", orderPicVosBean.getType());
                                                jsonElements.add(jsonObject);
                                            }

                                        });
                                return jsonElements;
                            }
                        })
                        .compose(RxSchedulers.<JsonArray>io2main())
                        .as(AutoDispose.<JsonArray>autoDisposable(
                                AndroidLifecycleScopeProvider.from(saveOrderActivity.this, Lifecycle.Event.ON_DESTROY)))
                        .subscribe(new RxSubscriber<JsonArray>() {
                            @Override
                            public void _onNext(JsonArray jsonElements1) {

                            }

                            @Override
                            public void _onError(Throwable e, String msg) {

                            }

                            @Override
                            public void onComplete() {
                                if (jsonElements.size() == orderPicVos.size()) {
                                    //开始执行保存操作
                                    saveOrderInfo(jsonElements);
                                }
                                super.onComplete();
                            }
                        });
            }
        });
    }

    /**
     * 保存信息
     *
     * @param jsonElements1
     */
    private void saveOrderInfo(JsonArray jsonElements1) {
        //开始执行保存接口操作
        JsonObject jsonObject = new JsonObject();
        if (electronOrderBean.getId() != 0) {
            jsonObject.addProperty("id", electronOrderBean.getId());
        }
        String operatorId = SPUtil.getInstance(getApplicationContext()).getString("userId");
        jsonObject.addProperty("userId", operatorId);
        jsonObject.addProperty("workOrderId", electronOrderBean.getWorkOrderId());
        jsonObject.addProperty("serviceReasons", electronOrderBean.getServiceReasons());
        jsonObject.addProperty("serviceContent", electronOrderBean.getServiceContent());
        jsonObject.addProperty("serviceResults", electronOrderBean.getServiceResults());
        jsonObject.addProperty("serviceAdvice", electronOrderBean.getServiceAdvice());
        jsonObject.addProperty("serviceEnd1", cbIsNormalId.isChecked() ? "1" : "0");
        jsonObject.addProperty("leaveTime", leaveTime);
        jsonObject.addProperty("travelToTime", travelToTime);
        jsonObject.addProperty("travelBackTime", travelBackTime);
        jsonObject.addProperty("bulbBrand", electronOrderBean.getBulbBrand());
        jsonObject.addProperty("bulbModel", electronOrderBean.getBulbModel());
        jsonObject.addProperty("bulbHeatCapacity", electronOrderBean.getBulbHeatCapacity());
        jsonObject.addProperty("bulbInspectionAmount", electronOrderBean.getBulbInspectionAmount());
        jsonObject.addProperty("bulbNumberRecords", electronOrderBean.getBulbNumberRecords());
        jsonObject.add("orderPicVos", jsonElements1);
        try {
            RetrofitManager.create(ApiService.class)
                    .entryElectronOrder(BaseJsonUtils.Base64String(new JSONObject(jsonObject.toString())))
                    .compose(RxSchedulers.<BaseEntity>io2main())
                    .as(AutoDispose.<BaseEntity>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new RxSubscriber<BaseEntity>() {
                        @Override
                        public void _onNext(BaseEntity baseEntity) {
                            ToastUtil.showShortSafe(baseEntity.msg, getApplicationContext());
                            if (baseEntity.isSuccess()) {
                                Observable.just(baseEntity.msg)
                                        .delay(2000, TimeUnit.MILLISECONDS)
                                        .subscribe(new Consumer<String>() {
                                            @Override
                                            public void accept(String s) throws Exception {
                                                ActivityUtils.getInstance().finishAllActivity();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            ToastUtil.showShortSafe(msg, getApplicationContext());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode && requestCode == 201) {
            String sign = (String) data.getExtras().get("sign");
            Picasso.get().load(new File(sign)).into(ivSignatureId);
            //更新地址
            List<ElectronOrderBean.OrderPicVosBean> orderPicVos = electronOrderBean.getOrderPicVos() ==
                    null ? new ArrayList<ElectronOrderBean.OrderPicVosBean>() : electronOrderBean.getOrderPicVos();
            for (ElectronOrderBean.OrderPicVosBean orderPicVo : orderPicVos) {
                if (orderPicVo.getType() == 5) {
                    orderPicVos.remove(orderPicVo);
                    break;
                }
            }
            ElectronOrderBean.OrderPicVosBean orderPicVosBean = new ElectronOrderBean.OrderPicVosBean();
            orderPicVosBean.setType(5);
            orderPicVosBean.setPicUrl(sign);
            orderPicVos.add(orderPicVosBean);
            electronOrderBean.setOrderPicVos(orderPicVos);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
