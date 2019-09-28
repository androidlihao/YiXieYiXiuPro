package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.RecordAadpter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.fixRecordBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author lihao
 * @date 2019-09-28 15:37
 * @description :维修服务记录
 */
public class fixRecordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_save_id)
    TextView tvSaveId;
    @BindView(R.id.rl_head_id)
    RelativeLayout rlHeadId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.tv_workNumber_id)
    TextView tvWorkNumberId;
    @BindView(R.id.tv_orderNo_id)
    TextView tvOrderNoId;
    @BindView(R.id.tv_accessoryName_id)
    TextView tvAccessoryNameId;
    @BindView(R.id.tv_faultType_id)
    TextView tvFaultTypeId;
    @BindView(R.id.tv_fix_time)
    TextView tvFixTime;
    @BindView(R.id.tv_arrivalTime_id)
    TextView tvArrivalTimeId;
    @BindView(R.id.rc_ServiceRecode_id)
    RecyclerView rcServiceRecodeId;
    @BindView(R.id.btn_addServiceRecode_id)
    Button btnAddServiceRecodeId;
    @BindView(R.id.root)
    LinearLayout root;
    int position;
    private LinkedList<fixRecordBean.ServiceRecodeVosBean> serviceRecodeVosBeans;
    private RecordAadpter recordAadpter;
    private RepairOrder order;
    private TimePickerView rqTime;
    private TimePickerView sjTime;
    private TimePickerView arrivalTime;
    private int pos;


    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_fix_record_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        addMiddleTitle(this, "添加服务记录");
        ButterKnife.bind(this);
        initRecordList();
        initDialog();
        //获取维修服务记录
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化时间控件
     */
    private void initDialog() {
        rqTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                recordAadpter.getData().get(pos).setServiceTime(TimeUtil.getTimeFormatParse(date));
                ((TextView) v).setText(TimeUtil.getTimeFormat(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCyclic(true)
                .build();

        sjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String format = new SimpleDateFormat("HH:mm:ss").format(date);
                ((TextView) v).setText(format);
                switch (v.getId()) {
                    case R.id.tv_startTime_id:
                        recordAadpter.getData().get(pos).setStartTime(format);
                        break;
                    case R.id.tv_endTime_id:
                        recordAadpter.getData().get(pos).setEndTime(format);
                        break;
                }
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{false, false, false, true, true, true})
                .isCyclic(true)
                .build();
        arrivalTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                ((TextView) v).setText(TimeUtil.getTimeFormatParse(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, true})
                .isCyclic(true)
                .build();
    }

    @OnClick({R.id.tv_save_id, R.id.tv_arrivalTime_id, R.id.btn_addServiceRecode_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save_id:
                save();
                break;
            case R.id.tv_arrivalTime_id:
                arrivalTime.show(view);
                break;
            case R.id.btn_addServiceRecode_id:
                recordAadpter.addData(new fixRecordBean.ServiceRecodeVosBean());
                rcServiceRecodeId.scrollToPosition(recordAadpter.getData().size());
                break;
        }
    }

    private void save() {
        String arrivalTime = tvArrivalTimeId.getText().toString();
        if (TextUtils.isEmpty(arrivalTime)) {
            ToastUtil.showShortSafe("请输入到达场地时间", this);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", order.getId());
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
            jsonObject.put("arrivalTime", arrivalTime);
            JSONArray jsonArray = new JSONArray();
            List<fixRecordBean.ServiceRecodeVosBean> data = recordAadpter.getData();
            for (int i = 0; i < data.size(); i++) {
                fixRecordBean.ServiceRecodeVosBean serviceRecodeVosBean = data.get(i);
                String serviceTime = serviceRecodeVosBean.getServiceTime();
                String startTime = serviceRecodeVosBean.getStartTime();
                String endTime = serviceRecodeVosBean.getEndTime();
                if (TextUtils.isEmpty(serviceTime)) {
                    ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的服务日期", this);
                    return;
                }
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的开始时间", this);
                    return;
                }
                if (TextUtils.isEmpty(endTime)) {
                    ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的结束时间", this);
                    return;
                }
                Integer j = startTime.compareTo(endTime);
                if (j >= 0) {
                    ToastUtil.showShortSafe("第" + (i + 1) + "条记录的结束时间应该晚于开始时间", this);
                    return;
                }
                String roadTime = data.get(i).getRoadTime();
                if (TextUtils.isEmpty(roadTime)) {
                    ToastUtil.showShortSafe("请输入第" + (i + 1) + "条记录的路途时间", this);
                    return;
                }
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("serviceTime", serviceTime);
                jsonObject1.put("startTime", startTime);
                jsonObject1.put("endTime", endTime);
                jsonObject1.put("roadTime", roadTime);
                jsonObject1.put("equipmentStatus", data.get(i).getEquipmentStatus());
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("serviceRecodeVos", jsonArray);
            commonLoading.show();
            RetrofitManager.create(ApiService.class)
                    .addServiceRecode(BaseJsonUtils.Base64String(jsonObject))
                    .compose(RxSchedulers.<BaseEntity>io2main())
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
                                                finish();
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

    /**
     * 服务记录列表
     */
    private void initRecordList() {
        rcServiceRecodeId.setLayoutManager(new LinearLayoutManager(this));
        serviceRecodeVosBeans = new LinkedList<>();
        recordAadpter = new RecordAadpter(R.layout.fix_record_item, serviceRecodeVosBeans);
        rcServiceRecodeId.setAdapter(recordAadpter);
        rcServiceRecodeId.setNestedScrollingEnabled(false);
        recordAadpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                pos = position;
                switch (view.getId()) {
                    case R.id.tv_serviceTime_time:
                        rqTime.show(view);
                        break;
                    case R.id.tv_startTime_id:
                        sjTime.show(view);
                        break;
                    case R.id.tv_endTime_id:
                        sjTime.show(view);
                        break;
                    case R.id.btn_delete_id:
                        DialogUtil.showPositiveDialog(fixRecordActivity.this, "提示", "确认删除?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.remove(position);
                            }
                        });
                        break;
                }
            }
        });
    }

    private void getData() throws JSONException {
        order = (RepairOrder) getIntent().getSerializableExtra("order");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", order.getId());
        jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
        RetrofitManager.create(ApiService.class)
                .getServiceRecodeList(BaseJsonUtils.Base64String(jsonObject))
                .compose(RxSchedulers.<BaseEntity<fixRecordBean>>io2main())
                .subscribe(new RxSubscriber<BaseEntity<fixRecordBean>>() {
                    @Override
                    public void _onNext(BaseEntity<fixRecordBean> fixRecordBeanBaseEntity) {
                        if (fixRecordBeanBaseEntity.isSuccess()) {
                            tvWorkNumberId.setText(SPUtil.getInstance(getApplicationContext()).getString("workNumber"));
                            fixRecordBean fixRecordBean = fixRecordBeanBaseEntity.data;
                            tvOrderNoId.setText(TextUtils.isEmpty(fixRecordBean.getOrderNo()) ? "" : fixRecordBean.getOrderNo());
                            tvAccessoryNameId.setText(TextUtils.isEmpty(fixRecordBean.getAccessoryName()) ? "" : fixRecordBean.getAccessoryName());
                            switch (fixRecordBeanBaseEntity.data.getFaultType()) {
                                case 1:
                                    tvFaultTypeId.setText("软件");
                                    break;
                                case 2:
                                    tvFaultTypeId.setText("硬件");
                                    break;
                                case 3:
                                    tvFaultTypeId.setText("软件和硬件");
                                    break;
                            }
                            tvArrivalTimeId.setText(TextUtils.isEmpty(fixRecordBean.getArrivalTime()) ? "" : fixRecordBean.getArrivalTime());
                            List<fixRecordBean.ServiceRecodeVosBean> serviceRecodeVos = fixRecordBeanBaseEntity.data.getServiceRecodeVos();
                            if (serviceRecodeVos == null || serviceRecodeVos.size() == 0) {
                                serviceRecodeVos = new LinkedList<>();
                                serviceRecodeVos.add(new fixRecordBean.ServiceRecodeVosBean());
                            }
                            recordAadpter.setNewData(serviceRecodeVos);
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {

                    }
                });
    }
}
