package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.enter_report;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.TemplateAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.enterReportActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.TemplateListBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.LogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Function;

/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :
 */
public class enter_report_2_fragment extends BaseFragment {

    @BindView(R.id.btn_next_id)
    Button btnNextId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.pullLoadMoreRecyclerView)
    RecyclerView pullLoadMoreRecyclerView;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    Unbinder unbinder;
    private TemplateAdapter templateAdapter;
    private List<SingleMaintainOrderBean.WorkTemplateVosBean> workTemplateVosBeans;
    private SingleMaintainOrderBean singleMaintainOrderBean;

    public static enter_report_2_fragment newInstance() {
        enter_report_2_fragment fragment = new enter_report_2_fragment();
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
    protected void initView() {
        if (!((enterReportActivity) getActivity()).isFrist) {
            rlSaveId.setVisibility(View.GONE);
        }
        workTemplateVosBeans = new ArrayList<>();
        singleMaintainOrderBean = ((enterReportActivity) getActivity()).getSingleMaintainOrderBean();
        if (singleMaintainOrderBean.getWorkTemplateVos() != null) {
            workTemplateVosBeans = singleMaintainOrderBean.getWorkTemplateVos();
        }
        templateAdapter = new TemplateAdapter(workTemplateVosBeans);
        pullLoadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pullLoadMoreRecyclerView.setAdapter(templateAdapter);
    }

    @Override
    protected void initData() {
        if (workTemplateVosBeans != null && workTemplateVosBeans.size() != 0) {
            //是回显数据，不在使用布局请求接口
            return;
        }
        //获取模板列表，然后设置模板
        JSONObject jsonObject = new JSONObject();
        String templateCode = getActivity().getIntent().getExtras().getString("templateCode");
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
                .map(new Function<BaseEntity<List<TemplateListBean>>, List<SingleMaintainOrderBean.WorkTemplateVosBean>>() {
                    @Override
                    public List<SingleMaintainOrderBean.WorkTemplateVosBean> apply(BaseEntity<List<TemplateListBean>> listBaseEntity) throws Exception {
                        ArrayList<SingleMaintainOrderBean.WorkTemplateVosBean> workTemplateVosBeans = new ArrayList<>();
                        if (listBaseEntity.isSuccess() && listBaseEntity.data != null) {
                            for (TemplateListBean templateListBean : listBaseEntity.data) {
                                SingleMaintainOrderBean.WorkTemplateVosBean workTemplateVosBean = new SingleMaintainOrderBean.WorkTemplateVosBean();
                                workTemplateVosBean.setTitle(templateListBean.getTitle());
                                workTemplateVosBean.setSubtitle(templateListBean.getSubtitle());
                                workTemplateVosBean.setTemplateId(templateListBean.getTemplateId());
                                workTemplateVosBean.setTemplateTypeId(templateListBean.getTemplateTypeId());
                                workTemplateVosBean.setTemplateInfoId(templateListBean.getTemplateInfoId());
                                ArrayList<SingleMaintainOrderBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean> objects = new ArrayList<>();
                                for (TemplateListBean.TemplateTypeListBean templateTypeListBean : templateListBean.getTemplateTypeList()) {
                                    //子模块
                                    SingleMaintainOrderBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean templateTypeAndTemplateVosBean
                                            = new SingleMaintainOrderBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean();
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
                .compose(RxSchedulers.<List<SingleMaintainOrderBean.WorkTemplateVosBean>>io2main())
                .as(AutoDispose.<List<SingleMaintainOrderBean.WorkTemplateVosBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<List<SingleMaintainOrderBean.WorkTemplateVosBean>>() {
                    @Override
                    public void _onNext(List<SingleMaintainOrderBean.WorkTemplateVosBean> workTemplateVosBeans) {
                        templateAdapter.setNewData(workTemplateVosBeans);
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
                ((enterReportActivity) getActivity()).save();
                break;
            case R.id.btn_next_id:
                save();
                start(enter_report_3_fragment.newInstance());
                break;
        }
    }

    /**
     * 保存数据
     */
    private void save() {
        //设置值
        singleMaintainOrderBean.setWorkTemplateVos(templateAdapter.getData());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
