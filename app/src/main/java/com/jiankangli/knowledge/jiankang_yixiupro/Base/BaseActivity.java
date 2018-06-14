package com.jiankangli.knowledge.jiankang_yixiupro.Base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.view.CommonLoading;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseActivity extends RxAppCompatActivity {

    @Nullable
    protected Toolbar toolbar;
    private Unbinder bind;
    private ExitBaseActivity_Broad exitBaseActivity_broad;
    public CommonLoading commonLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(layoutId);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏显示
        bind = ButterKnife.bind(this);
        initStatuBar();
        initToolbar();
        initLoading();
        //动态注册广播
        exitBaseActivity_broad = new ExitBaseActivity_Broad();
        IntentFilter intentFilter=new IntentFilter("drc.xxx.yyy.baseActivity");
        registerReceiver(exitBaseActivity_broad,intentFilter);
    }

    private void initLoading() {
        commonLoading = new CommonLoading(this, R.style.loadingDialog);
    }
    public void dialogdimiss(){
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        commonLoading.dismiss();
                        finish();
                    }
                });
    }
    //让状态栏颜色和toolbar颜色一致
    private void initStatuBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | localLayoutParams.flags);
        }
    }

    //初始化Toolbar
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_id);
        if (toolbar==null){
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//留下返回键
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //动态设置标题
    protected void addMiddleTitle(Context context, CharSequence title) {
        TextView textView = new TextView(context);
        textView.setTag("title");
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        textView.setTextSize(16);
        textView.setText(title);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        toolbar.addView(textView, params);
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();//解绑
        unregisterReceiver(exitBaseActivity_broad);//注销广播
    }
    //定义一个广播接收者
    public class  ExitBaseActivity_Broad extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

             int closeAll=intent.getIntExtra("closeAll",0);
             Log.i("TAG", "onReceive: "+closeAll);
             if (closeAll==1){
                 finish();//销毁baseActivity
             }
        }
    }

}
