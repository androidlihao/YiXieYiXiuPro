package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_polling;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_upkeep.bl_upkeep_2_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.pollingBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.upkeepBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.inspectionWorkOrderBeasInfo;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.pollingBlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.upkeepBlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :巡检工单-录入工单第一个界面
 */
public class bl_polling_1_fragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.tv_rummager_id)
    TextView tvRummagerId;
    @BindView(R.id.et_deviceNo_id)
    EditText etDeviceNoId;
    @BindView(R.id.tv_hosptionName_id)
    TextView tvHosptionNameId;
    @BindView(R.id.rb_3_id)
    RadioButton rb3Id;
    @BindView(R.id.rb_6_id)
    RadioButton rb6Id;
    @BindView(R.id.rg_id)
    RadioGroup rgId;
    @BindView(R.id.tv_deviceModel_id)
    TextView tvDeviceModelId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    @BindView(R.id.btn_next_id)
    Button btnNextId;
    private boolean getData;
    private pollingBlBean blBean;
    private TextView tvTitleId;
    private Button tvRecoverId;
    private Button tvDeleteId;
    private Button tvCancelId;
    private Dialog dialog;

    public static bl_polling_1_fragment newInstance() {
        bl_polling_1_fragment fragment = new bl_polling_1_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_polling_1_fragment_layout;
    }

    @Override
    public void onSupportVisible() {
        ((pollingBackTrackingActivity) getActivity()).changeView();
        super.onSupportVisible();
    }

    @Override
    protected void initView() {
        rgId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_3_id:
                        blBean.setServicingTime(1);
                        break;
                    case R.id.rb_6_id:
                        blBean.setServicingTime(2);
                        break;
                }
            }
        });


    }

    @Override
    protected void initData() {
        Object xjbl = SPUtils.get(getContext(), "xjbl", "");
        if (TextUtils.isEmpty((CharSequence) xjbl)) {
            //那么直接new一个
            initBlbean();
        } else {
            //直接恢复数据
            pollingBlBean blBean = GsonUtils.jsonTobean(xjbl.toString(), pollingBlBean.class);
            View view = View.inflate(getActivity(), R.layout.dialog_is_recover_layout, null);
            tvTitleId = (TextView) view.findViewById(R.id.tv_title_id);
            tvTitleId.setText(blBean.getDeviceNo());
            tvRecoverId = (Button) view.findViewById(R.id.tv_recover_id);
            tvDeleteId = (Button) view.findViewById(R.id.tv_delete_id);
            tvCancelId = (Button) view.findViewById(R.id.tv_cancel_id);
            tvDeleteId.setOnClickListener(this);
            tvRecoverId.setOnClickListener(this);
            tvCancelId.setOnClickListener(this);
            dialog = new Dialog(getActivity());
            dialog.setContentView(view);
            dialog.show();
        }
        tvRummagerId.setText(SPUtils.get(getActivity(), "name", "").toString());
    }

    private void initBlbean() {
        blBean = new pollingBlBean();
        String userId = (String) SPUtils.get(getContext(), "userId", -1 + "");
        //用户ID
        blBean.setUserId(Integer.parseInt(userId));

        /**
         * 设置值给首页
         */
        ((pollingBackTrackingActivity) getActivity()).setBlBean(blBean);
    }

    @Override
    protected void initListener() {
        etDeviceNoId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    String deviceNo = v.getText().toString().trim();
                    if (!TextUtils.isEmpty(deviceNo)) {
                        getData(deviceNo);
                    }
                    /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                }
                return handled;
            }
        });
    }

    public void getData(String deviceNo) {
        RetrofitManager.create(ApiService.class)
                .getinspectionrecodeOrderInfoEcho(getJson(deviceNo))
                .compose(RxSchedulers.<BaseEntity<inspectionWorkOrderBeasInfo>>io2main())
                .as(AutoDispose.<BaseEntity<inspectionWorkOrderBeasInfo>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<inspectionWorkOrderBeasInfo>>() {
                    @Override
                    public void _onNext(BaseEntity<inspectionWorkOrderBeasInfo> recodeWorkOrderBeasInfoBaseEntity) {
                        if (recodeWorkOrderBeasInfoBaseEntity.isSuccess()) {
                            setData(recodeWorkOrderBeasInfoBaseEntity.data);
                            //获取产品信息成功
                            getData = true;
                        } else {
                            ToastUtil.showShortSafe(recodeWorkOrderBeasInfoBaseEntity.msg, getContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe("产品详情获取失败", getContext());
                    }
                });
    }


    @OnClick({R.id.btn_save_id, R.id.btn_next_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_id:
                if (!getData) {
                    ToastUtil.showShortSafe("获取产品信息失败，无法保存", getContext());
                    return;
                }
                try {
                    SPUtils.put(getContext(), "xjbl", GsonUtils.beanTojson(blBean));
                    ToastUtil.showShortSafe("保存成功", getActivity());
                } catch (Exception e) {
                    ToastUtil.showShortSafe(e + "保存失败", getActivity());
                }
                break;
            case R.id.btn_next_id:
                String deviceNo = etDeviceNoId.getText().toString().trim();
                if (TextUtils.isEmpty(deviceNo)) {
                    ToastUtil.showShortSafe("请输入设备序号", getContext());
                    return;
                }
                if (!getData) {
                    ToastUtil.showShortSafe("获取产品信息失败", getContext());
                    return;
                }
                if (!rb6Id.isChecked() && !rb3Id.isChecked()) {
                    ToastUtil.showShortSafe("请选择维护时间", getActivity());
                    return;
                }
                start(bl_polling_2_fragment.newInstance());
                break;
        }
    }

    /**
     * 设置信息
     *
     * @param recodeWorkOrderBeasInfo
     */
    private void setData(inspectionWorkOrderBeasInfo recodeWorkOrderBeasInfo) {
        tvHosptionNameId.setText(recodeWorkOrderBeasInfo.getHospitalName());
        tvDeviceModelId.setText(recodeWorkOrderBeasInfo.getDeviceModel());
        //填充数据给blbean
        blBean.setDeviceNo(etDeviceNoId.getText().toString());
        blBean.setHospitaAddress(recodeWorkOrderBeasInfo.getHospitaAddress());
        blBean.setContractNo((String) recodeWorkOrderBeasInfo.getContractNo());
        blBean.setDeviceModel(recodeWorkOrderBeasInfo.getDeviceModel());
        blBean.setSectionName(recodeWorkOrderBeasInfo.getSectionName());
        blBean.setDeviceName(recodeWorkOrderBeasInfo.getDeviceName());
        blBean.setDeviceBrand(recodeWorkOrderBeasInfo.getDeviceBrand());
        blBean.setName(recodeWorkOrderBeasInfo.getName());
        blBean.setHospitalName(recodeWorkOrderBeasInfo.getHospitalName());
//        blBean.setTemplateCode(recodeWorkOrderBeasInfo.getTemplateCode());
//        String templateCode = recodeWorkOrderBeasInfo.getTemplateCode();
        //模板类型
        ((pollingBackTrackingActivity) getActivity()).setTemplateCode("inspection");
    }

    private String getJson(String deviceNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deviceNo", deviceNo);
            jsonObject.put("userId", SPUtils.get(getActivity(), "userId", -1 + ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return BaseJsonUtils.Base64String(jsonObject);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recover_id:
                //恢复数据
                Object bybl = SPUtils.get(getContext(), "xjbl", "");
                blBean = GsonUtils.jsonTobean(bybl.toString(), pollingBlBean.class);
                getData = true;
                ((pollingBackTrackingActivity) getActivity()).setBlBean(blBean);
                etDeviceNoId.setText(blBean.getDeviceNo() == null ? "" : blBean.getDeviceNo());
                tvHosptionNameId.setText(blBean.getHospitalName() == null ? "" : blBean.getHospitalName());
                switch (blBean.getServicingTime()) {
                    case 1:
                        rb3Id.setChecked(true);
                        break;
                    case 2:
                        rb6Id.setChecked(true);
                        break;
                }
                tvDeviceModelId.setText(blBean.getDeviceModel() == null ? "" : blBean.getDeviceModel());
                ((pollingBackTrackingActivity) getActivity()).setTemplateCode("inspection");
                dialog.cancel();
                break;
            case R.id.tv_delete_id:
                SPUtils.remove(getActivity(), "xjbl");
                initBlbean();
                dialog.cancel();
                break;
            case R.id.tv_cancel_id:
                dialog.cancel();
                break;
        }
    }

}
