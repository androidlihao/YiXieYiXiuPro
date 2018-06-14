package com.jiankangli.knowledge.jiankang_yixiupro.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.R;

import butterknife.ButterKnife;

/**
 * Created by 李浩 on 2018/6/13.
 */

public class CommonLoading extends Dialog{

    private TextView tv_msg;

    public CommonLoading(@NonNull Context context) {
        super(context);
    }

    public CommonLoading(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CommonLoading(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_loading);
        initView();//
    }


    private void initView() {
        tv_msg = findViewById(R.id.tv_msg);
    }
}
