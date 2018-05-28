package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Login;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.JsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.RegexUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SharePreferenceUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.view.LoadingDialog;

import org.json.JSONObject;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//登录界面
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_phone_id)
    EditText etPhoneId;
    @BindView(R.id.et_psd_id)
    EditText etPsdId;
    @BindView(R.id.bt_login)
    TextView btLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    private LoadingDialog dialog;
    private boolean login;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //判断是否登录了，如果对登录，直接跳转到主界面
        if (isLogin()){//如果已经登录就直接跳转，没有登录就算了
            loginMain();
        }
        //绑定初始化ButterKnife
        bind = ButterKnife.bind(this);
    }

    private void loginMain() {
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();//杀死当前界面
    }

    @Override
    protected void onStart() {
       dialog = new LoadingDialog(this);
       dialog.setCanceledOnTouchOutside(false);
       super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //界面销毁，网络状态取消
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();//解绑

    }

    @OnClick(R.id.bt_login)
    public void onBtLoginClicked() {
        //开始执行登录操作
        String phone=etPhoneId.getText().toString();//手机号
        String psd=etPsdId.getText().toString();//密码
        if (phone.isEmpty()||psd.isEmpty()){
            ToastUtils.showToast(this,"手机号或密码不能为空");
            return;
        }
        if (!RegexUtil.isMobileNumber(phone)){//手机号码不正常
            etPhoneId.setError("手机号码格式不正确，请从新输入");
            return;
        }
        login(phone,psd);
    }
    //登录操作
    private void login(String phone, String psd) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("phoneNumber",phone);
            jsonObject.put("password",psd);
            jsonObject.put("type", "1");
            String loginData=JsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class)
                    .sendLogin(loginData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())//回到主线程
                    .subscribe(new Observer<String>() {
                       @Override
                       public void onSubscribe(Disposable d) {
                           Log.i("TAG", "onSubscribe: ");
                           //开始登录动画
                           dialog.show();
                       }
                       @Override
                       public void onNext(final String string) {
                           //手动解析
                           Log.i("TAG", "onNext: "+string);//请求网络成功
                           Observable.timer(1500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                               @Override
                               public void accept(Long aLong) throws Exception {
                                         dialog.dismiss();
                                   switch (GsonUtil.GsonCode(string)){
                                       case "success":
                                           Login login= GsonUtil.GsonToBean(string,Login.class);
                                           //将相关数据存到手机上
                                           SharePreferenceUtils.put(getApplicationContext(),"psw",login.getData().getPassword());
                                           SharePreferenceUtils.put(getApplicationContext(),"userId",login.getData().getUserId());
                                           SharePreferenceUtils.put(getApplicationContext(),"name",login.getData().getUserName());
                                           SharePreferenceUtils.put(getApplicationContext(),"phone",login.getData().getPhoneNumber());
                                           SharePreferenceUtils.put(getApplicationContext(),"headPicUrl",login.getData().getHeadPicUrl());
                                           SharePreferenceUtils.put(getApplicationContext(),"status",login.getData().getStatus());
                                           SharePreferenceUtils.put(getApplicationContext(),"isLogin",true);
                                           //跳转到主界面
                                           ToastUtils.showToast(getApplicationContext(),"登录成功！");
                                           loginMain();
                                           break;
                                       case "error":
                                           ToastUtils.showToast(getApplicationContext(),GsonUtil.GsonMsg(string));//登录失败
                                           break;
                                   }
                               }
                           });
                       }

                       @Override
                       public void onError(Throwable e) {
                           Observable.timer(1500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                               @Override
                               public void accept(Long aLong) throws Exception {
                                   dialog.dismiss();
                                   ToastUtils.showToast(getApplicationContext(),"服务器或网络异常！");
                               }
                           });
                           Log.i("TAG", "onError: "+e);
                       }

                       @Override
                       public void onComplete() {
                           Log.i("TAG", "onComplete: ");
                           //停止登录动画
                       }
                   });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_forget)
    public void onTvForgetClicked() {
        //忘记密码，跳转到页面 12
        Intent intent=new Intent(this,ForgetActivity.class);
        startActivityForResult(intent,12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        if (requestCode==resultCode&&requestCode==12){
           // finish();//结束当前界面
            Log.i("TAG", "onActivityResult: "+"收到数据");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLogin() {
        login= (boolean) SharePreferenceUtils.get(getApplicationContext(),"isLogin",false);
        return login;
    }
}
