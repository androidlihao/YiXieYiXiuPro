package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_MmbOnlineAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Chat;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MmbOnlineActivity extends BaseActivity
        implements PullLoadMoreRecyclerView.PullLoadMoreListener{

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    @BindView(R.id.bt_NewMessage_id)
    TextView btNewMessageId;
    private String orderNo;
    private int id;
    private String name;
    private final static int REFRESH_CODE=1;
    private final static int LOADMORE_CODE=2;
    private int currentPage=1;//初始化为第一页
    private List<Chat.DataBean.ListBean> datalist;
    private Recycler_MmbOnlineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getintents();
        addMiddleTitle(this, name);
        //初始化Recyclerview
        initRecyclerview();
        //然后获取网络数据信息
        //然后根据网络数据信息进行多布局的排列
        //然后实现一种假聊天的效果
        //每次进入该页面都要刷新一下最新的聊天信息

    }
    private void initRecyclerview() {
        pullLoadMoreRecyclerView.setLinearLayout();
        //创建数据源
        datalist = new LinkedList();
        //创建适配器
        String userId= ((String) SPUtils.get(getApplicationContext(),"userId",-1+""));
        adapter = new Recycler_MmbOnlineAdapter(datalist,this,userId);
        //绑定适配器
        pullLoadMoreRecyclerView.setAdapter(adapter);
        pullLoadMoreRecyclerView.setFooterViewText("加载更多");
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
    }

    private void getintents() {
        orderNo = getIntent().getStringExtra("orderNo");
        id = getIntent().getIntExtra("Id",-1);
        name = getIntent().getStringExtra("name");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mmb_online;
    }

    @OnClick(R.id.bt_NewMessage_id)
    public void onViewClicked() {
        //执行留言操作
        Intent intent=new Intent(this,WriteMMBActivity.class);
        intent.putExtra("ChatId",id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        pullLoadMoreRecyclerView.refresh();//每次都刷新一下
        super.onResume();
    }

    @Override
    public void onRefresh() {
        getData(REFRESH_CODE);
    }

    @Override
    public void onLoadMore() {
        getData(LOADMORE_CODE);
    }

    private void getData(final int code) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(this,"userId",-1+""));
            jsonObject.put("id",id);
            jsonObject.put("orderNo", orderNo);
            jsonObject.put("pageNum", currentPage);
            jsonObject.put("pageSize", 10 + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitManager.create(ApiService.class)
                .getOnlineMsg(BaseJsonUtils.Base64String(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        switch (GsonUtil.GsonCode(s)){
                            case "success":
                                //解析成实体类
                                Chat chat=GsonUtil.GsonToBean(s,Chat.class);
                                switch (code){
                                    case REFRESH_CODE:
                                        datalist.addAll(chat.getData().getList());
                                        break;
                                    case LOADMORE_CODE:
                                        datalist.addAll(chat.getData().getList());
                                        break;
                                }
                                adapter.notifyDataSetChanged();
                                break;
                            case "error":
                                ToastUtils.showToast(getApplicationContext(),GsonUtil.GsonMsg(s));
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(getApplicationContext(),"服务器或网络异常");
                    }

                    @Override
                    public void onComplete() {
                       pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });
    }
}
