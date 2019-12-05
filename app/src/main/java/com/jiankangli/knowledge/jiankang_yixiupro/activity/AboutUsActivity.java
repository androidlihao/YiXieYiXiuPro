package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.BuildConfig;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.tv_AboutUs_id)
    TextView tvAboutUsId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.textView)
    TextView textView;
    private Object data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "关于我们");
        getDatas();
    }

    private void setData(String Abouts) {
        tvAboutUsId.setText(Abouts);
        tvAboutUsId.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setText("版本号:v"+ BuildConfig.VERSION_NAME);
    }

    private void getDatas() {
        String AboutUs = SPUtils.get(this, "aboutUs", "").toString();
        if (AboutUs.trim().isEmpty()) {
            RetrofitManager.create(ApiService.class)
                    .getContents()
                    .compose(RxSchedulers.<String>io2main())
                    .as(AutoDispose.<String>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            //开始的一些准备工作
                        }

                        @Override
                        public void onNext(String s) {
                            switch (GsonUtil.GsonCode(s)) {
                                case "success":
                                    //展示出来
                                    try {
                                        String aboutContent = GsonUtil.GsonJsonObject(s, "data").get("content").toString();
                                        setData(aboutContent);
                                        SPUtils.put(getApplicationContext(), "aboutUs", aboutContent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "error":

                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            setData(AboutUs);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }


}
