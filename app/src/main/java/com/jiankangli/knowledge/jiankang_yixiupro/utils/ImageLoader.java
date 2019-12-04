package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by 李浩 on 2018/6/20.
 */

public  class ImageLoader extends com.youth.banner.loader.ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Picasso.get()
                .load((String) path)
                .into(imageView);
    }
}
