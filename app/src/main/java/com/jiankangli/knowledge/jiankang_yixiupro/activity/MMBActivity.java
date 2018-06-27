package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.Recycle_MMBAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MineMsg;
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

public class MMBActivity extends BaseActivity implements
        PullLoadMoreRecyclerView.PullLoadMoreListener,BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.rvMMB)
    public PullLoadMoreRecyclerView rvMMB;
    public final static int REFRESH_CODE=1;
    public final static int LOADMORD_CODE=2;
    public int currentPage=0;//默认为0,每次请求服务器加1
    private BaseQuickAdapter adapter;
    private List datalist = new LinkedList<>(); //准备数据源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "我的留言");
        initRecyclerView();
        loadData(REFRESH_CODE);
    }

    private void initRecyclerView() {
        //准备数据源
        //准备适配器
        rvMMB.setLinearLayout();
        adapter = new Recycle_MMBAdapter(R.layout.mine_msg_item,datalist);
        rvMMB.setAdapter(adapter);//绑定适配器
        adapter.setEmptyView(R.layout.empty_layout,rvMMB);
        rvMMB.setFooterViewText("加载中...");
        adapter.setOnItemClickListener(this);
        rvMMB.setOnPullLoadMoreListener(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mmb;
    }
    @Override
    public void onRefresh() {
        loadData(REFRESH_CODE);
    }

    @Override
    public void onLoadMore() {
        loadData(LOADMORD_CODE);
    }

    //加载更多数据
    private void loadData(final int code) {
        currentPage++;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SharePreferenceUtils.get(this, "userId", ""));
            jsonObject.put("chatId", "23");
            jsonObject.put("pageNum", currentPage);
            jsonObject.put("pageSize", "10");
            String string = BaseJsonUtils.Base64String(jsonObject);
            //开始执行刷新过程
            RetrofitManager.create(ApiService.class)
                    .getMyMsg(string)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.<String>bindToLifecycle())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            commonLoading.show();
                        }

                        @Override
                        public void onNext(String s) {
                            switch (GsonUtil.GsonCode(s)) {
                                case "success":
                                    MineMsg mineMsg=GsonUtil.GsonToBean(s,MineMsg.class);
                                    //解析数据
                                    switch (code){
                                        case REFRESH_CODE:
                                            datalist.addAll(0,mineMsg.getData().getList());
                                            ToastUtils.showToast(getApplicationContext(),"刷新成功");
                                            break;
                                        case LOADMORD_CODE:
                                            if (mineMsg.getData().getList().size()==0){
                                                ToastUtils.showToast(getApplicationContext(),"没有更多数据");
                                            }
                                            datalist.addAll(mineMsg.getData().getList());
                                            break;
                                    }
                                    break;
                                case "error":
                                    try {
                                        ToastUtils.showToast(getApplicationContext(), GsonUtil.GsonJsonObject(s, "data").getString("msg"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(), "网络或服务器异常！");
                        }

                        @Override
                        public void onComplete() {
                            adapter.notifyDataSetChanged();
                            commonLoading.dismiss();
                            rvMMB.setPullLoadMoreCompleted();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
