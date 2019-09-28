package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;

import java.util.HashMap;


/**
 * @author lihao
 * @date 2019-09-13 19:21
 * @description :
 */
public class OrderDetailsAdapter extends BaseQuickAdapter<HashMap<String,String>, BaseViewHolder> {

    public OrderDetailsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HashMap<String, String> item) {
        for (String key : item.keySet()) {
            helper.setText(R.id.tv_item_key,key);
             helper.setText(R.id.tv_item_value,item.get(key));
        }
    }


}
