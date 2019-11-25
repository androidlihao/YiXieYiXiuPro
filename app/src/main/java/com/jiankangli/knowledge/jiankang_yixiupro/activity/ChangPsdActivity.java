package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangPsdActivity extends BaseActivity {

    @BindView(R.id.et_newPsd_id)
    EditText etNewPsdId;
    @BindView(R.id.et_newPsdToo_id)
    EditText etNewPsdTooId;
    @BindView(R.id.btn_submit_id)
    Button btnSubmitId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.AVLoadingIndicatorView)
    com.wang.avi.AVLoadingIndicatorView AVLoadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "重置密码");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chang_psd;
    }

    @OnClick(R.id.btn_submit_id)
    public void onViewClicked() {
        //获取密码
        String newPsd = etNewPsdId.getText().toString().trim();
        String newPsdToo = etNewPsdTooId.getText().toString().trim();
        if (newPsd.isEmpty() || newPsdToo.isEmpty()) {
            ToastUtils.showToast(getApplicationContext(), "密码不能为空");
            return;
        }
        if (newPsd.equals(newPsdToo)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("phoneNumber", getIntent().getStringExtra("phoneNumber"));
                jsonObject.put("passWord", newPsd);
                String jsonString = BaseJsonUtils.Base64String(jsonObject);
                RetrofitManager.create(ApiService.class).resetPsd(jsonString)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .as(AutoDispose.<String>autoDisposable(
                                AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                AVLoadingIndicatorView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onNext(String s) {
                                switch (GsonUtil.GsonCode(s)) {
                                    case "success":
                                        ToastUtils.showToast(getApplicationContext(), "密码更改成功，请登录");
                                        // ResetPsd resetPsd=GsonUtil.GsonToBean(s, ResetPsd.class);
                                        setResult(13);
                                        finish();
                                        break;
                                    case "error":
                                        ToastUtils.showToast(getApplicationContext(), GsonUtil.GsonMsg(s));
                                        break;
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                AVLoadingIndicatorView.setVisibility(View.GONE);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ToastUtils.showToast(getApplicationContext(), "两次密码不一致");

        }
    }
}
