package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 李浩 on 2018/5/17.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
