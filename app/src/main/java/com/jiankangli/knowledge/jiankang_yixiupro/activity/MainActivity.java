package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.graphics.Color;
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
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.newMsgAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.ElectronOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SpareParts;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Status;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.messagePushBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ImageLoader;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
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

import static com.jiankangli.knowledge.jiankang_yixiupro.Constant.Constants.PIC_URL;

public class MainActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    private newMsgAdapter newMsgAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "医械医修+");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initRecycler();
        swLoadMsg.setOnRefreshListener(this);
        onRefresh();
    }

    private void initRecycler() {
        rvMsgId.setLayoutManager(new LinearLayoutManager(this));
        newMsgAdapters = new newMsgAdapter(R.layout.item_push_msg_layout);
        rvMsgId.setAdapter(newMsgAdapters);
        newMsgAdapters.setOnLoadMoreListener(this, rvMsgId);
        newMsgAdapters.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转
                messagePushBean messagePushBean = newMsgAdapters.getData().get(position);
                Intent intent = null;
                switch (messagePushBean.getTypeString()){
                    case "巡检":
                        intent=new Intent(getApplication(),PollingOrderDetailsActivity.class);
                        PollingOrder pollingOrder = new PollingOrder();
                        pollingOrder.setListStatus(messagePushBean.getOrderStatus());
                        pollingOrder.setId(messagePushBean.getWorkOrderId());
                        intent.putExtra("order",pollingOrder);
                        break;
                    case "保养":
                        intent=new Intent(getApplication(),UpkeepOrderDetailsActivity.class);
                        UpkeepOrder upkeepOrder = new UpkeepOrder();
                        upkeepOrder.setListStatus(messagePushBean.getOrderStatus());
                        upkeepOrder.setId(messagePushBean.getWorkOrderId());
                        intent.putExtra("order",upkeepOrder);
                        break;
                    case "维修":
                        intent=new Intent(getApplication(),OrderDetailsActivity.class);
                        RepairOrder repairOrder = new RepairOrder();
                        repairOrder.setListStatus(messagePushBean.getOrderStatus());
                        repairOrder.setId(messagePushBean.getWorkOrderId());
                        intent.putExtra("order",repairOrder);
                        break;
                    case "备件":
                        intent=new Intent(getApplication(),PartDetailsActivity.class);
                        SpareParts spareParts =new SpareParts();
                        //然后执行跳转逻辑
                        spareParts.setId(messagePushBean.getWorkOrderId());
                        spareParts.setAccessoryStatus(messagePushBean.getOrderStatus());
                        intent.putExtra("part",spareParts);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    private void initViewData() {
        Picasso.get().load
                (PIC_URL + SPUtils.get(
                        this, "headPicUrl", "" +
                                "")).error(R.mipmap.home_touxiang).into(ivTouxiangId);
        Log.i("TAG", "initViewData: " + PIC_URL + SPUtils.get(
                this, "headPicUrl", "" +
                        ""));
        String statu = (String) SPUtils.get(this, "status", "");
        switch (statu) {
            case "1":
                ivOnlineStatuId.setImageResource(R.mipmap.online);
                break;
            case "2":
                ivOnlineStatuId.setImageResource(R.mipmap.busy);
                break;
        }
        ArrayList mList = new ArrayList<>();
        //获取图片路径
//        RetrofitManager.create(ApiService.class)
//                .getBannerList()
//                .compose(RxSchedulers.<BaseEntity<List<BannerBean>>>io2main())
//                .subscribe(new Consumer<BaseEntity<List<BannerBean>>>() {
//                    @Override
//                    public void accept(BaseEntity<List<BannerBean>> listBaseEntity) throws Exception {
//                        ArrayList mList = new ArrayList<>();
//                        for (BannerBean datum : listBaseEntity.data) {
//                            mList.add(Constants.PIC_URL+datum.getBannerPath());
//                        }
//                        banner.setImages(mList)
//                                .setImageLoader(new ImageLoader())
//                                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
//                                .setBannerAnimation(Transformer.Default)
//                                .setDelayTime(3000)
//                                .start();
//                    }
//                });
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
            jsonObject.put("userId", SPUtils.get(getApplicationContext(), "userId", -1 + ""));
            jsonObject.put("status", statu);
            String string = BaseJsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class)
                    .changeStatu(string)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Status>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Status status) {
                            SPUtils.put(getApplicationContext(), "status", status.data.status);
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
        pageNum = 1;
        getPushMessage();
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        getPushMessage();
    }

    private void getPushMessage() {
        RetrofitManager.create(ApiService.class)
                .messagePush(getJson())
                .compose(RxSchedulers.<BaseEntity<List<messagePushBean>>>io2main())
                .as(AutoDispose.<BaseEntity<List<messagePushBean>>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<List<messagePushBean>>>() {
                    @Override
                    public void _onNext(BaseEntity<List<messagePushBean>> listBaseEntity) {
                        if (listBaseEntity.isSuccess()) {
                            List<messagePushBean> data = listBaseEntity.data;
                            if (data != null) {
                                if (pageNum == 1) {
                                    //刷新的情况
                                    newMsgAdapters.setNewData(data);
                                } else {
                                    newMsgAdapters.addData(data);
                                }

                            }
                        } else {
                            ToastUtils.showToast(getApplicationContext(), listBaseEntity.msg);
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtils.showToast(getApplicationContext(), msg);
                    }

                    @Override
                    public void onComplete() {
                        if (swLoadMsg!=null&&swLoadMsg.isRefreshing()) {
                            swLoadMsg.setRefreshing(false);
                        }
                        int i = newMsgAdapters.getData().size() % 20;
                        if (i != 0 && newMsgAdapters.getData().size() != 0) {
                            if (newMsgAdapters.isLoading()) {
                                //加载结束,没有更多数据
                                newMsgAdapters.loadMoreEnd();
                            }
                        } else {
                            //有更多的数据
                            if (newMsgAdapters.isLoading()) {
                                newMsgAdapters.loadMoreComplete();
                            }

                        }
                        super.onComplete();
                    }
                });
    }

    @OnClick({R.id.view_repaid_id, R.id.view_upkeep_id, R.id.view_polling_id, R.id.view_part_id})
    public void onViewClicked(View view) {
        Class cla = null;
        switch (view.getId()) {
            case R.id.view_repaid_id:
                cla = RepairOrderActivity.class;
                break;
            case R.id.view_upkeep_id:
                cla = UpkeepActivity.class;
                break;
            case R.id.view_polling_id:
                cla = PollingActivity.class;
                break;
            case R.id.view_part_id:
                cla = SparePartsActivity.class;
                break;
        }
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }

    //页码数
    private int pageNum = 1;

    /**
     * 获取工单详情Json数据
     *
     * @return
     */
    private String getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("pageNum", pageNum);
            jsonObject1.put("pageTotal", 20);
            jsonObject.put("page", jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }

}
