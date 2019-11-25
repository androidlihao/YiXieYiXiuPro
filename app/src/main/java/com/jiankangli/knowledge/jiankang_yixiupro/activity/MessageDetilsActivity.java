package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MsgDetils;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MessageDetilsActivity extends BaseActivity {

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.tv_msg_content)
    TextView tvMsgContent;
    @BindView(R.id.tv_msg_time)
    TextView tvMsgTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "消息详情");
        getData();
    }

    private void getData() {
         try {
             JSONObject jsonObject=new JSONObject();
             jsonObject.put("msgId", getIntent().getIntExtra("id",-1));
             RetrofitManager.create(ApiService.class)
                     .getMsgDetils(BaseJsonUtils.Base64String(jsonObject))
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .as(AutoDispose.<MsgDetils>autoDisposable(
                             AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                     .subscribe(new Observer<MsgDetils>() {
                         @Override
                         public void onSubscribe(Disposable d) {
                             commonLoading.show();
                         }

                         @Override
                         public void onNext(MsgDetils msgDetils) {
                             switch (msgDetils.code){
                                 case "success":
                                     tvMsgContent.setText(msgDetils.getData().getContent());
                                     tvMsgTime.setText(msgDetils.getData().getSendTime());
                                     break;
                                 case "error":
                                     ToastUtils.showToast(getApplicationContext(),msgDetils.getMsg());
                                     break;
                             }
                         }

                         @Override
                         public void onError(Throwable e) {
                             ToastUtils.showToast(getApplicationContext(),"服务器或网络异常");
                         }

                         @Override
                         public void onComplete() {
                            commonLoading.dismiss();
                         }
                     });
         }catch (Exception e){
             e.printStackTrace();
         }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detils;
    }
}
