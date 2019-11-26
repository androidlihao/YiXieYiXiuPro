package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.historyBean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-11-26 12:05
 * @description :
 */
public class Recycler_history_Adapter extends BaseQuickAdapter<historyBean, BaseViewHolder> {


    public Recycler_history_Adapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, historyBean item) {
        helper.addOnClickListener(R.id.iv_delete_id);
        helper.setText(R.id.tv_his_id,item.getSearchText());
    }
}
