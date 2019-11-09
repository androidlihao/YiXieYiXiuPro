package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.enter_polling_report;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Constant.DbConstant;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.enterPollingReportActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MaintainDataBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.gen.MaintainDataBeanDao;
import com.jiankangli.knowledge.jiankang_yixiupro.greendao.GreenDaoContext;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GreenDaoUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :录入工单第一个界面
 */
public class enter_polling_report_1_fragment extends BaseFragment {


    @BindView(R.id.tv_rummager_id)
    TextView tvRummagerId;
    @BindView(R.id.rb_3_id)
    RadioButton rb3Id;
    @BindView(R.id.rb_6_id)
    RadioButton rb6Id;
    @BindView(R.id.tv_deviceModel_id)
    TextView tvDeviceModelId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    @BindView(R.id.btn_next_id)
    Button btnNextId;
    private SingleMaintainOrderBean singleMaintainOrderBean;


    public static enter_polling_report_1_fragment newInstance() {
        enter_polling_report_1_fragment fragment = new enter_polling_report_1_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.enter_polling_report_1_fragment_layout;
    }

    @Override
    protected void initView() {
        if (!((enterPollingReportActivity) getActivity()).isFrist) {
            rlSaveId.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        if (((enterPollingReportActivity) getActivity()).isFrist) {
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
                int id = ((enterPollingReportActivity) getActivity()).order.getId();
                GreenDaoUtil greenDaoUtil = GreenDaoUtil.getInstance(new GreenDaoContext(getActivity().getApplicationContext(), DbConstant.NEW_BY_DB));
                List<MaintainDataBean> list =
                        greenDaoUtil.getDaoSession().getMaintainDataBeanDao().queryBuilder().where(MaintainDataBeanDao.Properties.Id.eq(id)).list();
                e.onNext(list);
            }
        }).compose(RxSchedulers.<List<MaintainDataBean>>io2main())
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
                        ((enterPollingReportActivity) getActivity()).setSingleMaintainOrderBean(singleMaintainOrderBean);
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
                .getSingleInspectionOrder(((enterPollingReportActivity) getActivity()).getJson())
                .compose(RxSchedulers.<BaseEntity<SingleMaintainOrderBean>>io2main())
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
                        ((enterPollingReportActivity) getActivity()).setSingleMaintainOrderBean(singleMaintainOrderBean);
                        setTextString();
                        super.onComplete();
                    }
                });
    }

    private void setTextString() {
        if (singleMaintainOrderBean != null) {
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

    @OnClick({R.id.btn_save_id, R.id.btn_next_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_id:
                saveData();
                ((enterPollingReportActivity) getActivity()).save();
                break;
            case R.id.btn_next_id:
                if (!rb6Id.isChecked() && !rb3Id.isChecked()) {
                    ToastUtil.showShortSafe("请选择维护时间", getActivity());
                    return;
                }
                saveData();//先把修改的数据保存起来，然后进行下一步操作
                start(enter_polling_report_2_fragment.newInstance());
                break;
        }
    }

    /**
     * 保存修改的数据当前数据
     */
    private void saveData() {
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

}
