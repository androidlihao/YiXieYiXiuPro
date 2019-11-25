package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.view.AutoEdittext;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WriteMMBActivity extends BaseActivity {

    @BindView(R.id.tv_submitFeedback_id)
    TextView tvSubmitFeedbackId;
    @BindView(R.id.et_AutoEdittext_id)
    AutoEdittext etAutoEdittextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "我的留言");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @OnClick(R.id.tv_submitFeedback_id)
    public void onViewClicked() {
        //提交反馈
        final String content = etAutoEdittextId.getContentText();
        if (content.trim().isEmpty()) {
            ToastUtils.showToast(getApplicationContext(), "请先书写留言以后才能提交哦");
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", SPUtils.get(this, "userId", ""));
            jsonObject.put("name", SPUtils.get(this, "name", ""));
            jsonObject.put("chatId", getIntent().getIntExtra("ChatId",-1));
            jsonObject.put("headPicUrl", SPUtils.get(this, "headPicUrl", ""));
            jsonObject.put("content", content);
            RetrofitManager.create(ApiService.class)
                    .SubmitMMB(BaseJsonUtils.Base64String(jsonObject))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.<String>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            //进度条
                           commonLoading.show();
                        }
                        @Override
                        public void onNext(String s) {
                            Log.i("TAG", "onNext: "+s);
                            switch (GsonUtil.GsonCode(s)) {
                                case "success":
                                    ToastUtils.showToast(getApplicationContext(), "提交成功");
                                    finish();
                                    break;
                                case "error":
                                    ToastUtils.showToast(getApplicationContext(), GsonUtil.GsonMsg(s));
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(), "服务器或网络异常");
                        }

                        @Override
                        public void onComplete() {
                            //取消进度条
                            dialogdimiss();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
