package com.jiankangli.knowledge.jiankang_yixiupro.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.OrderFragmentPageApapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PollingActivity extends BaseActivity {

    @BindView(R.id.tv_entering_id)
    TextView tvEnteringId;
    @BindView(R.id.rl_head_id)
    RelativeLayout rlHeadId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_id)
    ViewPager viewpagerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "巡检列表");
        initview();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upkeep;
    }

    String[] titles = {"全部", "等待巡检", "正在巡检", "服务确认", "正在审核", "审核失败", "巡检完成"};

    private void initview() {
        tvEnteringId.setText("录入报告");
        //准备适配器
        OrderFragmentPageApapter adapter = new OrderFragmentPageApapter(getSupportFragmentManager(), titles,"巡检");
        viewpagerId.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpagerId);
    }

    @OnClick(R.id.tv_entering_id)
    public void onViewClicked() {
    }
}
