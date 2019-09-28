package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.view.SignatureView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author lihao
 * @date 2019-09-26 17:11
 * @description :
 */
public class signActivity extends SupportActivity implements View.OnClickListener {
    @BindView(R.id.tv_cancel_id)
    TextView tvCancelId;
    @BindView(R.id.tv_reset_id)
    TextView tvResetId;
    @BindView(R.id.tv_save_id)
    TextView tvSaveId;
    @BindView(R.id.rl_head_id)
    LinearLayout rlHeadId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.signView)
    SignatureView signView;
    final public static String path= Environment.getExternalStorageDirectory()+"/yixiuPro/photos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_signature_layout);
        ButterKnife.bind(this);
        initStatuBar();
        initListtener();
    }

    private void initListtener() {
        tvResetId.setOnClickListener(this);
        tvSaveId.setOnClickListener(this);
        tvCancelId.setOnClickListener(this);
    }
    /**
     * 让状态栏颜色和toolbar颜色一致
     */
    private void initStatuBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_save_id:
                File file = new File(path,"sign"+ TimeUtil.getCurrentTime()+".png");
                boolean b = signView.SaveBitmapToFile(file);
                if (b){
                    Intent intent=new Intent();
                    intent.putExtra("sign",file.getPath());
                    setResult(201,intent);
                    finish();
                }
                break;
            case R.id.tv_reset_id:
                signView.reset();
                break;
            case R.id.tv_cancel_id:
                finish();
                break;
        }
    }
}
