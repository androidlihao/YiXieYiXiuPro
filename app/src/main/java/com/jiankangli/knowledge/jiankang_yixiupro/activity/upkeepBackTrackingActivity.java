package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair.bl_repair_1_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair.bl_repair_2_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair.bl_repair_3_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair.bl_repair_4_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean;
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
    @BindView(R.id.tv_entering_id)
    TextView tvEnteringId;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    public blBean blBean;

    @Override
    protected int getLayoutId() {
        return R.layout.enter_report_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeTitle("录入报告");
        initFragment();
        //得到请求参数
        blBean = new blBean();
    }

    public void changeTitle(String title) {
        addMiddleTitle(this, title);
        if (getSupportFragmentManager().getBackStackEntryCount()==8) {
            tvEnteringId.setVisibility(View.INVISIBLE);
        }else {
            tvEnteringId.setVisibility(View.VISIBLE);
        }
    }

    private void initFragment() {
        if (findFragment(bl_repair_1_fragment.class) == null) {
            // 加载根Fragment
            loadRootFragment(R.id.fl_content, bl_repair_1_fragment.newInstance());
            //全局改变Fragment的动画
            setFragmentAnimator(new DefaultHorizontalAnimator());
        }
    }

    @OnClick(R.id.tv_entering_id)
    public void onViewClicked() {
        //下一步
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 1){
            //获取信息
            bl_repair_1_fragment bl_repair_1_fragment = (bl_repair_1_fragment) getSupportFragmentManager().getFragments().get(0);
            //判断是否有值
            bl_repair_1_fragment.toNext();
        }else if (backStackEntryCount==2){
            //获取信息
            bl_repair_2_fragment bl_repair_2_fragment = (bl_repair_2_fragment) getSupportFragmentManager().getFragments().get(1);
            //判断是否有值
            bl_repair_2_fragment.toNext();
        }else if (backStackEntryCount>2&&backStackEntryCount<7){
            bl_repair_3_fragment bl_repair_3_fragment = (bl_repair_3_fragment) getSupportFragmentManager().getFragments().get(backStackEntryCount-1);
            //判断是否有值
            bl_repair_3_fragment.toNext();
        }else if (backStackEntryCount==7){
            //获取信息
            bl_repair_4_fragment bl_repair_4_fragment = (bl_repair_4_fragment) getSupportFragmentManager().getFragments().get(backStackEntryCount-1);
            //判断是否有值
            bl_repair_4_fragment.toNext();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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
                break;
        }
        return true;
    }
}
