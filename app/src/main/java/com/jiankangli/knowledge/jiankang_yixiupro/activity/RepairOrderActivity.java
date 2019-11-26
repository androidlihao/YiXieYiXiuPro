package com.jiankangli.knowledge.jiankang_yixiupro.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.OrderFragmentPageApapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RepairOrderActivity extends BaseActivity {

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
        addMiddleTitle(this, "维修工单");
        initview();
    }

    String[] titles = {"全部工单", "等待维修", "正在维修", "服务确认", "正在审核", "审核失败", "维修完成"};

    private void initview() {
        //准备适配器
        OrderFragmentPageApapter adapter = new OrderFragmentPageApapter(getSupportFragmentManager(), titles, "维修");
        viewpagerId.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpagerId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair_order;
    }

    @OnClick({R.id.tv_entering_id, R.id.rl_search_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_entering_id:
                Intent intent = new Intent(this, repairBackTrackingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_search_id:
                //跳转到搜索页面
                Intent intent1 = new Intent(this, SearchActivity.class);
                intent1.putExtra("type","维修");
                startActivity(intent1);
                break;
        }
    }
}
