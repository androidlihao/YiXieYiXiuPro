package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.SpartRecordAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.repairBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :录入维修工单-备件使用记录
 */
public class bl_repair_4_fragment extends BaseFragment {


    @BindView(R.id.rc_PatrRecode_id)
    RecyclerView rcPatrRecodeId;
    @BindView(R.id.btn_addPartRecode_id)
    Button btnAddPartRecodeId;
    @BindView(R.id.sc)
    NestedScrollView sc;
    private int pos;

    private com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean blBean;
    private List<com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean.SparePartsBean> sparePartsBeans;
    private SpartRecordAdapter recordAadpter;

    public static bl_repair_4_fragment newInstance() {
        bl_repair_4_fragment fragment = new bl_repair_4_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    public void onSupportVisible() {
        ((repairBackTrackingActivity) getActivity()).changeTitle("录入维修工单");
        super.onSupportVisible();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_repair_4_fragment_layout;
    }

    @Override
    protected void initView() {
        blBean = ((repairBackTrackingActivity) getActivity()).blBean;
        initRecordList();

    }

    /**
     * 服务记录列表
     */
    private void initRecordList() {
        rcPatrRecodeId.setLayoutManager(new LinearLayoutManager(getActivity()));
        sparePartsBeans = blBean.getSpareParts() == null ? new ArrayList<blBean.SparePartsBean>() : blBean.getSpareParts();
        recordAadpter = new SpartRecordAdapter(R.layout.spart_record_item, sparePartsBeans);
        rcPatrRecodeId.setAdapter(recordAadpter);
        rcPatrRecodeId.setNestedScrollingEnabled(false);
        recordAadpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                pos = position;
                switch (view.getId()) {
                    case R.id.btn_delete_id:
                        DialogUtil.showPositiveDialog(getActivity(), "提示", "确认删除?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.remove(position);
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {

    }

    public void toNext() {
        List<blBean.SparePartsBean> data = recordAadpter.getData();
        for (int i = 0; i < data.size(); i++) {
            blBean.SparePartsBean sparePartsBean = data.get(i);
            String name = sparePartsBean.getAccessoryName();
            String no = sparePartsBean.getAccessoryNo();
            int number = sparePartsBean.getNumber();
            String accRemark = sparePartsBean.getAccRemark();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的备件名称", getContext());
                return;
            }
            if (TextUtils.isEmpty(no)) {
                ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的备件编号", getContext());
                return;
            }
            if (TextUtils.isEmpty(number + "")) {
                ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的数量", getContext());
                return;
            }
            if (TextUtils.isEmpty(accRemark + "")) {
                ToastUtil.showShortSafe("请选择第" + (i + 1) + "条记录的备注", getContext());
                return;
            }
        }
        blBean.setSpareParts(recordAadpter.getData());
        //跳转到下一界面
        start(bl_repair_5_fragment.newInstance());
    }

    @Override
    public void onDestroy() {
        ((repairBackTrackingActivity) getActivity()).blBean.setSpareParts(recordAadpter.getData());
        super.onDestroy();
    }


    @OnClick(R.id.btn_addPartRecode_id)
    public void onViewClicked() {
        recordAadpter.addData(new blBean.SparePartsBean());
        rcPatrRecodeId.scrollToPosition(recordAadpter.getData().size());
    }
}
