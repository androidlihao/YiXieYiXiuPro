package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
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
         helper.setText(R.id.tv_msg_id,item.getContent());
    }

    @Override
    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener, RecyclerView recyclerView) {
        super.setOnLoadMoreListener(requestLoadMoreListener, recyclerView);
    }
}
