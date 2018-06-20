package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;

public class MessageCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this,"消息中心");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }
}
