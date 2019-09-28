package com.jiankangli.knowledge.jiankang_yixiupro.Base;

import android.app.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 李浩 on 2018/6/21.
 */

public abstract class CommonFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.pullLoadMoreRecyclerView)
    public PullLoadMoreRecyclerView pullLoadMoreRecyclerView;

    public Activity activity;
    private Unbinder unbinder;
    public JSONObject jsonObject;

    public final static int REFRESH_CODE=1;
    public final static int LOADMORE_CODE=2;
    public int currentPage=0;//默认为0,每次请求服务器加1
    public List data=new LinkedList<>();

    protected abstract int getLayoutId();
    protected abstract void getData(int code, JSONObject jsonObject);//
    //获取宿主Activity
    protected Activity getHolding(){
        return getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//解绑
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
    protected void initView(BaseQuickAdapter adapter) {
        pullLoadMoreRecyclerView.setLinearLayout();
        //填充适配器
        pullLoadMoreRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        pullLoadMoreRecyclerView.setFooterViewText("加载中..");
        adapter.setEmptyView(R.layout.empty_layout,pullLoadMoreRecyclerView);
        pullLoadMoreRecyclerView.refresh();
    }
    //更新数据源
    @Override
    public void onRefresh() {
        //下拉刷新
        currentPage=1;
        JsonStrings(REFRESH_CODE);
    }

    @Override
    public void onLoadMore() {
        //上拉加载
        JsonStrings(LOADMORE_CODE);
    }
    //请求服务器的js数据
    protected void JsonStrings(int code) {
        currentPage++;
        jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(getHolding(), "userId", -1 + ""));
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("pageNum", currentPage);//默认为第一页
            jsonObject.put("page", jsonObject1);
            int electronicStatus = getArguments().getInt("pos");
            jsonObject.put("electronicStatus", 1+electronicStatus+"");
            getData(code,jsonObject);//执行请求操作
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
