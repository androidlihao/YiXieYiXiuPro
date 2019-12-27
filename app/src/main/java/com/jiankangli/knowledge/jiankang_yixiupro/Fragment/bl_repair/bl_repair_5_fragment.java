package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair;

import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Listeners.TextListener;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.repairBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.serviceConfirmPageEchoActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.signActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.ElectronOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ActivityUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtils;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :录入维修工单-备件使用记录
 */
public class bl_repair_5_fragment extends BaseFragment {


    @BindView(R.id.cb_isNormal_id)
    CheckBox cbIsNormalId;
    @BindView(R.id.cb_isNeed_id)
    CheckBox cbIsNeedId;
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
    Unbinder unbinder;
    private com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean blBean;
    private TimePickerView pvTime;
    private String leaveTime;
    private String travelToTime;
    private String travelBackTime;
    private List<com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean.ElectronOrderVosBean.OrderPicVosBean> orderPicVos;

    public static bl_repair_5_fragment newInstance() {
        bl_repair_5_fragment fragment = new bl_repair_5_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    public void onSupportVisible() {
        ((repairBackTrackingActivity) getActivity()).changeTitle("录入维修工单");
        //隐藏

        super.onSupportVisible();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_repair_5_fragment_layout;
    }

    @Override
    protected void initView() {
        blBean = ((repairBackTrackingActivity) getActivity()).blBean;
        initDialog();
        initListener();
        cbIsNormalId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //标识设备是否正常工作
                if (isChecked) {
                    blBean.getElectronOrderVos().setServiceEnd1(1);
                } else {
                    blBean.getElectronOrderVos().setServiceEnd1(0);
                }

            }
        });
        cbIsNeedId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //标识设备是否正常工作
                if (isChecked) {
                    blBean.setWhetherExamine(1);
                } else {
                    blBean.setWhetherExamine(0);
                }

            }
        });
        etGoId.addTextChangedListener(new TextListener(etGoId.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                if (TextUtils.isEmpty(s)) {
                    blBean.getElectronOrderVos().setTravelToTime(0);
                } else {
                    blBean.getElectronOrderVos().setTravelToTime(Double.parseDouble(s.toString()));
                }
            }
        });
        etBackId.addTextChangedListener(new TextListener(etBackId.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                if (TextUtils.isEmpty(s)) {
                    blBean.getElectronOrderVos().setTravelBackTime(0);
                } else {
                    blBean.getElectronOrderVos().setTravelBackTime(Double.parseDouble(s.toString()));
                }
            }
        });
    }

    private void initDialog() {
        //时间选择器
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvSelectDateId.setText(TimeUtil.getTimeFormatParseMinute(date));
                blBean.setLeaveTime(tvSelectDateId.getText().toString());
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, false})
                .isCyclic(true)
                .build();
    }


    @Override
    protected void initData() {
        if (blBean.getElectronOrderVos() == null) {
            blBean.setElectronOrderVos(new blBean.ElectronOrderVosBean());
        }
        int i = blBean.getElectronOrderVos().getServiceEnd1();
        if (i == 1) {
            cbIsNormalId.setChecked(true);
        } else {
            cbIsNormalId.setChecked(false);
        }
        int whetherExamine = blBean.getWhetherExamine();
        if (whetherExamine == 1) {
            cbIsNeedId.setChecked(true);
        } else {
            cbIsNeedId.setChecked(false);
        }
        //离开场地时间
        String leaveTime = blBean.getLeaveTime();
        if (!TextUtils.isEmpty(leaveTime)) {
            tvSelectDateId.setText(leaveTime);
        }
        String travelToTime = blBean.getElectronOrderVos().getTravelToTime() + "";
        if (!TextUtils.isEmpty(travelToTime)&&Double.parseDouble(travelToTime)!=0) {
            etGoId.setText(travelToTime);
        }
        String traveBackTime = blBean.getElectronOrderVos().getTravelBackTime() + "";
        if (!TextUtils.isEmpty(traveBackTime)&&Double.parseDouble(traveBackTime)!=0) {
            etBackId.setText(traveBackTime);
        }
        if (blBean.getElectronOrderVos().getOrderPicVos() != null) {
            for (blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVo : blBean.getElectronOrderVos().getOrderPicVos()) {
                if (orderPicVo.getType() == 5) {
                    Picasso.get().load(orderPicVo.getPicUrl()).error(R.drawable.load_error).placeholder(R.drawable.loading)
                            .into(ivSignatureId);
                }
            }
        }
    }


    @Override
    protected void initListener() {

    }


    @OnClick({R.id.tv_select_date_id, R.id.tv_signature_id, R.id.btn_save_order_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_date_id:
                pvTime.show();
                break;
            case R.id.tv_signature_id:
                //跳转到签字面板
                Intent intent = new Intent(getActivity(), signActivity.class);
                startActivityForResult(intent, 201);
                break;
            case R.id.btn_save_order_id:
                //开始上传代码
                saveOrder();
                break;
        }
    }

    private void saveOrder() {
        orderPicVos = blBean.getElectronOrderVos().getOrderPicVos();
        boolean isSign = false;
        if (orderPicVos != null) {
            for (blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVo : orderPicVos) {
                if (orderPicVo.getType() == 5) {
                    isSign = true;
                }
            }
        }
        if (!isSign) {
            ToastUtil.showShortSafe("请签字", getContext());
            return;
        }
        leaveTime = tvSelectDateId.getText().toString();
        if (TextUtils.isEmpty(leaveTime)) {
            ToastUtil.showShortSafe("请输入离开场地时间", getContext());
            return;
        }
        travelToTime = etGoId.getText().toString();
        if (TextUtils.isEmpty(travelToTime)) {
            ToastUtil.showShortSafe("请输入差旅时间", getContext());
            return;
        }
        travelBackTime = etBackId.getText().toString();
        if (TextUtils.isEmpty(travelBackTime)) {
            ToastUtil.showShortSafe("请输入差旅时间", getContext());
            return;
        }
        DialogUtil.showPositiveDialog(getContext(), "提示", "是否提交工单数据", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((repairBackTrackingActivity) getActivity()).commonLoading.show();
                final JsonArray jsonElements = new JsonArray();
                if (orderPicVos == null) {
                    saveOrderInfo(jsonElements);
                    return;
                }
                Observable.fromIterable(orderPicVos)
                        .filter(new Predicate<blBean.ElectronOrderVosBean.OrderPicVosBean>() {
                            @Override
                            public boolean test(blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVosBean) throws Exception {
                                if (orderPicVosBean.getPicUrl().startsWith("http://")) {
                                    JsonObject jsonObject = new JsonObject();
                                    jsonElements.add(jsonObject);
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        })
                        .map(new Function<blBean.ElectronOrderVosBean.OrderPicVosBean, JsonArray>() {
                            @Override
                            public JsonArray apply(final blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVosBean) throws Exception {
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
                                                    orderPicVosBean.setPicUrl(picUrlBeanBaseEntity.data.getUrl());
                                                    jsonElements.add(jsonObject);
                                                } else {
                                                    orderPicVosBean.setPicUrl("");
                                                    jsonElements.add(jsonObject);
                                                }
                                            }

                                            @Override
                                            public void _onError(Throwable e, String msg) {
                                                orderPicVosBean.setPicUrl("");
                                                JsonObject jsonObject = new JsonObject();
                                                jsonElements.add(jsonObject);
                                            }

                                        });
                                return jsonElements;
                            }
                        })
                        .compose(RxSchedulers.<JsonArray>io2main())
                        .as(AutoDispose.<JsonArray>autoDisposable(
                                AndroidLifecycleScopeProvider.from(getActivity(), Lifecycle.Event.ON_DESTROY)))
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
     * @param
     * @param jsonElements
     */
    private void saveOrderInfo(JsonArray jsonElements) {
        String s = GsonUtils.beanTojson(blBean);
        try {
            String string = BaseJsonUtils.Base64String(new JSONObject(s));
            RetrofitManager.create(ApiService.class)
                    .recodeWorkOrder(string)
                    .compose(RxSchedulers.<BaseEntity>io2main())
                    .as(AutoDispose.<BaseEntity>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new RxSubscriber<BaseEntity>() {
                        @Override
                        public void _onNext(BaseEntity baseEntity) {
                            ((repairBackTrackingActivity) getActivity()).commonLoading.dismiss();
                            ToastUtil.showShortSafe(baseEntity.msg,getContext());
                            if (baseEntity.isSuccess()){
                                //跳转到服务确认界面
                                String string1 = baseEntity.data.toString();
                                try {
                                    int id = new JSONObject(string1).getInt("id");
                                    RepairOrder repairOrder=new RepairOrder();
                                    repairOrder.setId(id);
                                    repairOrder.setOrderStatus(4);
                                    Intent intent1 = new Intent(getActivity(), serviceConfirmPageEchoActivity.class);
                                    intent1.putExtra("order", repairOrder);
                                    startActivity(intent1);
                                    getActivity().finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            ((repairBackTrackingActivity) getActivity()).commonLoading.dismiss();
                            ToastUtil.showShortSafe(msg,getContext());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode && requestCode == 201) {
            String sign = (String) data.getExtras().get("sign");
            Picasso.get().load(new File(sign)).into(ivSignatureId);
            //更新地址
            blBean.ElectronOrderVosBean electronOrderBean = blBean.getElectronOrderVos();
            List<blBean.ElectronOrderVosBean.OrderPicVosBean> orderPicVos = electronOrderBean.getOrderPicVos() ==
                    null ? new ArrayList<blBean.ElectronOrderVosBean.OrderPicVosBean>() : electronOrderBean.getOrderPicVos();
            for (blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVo : orderPicVos) {
                if (orderPicVo.getType() == 5) {
                    orderPicVos.remove(orderPicVo);
                    break;
                }
            }
            blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVosBean = new blBean.ElectronOrderVosBean.OrderPicVosBean();
            orderPicVosBean.setType(5);
            orderPicVosBean.setPicUrl(sign);
            orderPicVos.add(orderPicVosBean);
            electronOrderBean.setOrderPicVos(orderPicVos);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
