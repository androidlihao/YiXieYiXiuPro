package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/21.
 */

public class Recycler_repairadapter extends BaseQuickAdapter<RepairOrder.DataBean,BaseViewHolder>{

    public Recycler_repairadapter(int layoutResId, @Nullable List<RepairOrder.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrder.DataBean item) {
        helper.setText(R.id.tv_hosptionName,item.getHospitalName());
        helper.setText(R.id.tv_times,item.getReportTime());
        helper.setText(R.id.tv_sn,item.getDeviceNo());
        switch (item.getOrderStatus()){
            case 0:
                break;
        }
    }
}
