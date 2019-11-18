package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;

/**
 * Created by 李浩 on 2018/6/26.
 */

public class Recycler_PollingAdapter extends BaseQuickAdapter<PollingOrder, BaseViewHolder> {


    public Recycler_PollingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PollingOrder item) {
        helper.setText(R.id.tv_hosptionName, item.getHospitalName());
        helper.setText(R.id.tv_times, item.getReportTime());
        helper.setText(R.id.tv_sn, "SN:"+item.getFactoryNo());
        helper.setText(R.id.tv_ET, "预计巡检时间:"+item.getBookTime());
        String statu = "";
        int ImageID = R.mipmap.joblist_awm;
        switch (item.getListStatus()) {
            case 1:
                statu = "全部";
                break;
            case 2:
                statu = "等待巡检";
                ImageID = R.mipmap.service1;
                break;
            case 3:
                statu = "正在巡检";
                ImageID = R.mipmap.service2;
                break;
            case 4:
                statu = "服务确认";
                ImageID = R.mipmap.service3;
                break;
            case 5:
                statu = "正在审核";
                ImageID = R.mipmap.service4;
                break;
            case 6:
                statu = "审核失败";
                ImageID = R.mipmap.service5;
                break;
            case 7:
                statu = "巡检完成";
                ImageID = R.mipmap.service6;
                break;
        }
        Log.i(TAG, "convert: " + statu);
        helper.setText(R.id.tv_state, statu);
        helper.setImageResource(R.id.iv_state, ImageID);
    }
}
