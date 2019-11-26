package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_history_Adapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.Constant.DbConstant;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.historyBean;
import com.jiankangli.knowledge.jiankang_yixiupro.gen.historyBeanDao;
import com.jiankangli.knowledge.jiankang_yixiupro.greendao.GreenDaoContext;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GreenDaoUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lihao
 * @date 2019-11-26 11:04
 * @description :搜索界面
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.et_search_id)
    EditText etSearchId;
    @BindView(R.id.tv_cancel_id)
    TextView tvCancelId;
    @BindView(R.id.rl_head_id)
    LinearLayout rlHeadId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.rc_history_id)
    RecyclerView rcHistoryId;
    private GreenDaoUtil greenDaoUtil;
    private Recycler_history_Adapter adapter;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.search_layout;
    }

    @Override
    public void initToolbar() {
        type = getIntent().getExtras().getString("type");
        greenDaoUtil = GreenDaoUtil.getInstance(new GreenDaoContext(getApplicationContext(), DbConstant.HISTORY_DB));
        etSearchId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    String deviceNo = v.getText().toString().trim();
                    if (!TextUtils.isEmpty(deviceNo)){
                        //插入数据库
                        historyBean historyBean = new historyBean();
                        historyBean.setSearchText(deviceNo);
                        historyBean.setType(type);
                        List<com.jiankangli.knowledge.jiankang_yixiupro.bean.historyBean> list = greenDaoUtil.getDaoSession().getHistoryBeanDao().queryBuilder().
                                where(historyBeanDao.Properties.SearchText.eq(deviceNo)
                                ,historyBeanDao.Properties.Type.eq(type)).list();
                        if (list.size()==0){
                            Boolean insert = greenDaoUtil.insert(historyBean);
                            queryDb();
                        }
                        startSearchResult(historyBean);
//                        getData(deviceNo);
                    }
                    /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }
                return handled;
            }
        });
        initHistory();
    }

    /**
     * 开始跳转到搜索页面搜结果
     * @param historyBean
     */
    private void startSearchResult(historyBean historyBean) {
        Intent intent=new Intent(this,SearchResultActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("history",historyBean);
        startActivity(intent);
    }

    /**
     * 初始化历史数据
     */
    private void initHistory() {
        rcHistoryId.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Recycler_history_Adapter(R.layout.item_history);
        rcHistoryId.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (view.getId()==R.id.iv_delete_id){
                    historyBean item = adapter.getItem(position);
                    adapter.remove(position);
                    greenDaoUtil.getDaoSession().getHistoryBeanDao().delete(item);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                startSearchResult(adapter.getData().get(position));
            }
        });
        queryDb();
    }

    /**
     * 查询历史记录数据库
     */
    private void queryDb() {
        List<historyBean> historyBeans = greenDaoUtil.getDaoSession().getHistoryBeanDao().
                queryBuilder().where(historyBeanDao.Properties.Type.eq(type)).list();
        adapter.setNewData(historyBeans);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_cancel_id)
    public void onViewClicked() {
        finish();
    }
}
