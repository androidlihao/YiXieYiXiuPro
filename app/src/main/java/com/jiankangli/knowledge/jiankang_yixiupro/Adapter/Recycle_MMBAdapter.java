package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MineMsg;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/27.
 */

public class Recycle_MMBAdapter extends BaseQuickAdapter<MineMsg.DataBean.ListBean,BaseViewHolder>{
    public Recycle_MMBAdapter(int layoutResId, @Nullable List<MineMsg.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,MineMsg.DataBean.ListBean item) {
        helper.setText(R.id.tv_order,item.getOrderNo());
        helper.setText(R.id.tv_time,item.getSendTime());
        helper.setText(R.id.tv_content,item.getContent());
        switch (item.getYesOrNo()){
            case "0":
                helper.getView(R.id.iv_has).setVisibility(View.INVISIBLE);
                break;
            default:
                helper.getView(R.id.iv_has).setVisibility(View.VISIBLE);
                break;
        }
    }
}
