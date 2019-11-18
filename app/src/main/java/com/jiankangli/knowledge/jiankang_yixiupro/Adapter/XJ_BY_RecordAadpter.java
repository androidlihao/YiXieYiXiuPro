package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.maintainOrderRecordBean;

import java.util.List;


/**
 * @author lihao
 * @date 2019-09-28 15:44
 * @description :巡检保养服务记录适配器 */
public class XJ_BY_RecordAadpter extends BaseQuickAdapter<maintainOrderRecordBean.ServiceRecordMapArrayBean, BaseViewHolder> {

    public XJ_BY_RecordAadpter(int layoutResId, @Nullable List<maintainOrderRecordBean.ServiceRecordMapArrayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final maintainOrderRecordBean.ServiceRecordMapArrayBean item) {
        final int layoutPosition = helper.getLayoutPosition();
        helper.addOnClickListener(R.id.tv_serviceTime_time);
        helper.addOnClickListener(R.id.tv_startTime_id);
        helper.addOnClickListener(R.id.tv_endTime_id);
        helper.addOnClickListener(R.id.btn_delete_id);
        helper.setText(R.id.tv_serviceTime_time,item.getServiceTime());
        helper.setText(R.id.tv_startTime_id,item.getStartTime());
        helper.setText(R.id.tv_endTime_id,item.getEndTime());
        helper.setText(R.id.tv_roadTime_id,item.getRoadTime());
        helper.getView(R.id.ll_sbzt_id).setVisibility(View.GONE);
        final EditText helperView = helper.getView(R.id.tv_roadTime_id);
        helperView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    getData().get(layoutPosition).setRoadTime(s.toString());
                }
        });
    }
}
