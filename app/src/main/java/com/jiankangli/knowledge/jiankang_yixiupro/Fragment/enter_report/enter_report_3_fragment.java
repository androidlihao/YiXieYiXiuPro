package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.enter_report;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Listeners.TextListener;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.enterReportActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.signActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :提交报告页面
 */
public class enter_report_3_fragment extends BaseFragment {

    @BindView(R.id.tv_select_date_id)
    TextView tvSelectDateId;
    @BindView(R.id.et_go_id)
    EditText etGoId;
    @BindView(R.id.et_back_id)
    EditText etBackId;
    @BindView(R.id.et_part_status_id)
    EditText etPartStatusId;
    @BindView(R.id.tv_part_status_id)
    TextView tvPartStatusId;
    @BindView(R.id.ll_content_id)
    LinearLayout llContentId;
    @BindView(R.id.tv_signature_id)
    TextView tvSignatureId;
    @BindView(R.id.iv_signature_id)
    ImageView ivSignatureId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.btn_next_id)
    Button btnNextId;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    Unbinder unbinder;

    private SingleMaintainOrderBean singleMaintainOrderBean;
    private TimePickerView pvTime1;

    public static enter_report_3_fragment newInstance() {
        enter_report_3_fragment fragment = new enter_report_3_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.enter_report_3_fragment_layout;
    }

    @Override
    protected void initView() {
        if (!((enterReportActivity) getActivity()).isFrist) {
            rlSaveId.setVisibility(View.GONE);
        }
        initDialog();
    }

    @Override
    protected void initData() {
        singleMaintainOrderBean = ((enterReportActivity) getActivity()).getSingleMaintainOrderBean();
        tvSelectDateId.setText(singleMaintainOrderBean.getLeaveTime());
        etGoId.setText(singleMaintainOrderBean.getTravelToTime() + "");
        etBackId.setText(singleMaintainOrderBean.getTravelBackTime() + "");
        etPartStatusId.setText(singleMaintainOrderBean.getProblemHandling());
        if (singleMaintainOrderBean.getOrderPicVo() != null) {
            String picUrl = singleMaintainOrderBean.getOrderPicVo().getPicUrl();
            if (picUrl.startsWith("http://")) {
                Picasso.get().load(singleMaintainOrderBean.getOrderPicVo().getPicUrl())
                        .error(R.drawable.load_error).placeholder(R.drawable.loading)
                        .into(ivSignatureId);
            } else {
                Picasso.get().load(new File(singleMaintainOrderBean.getOrderPicVo().getPicUrl()))
                        .error(R.drawable.load_error).placeholder(R.drawable.loading)
                        .into(ivSignatureId);
            }
        }


    }

    @Override
    protected void initListener() {
        etPartStatusId.addTextChangedListener(new TextListener(R.id.et_part_status_id) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                tvPartStatusId.setText(s.length() + "/" + 500);
                //修改问题描述
                singleMaintainOrderBean.setProblemHandling(s.toString());
            }
        });
        etGoId.addTextChangedListener(new TextListener(R.id.et_go_id) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                try {
                    singleMaintainOrderBean.setTravelToTime(Double.parseDouble(s.toString()));
                } catch (Exception e) {
                    singleMaintainOrderBean.setTravelToTime(0);
                }
            }
        });
        etBackId.addTextChangedListener(new TextListener(R.id.et_back_id) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                try {
                    singleMaintainOrderBean.setTravelBackTime(Double.parseDouble(s.toString()));
                } catch (Exception e) {
                    singleMaintainOrderBean.setTravelBackTime(0);
                }
            }
        });
    }


    @OnClick({R.id.btn_save_id, R.id.btn_next_id, R.id.tv_select_date_id, R.id.tv_signature_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_id:
                //保存数据到对应的中
                ((enterReportActivity) getActivity()).save();
                break;
            case R.id.btn_next_id:
                SingleMaintainOrderBean.OrderPicVoBean orderPicVo1 = singleMaintainOrderBean.getOrderPicVo();
                boolean isSign = false;
                if (orderPicVo1 != null) {
                    if (orderPicVo1.getType() == 5) {
                        isSign = true;

                    }
                }
                if (!isSign) {
                    ToastUtil.showShortSafe("请签字", getActivity());
                    return;
                }
                String leaveTime = tvSelectDateId.getText().toString();
                if (TextUtils.isEmpty(leaveTime)) {
                    ToastUtil.showShortSafe("请输入离开场地时间", getActivity());
                    return;
                }
                String travelToTime = etGoId.getText().toString();
                if (TextUtils.isEmpty(travelToTime)) {
                    ToastUtil.showShortSafe("请输入差旅时间", getActivity());
                    return;
                }
                String travelBackTime = etBackId.getText().toString();
                if (TextUtils.isEmpty(travelBackTime)) {
                    ToastUtil.showShortSafe("请输入差旅时间", getActivity());
                    return;
                }
                String trim = etPartStatusId.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showShortSafe("请输入问题描述", getActivity());
                    return;
                }
                ((enterReportActivity) getActivity()).upload();
                break;
            case R.id.tv_select_date_id:
                pvTime1.show();
                break;
            case R.id.tv_signature_id:
                //跳转到签字面板
                Intent intent = new Intent(getActivity(), signActivity.class);
                startActivityForResult(intent, 201);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode && requestCode == 201) {
            String sign = (String) data.getExtras().get("sign");
            Picasso.get().load(new File(sign)).into(ivSignatureId);
            //修改本地数据源
            SingleMaintainOrderBean.OrderPicVoBean orderPicVo = singleMaintainOrderBean.getOrderPicVo() == null ? new SingleMaintainOrderBean.OrderPicVoBean() : singleMaintainOrderBean.getOrderPicVo();
            orderPicVo.setCreateTime(TimeUtil.getCurrentTime());
            orderPicVo.setType(5);
            orderPicVo.setPicUrl(sign);
            singleMaintainOrderBean.setOrderPicVo(orderPicVo);
            singleMaintainOrderBean.setEngineerSignedTime(TimeUtil.getCurrentTime());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initDialog() {
        //时间选择器
        pvTime1 = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvSelectDateId.setText(TimeUtil.getTimeFormatParse(date));
                //设置离开场地时间
                singleMaintainOrderBean.setLeaveTime(TimeUtil.getTimeFormat(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, true})
                .isCyclic(true)
                .build();
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
