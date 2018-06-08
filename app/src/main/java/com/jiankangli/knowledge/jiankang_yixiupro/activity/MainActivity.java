package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.zhouwei.library.CustomPopWindow;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Status;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.youth.banner.Banner;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener{

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
    private CustomPopWindow popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "医械医修+Pro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
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
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_bag,null);
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
        switch (v.getId()){
            case R.id.tv_busy:
                //执行更改状态，然后根据状态修改
                ivOnlineStatuId.setImageResource(R.mipmap.busy);
                popWindow.dissmiss();
                changeStatus("2");
                break;
            case R.id.tv_msg:
                break;
            case R.id.tv_online:
                ivOnlineStatuId.setImageResource(R.mipmap.online);
                popWindow.dissmiss();
                changeStatus("1");
                break;
            case R.id.tv_person:
                Intent intent=new Intent(this,PersonalActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void changeStatus(final String statu) {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId", SharePreferenceUtils.get(getApplicationContext(),"userId",-1+""));
            jsonObject.put("status",statu);
            String string=BaseJsonUtils.Base64String(jsonObject);
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
                            SharePreferenceUtils.put(getApplicationContext(),"status",status.data.status);

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(),"服务器或网络异常！");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
