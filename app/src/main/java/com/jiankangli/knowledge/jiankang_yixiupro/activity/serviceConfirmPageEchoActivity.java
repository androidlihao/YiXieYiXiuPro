package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.OdrerDetailsBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.serviceConfirmBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.workEvaluationBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.RegexUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author lihao
 * @date 2019-09-29 21:35
 * @description :维修服务确认
 * */
public class serviceConfirmPageEchoActivity extends BaseActivity {
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.tv_workOrder_details_id)
    TextView tvWorkOrderDetailsId;
    @BindView(R.id.et_kh_phone_id)
    EditText etKhPhoneId;
    @BindView(R.id.tv_kh_sendMsg_id)
    TextView tvKhSendMsgId;
    @BindView(R.id.et_xs_phone_id)
    EditText etXsPhoneId;
    @BindView(R.id.tv_xs_sendMsg_id)
    TextView tvXsSendMsgId;
    @BindView(R.id.btn_workOrder_Evaluation_id)
    Button btnWorkOrderEvaluationId;
    @BindView(R.id.tv_workOrder_Evaluation_id)
    TextView tvWorkOrderEvaluationId;
    @BindView(R.id.btn_submit_id)
    Button btnSubmitId;
    @BindView(R.id.tv_queryCode_id)
    TextView tvQueryCodeId;
    private RepairOrder order;

    @Override
    protected int getLayoutId() {
        return R.layout.service_confirm_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "服务确认");
        order = (RepairOrder) getIntent().getSerializableExtra("order");
        tvWorkOrderDetailsId.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        getData();
    }


    @OnClick({R.id.tv_kh_sendMsg_id, R.id.tv_xs_sendMsg_id, R.id.btn_workOrder_Evaluation_id, R.id.btn_submit_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_kh_sendMsg_id:
                String phone = etKhPhoneId.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showShortSafe("请输入客户电话", this);
                    return;
                }
                if (!RegexUtil.isMobileNumber(phone)) {
                    ToastUtil.showShortSafe("手机号码不符合规则", this);
                    return;
                }
                sendMessage(phone,1,view);
                break;
            case R.id.tv_xs_sendMsg_id:
                String phone1 = etXsPhoneId.getText().toString();
                if (TextUtils.isEmpty(phone1)) {
                    ToastUtil.showShortSafe("请输入销售电话", this);
                    return;
                }
                if (!RegexUtil.isMobileNumber(phone1)) {
                    ToastUtil.showShortSafe("手机号码不符合规则", this);
                    return;
                }
                sendMessage(phone1,2,view);
                break;
            case R.id.btn_workOrder_Evaluation_id:
                //工单评价查询
                queryWorkEvaluation();
                break;
            case R.id.btn_submit_id:
                serviceConfirm();
                break;
        }
    }

    /**
     * 服务确认提交审核
     */
    private void serviceConfirm() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtil.getInstance(getApplicationContext()).getString("userId"));
            jsonObject.put("temporaryNum", etKhPhoneId.getText().toString());
            jsonObject.put("queryCode", tvQueryCodeId.getText().toString());
            jsonObject.put("id", order.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        commonLoading.show();
        RetrofitManager.create(ApiService.class)
                .serviceConfirm(js)
                .compose(RxSchedulers.<BaseEntity<List<OdrerDetailsBean>>>io2main())
                .as(AutoDispose.<BaseEntity<List<OdrerDetailsBean>>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<List<OdrerDetailsBean>>>() {
                    @Override
                    public void _onNext(BaseEntity<List<OdrerDetailsBean>> odrerDetailsBeanBaseEntity) {
                        if (odrerDetailsBeanBaseEntity.isSuccess()) {
                            ToastUtil.showShortSafe("提交成功", getApplicationContext());
                            order.setListStatus(5);
                            Observable.just("")
                                    .delay(2000, TimeUnit.MILLISECONDS)
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String s) throws Exception {
                                            Intent intent = new Intent(serviceConfirmPageEchoActivity.this, OrderDetailsActivity.class);
                                            intent.putExtra("order", order);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                        } else {
                            ToastUtil.showShortSafe(odrerDetailsBeanBaseEntity.msg, getApplicationContext());
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
    }

    /**
     * 工单评价查询
     */
    private void queryWorkEvaluation() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",SPUtil.getInstance(getApplicationContext()).getString("userId"));
            jsonObject.put("workOrderId", order.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        commonLoading.show();
        RetrofitManager.create(ApiService.class)
                .queryWorkEvaluation(js)
                .compose(RxSchedulers.<BaseEntity<workEvaluationBean>>io2main())
                .as(AutoDispose.<BaseEntity<workEvaluationBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<workEvaluationBean>>() {
                    @Override
                    public void _onNext(BaseEntity<workEvaluationBean> workEvaluationBeanBaseEntity) {
                        if (workEvaluationBeanBaseEntity.isSuccess()) {
                            if (workEvaluationBeanBaseEntity.data.isWorkEvaluation()) {
                                tvWorkOrderEvaluationId.setText("已评价");
                            } else {
                                tvWorkOrderEvaluationId.setText("未评价");
                            }
                            if (workEvaluationBeanBaseEntity.data.isEvaluationButten()) {
                                btnSubmitId.setEnabled(true);
                            } else {
                                btnSubmitId.setEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {

                    }

                    @Override
                    public void onComplete() {
                        commonLoading.dismiss();
                        super.onComplete();
                    }
                });

    }

    /**
     * 发送手机号码
     *
     * @param phone
     * @param view
     */
    private void sendMessage(String phone, int status, final View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",SPUtil.getInstance(getApplicationContext()).getString("userId"));
            jsonObject.put("temporaryNum", phone);
            jsonObject.put("queryCode", tvQueryCodeId.getText().toString());
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        RetrofitManager.create(ApiService.class)
                .sendMessage(js)
                .compose(RxSchedulers.<BaseEntity>io2main())
                .as(AutoDispose.<BaseEntity>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity baseEntity) {
                        ToastUtil.showShortSafe(baseEntity.msg, getApplicationContext());
                        //开始倒计时，而且按钮不可以点击
                        countDown(view);
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg, getApplicationContext());
                    }
                });
    }

    private void countDown(final View view) {
        final int count = 120;
        Observable.interval(0,1,TimeUnit.SECONDS)
                .take(count)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ((TextView) view).setTextColor(Color.parseColor("#BDBBBB"));
                        view.setEnabled(false);
                    }
                })
                .as(AutoDispose.<Long>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        ((TextView) view).setText(aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        view.setEnabled(true);
                        ((TextView) view).setTextColor(getResources().getColor(R.color.black));
                        ((TextView) view).setText("发送短信");
                    }
                });
    }

    /**
     * 获取服务确认信息
     */
    private void getData() {
        RetrofitManager.create(ApiService.class)
                .serviceConfirmPageEcho(getJson())
                .compose(RxSchedulers.<BaseEntity<serviceConfirmBean>>io2main())
                .as(AutoDispose.<BaseEntity<serviceConfirmBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<serviceConfirmBean>>() {
                    @Override
                    public void _onNext(BaseEntity<serviceConfirmBean> serviceConfirmBeanBaseEntity) {
                        if (serviceConfirmBeanBaseEntity.isSuccess()) {
                            serviceConfirmBean data = serviceConfirmBeanBaseEntity.data;
                            if (data != null) {
                                etKhPhoneId.setText(data.getTemporaryNum());
                                etXsPhoneId.setText(data.getServicerPhone());
                                tvQueryCodeId.setText(tvQueryCodeId.getText().toString() + data.getQueryCode());
                            }
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        Log.i("TAG", "_onError: ");
                    }
                });
    }

    /**
     * 获取工单详情Json数据
     *
     * @return
     */
    private String getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",SPUtil.getInstance(getApplicationContext()).getString("userId"));
            jsonObject.put("id", order.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }

    @OnClick(R.id.tv_workOrder_details_id)
    public void onViewClicked() {
        Intent intent1=new Intent(this,OrderPdfActivity.class);
        intent1.putExtra("pdfType",1);
        intent1.putExtra("workOrderId",order.getId());
        startActivity(intent1);
    }
}
