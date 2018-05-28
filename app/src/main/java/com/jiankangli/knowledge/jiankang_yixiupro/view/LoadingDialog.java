package com.jiankangli.knowledge.jiankang_yixiupro.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jiankangli.knowledge.jiankang_yixiupro.R;


/**
 * Created by 李浩 on 2018/5/17.
 */

public class LoadingDialog extends Dialog {


    public LoadingDialog(Context context) {
        super(context);
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        LinearLayout linearLayout = this.findViewById(R.id.LinearLayout);
        //linearLayout.getBackground().setAlpha(210);
    }
}
