package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePsd2Activity extends BaseActivity {

    @BindView(R.id.set_et_psw)
    EditText setEtPsw;//原密码
    @BindView(R.id.set_et_new_psw)
    EditText setEtNewPsw;//新密码
    @BindView(R.id.set_et_psw_more)
    EditText setEtPswMore;//确认密码
    @BindView(R.id.set_submit)
    TextView setSubmit;
    private String psd;
    private String newPsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "修改密码");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_psd2;
    }

    @OnClick(R.id.set_submit)
    public void onViewClicked() {
        //获取
        psd = setEtPsw.getText().toString().trim();
        newPsd = setEtNewPsw.getText().toString().trim();
        String affnewPsd=setEtPswMore.getText().toString().trim();
        if (psd.isEmpty()|| newPsd.isEmpty()||affnewPsd.isEmpty()){
            ToastUtils.showToast(getApplicationContext(),"密码不能为为空!");
            return;
        }
        if (!newPsd.equals(affnewPsd)){
            ToastUtils.showToast(getApplicationContext(),"两次密码不一致!");
            return;
        }
        //执行修改操作
        change();
    }

    private void change() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SharePreferenceUtils.get(this,"userId", ""));
            jsonObject.put("oldPassWord",psd );
            jsonObject.put("newPassWord",newPsd);
            String jsonString = BaseJsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class)
                    .changePsd(jsonString)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.<String>bindToLifecycle())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            commonLoading.show();
                        }

                        @Override
                        public void onNext(String s) {
                            dialogdimiss();
                            switch (GsonUtil.GsonCode(s)){
                                case "success":
                                    ToastUtils.showToast(getApplicationContext(),"修改成功");
                                    finish();
                                    break;
                                case "error":
                                    ToastUtils.showToast(getApplicationContext(),GsonUtil.GsonMsg(s));
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(),"网络或服务器异常!");
                            dialogdimiss();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
