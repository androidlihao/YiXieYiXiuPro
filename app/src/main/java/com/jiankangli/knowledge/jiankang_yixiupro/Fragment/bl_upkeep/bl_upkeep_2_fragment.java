package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_upkeep;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.XJ_BY_RecordAadpter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair.bl_repair_3_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.upkeepBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.maintainOrderRecordBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.upkeepBlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :录入工单第二个界面
 */
public class bl_upkeep_2_fragment extends BaseFragment {


    @BindView(R.id.tv_fix_time)
    TextView tvFixTime;
    @BindView(R.id.tv_arrivalTime_id)
    TextView tvArrivalTimeId;
    @BindView(R.id.rc_ServiceRecode_id)
    RecyclerView rcServiceRecodeId;
    @BindView(R.id.btn_addServiceRecode_id)
    Button btnAddServiceRecodeId;
    @BindView(R.id.sc)
    NestedScrollView sc;

    private List<maintainOrderRecordBean.ServiceRecordMapArrayBean> serviceRecodeVosBeans;
    private XJ_BY_RecordAadpter recordAadpter;
    private int pos;
    private TimePickerView rqTime;
    private TimePickerView sjTime;
    private TimePickerView arrivalTime;
    private upkeepBlBean blBean;

    public static bl_upkeep_2_fragment newInstance() {
        bl_upkeep_2_fragment fragment = new bl_upkeep_2_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_upkeep_2_fragment_layout;
    }

    @Override
    protected void initView() {
        blBean = ((upkeepBackTrackingActivity) getActivity()).blBean;
        initRecordList();
        initDialog();
    }

    @Override
    public void onSupportVisible() {
        ((upkeepBackTrackingActivity) getActivity()).changeView();
        super.onSupportVisible();
    }

