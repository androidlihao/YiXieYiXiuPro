package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.repairBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RecodeWorkOrderBeasInfo;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :录入工单第一个界面
 */
public class bl_repair_1_fragment extends BaseFragment {


    @BindView(R.id.tv_workNumber_id)
    TextView tvWorkNumberId;
    @BindView(R.id.et_deviceNo_id)
    EditText etDeviceNoId;
    @BindView(R.id.tv_name_id)
    EditText tvNameId;
    @BindView(R.id.tv_projectCode_id)
    EditText tvProjectCodeId;
    @BindView(R.id.tv_contractNo_id)
    EditText tvContractNoId;
    @BindView(R.id.tv_deviceBrand_id)
    EditText tvDeviceBrandId;
    @BindView(R.id.tv_deviceName_id)
    EditText tvDeviceNameId;
    @BindView(R.id.tv_deviceModel_id)
    EditText tvDeviceModelId;
    @BindView(R.id.tv_factoryNo_id)
    EditText tvFactoryNoId;
    @BindView(R.id.tv_sectionName_id)
    EditText tvSectionNameId;
    @BindView(R.id.tv_phoneNumber_id)
    EditText tvPhoneNumberId;
    @BindView(R.id.tv_hospitaAddress_id)
    EditText tvHospitaAddressId;
    @BindView(R.id.tv_servicerName_id)
    EditText tvServicerNameId;
    @BindView(R.id.tv_derviceContractStatus_id)
    EditText tvDerviceContractStatusId;
    @BindView(R.id.tv_repairType_id)
    EditText tvRepairTypeId;
    Unbinder unbinder;
    private boolean getData;

    public static bl_repair_1_fragment newInstance() {
        bl_repair_1_fragment fragment = new bl_repair_1_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_repair_1_fragment_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvWorkNumberId.setText(SPUtil.getInstance(getActivity()).getString("workNumber"));
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
                    if (!TextUtils.isEmpty(deviceNo)){
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
                .getRecodeWorkOrderBeasInfo(getJson(deviceNo))
                .compose(RxSchedulers.<BaseEntity<RecodeWorkOrderBeasInfo>>io2main())
                .as(AutoDispose.<BaseEntity<RecodeWorkOrderBeasInfo>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<RecodeWorkOrderBeasInfo>>() {
                    @Override
                    public void _onNext(BaseEntity<RecodeWorkOrderBeasInfo> recodeWorkOrderBeasInfoBaseEntity) {
                        if (recodeWorkOrderBeasInfoBaseEntity.isSuccess()){
                            setData(recodeWorkOrderBeasInfoBaseEntity.data);
                            //获取产品信息成功
                            getData = true;
                        }else {
                            ToastUtil.showShortSafe(recodeWorkOrderBeasInfoBaseEntity.msg,getContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe("产品详情获取失败",getContext());
                    }
                });
    }

    public void toNext(){
        String deviceNo = etDeviceNoId.getText().toString().trim();
        if (TextUtils.isEmpty(deviceNo)) {
            ToastUtil.showShortSafe("请输入设备序号", getContext());
            return;
        }
        if (!getData){
            ToastUtil.showShortSafe("获取产品信息失败", getContext());
            return;
        }
        start(bl_repair_2_fragment.newInstance());
        save();
    }


    @Override
    public void onSupportVisible() {
        ((repairBackTrackingActivity) getActivity()).changeTitle("录入维修工单");
        super.onSupportVisible();
    }

    /**
     * 保存值
     */
    private void save() {
        blBean blBean = ((repairBackTrackingActivity) getActivity()).blBean;
        //设备序号
        blBean.setDeviceNo(etDeviceNoId.getText().toString());
        String userId =SPUtil.getInstance(getContext().getApplicationContext()).getString("userId");
        //用户ID
        blBean.setUserId(Integer.parseInt(userId));
        //工程师姓名
        blBean.setEngineerName((String) SPUtils.get(getActivity(),"name",""));
        //工程师电话
        blBean.setEngineerPhone((String) SPUtils.get(getActivity(),"phone"," "));
    }

    /**
     * 设置信息
     * @param recodeWorkOrderBeasInfo
     */
    private void setData(RecodeWorkOrderBeasInfo recodeWorkOrderBeasInfo) {
        tvNameId.setText(recodeWorkOrderBeasInfo.getName());
        tvProjectCodeId.setText(recodeWorkOrderBeasInfo.getProjectCode());
        tvContractNoId.setText(recodeWorkOrderBeasInfo.getContractNo());
        tvDeviceBrandId.setText(recodeWorkOrderBeasInfo.getDeviceBrand());
        tvDeviceNameId.setText(recodeWorkOrderBeasInfo.getDeviceName());
        tvDeviceModelId.setText(recodeWorkOrderBeasInfo.getDeviceModel());
        tvFactoryNoId.setText(recodeWorkOrderBeasInfo.getFactoryNo());
        tvSectionNameId.setText(recodeWorkOrderBeasInfo.getSectionName());
        tvPhoneNumberId.setText(recodeWorkOrderBeasInfo.getPhoneNumber());
        tvHospitaAddressId.setText(recodeWorkOrderBeasInfo.getHospitaAddress());
        tvServicerNameId.setText(recodeWorkOrderBeasInfo.getServicerName());
        String s = TextUtils.isEmpty(recodeWorkOrderBeasInfo.getDerviceContractStatus()) ? "暂无数据" : recodeWorkOrderBeasInfo.getDerviceContractStatus();
        tvDerviceContractStatusId.setText(s);
        tvRepairTypeId.setText("维修");
    }

    private String getJson(String deviceNo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deviceNo", deviceNo);
            jsonObject.put("userId",SPUtil.getInstance(getActivity().getApplicationContext()).getString("userId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return BaseJsonUtils.Base64String(jsonObject);
    }
}
