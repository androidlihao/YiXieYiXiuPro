package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.MessageCenterAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MsgCenter;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class MessageCenterActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemClickListener{

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.rc_id)
    RecyclerView rcId;
    @BindView(R.id.sw_load_msg)
    SwipeRefreshLayout swLoadMsg;
    private int currentPage=0;//默认为1
    private List<MsgCenter.DataBean.ListBean> Datalist=new LinkedList<>();
    private MessageCenterAdapter adapter;
    private final static int REFRESH_CODE=1;
    private final static int LOADMORE_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "消息中心");
        initAdapter();
        initRecycler();
        initSwRefresh();
    }

    private void initAdapter() {
        //准备适配器
        adapter = new MessageCenterAdapter(R.layout.simple_list_item,Datalist);
        //填充适配器
        adapter.bindToRecyclerView(rcId);
        adapter.setOnItemClickListener(this);
        View view=getLayoutInflater().inflate(R.layout.loadmoreing,null);
        adapter.addFooterView(view);
    }
    private void setFooterViewMsg(final String footerViewMsg, final int code){
        View view=adapter.getFooterLayout();
        final TextView textView=view.findViewById(R.id.tv_load_id);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        textView.setText(footerViewMsg);
                        textView.setVisibility(code);
                    }
                });
    }
    private void initSwRefresh() {
        swLoadMsg.setOnRefreshListener(this);
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcId.setLayoutManager(linearLayoutManager);
        rcId.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
                if (manager instanceof LinearLayoutManager){
                    int lastCompletelyVisibleItemPosition =((LinearLayoutManager) manager).findLastVisibleItemPosition();
                    if (lastCompletelyVisibleItemPosition==adapter.getItemCount()-1
                            &&adapter.getItemCount()>=10&&newState==RecyclerView.SCROLL_STATE_IDLE){
                         getData(LOADMORE_CODE);
                    }
                }
            }
        });
        //rcId.addItemDecoration();
        //准备数据源
        this.onRefresh();//开始刷新操作
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    public void getData(final int Code) {
        try {
            currentPage++;
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId", SPUtils.get(this,"userId",-1+""))  ;
            jsonObject.put("pageNum",currentPage+"");
            jsonObject.put("pageSize","10");
            RetrofitManager.create(ApiService.class)
                    .getMsgCenter(BaseJsonUtils.Base64String(jsonObject))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MsgCenter>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            if (Code==LOADMORE_CODE){
                                setFooterViewMsg("加载中...",VISIBLE);
                            }
                        }
                        @Override
                        public void onNext(MsgCenter msgCenter) {
                            List<MsgCenter.DataBean.ListBean> list = msgCenter.getData().getList();//请求网络得到的数据
                            Log.i("TAG", "onNext: "+list.size());
                            if (list.size()<1){
                                ToastUtils.showToast(getApplicationContext(),"没有更多数据");
                            }
                            switch (Code){
                                case REFRESH_CODE:
                                    Datalist.addAll(0,list);//刷新的话放第一位
                                    break;
                                case LOADMORE_CODE:
                                    Datalist.addAll(list);//放集合最后
                                    break;
                            }
                            if (list.size()<10){
                                setFooterViewMsg("没有更多数据",VISIBLE);
                            }
                            adapter.setNewData(Datalist);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(),"服务器或网络异常!");
                        }

                        @Override
                        public void onComplete() {
                            switch (Code){
                                case REFRESH_CODE:
                                    swLoadMsg.setRefreshing(false);
                                    break;
                                case LOADMORE_CODE:
                                    break;
                            }
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //下拉刷新
    @Override
    public void onRefresh() {
       getData(REFRESH_CODE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MsgCenter.DataBean.ListBean item= (MsgCenter.DataBean.ListBean) adapter.getData().get(position);
        Intent intent=new Intent(this,MessageDetilsActivity.class);
        intent.putExtra("id",item.getId());
        startActivity(intent);
    }

}
