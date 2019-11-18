package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.sparePartBean;
import com.squareup.picasso.Picasso;


/**
 * @author lihao
 * @date 2019-09-13 20:29
 * @description :
 */
public class Recycler_SparePart_imagleAdapter extends BaseQuickAdapter<sparePartBean.AccessoryPicVosBean, BaseViewHolder> {


    public Recycler_SparePart_imagleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, sparePartBean.AccessoryPicVosBean item) {
        Picasso.get().load(item.getPicUrl())
                .placeholder(R.drawable.loading)
                .error(R.drawable.load_error)
                .into((ImageView) helper.getView(R.id.iv_id));
    }
}
