package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/26.
 */

public class Recycler_UpKeepAdapter extends BaseQuickAdapter<UpkeepOrder.DataBean,BaseViewHolder>{
    public Recycler_UpKeepAdapter(int layoutResId, @Nullable List<UpkeepOrder.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpkeepOrder.DataBean item) {

    }
}
