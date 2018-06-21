package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.TextScroll;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/21.
 */

public class newMsgAdapter extends BaseQuickAdapter<TextScroll,BaseViewHolder>{
    public newMsgAdapter(int layoutResId, @Nullable List<TextScroll> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextScroll item) {
        Log.i(TAG, "convert: "+item.getData().getContent());
         helper.setText(android.R.id.text1,item.getData().getContent());
    }


}