    /**
     * 服务记录列表
     */
    private void initRecordList() {
        rcServiceRecodeId.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceRecodeVosBeans = new LinkedList<>();
        List<upkeepBlBean.ServiceRecodeVosBean> serviceRecodeVos = blBean.getServiceRecodeVos();
        if (serviceRecodeVos == null) {
            serviceRecodeVosBeans.add(new maintainOrderRecordBean.ServiceRecordMapArrayBean());
        } else {
            for (upkeepBlBean.ServiceRecodeVosBean serviceRecodeVo : serviceRecodeVos) {
                maintainOrderRecordBean.ServiceRecordMapArrayBean serviceRecordMapArrayBean = new maintainOrderRecordBean.ServiceRecordMapArrayBean();
                serviceRecordMapArrayBean.setEndTime(serviceRecodeVo.getEndTime());
                serviceRecordMapArrayBean.setStartTime(serviceRecodeVo.getStartTime());
                serviceRecordMapArrayBean.setServiceTime(serviceRecodeVo.getServiceTime());
                serviceRecordMapArrayBean.setRoadTime(serviceRecodeVo.getRoadTime());
                serviceRecodeVosBeans.add(serviceRecordMapArrayBean);
            }
        }
        tvArrivalTimeId.setText(TextUtils.isEmpty(blBean.getArrivalTime()) ? "" : blBean.getArrivalTime());

        recordAadpter = new XJ_BY_RecordAadpter(R.layout.fix_record_item, serviceRecodeVosBeans);
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
                        DialogUtil.showPositiveDialog(getActivity(), "提示", "确认删除?", new DialogInterface.OnClickListener() {
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

    /**
     * 初始化时间控件
     */
    private void initDialog() {
        rqTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                recordAadpter.getData().get(pos).setServiceTime(TimeUtil.getTimeFormat(date));
                ((TextView) v).setText(TimeUtil.getTimeFormat(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCyclic(true)
                .build();

        sjTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String format = new SimpleDateFormat("HH:mm").format(date);
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
                .setType(new boolean[]{false, false, false, true, true, false})
                .isCyclic(true)
                .build();
        arrivalTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                ((TextView) v).setText(TimeUtil.getTimeFormatParseMinute(date));
                blBean.setArrivalTime(((TextView) v).getText().toString());
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, false})
                .isCyclic(true)
                .build();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public void toNext() {
        String arrivalTime = tvArrivalTimeId.getText().toString().trim();
        if (TextUtils.isEmpty(arrivalTime)) {
            ToastUtil.showShortSafe("请输入到达场地时间", getContext());
            return;
        }
        List<upkeepBlBean.ServiceRecodeVosBean> serviceRecodeVos = new ArrayList<>();
        List<maintainOrderRecordBean.ServiceRecordMapArrayBean> data = recordAadpter.getData();
        for (int i = 0; i < data.size(); i++) {
            maintainOrderRecordBean.ServiceRecordMapArrayBean serviceRecodeVosBean = data.get(i);
            String serviceTime = serviceRecodeVosBean.getServiceTime();
            String startTime = serviceRecodeVosBean.getStartTime();
            String endTime = serviceRecodeVosBean.getEndTime();
            if (TextUtils.isEmpty(serviceTime)) {
                ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的服务日期", getContext());
                return;
            }
            if (TextUtils.isEmpty(startTime)) {
                ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的开始时间", getContext());
                return;
            }
            if (TextUtils.isEmpty(endTime)) {
                ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的结束时间", getContext());
                return;
            }
            Integer j = startTime.compareTo(endTime);
            if (j >= 0) {
                ToastUtil.showShortSafe("第" + (i + 1) + "条记录的结束时间应该晚于开始时间", getContext());
                return;
            }
            String roadTime = data.get(i).getRoadTime();
            if (TextUtils.isEmpty(roadTime)) {
                ToastUtil.showShortSafe("请输入第" + (i + 1) + "条记录的路途时间", getContext());
                return;
            }
            upkeepBlBean.ServiceRecodeVosBean serviceRecodeVosBean1 = new upkeepBlBean.ServiceRecodeVosBean();
            serviceRecodeVosBean1.setServiceTime(serviceTime);
            serviceRecodeVosBean1.setStartTime(startTime);
            serviceRecodeVosBean1.setEndTime(endTime);
            serviceRecodeVosBean1.setRoadTime(roadTime);
            serviceRecodeVos.add(serviceRecodeVosBean1);
        }
        ((upkeepBackTrackingActivity) getActivity()).blBean.setServiceRecodeVos(serviceRecodeVos);
        //跳转到下一界面
        Bundle bundle = new Bundle();
        bundle.putInt("currentPos", 1);
        start(bl_upkeep_3_fragment.newInstance(bundle));
    }

    @Override
    public void onDestroy() {
        List<upkeepBlBean.ServiceRecodeVosBean> serviceRecodeVos = new ArrayList<>();
        List<maintainOrderRecordBean.ServiceRecordMapArrayBean> data = recordAadpter.getData();
        for (int i = 0; i < data.size(); i++) {
            maintainOrderRecordBean.ServiceRecordMapArrayBean serviceRecodeVosBean = data.get(i);
            String serviceTime = serviceRecodeVosBean.getServiceTime();
            String startTime = serviceRecodeVosBean.getStartTime();
            String endTime = serviceRecodeVosBean.getEndTime();
            String roadTime = data.get(i).getRoadTime();
            upkeepBlBean.ServiceRecodeVosBean serviceRecodeVosBean1 = new upkeepBlBean.ServiceRecodeVosBean();
            serviceRecodeVosBean1.setServiceTime(serviceTime);
            serviceRecodeVosBean1.setStartTime(startTime);
            serviceRecodeVosBean1.setEndTime(endTime);
            serviceRecodeVosBean1.setRoadTime(roadTime);
            serviceRecodeVos.add(serviceRecodeVosBean1);
        }
        ((upkeepBackTrackingActivity) getActivity()).blBean.setServiceRecodeVos(serviceRecodeVos);
        super.onDestroy();
    }

    @OnClick({R.id.tv_arrivalTime_id, R.id.btn_addServiceRecode_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_arrivalTime_id:
                arrivalTime.show(view);
                break;
            case R.id.btn_addServiceRecode_id:
                recordAadpter.addData(new maintainOrderRecordBean.ServiceRecordMapArrayBean());
                rcServiceRecodeId.scrollToPosition(recordAadpter.getData().size());
                break;
        }
    }


}
