package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.enter_report;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Constant.DbConstant;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.enterReportActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MaintainDataBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.gen.MaintainDataBeanDao;
import com.jiankangli.knowledge.jiankang_yixiupro.greendao.GreenDaoContext;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GreenDaoUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :录入工单第一个界面
 */
public class enter_report_1_fragment extends BaseFragment {

    @BindView(R.id.btn_next_id)
    Button btnNextId;
    @BindView(R.id.tv_rummager_id)
    TextView tvRummagerId;
    @BindView(R.id.rb_3_id)
    RadioButton rb3Id;
    @BindView(R.id.rb_6_id)
    RadioButton rb6Id;
    @BindView(R.id.tv_deviceModel_id)
    TextView tvDeviceModelId;
    @BindView(R.id.et_softwareVersion_id)
    EditText etSoftwareVersionId;
    @BindView(R.id.tv_loadingTime_id)
    TextView tvLoadingTimeId;
    @BindView(R.id.tv_patientFlow_id)
    EditText tvPatientFlowId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    Unbinder unbinder;
    private SingleMaintainOrderBean singleMaintainOrderBean;
    private TimePickerView build;

    public static enter_report_1_fragment newInstance() {
        enter_report_1_fragment fragment = new enter_report_1_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.enter_report_1_fragment_layout;
    }

