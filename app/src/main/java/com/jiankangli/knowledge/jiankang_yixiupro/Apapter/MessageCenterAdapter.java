package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MsgCenter;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/22.
 */

public class MessageCenterAdapter extends BaseQuickAdapter<MsgCenter.DataBean.ListBean,BaseViewHolder> {
    public MessageCenterAdapter(int layoutResId, @Nullable List<MsgCenter.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenter.DataBean.ListBean item) {
         helper.setText(android.R.id.text1,item.getContent());
    }


}
