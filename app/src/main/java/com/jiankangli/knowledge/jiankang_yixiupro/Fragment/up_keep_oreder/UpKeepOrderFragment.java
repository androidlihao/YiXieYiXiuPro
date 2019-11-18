package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.up_keep_oreder;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_UpKeepAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.UpkeepOrderDetailsActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.UpkeepServiceConfirmPageEchoActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UpKeepOrderFragment extends BaseFragment<UpkeepOrderPresenter> implements UpkeepOrderContract.View,PullLoadMoreRecyclerView.PullLoadMoreListener {

    public BaseQuickAdapter adapter;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    //搜索页数
    private int currentPage=1;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair_order;
    }

    @Override
    protected void initView() {
        adapter = new Recycler_UpKeepAdapter(R.layout.list_item2);
        pullLoadMoreRecyclerView.setLinearLayout();
        pullLoadMoreRecyclerView.setAdapter(adapter);
        pullLoadMoreRecyclerView.setFooterViewText("加载中..");
        adapter.setEmptyView(R.layout.empty_layout,pullLoadMoreRecyclerView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        pullLoadMoreRecyclerView.setIsRefresh(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UpkeepOrder upkeepOrder = (UpkeepOrder) adapter.getData().get(position);
                //然后执行跳转逻辑
                switch (upkeepOrder.getListStatus()) {
                    case 4:
                        //调整到服务确认页面
                        Intent intent1 = new Intent(getActivity(), UpkeepServiceConfirmPageEchoActivity.class);
                        intent1.putExtra("order", upkeepOrder);
                        startActivity(intent1);
                        break;
                    default:
                        Intent intent = new Intent(getActivity(), UpkeepOrderDetailsActivity.class);
                        intent.putExtra("order", upkeepOrder);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onSupportVisible() {
        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
        super.onSupportVisible();
    }

    @Override
    protected void CreatePresenter() {
        mPresenter = new UpkeepOrderPresenter();
    }

    @Override
    public void onRefresh() {
        currentPage=1;
        mPresenter.getRepairOrders();
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        mPresenter.getRepairOrders();
    }

    @Override
    public int getcurrentPage() {
        return currentPage;
    }

    @Override
    public int getCurrentElectronic() {
        return getArguments().getInt("pos");
    }

    @Override
    public void setNewData(List<UpkeepOrder> upkeepOrders) {
        List data = adapter.getData();
        if (currentPage==1){
            adapter.setNewData(upkeepOrders);
        }else if (currentPage!=1&&upkeepOrders.size()!=0){
            data.addAll(upkeepOrders);
            adapter.setNewData(data);
        }else {
            ToastUtil.showShortSafe("没有更多工单",mView.getContext());
        }
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
