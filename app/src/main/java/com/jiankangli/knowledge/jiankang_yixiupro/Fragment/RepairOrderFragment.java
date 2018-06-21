package com.jiankangli.knowledge.jiankang_yixiupro.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_repairadapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepairOrderFragment extends BaseFragment {


    @BindView(R.id.ry_order_id)
    RecyclerView ryOrderId;
    Unbinder unbinder;
    private List<RepairOrder.DataBean> data;

    public RepairOrderFragment() {
        // Required empty public constructor
    }
    String string;
    int pos;
    @SuppressLint("ValidFragment")
    public RepairOrderFragment(String string, int pos) {
        // Required empty public constructor
        Log.i(TAG, "RepairOrderFragment: " + pos + string);
        this.pos=pos;
        this.string=string;
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: "+pos+string);
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //准备数据源
        getData();
        //准备适配器
        Recycler_repairadapter recycler_repairadapter=new Recycler_repairadapter(R.layout.list_item,data);
        //填充适配器
        ryOrderId.setAdapter(recycler_repairadapter);
    }

    //初次获取数据
    @Override
    protected void getData() {
        JSONObject jsonObject=new JSONObject();
        RetrofitManager.create(ApiService.class)
                .getRepairOrder(BaseJsonUtils.Base64String(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RepairOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RepairOrder repairOrder) {
                       //得到网络数据
                        data = repairOrder.getData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //更新数据源
    @Override
    protected void updateListData() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
