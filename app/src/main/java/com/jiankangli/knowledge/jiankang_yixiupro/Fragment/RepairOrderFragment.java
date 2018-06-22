package com.jiankangli.knowledge.jiankang_yixiupro.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycler_repairadapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
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

    private List<RepairOrder.DataBean> data;
    private Recycler_repairadapter recycler_repairadapter;

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
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getUserVisibleHint()){

        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId", SharePreferenceUtils.get(getHolding(),"userId",-1+""));
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("pageNum",1);//默认为第一页
            jsonObject.put("page",jsonObject1);
            String electronicStatus="";
            switch (getArguments().getString("pos")){
                case "全部工单":
                    electronicStatus="1";
                    break;
                case "等待维修":
                    electronicStatus="2";
                    break;
                case "正在维修":
                    electronicStatus="3";
                    break;
                case "服务确认":
                    electronicStatus="4";
                    break;
                case "正在审核":
                    electronicStatus="5";
                    break;
                case "审核失败":
                    electronicStatus="6";
                    break;
                case "维修完成":
                    electronicStatus="7";
                    break;
            }
            jsonObject.put("electronicStatus",electronicStatus);
            Log.i(TAG, "initView: "+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //准备数据源
        getData(BaseJsonUtils.Base64String(jsonObject));
        //准备适配器
        recycler_repairadapter = new Recycler_repairadapter(R.layout.list_item,data);
        //填充适配器
        ryOrderId.setAdapter(recycler_repairadapter);
    }

    //初次获取数据
    @Override
    protected void getData(String electroicStatus) {
        RetrofitManager.create(ApiService.class)
                .getRepairOrder(electroicStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RepairOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RepairOrder repairOrder) {
                        Log.i(TAG, "onNext: "+repairOrder.getMsg());
                        switch (repairOrder.getCode()){
                            case "200":
                                data=repairOrder.getData();
                                recycler_repairadapter.notifyDataSetChanged();//刷新适配器
                                ToastUtils.showToast(getActivity(),"获取到"+repairOrder.getData().size()+"信息");
                                break;
                            case "3000":
                                ToastUtils.showToast(getContext(),repairOrder.getMsg());
                                break;
                        }
                       //得到网络数据
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(getActivity(),"网络或服务器异常");
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

}
