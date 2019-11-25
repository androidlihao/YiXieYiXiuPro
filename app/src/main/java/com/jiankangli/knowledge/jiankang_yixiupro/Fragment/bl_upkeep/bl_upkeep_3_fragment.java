package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_upkeep;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.upkeep_TemplateAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.upkeepBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.TemplateListBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.upkeepBlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.LogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Function;

/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :
 */
public class bl_upkeep_3_fragment extends BaseFragment {

    @BindView(R.id.btn_next_id)
    Button btnNextId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.pullLoadMoreRecyclerView)
    RecyclerView pullLoadMoreRecyclerView;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    private upkeep_TemplateAdapter templateAdapter;
    private List<upkeepBlBean.WorkTemplateVosBean> workTemplateVosBeans;
    private upkeepBlBean blBean;

    public static bl_upkeep_3_fragment newInstance(Bundle bundle) {
        bl_upkeep_3_fragment fragment = new bl_upkeep_3_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.enter_report_2_fragment_layout;
    }
    @Override
    public void onSupportVisible() {
        ((upkeepBackTrackingActivity) getActivity()).changeView();
        super.onSupportVisible();
    }
    @Override
    protected void initView() {
        blBean = ((upkeepBackTrackingActivity) getActivity()).blBean;
        workTemplateVosBeans = new ArrayList<>();
        templateAdapter = new upkeep_TemplateAdapter(workTemplateVosBeans);
        pullLoadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pullLoadMoreRecyclerView.setAdapter(templateAdapter);
    }

    @Override
    protected void initData() {
        //获取模板列表，然后设置模板,然后再填充值就可以了，因为此时的布局是稳定的
        final List<upkeepBlBean.WorkTemplateVosBean> workTemplateVos = blBean.getWorkTemplateVos();
        if (workTemplateVos!=null){
            //说明是有值得，已经保存了
            workTemplateVosBeans.addAll(workTemplateVos);
            templateAdapter.setNewData(workTemplateVosBeans);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        String templateCode = blBean.getTemplateCode();
        try {
            jsonObject.put("pageNum", 1);
            jsonObject.put("pageSize", 200);
            jsonObject.put("userId", SPUtils.get(getActivity(), "userId", -1 + ""));
            jsonObject.put("code", templateCode);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        RetrofitManager.create(ApiService.class)
                .getTemplateList(BaseJsonUtils.Base64String(jsonObject))
                .map(new Function<BaseEntity<List<TemplateListBean>>, List<upkeepBlBean.WorkTemplateVosBean>>() {
                    @Override
                    public List<upkeepBlBean.WorkTemplateVosBean> apply(BaseEntity<List<TemplateListBean>> listBaseEntity) throws Exception {
                        //先获取到布局，然后进行值的填充
                        ArrayList<upkeepBlBean.WorkTemplateVosBean> workTemplateVosBeans = new ArrayList<>();
                        if (listBaseEntity.isSuccess() && listBaseEntity.data != null) {
                            for (TemplateListBean templateListBean : listBaseEntity.data) {
                                upkeepBlBean.WorkTemplateVosBean workTemplateVosBean = new upkeepBlBean.WorkTemplateVosBean();
                                workTemplateVosBean.setTemplateId(templateListBean.getTemplateId());
                                workTemplateVosBean.setTemplateTypeId(templateListBean.getTemplateTypeId());
                                workTemplateVosBean.setTemplateInfoId(templateListBean.getTemplateInfoId());
                                workTemplateVosBean.setSubtitle(templateListBean.getSubtitle());
                                workTemplateVosBean.setTitle(templateListBean.getTitle());
                                ArrayList<upkeepBlBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean> objects = new ArrayList<>();
                                for (TemplateListBean.TemplateTypeListBean templateTypeListBean : templateListBean.getTemplateTypeList()) {
                                    //子模块
                                    upkeepBlBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean templateTypeAndTemplateVosBean
                                            = new upkeepBlBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean();
                                    templateTypeAndTemplateVosBean.setId(templateTypeListBean.getId());
                                    templateTypeAndTemplateVosBean.setName(templateTypeListBean.getName());
                                    objects.add(templateTypeAndTemplateVosBean);
                                }
                                workTemplateVosBean.setTemplateTypeAndTemplateVos(objects);
                                workTemplateVosBeans.add(workTemplateVosBean);
                            }
                        }
                        return workTemplateVosBeans;
                    }
                })
                .compose(RxSchedulers.<List<upkeepBlBean.WorkTemplateVosBean>>io2main())
                .subscribe(new RxSubscriber<List<upkeepBlBean.WorkTemplateVosBean>>() {
                    @Override
                    public void _onNext(List<upkeepBlBean.WorkTemplateVosBean> workTemplateVosBeans) {
                        templateAdapter.setNewData(workTemplateVosBeans);
                        blBean.setWorkTemplateVos(workTemplateVosBeans);
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        LogUtil.e(msg);
                    }

                });
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.btn_save_id, R.id.btn_next_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_id:
                //保存数据到对应的中
                save();
                break;
            case R.id.btn_next_id:
                start(bl_upkeep_4_fragment.newInstance());
                Log.i("TAG", "onViewClicked: ");
                break;
        }
    }

    /**
     * 保存数据
     */
    private void save() {
        //设置值
        try {
            SPUtils.put(getContext(), "bybl", GsonUtils.beanTojson(blBean));
            ToastUtil.showShortSafe("保存成功",getActivity());
        }catch (Exception e){
            ToastUtil.showShortSafe(e+"保存失败",getActivity());
        }
    }

}
