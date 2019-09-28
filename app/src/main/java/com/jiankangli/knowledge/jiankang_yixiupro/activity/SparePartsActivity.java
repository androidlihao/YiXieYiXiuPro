package com.jiankangli.knowledge.jiankang_yixiupro.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.OrderFragmentPageApapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 备件
 */
public class SparePartsActivity extends BaseActivity {

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
    @BindView(R.id.rl_search_id)
    RelativeLayout rlSearchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "备件");
        initview();
    }

    String[] titles = {"全部", "审批中", "配送中", "缺货", "配送完成"};

    private void initview() {
        rlSearchId.setVisibility(View.GONE);
        //准备适配器
        tvEnteringId.setText("申请备件");
        OrderFragmentPageApapter adapter = new OrderFragmentPageApapter(getSupportFragmentManager(), titles, "备件");
        viewpagerId.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpagerId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair_order;
    }

    @OnClick(R.id.tv_entering_id)
    public void onViewClicked() {
        //申请配件
        startActivity(new Intent(this,applySparePartActivity.class));
    }

}
