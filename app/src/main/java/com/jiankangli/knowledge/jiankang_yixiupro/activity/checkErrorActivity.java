package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * @author lihao
 * @date 2019-09-28 20:00
 * @description :失败原因
 */
public class checkErrorActivity extends BaseActivity {
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.tv_reason_id)
    TextView tvReasonId;
    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_error_reason_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this,"失败原因");
        id = (String) getIntent().getExtras().get("id");
        initData();
    }

    private void initData() {
        RetrofitManager.create(ApiService.class)
                .getFailureCause(getJson())
                .compose(RxSchedulers.<BaseEntity>io2main())
                .as(AutoDispose.<BaseEntity>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity baseEntity) {
                        if (baseEntity.isSuccess()){
                            try {
                                String string = baseEntity.data.toString();
                                JSONObject jsonObject = new JSONObject(string);
                                tvReasonId.setText(jsonObject.getString("failureCause"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg,getApplicationContext());
                    }
                });
    }
    /**
     * 获取工单详情Json数据
     *
     * @return
     */
    private String getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtil.getInstance(getApplicationContext()).getString("userId"));
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }
}
