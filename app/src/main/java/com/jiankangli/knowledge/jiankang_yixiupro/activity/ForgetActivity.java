package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.AutoCode;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.JsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.RegexUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForgetActivity extends BaseActivity {

    @BindView(R.id.et_numPhone_id)
    EditText etNumPhoneId;
    @BindView(R.id.et_Auto_id)
    EditText etAutoId;
    @BindView(R.id.btn_AutoCode_id)
    TextView btnAutoCodeId;
    @BindView(R.id.btn_next_id)
    Button btnNextId;
    private String vcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "忘记密码");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;//setContentView方法已经在父类中实现了
    }

    @OnClick(R.id.btn_next_id)
    public void onBtnNextIdClicked() {
        //比对验证码是否正确
        String Etautocode=etAutoId.getText().toString().trim();
        if (etNumPhoneId.getText().toString().trim().isEmpty()){
            ToastUtils.showToast(getApplicationContext(),"请输入手机号码");
            return;
        }
        if (Etautocode.isEmpty()){
            ToastUtils.showToast(getApplicationContext(),"验证码不能为空");
            return;
        }
        if (Etautocode.equals(vcode)){
            Intent intent=new Intent(this,ChangPsdActivity.class);//跳转到修改界面
            intent.putExtra("phoneNumber",etNumPhoneId.getText().toString().trim());
            startActivityForResult(intent,13);

        }else{
            ToastUtils.showToast(getApplicationContext(),"请仔细检查验证码");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==13&&requestCode==resultCode){
            //结束当前页面，并将数据传回上一个界面
            setResult(12);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_AutoCode_id)
    public void onBtnAutoCodeIdClicked() {
        //获取手机号码
        final String numberphone=etNumPhoneId.getText().toString().trim();
        if (numberphone.isEmpty()){
            ToastUtils.showToast(this,"请输入手机号以后再获取验证码");
            return;
        }
        if (!RegexUtil.isMobileNumber(numberphone)){
            ToastUtils.showToast(this,"手机号码格式不正确");
            return;
        }
        //开始获取手机验证码
        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())//最后回到主线程
                .doOnSubscribe(new Consumer<Disposable>() {//开始异步操作之前的准备工作
            @SuppressLint("ResourceAsColor")
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.i("TAG", "accept: ");
                   btnAutoCodeId.setEnabled(false);
                   btnAutoCodeId.setBackgroundColor(R.color.colorgray);

            }
        })
                .compose(this.<Long>bindToLifecycle())//管理Rxjava的生命周期
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //开始执行网络操作
                        getCheckCode(numberphone);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        //消除当前界面的时候，依旧在执行，当最后执行的时候发现这个控件没有了
                        Log.i("TAG", "Long: "+aLong);
                        btnAutoCodeId.setText("重新获取"+(59-aLong)+"s");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(getApplicationContext(),"服务器或网络异常!");
                    }

                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onComplete(){
                        btnAutoCodeId.setBackgroundColor(Color.parseColor("#A5D452"));
                        btnAutoCodeId.setEnabled(true);//从新变得可以点击
                        btnAutoCodeId.setText("重新获取");
                        //60秒之后，验证码过期
                    }
                });
    }

    private void getCheckCode(String numberphone) {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("phoneNumber",numberphone);
            jsonObject.put("type","3");
             String string = JsonUtils.Base64String(jsonObject);
            RetrofitManager.create(ApiService.class).getCode(string)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            Log.i("TAG", "onNext: "+s);
                         //获取数据成功
                            switch (GsonUtil.GsonCode(s)){
                                case "success":
                                    ToastUtils.showToast(getApplicationContext(),"验证码获取成功");//登录失败
                                    AutoCode autoCode=GsonUtil.GsonToBean(s, AutoCode.class);
                                    vcode = autoCode.data.vcode;//得到验证码
                                    break;
                                case "error":
                                    ToastUtils.showToast(getApplicationContext(),GsonUtil.GsonMsg(s));//获取失败
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast(getApplicationContext(),"服务器或网络异常！");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
