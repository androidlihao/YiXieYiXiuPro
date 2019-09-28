package com.jiankangli.knowledge.jiankang_yixiupro.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * @author : chenxin
 * @date : 2019-01-16 16:17
 * @description : fragment 抽象基类
 */
public abstract class SimpleFragment extends SupportFragment {
    protected Context mContext;
    protected View mView;
    private TextView tvTitle;
    private Toolbar mToolbar;
    private ImageButton imgBack;
    private TextView rightText;
    private Unbinder mUnbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getBefore(savedInstanceState);
        mView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this,mView);
        setStatusBar();
        initView();
        initListener();
        initData();
        return mView;

    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setRightButton(String text, View.OnClickListener listener) {
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setOnClickListener(listener);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    /**
     * 放入布局资源Id
     *
     * @return 资源Id
     */
    protected abstract int getLayoutId();

    /**
     * 设置状态栏
     * （建议配合StatusBarUtil实现各种状态栏效果）
     */
    protected void setStatusBar() {
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听事件
     */
    protected abstract void initListener();

    /**
     * 在初始化布局之前，获取跳转页面参数的方法
     *
     * @param savedInstanceState 携带参数
     */
    public void getBefore(Bundle savedInstanceState) {
    }

}
