package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;



import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.OdrerDetailsBean;

import com.squareup.picasso.Picasso;


/**
 * @author lihao
 * @date 2019-09-13 20:29
 * @description :
 */
public class Recycler_imagleAdapter extends BaseQuickAdapter<OdrerDetailsBean.OrderPicVosBean, BaseViewHolder> {


    public Recycler_imagleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OdrerDetailsBean.OrderPicVosBean item) {
        Picasso.get().load(item.getPicUrl())
                .placeholder(R.drawable.loading)
                .error(R.drawable.load_error)
                .into((ImageView) helper.getView(R.id.iv_id));
    }
}
