package com.jiankangli.knowledge.jiankang_yixiupro.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_PollingAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;


/**
 * Created by 李浩 on 2018/6/26.
 */

@SuppressLint("ValidFragment")
public class PollingFragment extends BaseFragment{

    private Recycler_PollingAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair_order;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=super.onCreateView(inflater, container, savedInstanceState);
        adapter = new Recycler_PollingAdapter(R.layout.list_item2,data);
        initView(adapter);
        return view;
    }

    @Override
    protected void getData(final int code, JSONObject jsonObject) {
        Log.i(TAG, "getData: "+jsonObject);
        RetrofitManager.create(ApiService.class)
                .getPollingOrder(BaseJsonUtils.Base64String(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PollingOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PollingOrder pollingOrder) {
                        List<UpkeepOrder.DataBean> list = pollingOrder.getData();
                        switch (pollingOrder.getCode()){
                            case "200":
                                switch (code){
                                    case REFRESH_CODE:
                                        data.addAll(0,list);
                                        if (list.size()!=0&&currentPage!=1){
                                            ToastUtils.showToast(getContext(),"刷新成功");
                                        }
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
                                ToastUtils.showToast(getContext(), pollingOrder.getMsg());
                                break;
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(getHolding(),"服务器或网络异常");
                    }

                    @Override
                    public void onComplete() {
                        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

}
