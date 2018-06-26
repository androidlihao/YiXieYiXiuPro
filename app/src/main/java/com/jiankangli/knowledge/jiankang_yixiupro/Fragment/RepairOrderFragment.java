package com.jiankangli.knowledge.jiankang_yixiupro.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_repairadapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class RepairOrderFragment extends BaseFragment {


    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    public List data=new LinkedList<>();
    public Recycler_repairadapter adapter;
    public int currentPage=0;//默认为1
    public final static int REFRESH_CODE=1;
    public final static int LOADMORE_CODE=2;
    public JSONObject jsonObject;

    String string;
    int pos;

    @SuppressLint("ValidFragment")
    public RepairOrderFragment(String string, int pos) {
        this.pos = pos;
        this.string = string;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        pullLoadMoreRecyclerView.setLinearLayout();
        //准备适配器
        adapter = new Recycler_repairadapter(R.layout.list_item, data);
        //填充适配器
        pullLoadMoreRecyclerView.setAdapter(adapter);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);

        pullLoadMoreRecyclerView.setFooterViewText("加载中..");

        pullLoadMoreRecyclerView.refresh();
    }

    //初次获取数据
    @Override
    protected void getData(final int code,JSONObject jsonObject) {
        RetrofitManager.create(ApiService.class)
                .getRepairOrder(BaseJsonUtils.Base64String(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String string) {
                        RepairOrder repairOrder=GsonUtil.GsonToBean(string,RepairOrder.class);
                        List<RepairOrder.DataBean> list = repairOrder.getData();
                        switch (repairOrder.getCode()){
                            case "200":
                                switch (code){
                                    case REFRESH_CODE:
                                        data.addAll(0,list);
                                        break;
                                    case LOADMORE_CODE:
                                        data.addAll(list);
                                        if (list.size()==0||list.isEmpty()||list==null){
                                            ToastUtils.showToast(getContext(),"没有更多数据");
                                        }
                                        break;
                                }
                                break;
                            case "3000":
                                ToastUtils.showToast(getContext(), repairOrder.getMsg());
                                break;
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(getActivity(), "网络或服务器异常");
                    }

                    @Override
                    public void onComplete() {
                       pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });
    }
    //请求服务器的js数据
    @Override
    protected void JsonStrings(int code) {
        currentPage++;
        jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SharePreferenceUtils.get(getHolding(), "userId", -1 + ""));
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

    //更新数据源

    @Override
    public void onRefresh() {
       //刷新
        JsonStrings(REFRESH_CODE);
    }

    @Override
    public void onLoadMore() {
      //下拉加载
        JsonStrings(LOADMORE_CODE);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
