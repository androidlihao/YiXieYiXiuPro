package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.OrderDetailsAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.displayBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.inspectionBaseInfoBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.maintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ActivityUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.AssetUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DicUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.MapBeanUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 锦绣前程 on 2018/9/17.
 */
//保养工单详情页面
public class PollingOrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.rc_details_id)
    RecyclerView rcDetailsId;
    @BindView(R.id.tv_entering_id)
    TextView tvEnteringId;
    @BindView(R.id.rl_head_id)
    RelativeLayout rlHeadId;
    @BindView(R.id.fl_content_id)
    FrameLayout flContentId;
    private PollingOrder order;
    private OrderDetailsAdapter orderDetailsAdapter;
    private com.jiankangli.knowledge.jiankang_yixiupro.bean.displayBean displayBean;
    private CheckBox cbSoftwareId;
    private CheckBox cbHardwareId;
    private Button btnSureId;

    private Button btnStartRepairId;
    private Button btnRecordForServeId;
    private Button btnEnterTheRepairOrderId;
    private String orderNo;
    private Button btnWorkOrderDetailsId;
    private String deviceModel;
//    private String templateCode;
    private Button btnCreateOrderId;

    @Override
    protected int getLayoutId() {
        return R.layout.upkeep_order_details_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = (PollingOrder) getIntent().getSerializableExtra("order");
        initView();
        changeUI();
        initData();
        ActivityUtils.getInstance().addActivity(this);
    }

    private void changeUI() {
        flContentId.removeAllViews();
        switch (order.getListStatus()) {
            case 2:
                addMiddleTitle(this, "等待巡检");
                View inflate = LayoutInflater.from(this).inflate(R.layout.start_repair_layout, null);
                btnStartRepairId = inflate.findViewById(R.id.btn_start_repair_id);
                btnStartRepairId.setText("开始巡检");
                flContentId.addView(inflate);
                tvEnteringId.setVisibility(View.GONE);
                break;
            case 3:
                addMiddleTitle(this, "正在巡检");
                View view = LayoutInflater.from(this).inflate(R.layout.repairint_layout, null);
                btnRecordForServeId = (Button) view.findViewById(R.id.btn_record_for_serve_id);
                btnRecordForServeId.setText("巡检记录");
                btnEnterTheRepairOrderId = (Button) view.findViewById(R.id.btn_Enter_the_repair_order_id);
                btnEnterTheRepairOrderId.setText("填写报告");
                flContentId.addView(view);
                tvEnteringId.setText("申请备件");
                break;
            case 4:
                addMiddleTitle(this, "服务确认");
                break;
            case 5:
                addMiddleTitle(this, "正在审核");
                View view1 = LayoutInflater.from(this).inflate(R.layout.verification_work_orders_layout, null);
                btnWorkOrderDetailsId = (Button) view1.findViewById(R.id.btn_workOrder_details_id);
                btnCreateOrderId = view1.findViewById(R.id.btn_create_order_id);
                flContentId.addView(view1);
                tvEnteringId.setVisibility(View.GONE);
                break;
            case 6:
                addMiddleTitle(this, "审核失败");
                View view2 = LayoutInflater.from(this).inflate(R.layout.repairint_layout, null);
                btnRecordForServeId = (Button) view2.findViewById(R.id.btn_record_for_serve_id);
                btnEnterTheRepairOrderId = (Button) view2.findViewById(R.id.btn_Enter_the_repair_order_id);
                btnEnterTheRepairOrderId.setText("修改报告");
                btnRecordForServeId.setText("修改巡检记录");
                flContentId.addView(view2);
                tvEnteringId.setText("失败原因");
                break;
            case 7:
                addMiddleTitle(this, "巡检完成");
                View view3 = LayoutInflater.from(this).inflate(R.layout.verification_work_orders_layout, null);
                btnWorkOrderDetailsId = (Button) view3.findViewById(R.id.btn_workOrder_details_id);
                btnCreateOrderId = view3.findViewById(R.id.btn_create_order_id);
                flContentId.addView(view3);
                tvEnteringId.setVisibility(View.GONE);
                tvEnteringId.setVisibility(View.GONE);
                break;

        }
        initListtener();
    }

    private void initListtener() {
        if (btnStartRepairId != null) {
            btnStartRepairId.setOnClickListener(this);
        }
        if (btnEnterTheRepairOrderId != null) {
            btnEnterTheRepairOrderId.setOnClickListener(this);
        }
        if (btnRecordForServeId != null) {
            btnRecordForServeId.setOnClickListener(this);
        }
        if (tvEnteringId != null) {
            tvEnteringId.setOnClickListener(this);
        }
        if (btnWorkOrderDetailsId != null) {
            btnWorkOrderDetailsId.setOnClickListener(this);
        }
        if (btnCreateOrderId!=null) {
            btnCreateOrderId.setOnClickListener(this);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        orderDetailsAdapter = new OrderDetailsAdapter(R.layout.item_details);
        rcDetailsId.setLayoutManager(new LinearLayoutManager(this));
        rcDetailsId.setAdapter(orderDetailsAdapter);
    }

    /**
     * 获取数据
     */
    private void initData() {
        InputStream is = AssetUtil.getIsFromAssets("displayConfig.json", this);
        JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
        displayBean = new Gson().fromJson(jsonReader, displayBean.class);
        RetrofitManager.create(ApiService.class)
                .getinspectionOrderInfo(getJson())
                .compose(RxSchedulers.<BaseEntity<inspectionBaseInfoBean>>io2main())
                .subscribe(new RxSubscriber<BaseEntity<inspectionBaseInfoBean>>() {
                    @Override
                    public void _onNext(BaseEntity<inspectionBaseInfoBean> baseEntity) {
                        if (baseEntity.isSuccess()) {
                            orderNo = baseEntity.data.getOrderNo();
                            deviceModel = baseEntity.data.getDeviceModel();
//                            templateCode = baseEntity.data.getTemplateCode();
                            Map<String, Object> map = MapBeanUtil.object2Map(baseEntity.data);
                            List<HashMap<String, String>> list = new LinkedList<>();
                            for (String key : displayBean.getUpKeepdisplayBean()) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put(DicUtil.getKeyOrValue(key, getApplicationContext()), map.get(key) == null ? "" : map.get(key).toString());
                                list.add(hashMap);
                            }
                            orderDetailsAdapter.setNewData(list);
                        } else {
                            ToastUtil.showShortSafe(baseEntity.msg, getApplicationContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg, getApplicationContext());
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
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
            jsonObject.put("workOrderId", order.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_order_id:
                //开始生成维修工单
                Intent intent5=new Intent(this,CreateWxOrderActivity.class);
                intent5.putExtra("OrderId",order.getId());
                startActivity(intent5);
                break;
            case R.id.btn_start_repair_id:
                //开始巡检操作
                startRepair();
                break;
            case R.id.btn_record_for_serve_id:
                //服务记录
                Intent intents = new Intent(this, upKeepRecordActivity.class);
                intents.putExtra("orderId", order.getId());
                startActivity(intents);
                break;
            case R.id.btn_Enter_the_repair_order_id:
                //填写报告
                Intent intent = new Intent(this, enterPollingReportActivity.class);
                //如果为审核失败的话，那么修改完报告，再次审核
                if (order.getListStatus()==6) {
                    intent.putExtra("isFrist",false);
                }else {
                    //如果为填写报告，直接进入服务确认环节
                    intent.putExtra("isFrist",true);
                }
                intent.putExtra("deviceModel",deviceModel);
//                intent.putExtra("templateCode",templateCode);
                intent.putExtra("order", order);
                startActivity(intent);
                break;
            case R.id.tv_entering_id:
                //申请配件
                if (tvEnteringId.getText().equals("申请备件")) {
                    Intent intent1 = new Intent(this, applySparePartActivity.class);
                    intent1.putExtra("orderNo", orderNo);
                    startActivity(intent1);
                } else if (tvEnteringId.getText().equals("失败原因")) {
                    Intent intent1 = new Intent(this, checkErrorActivity.class);
                    intent1.putExtra("id", order.getId() + "");
                    startActivity(intent1);
                }
                break;
            case R.id.btn_workOrder_details_id:
                //工单详情
                Intent intent1 = new Intent(this, OrderPdfActivity.class);
                //3为巡检
                intent1.putExtra("pdfType", 3);
                intent1.putExtra("workOrderId", order.getId());
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    /**
     * 开始巡检
     */
    private void startRepair() {
        RetrofitManager.create(ApiService.class)
                .startInspection(getJson())
                .compose(RxSchedulers.<BaseEntity>io2main())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity baseEntity) {
                        ToastUtil.showShortSafe(baseEntity.msg, getApplicationContext());
                        if (baseEntity.isSuccess()) {
                            //开始维修成功，修改当前状态为正在维修
                            order.setListStatus(3);
                            changeUI();
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg, getApplicationContext());
                    }
                });
    }
}
