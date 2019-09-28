package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.spare_parts;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_SparePartsAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_repairadapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.PartDetailsActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SpareParts;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SparePartsFragment extends BaseFragment<SparePartsPresenter> implements SparePartsContract.View,PullLoadMoreRecyclerView.PullLoadMoreListener {

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
        adapter = new Recycler_SparePartsAdapter(R.layout.list_item);
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
                SpareParts spareParts = (SpareParts) adapter.getData().get(position);
                //然后执行跳转逻辑
                Intent intent = new Intent(getActivity(), PartDetailsActivity.class);
                intent.putExtra("part",spareParts);
                startActivity(intent);
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
        mPresenter = new SparePartsPresenter();
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
    public void setNewData(List<SpareParts> SpareParts) {
        List data = adapter.getData();
        if (currentPage==1){
//            ToastUtil.showShortSafe("刷新成功",mView.getContext());
            adapter.setNewData(SpareParts);
        }else if (currentPage!=1&&SpareParts.size()!=0){
//            ToastUtil.showShortSafe("加载成功",mView.getContext());
            data.addAll(SpareParts);
            adapter.setNewData(data);
        }else {
            ToastUtil.showShortSafe("没有更多工单",mView.getContext());
        }
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
}
