package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;


import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.messagePushBean;


/**
 * Created by 李浩 on 2018/6/21.
 */

public class newMsgAdapter extends BaseQuickAdapter<messagePushBean,BaseViewHolder>{

    public newMsgAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, messagePushBean item) {
        switch (item.getTypeString()){
            case "巡检":
                helper.setBackgroundColor(R.id.tv_typeString_id, Color.parseColor("#48FF3B"));
                break;
            case "保养":
                helper.setBackgroundColor(R.id.tv_typeString_id, Color.parseColor("#2196F3"));
                break;
            case "维修":
                helper.setBackgroundColor(R.id.tv_typeString_id, Color.parseColor("#FFEB3B"));
                break;
            case "备件":
                helper.setBackgroundColor(R.id.tv_typeString_id, Color.parseColor("#F44336"));
                break;
        }
        helper.setText(R.id.tv_typeString_id,item.getTypeString());
        helper.setText(R.id.tv_content_id,item.getContent());
    }

}
