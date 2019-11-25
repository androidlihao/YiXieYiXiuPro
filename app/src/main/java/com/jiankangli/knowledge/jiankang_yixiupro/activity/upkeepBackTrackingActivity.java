package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_upkeep.bl_upkeep_1_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_upkeep.bl_upkeep_2_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.upkeepBlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;

/**
 * @author lihao
 * @date 2019-11-09 18:17
 * @description :保养补录
 */
public class upkeepBackTrackingActivity extends BaseActivity {
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    public upkeepBlBean blBean;
    public String templateCode;
    @BindView(R.id.tv_entering_id)
    TextView tvEnteringId;

    @Override
    protected int getLayoutId() {
        return R.layout.enter_report_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "录入报告");
        initFragment();
        //判断是否有保存数据,如果又保存数据的存在，直接搜索
    }

    public void changeView() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 2) {
            tvEnteringId.setVisibility(View.VISIBLE);
        } else {
            tvEnteringId.setVisibility(View.GONE);
        }
    }


    private void initFragment() {
        if (findFragment(bl_upkeep_1_fragment.class) == null) {
            // 加载根Fragment
            loadRootFragment(R.id.fl_content, bl_upkeep_1_fragment.newInstance());
            //全局改变Fragment的动画
            setFragmentAnimator(new DefaultHorizontalAnimator());
        }
    }

    public void setBlBean(upkeepBlBean blBean) {
        this.blBean = blBean;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeDialog();
                break;
        }
        return true;
    }

    private void closeDialog() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            DialogUtil.showPositiveDialog(this, "警告", "关闭后，您填写的内容将会丢失", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.finishAfterTransition(upkeepBackTrackingActivity.this);
                }
            });
        }
    }

    @Override
    public void onBackPressedSupport() {
        closeDialog();
    }

    @OnClick(R.id.tv_entering_id)
    public void onViewClicked() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 2) {
            ((bl_upkeep_2_fragment) getSupportFragmentManager().getFragments().get(1)).toNext();
        }
    }
}
