package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_PollingAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_UpKeepAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_repairadapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.historyBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * @author lihao
 * @date 2019-11-26 14:56
 * @description :搜索结果页面
 */
public class SearchResultActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener{

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private BaseQuickAdapter adapter;
    //搜索页数
    private int currentPage = 1;
    private com.jiankangli.knowledge.jiankang_yixiupro.bean.historyBean historyBean;

    @Override
    protected int getLayoutId() {
        return R.layout.search_result_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "搜索结果");
        historyBean = (historyBean) getIntent().getSerializableExtra("history");
        initView();
        initListener();
        pullLoadMoreRecyclerView.setRefreshing(true);
        pullLoadMoreRecyclerView.refresh();
    }

    private void initView() {
        switch (getIntent().getStringExtra("type")){
            case "维修":
                adapter = new Recycler_repairadapter(R.layout.list_item);
                break;
            case "保养":
                adapter = new Recycler_UpKeepAdapter(R.layout.list_item2);
                break;
            case "巡检":
                adapter = new Recycler_PollingAdapter(R.layout.list_item2);
                break;
        }
        pullLoadMoreRecyclerView.setLinearLayout();
        pullLoadMoreRecyclerView.setAdapter(adapter);
        pullLoadMoreRecyclerView.setFooterViewText("加载中..");
        adapter.setEmptyView(R.layout.empty_layout, pullLoadMoreRecyclerView);
    }

    private void initListener() {
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        pullLoadMoreRecyclerView.setIsRefresh(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (getIntent().getStringExtra("type")){
                    case "维修":
                        RepairOrder repairOrder = (RepairOrder) adapter.getData().get(position);
                        //然后执行跳转逻辑
                        switch (repairOrder.getListStatus()) {
                            case 4:
                                Intent intent1 = new Intent(SearchResultActivity.this, serviceConfirmPageEchoActivity.class);
                                intent1.putExtra("order", repairOrder);
                                startActivity(intent1);
                                break;
                            default:
                                Intent intent = new Intent(SearchResultActivity.this, OrderDetailsActivity.class);
                                intent.putExtra("order", repairOrder);
                                startActivity(intent);
                                break;
                        }
                        break;
                    case "保养":
                        UpkeepOrder upkeepOrder = (UpkeepOrder) adapter.getData().get(position);
                        //然后执行跳转逻辑
                        switch (upkeepOrder.getListStatus()) {
                            case 4:
                                //调整到服务确认页面
                                Intent intent1 = new Intent(SearchResultActivity.this, UpkeepServiceConfirmPageEchoActivity.class);
                                intent1.putExtra("order", upkeepOrder);
                                startActivity(intent1);
                                break;
                            default:
                                Intent intent = new Intent(SearchResultActivity.this, UpkeepOrderDetailsActivity.class);
                                intent.putExtra("order", upkeepOrder);
                                startActivity(intent);
                                break;
                        }
                        break;
                    case "巡检":
                        PollingOrder pollingOrder = (PollingOrder) adapter.getData().get(position);
                        //然后执行跳转逻辑
                        switch (pollingOrder.getListStatus()) {
                            case 4:
                                //调整到服务确认页面
                                Intent intent1 = new Intent(SearchResultActivity.this, inspectionServiceConfirmPageEchoActivity.class);
                                intent1.putExtra("order", pollingOrder);
                                startActivity(intent1);
                                break;
                            default:
                                Intent intent = new Intent(SearchResultActivity.this, PollingOrderDetailsActivity.class);
                                intent.putExtra("order", pollingOrder);
                                startActivity(intent);
                                break;
                        }
                        break;
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        switch (getIntent().getStringExtra("type")){
            case "维修":
                getRepairOrders();
                break;
            case "保养":
                getUpkeepOrder();
                break;
            case "巡检":
                getPollingOrder();
                break;
        }

    }

    @Override
    public void onLoadMore() {
        currentPage++;
        switch (getIntent().getStringExtra("type")){
            case "维修":
                getRepairOrders();
                break;
            case "保养":
                getUpkeepOrder();
                break;
            case "巡检":
                getPollingOrder();
                break;
        }
    }

    /**
     * 获取保养
     */
    public void getUpkeepOrder() {
        RetrofitManager.create(ApiService.class)
                .getUpkeepOrder(getJson())
                .compose(RxSchedulers.<BaseEntity<List<UpkeepOrder>>>io2main())
                .as(AutoDispose.<BaseEntity<List<UpkeepOrder>>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<List<UpkeepOrder>>>() {
                    @Override
                    public void _onNext(BaseEntity<List<UpkeepOrder>> listBaseEntity) {
                        if (listBaseEntity.isSuccess()){
                            setNewData(listBaseEntity.data);
                        }else {
                            ToastUtil.showShortSafe(listBaseEntity.msg,getApplicationContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg,getApplicationContext());
                    }
                });

    }
    /**
     * 获取巡检
     */
    public void getPollingOrder() {
        RetrofitManager.create(ApiService.class)
                .getPollingOrder(getJson())
                .compose(RxSchedulers.<BaseEntity<List<PollingOrder>>>io2main())
                .as(AutoDispose.<BaseEntity<List<PollingOrder>>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<List<PollingOrder>>>() {
                    @Override
                    public void _onNext(BaseEntity<List<PollingOrder>> listBaseEntity) {
                        if (listBaseEntity.isSuccess()){
                            setNewData(listBaseEntity.data);
                        }else {
                            ToastUtil.showShortSafe(listBaseEntity.msg,getApplicationContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg,getApplicationContext());
                    }
                });

    }
    public void getRepairOrders() {
        RetrofitManager.create(ApiService.class)
                .getRepairOrder(getJson())
                .compose(RxSchedulers.<BaseEntity<List<RepairOrder>>>io2main())
                .as(AutoDispose.<BaseEntity<List<RepairOrder>>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<List<RepairOrder>>>() {
                    @Override
                    public void _onNext(BaseEntity<List<RepairOrder>> listBaseEntity) {
                        if (listBaseEntity.isSuccess()){
                            setNewData(listBaseEntity.data);
                        }else {
                            ToastUtil.showShortSafe(listBaseEntity.msg,getApplicationContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg,getApplicationContext());
                    }
                });

    }
    public void setNewData(List orders) {
        List data = adapter.getData();
        if (currentPage == 1) {
            adapter.setNewData(orders);
        } else if (currentPage != 1 && orders.size() != 0) {
            data.addAll(orders);
            adapter.setNewData(data);
        } else {
            ToastUtil.showShortSafe("没有更多工单", getApplicationContext());
        }
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }
    /**
     * 获取json数据
     */
    public String getJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(getApplicationContext(), "userId", -1 + ""));
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("pageNum", currentPage);
            jsonObject.put("hospitalName",historyBean.getSearchText());
            jsonObject.put("page", jsonObject1);
            int electronicStatus = 1;
//            jsonObject.put("electronicStatus", electronicStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }
}
