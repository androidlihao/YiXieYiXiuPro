package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SpareParts;

/**
 * @author lihao
 * @date 2019-09-13 17:26
 * @description :
 */
public class Recycler_SparePartsAdapter extends BaseQuickAdapter<SpareParts, BaseViewHolder> {

    public Recycler_SparePartsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpareParts SpareParts) {
        helper.setText(R.id.tv_hosptionName, SpareParts.getAccessoryName());
        helper.setText(R.id.tv_times, SpareParts.getApplyTime());
        helper.getView(R.id.tv_sn).setVisibility(View.INVISIBLE);
        String statu = "";
        switch (SpareParts.getAccessoryStatus()) {
            case 1 :
                statu = "审批中";
                break;
            case 2 :
                statu = "配送中";
                break;
            case 3 :
                statu = "缺货";
                break;
            case 4 :
                statu = "配送完成";
                break;

        }
        helper.setText(R.id.tv_state, statu);
        helper.getView(R.id.iv_state).setVisibility(View.INVISIBLE);
    }
}
