package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;


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
        helper.setText(R.id.tv_typeString_id,item.getTypeString());
        helper.setText(R.id.tv_content_id,item.getContent());
    }

}
