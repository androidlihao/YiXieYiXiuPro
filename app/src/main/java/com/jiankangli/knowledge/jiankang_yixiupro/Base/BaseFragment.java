package com.jiankangli.knowledge.jiankang_yixiupro.Base;

import android.app.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by 李浩 on 2018/6/21.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void getData();//

    protected abstract void updateListData();
    //获取宿主Activity
    protected Activity getHolding(){
        return getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,view);
        initView(view,savedInstanceState);
        return view;
    }

}
