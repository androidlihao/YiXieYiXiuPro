package com.jiankangli.knowledge.jiankang_yixiupro.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_repairadapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class RepairOrderFragment extends BaseFragment {

    public BaseQuickAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair_order;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=super.onCreateView(inflater,container,savedInstanceState);
        adapter=new Recycler_repairadapter(R.layout.list_item, data);
        initView(adapter);
        return view;
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
                                        if (currentPage!=1){
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


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RepairOrder.DataBean dataBean= (RepairOrder.DataBean) adapter.getData().get(position);
        //然后执行跳转逻辑
    }
}
