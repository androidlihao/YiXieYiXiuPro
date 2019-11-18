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
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.OrderDetailsAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_imagleAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.OdrerDetailsBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.displayBean;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 锦绣前程 on 2018/9/17.
 */

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.rc_details_id)
    RecyclerView rcDetailsId;
    @BindView(R.id.rc_img_id)
    RecyclerView rcImgId;
    @BindView(R.id.tv_fault_des_id)
    TextView tvFaultDesId;
    @BindView(R.id.tv_entering_id)
    TextView tvEnteringId;
    @BindView(R.id.rl_head_id)
    RelativeLayout rlHeadId;
    @BindView(R.id.fl_content_id)
    FrameLayout flContentId;
    private RepairOrder order;
    private OrderDetailsAdapter orderDetailsAdapter;
    private com.jiankangli.knowledge.jiankang_yixiupro.bean.displayBean displayBean;
    private Recycler_imagleAdapter recycler_imagleAdapter;
    private CheckBox cbSoftwareId;
    private CheckBox cbHardwareId;
    private Button btnSureId;
    private Dialog dialog;
    private Button btnStartRepairId;
    private Button btnRecordForServeId;
    private Button btnEnterTheRepairOrderId;
    private String orderNo;
    private Button btnWorkOrderDetailsId;

    @Override
    protected int getLayoutId() {
        return R.layout.order_details_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = (RepairOrder) getIntent().getSerializableExtra("order");
        initView();
        changeUI();
        initData();
        initListtener();
        ActivityUtils.getInstance().addActivity(this);
    }

    private void changeUI() {
        flContentId.removeAllViews();
        switch (order.getListStatus()) {
            case 2:
                addMiddleTitle(this, "等待维修");
                View inflate = LayoutInflater.from(this).inflate(R.layout.start_repair_layout, null);
                btnStartRepairId = inflate.findViewById(R.id.btn_start_repair_id);
                flContentId.addView(inflate);
                tvEnteringId.setVisibility(View.GONE);
                break;
            case 3:
                addMiddleTitle(this, "正在维修");
                View view = LayoutInflater.from(this).inflate(R.layout.repairint_layout, null);
                btnRecordForServeId = (Button) view.findViewById(R.id.btn_record_for_serve_id);
                btnEnterTheRepairOrderId = (Button) view.findViewById(R.id.btn_Enter_the_repair_order_id);
                flContentId.addView(view);
                tvEnteringId.setText("申请备件");
                break;
            case 4:
                addMiddleTitle(this, "服务确认");
                break;
            case 5:
                addMiddleTitle(this, "正在审核");
                View view1 = LayoutInflater.from(this).inflate(R.layout.details_of_work_orders_layout, null);
                btnWorkOrderDetailsId = (Button) view1.findViewById(R.id.btn_workOrder_details_id);
                flContentId.addView(view1);
                tvEnteringId.setVisibility(View.GONE);
                break;
            case 6:
                addMiddleTitle(this, "审核失败");
                View view2 = LayoutInflater.from(this).inflate(R.layout.repairint_layout, null);
                btnRecordForServeId = (Button) view2.findViewById(R.id.btn_record_for_serve_id);
                btnEnterTheRepairOrderId = (Button) view2.findViewById(R.id.btn_Enter_the_repair_order_id);
                btnEnterTheRepairOrderId.setText("修改工单");
                flContentId.addView(view2);
                tvEnteringId.setText("失败原因");
                break;
            case 7:
                addMiddleTitle(this, "维修完成");
                View view3 = LayoutInflater.from(this).inflate(R.layout.details_of_work_orders_layout, null);
                btnWorkOrderDetailsId = (Button) view3.findViewById(R.id.btn_workOrder_details_id);
                flContentId.addView(view3);
                tvEnteringId.setVisibility(View.GONE);
                tvEnteringId.setVisibility(View.GONE);
                break;

        }
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
    }

    /**
     * 初始化view
     */
    private void initView() {
        orderDetailsAdapter = new OrderDetailsAdapter(R.layout.item_details);
        rcDetailsId.setLayoutManager(new LinearLayoutManager(this));
        rcDetailsId.setAdapter(orderDetailsAdapter);
        recycler_imagleAdapter = new Recycler_imagleAdapter(R.layout.imagle_item);
        //图片
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcImgId.setLayoutManager(layoutManager);
        rcImgId.setAdapter(recycler_imagleAdapter);

    }

    /**
     * 获取数据
     */
    private void initData() {
        InputStream is = AssetUtil.getIsFromAssets("displayConfig.json", this);
        JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
        displayBean = new Gson().fromJson(jsonReader, displayBean.class);
        RetrofitManager.create(ApiService.class)
                .getRepairWorkOrderBaseInfo(getJson())
                .compose(RxSchedulers.<BaseEntity<OdrerDetailsBean>>io2main())
                .subscribe(new RxSubscriber<BaseEntity<OdrerDetailsBean>>() {
                    @Override
                    public void _onNext(BaseEntity<OdrerDetailsBean> baseEntity) {
                        if (baseEntity.isSuccess()) {
                            orderNo = baseEntity.data.getOrderNo();
                            tvFaultDesId.setText(baseEntity.data.getRemark());
                            Map<String, Object> map = MapBeanUtil.object2Map(baseEntity.data);
                            List<HashMap<String, String>> list = new LinkedList<>();
                            for (String key : displayBean.getDisplayBean()) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put(DicUtil.getKeyOrValue(key, getApplicationContext()), map.get(key) == null ? "" : map.get(key).toString());
                                list.add(hashMap);
                            }
                            List<OdrerDetailsBean.OrderPicVosBean> orderPicVos = baseEntity.data.getOrderPicVos();
                            if (orderPicVos == null) {
                                orderPicVos = new ArrayList<>();
                            }
                            recycler_imagleAdapter.setNewData(orderPicVos);
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
            jsonObject.put("id", order.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_repair_id:
                ShowDialog();
                break;
            case R.id.btn_sure_id:
                if (!cbHardwareId.isChecked() && !cbSoftwareId.isChecked()) {
                    ToastUtil.showShortSafe("请选择故障类型(可多选)", this);
                    return;
                }
                startRepair();
                break;
            case R.id.btn_record_for_serve_id:
                //服务记录
                Intent intents = new Intent(this, fixRecordActivity.class);
                intents.putExtra("order", order);
                startActivity(intents);
                break;
            case R.id.btn_Enter_the_repair_order_id:
                //录入工单
                Intent intent = new Intent(this, ServiceOrderActivity.class);
                intent.putExtra("type", 2);
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
                Intent intent1=new Intent(this,OrderPdfActivity.class);
                intent1.putExtra("pdfType",1);
                intent1.putExtra("workOrderId",order.getId());
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    /**
     * 开始维修dialog
     */
    private void ShowDialog() {
        dialog = new Dialog(this, R.style.CustomDialogStyle);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_faulttype_layout, null);
        cbSoftwareId = (CheckBox) inflate.findViewById(R.id.cb_software_id);
        cbHardwareId = (CheckBox) inflate.findViewById(R.id.cb_hardware_id);
        btnSureId = (Button) inflate.findViewById(R.id.btn_sure_id);
        btnSureId.setOnClickListener(this);
        dialog.setContentView(inflate);
        dialog.show();
    }

    /**
     * 开始维修
     */
    private void startRepair() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
            jsonObject.put("id", order.getId());
            if (cbSoftwareId.isChecked() && cbHardwareId.isChecked()) {
                jsonObject.put("faultType", 3);
            } else {
                if (cbSoftwareId.isChecked()) {
                    jsonObject.put("faultType", 1);
                }
                if (cbHardwareId.isChecked()) {
                    jsonObject.put("faultType", 2);
                }
            }
            String js = BaseJsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class)
                    .updateWorkOrder(js)
                    .compose(RxSchedulers.<BaseEntity>io2main())
                    .subscribe(new RxSubscriber<BaseEntity>() {
                        @Override
                        public void _onNext(BaseEntity baseEntity) {
                            dialog.dismiss();
                            ToastUtil.showShortSafe(baseEntity.msg, getApplicationContext());
                            if (baseEntity.isSuccess()) {
                                //开始维修成功，修改当前状态为正在维修
                                order.setListStatus(3);
                                changeUI();
                            }
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            dialog.dismiss();
                            ToastUtil.showShortSafe(msg, getApplicationContext());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
