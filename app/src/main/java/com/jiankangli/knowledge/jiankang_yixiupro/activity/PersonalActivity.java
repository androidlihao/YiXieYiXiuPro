package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.os.Bundle;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;

public class PersonalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this,"个人中心");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }
}
