package com.jiankangli.knowledge.jiankang_yixiupro.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;



/**
 * @author : chenxin
 * @date : 2019-01-16 16:17
 * @description : fragment基类
 */
public abstract class BaseFragment<P extends BasePresenter> extends SimpleFragment implements BaseView {


    public P mPresenter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CreatePresenter();
        if (mPresenter != null) {
            mPresenter.bindView(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unbindView();
        }
        super.onDestroy();
    }

    @Override
    public void showMessageDialog(String msg) {
        DialogUtil.showConfirmDialog(mContext, msg);
    }

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = DialogUtil.showProgressDialog(mContext, "", msg);
        } else {
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 注册Fragment到Dagger
     */
    protected abstract void CreatePresenter();
}
