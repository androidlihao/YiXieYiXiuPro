package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : linchao
 * @date: 2019-01-28 10:13
 * Description:
 */
public class AssetUtil {

    /**
     * 根据文件名获取assets目录下的图片资源
     *
     * @param filename
     * @return
     */
    public static Bitmap getBitmapFromAssets(String filename, Context context) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            // 获取字节流
            is = context.getAssets().open(filename);
            // 转成图片对象
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    // 关闭字节流
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static InputStream getIsFromAssets(String fileName, Context context) {
        InputStream is;
        try {
            is = context.getAssets().open(fileName);
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