    @Override
    protected void initView() {
        if (!((enterReportActivity) getActivity()).isFrist){
            rlSaveId.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        if (((enterReportActivity) getActivity()).isFrist) {
            //如果当前不为修改
            getLocalData();
        } else {
            //如果当前为修改
            getData();
        }

    }

    /**
     * 读取本地的数据库
     */
    private void getLocalData() {
        Observable.create(new ObservableOnSubscribe<List<MaintainDataBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MaintainDataBean>> e) throws Exception {
                //直接读取本地数据库
                int id = ((enterReportActivity) getActivity()).order.getId();
                GreenDaoUtil greenDaoUtil = GreenDaoUtil.getInstance(new GreenDaoContext(getActivity().getApplicationContext(), DbConstant.NEW_BY_DB));
                List<MaintainDataBean> list =
                        greenDaoUtil.getDaoSession().getMaintainDataBeanDao().queryBuilder().where(MaintainDataBeanDao.Properties.Id.eq(id)).list();
                e.onNext(list);
            }
        }).compose(RxSchedulers.<List<MaintainDataBean>>io2main())
                .as(AutoDispose.<List<MaintainDataBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<List<MaintainDataBean>>() {
                    @Override
                    public void _onNext(List<MaintainDataBean> list) {
                        if (list.size() > 0 && list.get(0).getJsonObject() != null) {
                            String jsonObject = list.get(0).getJsonObject();
                            singleMaintainOrderBean = GsonUtil.GsonToBean(jsonObject, SingleMaintainOrderBean.class);
                        } else {
                            singleMaintainOrderBean = new SingleMaintainOrderBean();
                        }
                        onComplete();
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        singleMaintainOrderBean = new SingleMaintainOrderBean();
                        onComplete();
                    }

                    @Override
                    public void onComplete() {
                        //给主界面赋值
                        ((enterReportActivity) getActivity()).setSingleMaintainOrderBean(singleMaintainOrderBean);
                        setTextString();
                        super.onComplete();
                    }
                });
    }

    /**
     * 第一次进入时候，获取回显示数据,然后显示出来
     */
    private void getData() {
        RetrofitManager.create(ApiService.class)
                .getSingleMaintainOrder(((enterReportActivity) getActivity()).getJson())
                .compose(RxSchedulers.<BaseEntity<SingleMaintainOrderBean>>io2main())
                .as(AutoDispose.<BaseEntity<SingleMaintainOrderBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<SingleMaintainOrderBean>>() {
                    @Override
                    public void _onNext(BaseEntity<SingleMaintainOrderBean> singleMaintainOrderBeanBaseEntity) {
                        if (singleMaintainOrderBeanBaseEntity.isSuccess()) {
                            //如果成功
                            singleMaintainOrderBean = singleMaintainOrderBeanBaseEntity.data;
                        } else {
                            singleMaintainOrderBean = new SingleMaintainOrderBean();
                            ToastUtil.showShortSafe(singleMaintainOrderBeanBaseEntity.msg, getContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        singleMaintainOrderBean = new SingleMaintainOrderBean();
                    }

                    @Override
                    public void onComplete() {
                        //给主界面赋值
                        ((enterReportActivity) getActivity()).setSingleMaintainOrderBean(singleMaintainOrderBean);
                        setTextString();
                        super.onComplete();
                    }
                });
    }

    private void setTextString() {
        if (singleMaintainOrderBean != null) {
            //装机时间
            String loadingTime = singleMaintainOrderBean.getLoadingTime();
            tvLoadingTimeId.setText(TextUtils.isEmpty(loadingTime) ? "" : loadingTime);
            int patientFlow = singleMaintainOrderBean.getPatientFlow();
            tvPatientFlowId.setText(patientFlow == 0 ? "" : patientFlow + "");
            String softwareVersion = singleMaintainOrderBean.getSoftwareVersion();
            etSoftwareVersionId.setText(TextUtils.isEmpty(softwareVersion) ? "" : softwareVersion);
            int servicingTime = singleMaintainOrderBean.getServicingTime();
            switch (servicingTime) {
                case 1:
                    rb3Id.setChecked(true);
                    break;
                case 2:
                    rb6Id.setChecked(true);
                    break;
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_loadingTime_id, R.id.btn_save_id, R.id.btn_next_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_loadingTime_id:
                //时间选择器
                if (build == null) {
                    build = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            tvLoadingTimeId.setText(TimeUtil.getTimeFormatParse(date));
                        }
                        //默认设置为年月日时分秒
                    }).setLabel("年", "月", "日", "时", "分", "秒")
                            .setType(new boolean[]{true, true, true, true, true, true})
                            .isCyclic(true)
                            .build();
                }
                build.show();
                break;
            case R.id.btn_save_id:
                saveData();
                ((enterReportActivity) getActivity()).save();
                break;
            case R.id.btn_next_id:
                if (!rb6Id.isChecked() && !rb3Id.isChecked()) {
                    ToastUtil.showShortSafe("请选择维护时间", getActivity());
                    return;
                }
                saveData();//先把修改的数据保存起来，然后进行下一步操作
                start(enter_report_2_fragment.newInstance());
                break;
        }
    }

    /**
     * 保存修改的数据当前数据
     */
    private void saveData() {
        //软件版本
        String softwareVersion = etSoftwareVersionId.getText().toString();
        singleMaintainOrderBean.setSoftwareVersion(softwareVersion);
        //装机时间
        String loadingTime = tvLoadingTimeId.getText().toString();
        singleMaintainOrderBean.setLoadingTime(loadingTime);
        //病人流量
        String patientFlow = tvPatientFlowId.getText().toString();
        singleMaintainOrderBean.setPatientFlow(TextUtils.isEmpty(patientFlow) ? 0 : Integer.parseInt(patientFlow));
        //设备型号
        String deviceModel = tvDeviceModelId.getText().toString();
        singleMaintainOrderBean.setDeviceModel(deviceModel);
        //设置维护时间
        if (rb3Id.isChecked()) {
            singleMaintainOrderBean.setServicingTime(1);
        } else if (rb6Id.isChecked()) {
            singleMaintainOrderBean.setServicingTime(2);
        } else {
            singleMaintainOrderBean.setServicingTime(0);
        }
    }

    /**
     * 数据显示直接更新数据
     */
    @Override
    public void onSupportVisible() {
        tvRummagerId.setText(SPUtils.get(getActivity(), "name", "").toString());
        String deviceModel1 = getActivity().getIntent().getExtras().getString("deviceModel");
        tvDeviceModelId.setText(deviceModel1);
        setTextString();
        super.onSupportVisible();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
