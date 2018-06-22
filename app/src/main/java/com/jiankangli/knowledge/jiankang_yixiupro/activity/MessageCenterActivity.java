package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.MessageCenterAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MsgCenter;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MessageCenterActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.rc_id)
    RecyclerView rcId;
    @BindView(R.id.sw_load_msg)
    SwipeRefreshLayout swLoadMsg;
    private int currentPage=1;//默认为1
    private List<MsgCenter.DataBean.ListBean> list;
    private List<MsgCenter.DataBean.ListBean> Datalist=new ArrayList<>();
    private MessageCenterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "消息中心");
        initRecycler();
        initSwRefresh();
    }

    private void initSwRefresh() {
        swLoadMsg.setRefreshing(true);
    }

    private void initRecycler() {
        rcId.setLayoutManager(new LinearLayoutManager(this));
        //rcId.addItemDecoration();
        //准备数据源
        getData(currentPage);
        //准备适配器
        adapter = new MessageCenterAdapter(android.R.layout.simple_list_item_1,Datalist);
        //填充适配器
        rcId.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    public void getData(int currentPage2) {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId", SharePreferenceUtils.get(this,"userId",-1+""))  ;
            jsonObject.put("pageNum",currentPage2+"");
            jsonObject.put("pageSize","10");
            RetrofitManager.create(ApiService.class)
                    .getMsgCenter(BaseJsonUtils.Base64String(jsonObject))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MsgCenter>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(MsgCenter msgCenter) {
                            list = msgCenter.getData().getList();//刷新得到的更多数据
                            Datalist.addAll(list);
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(),"服务器或网络异常!");
                        }

                        @Override
                        public void onComplete() {
                          swLoadMsg.setRefreshing(false);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //下拉刷新
    @Override
    public void onRefresh() {

    }
}
