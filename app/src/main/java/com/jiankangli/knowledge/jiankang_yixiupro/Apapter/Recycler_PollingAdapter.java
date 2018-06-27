package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/26.
 */

public class Recycler_PollingAdapter extends BaseQuickAdapter<UpkeepOrder.DataBean,BaseViewHolder>{
    public Recycler_PollingAdapter(int layoutResId, @Nullable List<UpkeepOrder.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpkeepOrder.DataBean item) {
        helper.setText(R.id.tv_hosptionName,item.getHospitalName());
        helper.setText(R.id.tv_times,item.getReportTime());
        helper.setText(R.id.tv_sn,item.getDeviceNo());
        helper.setText(R.id.tv_ET,item.getBookTime());
        String statu="";
        int ImageID = R.mipmap.joblist_awm;
        switch (item.getListStatus()){
            case 1:
                statu="全部";
                break;
            case 2:
                statu="等待巡检";
                ImageID=R.mipmap.service1;
                break;
            case 3:
                statu="正在巡检";
                ImageID=R.mipmap.service2;
                break;
            case 4:
                statu="服务确认";
                ImageID=R.mipmap.service3;
                break;
            case 5:
                statu="正在审核";
                ImageID=R.mipmap.service4;
                break;
            case 6:
                statu="审核失败";
                ImageID=R.mipmap.service5;
                break;
            case 7:
                statu="巡检完成";
                ImageID=R.mipmap.service6;
                break;
        }
        Log.i(TAG, "convert: "+statu);
        helper.setText(R.id.tv_state,statu);
        helper.setImageResource(R.id.iv_state,ImageID);
    }
}
