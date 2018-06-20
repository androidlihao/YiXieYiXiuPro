package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MMBActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.rvMMB)
    RecyclerView rvMMB;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "我的留言");
        initSwipeRefresh();
        loadData();
    }

    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mmb;
    }


    @Override
    public void onRefresh() {
        loadData();
    }
    //加载更多数据
    private void loadData() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId", SharePreferenceUtils.get(this, "userId", ""));
            jsonObject.put("chatId", "23");
            jsonObject.put("pageNum", "1");
            jsonObject.put("pageSize", "10");
            String string=BaseJsonUtils.Base64String(jsonObject);
            //开始执行刷新过程
            RetrofitManager.create(ApiService.class)
                    .getMyMsg(string)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.<String>bindToLifecycle())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            Log.i("TAG", "MMB: "+s);
                            switch (GsonUtil.GsonCode(s)){
                                case "success":
                                    //解析数据

                                    break;
                                case "error":
                                    try {
                                        ToastUtils.showToast(getApplicationContext(),GsonUtil.GsonJsonObject(s,"data").getString("msg"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                            if (mSwipeRefreshLayout.isRefreshing()){
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(),"网络或服务器异常！");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
