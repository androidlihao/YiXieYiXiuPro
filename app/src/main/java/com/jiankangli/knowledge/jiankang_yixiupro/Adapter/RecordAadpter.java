package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.fixRecordBean;

import java.util.List;


/**
 * @author lihao
 * @date 2019-09-28 15:44
 * @description :维修服务记录适配器
 */
public class RecordAadpter extends BaseQuickAdapter<fixRecordBean.ServiceRecordMapArrayBean, BaseViewHolder> {

    public RecordAadpter(int layoutResId, @Nullable List<fixRecordBean.ServiceRecordMapArrayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final fixRecordBean.ServiceRecordMapArrayBean item) {
        final int layoutPosition = helper.getLayoutPosition();
        helper.addOnClickListener(R.id.tv_serviceTime_time);
        helper.addOnClickListener(R.id.tv_startTime_id);
        helper.addOnClickListener(R.id.tv_endTime_id);
        helper.addOnClickListener(R.id.btn_delete_id);
        helper.setText(R.id.tv_serviceTime_time, item.getServiceTime());
        helper.setText(R.id.tv_startTime_id, item.getStartTime());
        helper.setText(R.id.tv_endTime_id, item.getEndTime());
        helper.setText(R.id.tv_roadTime_id, item.getRoadTime());
        switch (item.getEquipmentStatus()) {
            case 1:
                helper.setChecked(R.id.rb_fault_id,true);
                break;
            case 2:
                helper.setChecked(R.id.rb_part_fault_id,true);
                break;
            case 3:
                helper.setChecked(R.id.rb_normal_id,true);
                break;
        }
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
        ((RadioGroup) helper.getView(R.id.radioGroup_id)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fault_id:
                        getData().get(layoutPosition).setEquipmentStatus(1);
                        break;
                    case R.id.rb_part_fault_id:
                        getData().get(layoutPosition).setEquipmentStatus(2);
                        break;
                    case R.id.rb_normal_id:
                        getData().get(layoutPosition).setEquipmentStatus(3);
                        break;
                }
            }
        });
    }
}
