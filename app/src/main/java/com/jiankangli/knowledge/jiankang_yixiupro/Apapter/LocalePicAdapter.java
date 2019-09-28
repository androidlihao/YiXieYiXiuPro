package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.luck.picture.lib.entity.LocalMedia;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * @author : chenxin
 * @date: 2019-04-01 17:40
 * Description:
 */
public class LocalePicAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {

    public LocalePicAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalMedia localMedia) {
        ImageView imgPic = helper.getView(R.id.img_pic);
        imgPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (localMedia.getPath().startsWith("http://")){
            Picasso.get().load(localMedia.getPath())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.load_error)
                    .config(Bitmap.Config.RGB_565)
                    .into(imgPic);
        }else {
            Picasso.get().load(new File(localMedia.getPath()))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.load_error)
                    .config(Bitmap.Config.RGB_565)
                    .into(imgPic);
        }
        helper.addOnClickListener(R.id.iv_close);
    }
}
