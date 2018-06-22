package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.jiankangli.knowledge.jiankang_yixiupro.Apapter.newMsgAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Status;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.TextScroll;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ImageLoader;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.jiankangli.knowledge.jiankang_yixiupro.Constant.constant.PIC_URL;

public class MainActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_online_statu_id)
    ImageView ivOnlineStatuId;
    @BindView(R.id.rl_head_id)
    RelativeLayout rlHeadId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.iv_touxiang_id)
    ImageView ivTouxiangId;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_msg_id)
    RecyclerView rvMsgId;
    @BindView(R.id.sw_load_msg)
    SwipeRefreshLayout swLoadMsg;
    @BindView(R.id.view_repaid_id)
    LinearLayout viewRepaidId;
    @BindView(R.id.view_upkeep_id)
    LinearLayout viewUpkeepId;
    @BindView(R.id.view_polling_id)
    LinearLayout viewPollingId;
    @BindView(R.id.view_part_id)
    LinearLayout viewPartId;
    private CustomPopWindow popWindow;
    private List<TextScroll> list;
    private newMsgAdapter newMsgAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "医械医修+");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initRecycler();
        swLoadMsg.setOnRefreshListener(this);
        //进入页面开始刷新
        swLoadMsg.setRefreshing(true);
        this.onRefresh();
    }

    private void initRecycler() {
        rvMsgId.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
        //准备数据源
        list = new ArrayList<>();
        newMsgAdapters = new newMsgAdapter(android.R.layout.simple_list_item_1, list);
        rvMsgId.setAdapter(newMsgAdapters);
        newMsgAdapters.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转
            }
        });
    }

    private void initViewData() {
        Picasso.get().load
                (PIC_URL + SharePreferenceUtils.get(
                        this, "headPicUrl", "" +
                                "")).error(R.mipmap.home_touxiang).into(ivTouxiangId);
        Log.i("TAG", "initViewData: "+PIC_URL + SharePreferenceUtils.get(
                this, "headPicUrl", "" +
                        ""));
        String statu = (String) SharePreferenceUtils.get(this, "status", "");
        switch (statu) {
            case "1":
                ivOnlineStatuId.setImageResource(R.mipmap.online);
                break;
            case "2":
                ivOnlineStatuId.setImageResource(R.mipmap.busy);
                break;
        }
        ArrayList mList = new ArrayList<>();
        mList.add(R.mipmap.scroll_pic1);
        mList.add(R.mipmap.scroll_pic2);
        mList.add(R.mipmap.scroll_pic3);
        banner.setImages(mList)
                .setImageLoader(new ImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setBannerAnimation(Transformer.Default)
                .setDelayTime(3000)
                .start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化数据
        initViewData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    //定制主页的状态栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.rl_head_id)
    public void onViewClicked() {
        int Xoff = (int) (getResources().getDimensionPixelSize(R.dimen.px_50) + rlHeadId.getMeasuredWidth());
        int Yoff = rlHeadId.getHeight() - ivTouxiangId.getMeasuredHeight();
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_bag, null);
        contentView.findViewById(R.id.tv_person).setOnClickListener(this);
        contentView.findViewById(R.id.tv_msg).setOnClickListener(this);
        contentView.findViewById(R.id.tv_online).setOnClickListener(this);
        contentView.findViewById(R.id.tv_busy).setOnClickListener(this);
        popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .create()
                //弹出弹窗,选择状态
                .showAsDropDown(rlHeadId, -Xoff, -Yoff / 2);
    }

    //从新退出程序功能
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    Long cumExitTime = 0l;

    //退出APP
    private void exit() {
        if ((System.currentTimeMillis() - cumExitTime) > 2000) {
            ToastUtils.showToast(getApplicationContext(), "再按一次退出程序");
            cumExitTime = System.currentTimeMillis();
        } else {
            //发送广播
            Intent intent = new Intent("drc.xxx.yyy.baseActivity");
            intent.putExtra("closeAll", 1);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_busy:
                //执行更改状态，然后根据状态修改
                ivOnlineStatuId.setImageResource(R.mipmap.busy);
                changeStatus("2");
                break;
            case R.id.tv_msg:
                Intent intents = new Intent(this, MessageCenterActivity.class);
                startActivity(intents);
                break;
            case R.id.tv_online:
                ivOnlineStatuId.setImageResource(R.mipmap.online);
                changeStatus("1");
                break;
            case R.id.tv_person:
                Intent intent = new Intent(this, PersonalActivity.class);
                startActivity(intent);
                break;

        }
        popWindow.dissmiss();
    }

    private void changeStatus(final String statu) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", SharePreferenceUtils.get(getApplicationContext(), "userId", -1 + ""));
            jsonObject.put("status", statu);
            String string = BaseJsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class)
                    .changeStatu(string)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<Status>bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Status>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Status status) {
                            SharePreferenceUtils.put(getApplicationContext(), "status", status.data.status);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(), "服务器或网络异常！");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        //开始获取数据更新
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", SharePreferenceUtils.get(this, "userId", ""));
            String string = BaseJsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class)
                    .getnotice(string)
                    .compose(this.<String>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            Log.i("TAG", "onNext: " + s);
                            Log.i("TAG", "onNext: " + Thread.currentThread().getName());
                            switch (GsonUtil.GsonCode(s)) {
                                case "success":
                                    TextScroll textScroll = GsonUtil.GsonToBean(s, TextScroll.class);
                                    //得到网络数据，开始修改数据源
                                    list.clear();
                                    list.add(textScroll);
                                    newMsgAdapters.notifyDataSetChanged();//更新数据源
                                    ToastUtils.showToast(getApplicationContext(),"刷新成功");
                                    break;
                                case "error":
                                    ToastUtils.showToast(getApplicationContext(), GsonUtil.GsonMsg(s));
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(), "服务器或网络异常!");
                            swLoadMsg.setRefreshing(false);

                        }

                        @Override
                        public void onComplete() {
                            swLoadMsg.setRefreshing(false);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.view_repaid_id, R.id.view_upkeep_id, R.id.view_polling_id, R.id.view_part_id})
    public void onViewClicked(View view) {
        Class cla = null;
        switch (view.getId()) {
            case R.id.view_repaid_id:
                cla=RepairOrderActivity.class;
                break;
            case R.id.view_upkeep_id:
                break;
            case R.id.view_polling_id:
                break;
            case R.id.view_part_id:
                break;
        }
        Intent intent=new Intent(this,cla);
        startActivity(intent);
    }
}
