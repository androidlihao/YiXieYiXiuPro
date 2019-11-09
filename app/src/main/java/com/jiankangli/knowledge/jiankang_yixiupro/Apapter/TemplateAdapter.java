package com.jiankangli.knowledge.jiankang_yixiupro.Apapter;


import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.jiankangli.knowledge.jiankang_yixiupro.Listeners.TextListener;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-10-24 16:28
 * @description :模板适配器
 */
public class TemplateAdapter extends BaseQuickAdapter<SingleMaintainOrderBean.WorkTemplateVosBean, BaseViewHolder> {


    public TemplateAdapter(@Nullable List<SingleMaintainOrderBean.WorkTemplateVosBean> data) {
        super(data);
        //Step.1
        setMultiTypeDelegate(new MultiTypeDelegate<SingleMaintainOrderBean.WorkTemplateVosBean>() {
            @Override
            protected int getItemType(SingleMaintainOrderBean.WorkTemplateVosBean entity) {
                //根据你的实体类来判断布局类型
                return entity.getTemplateTypeId();
            }
        });
        //Step.2
        getMultiTypeDelegate()
                .registerItemType(1, R.layout.item_mb1_layout)
                .registerItemType(2, R.layout.item_mb2_layout)
                .registerItemType(3, R.layout.item_mb3_layout)
                .registerItemType(4, R.layout.item_mb4_layout)
                .registerItemType(5, R.layout.item_mb5_layout)
                .registerItemType(6, R.layout.item_mb6_layout)
                .registerItemType(7, R.layout.item_mb7_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, final SingleMaintainOrderBean.WorkTemplateVosBean item) {
        //得到当前操作项的位置
        final int layoutPosition = helper.getLayoutPosition();
        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.tv_title_id, title);
        }
        //如果为模块1
        List<TemplateTypeAndTemplateVosBean> templateTypeAndTemplateVos = item.getTemplateTypeAndTemplateVos();
        for (final TemplateTypeAndTemplateVosBean templateTypeAndTemplateVo : templateTypeAndTemplateVos) {
            //获取到模块1的子模块
            if (TextUtils.isEmpty(templateTypeAndTemplateVo.getName())) {
                continue;
            }
            if (item.getTemplateTypeId() == 7) {
                //如果当前为模板7
                if (templateTypeAndTemplateVo.getName().equals("Type(K)")) {
                    templateTypeAndTemplateVo.setName("型号(K)");
                }
            }
            switch (templateTypeAndTemplateVo.getName()) {
                case "AN(V)":
                    boolean anv = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!anv) {
                        helper.setText(R.id.et_anv_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "BN(V)":
                    boolean bnv = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!bnv) {
                        helper.setText(R.id.et_bnv_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "CN(V)":
                    boolean cnv = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!cnv) {
                        helper.setText(R.id.et_cnv_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "温度(℃)":
                    boolean wd = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!wd) {
                        helper.setText(R.id.et_wd_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "湿度(%)":
                    boolean sd = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!sd) {
                        helper.setText(R.id.et_sd_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "Type(K)":
                    boolean type = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!type) {
                        helper.setText(R.id.et_type_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "含量(%)":
                    boolean hl = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!hl) {
                        helper.setText(R.id.et_hl_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "曝光记录(mAs)":
                    boolean bgjl = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!bgjl) {
                        helper.setText(R.id.et_bgjl_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;
                case "结果":
                    //结果设置
                    String jg = templateTypeAndTemplateVo.getContent();
                    if (jg == null) {
                        helper.setChecked(R.id.cb_id, false);
                    } else {
                        switch (jg) {
                            case "0":
                                helper.setChecked(R.id.cb_id, false);
                                break;
                            case "1":
                                helper.setChecked(R.id.cb_id, true);
                                break;
                        }
                    }
                    //给每个结果设置一个监听值
                    helper.setOnCheckedChangeListener(R.id.cb_id, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                //如果选中了，那么当前cb为1
                                templateTypeAndTemplateVo.setContent("1");
                            } else {
                                templateTypeAndTemplateVo.setContent("0");
                            }
                        }
                    });
                    break;
                case "型号(K)":
                    //型号
                    boolean xh = TextUtils.isEmpty(templateTypeAndTemplateVo.getContent());
                    if (!xh) {
                        helper.setText(R.id.et_xh_id, templateTypeAndTemplateVo.getContent());
                    }
                    break;

            }
        }
        switch (item.getTemplateTypeId()) {
            case 1:
                EditText et_anv_id = helper.getView(R.id.et_anv_id);
                EditText et_bnv_id = helper.getView(R.id.et_bnv_id);
                EditText et_cnv_id = helper.getView(R.id.et_cnv_id);
                setTextChangeListener(et_anv_id, layoutPosition);
                setTextChangeListener(et_bnv_id, layoutPosition);
                setTextChangeListener(et_cnv_id, layoutPosition);
                break;
            case 2:
                EditText et_wd_id = helper.getView(R.id.et_wd_id);
                EditText et_sd_id = helper.getView(R.id.et_sd_id);
                setTextChangeListener(et_wd_id, layoutPosition);
                setTextChangeListener(et_sd_id, layoutPosition);
                break;
            case 3:
            case 4:
                for (TemplateTypeAndTemplateVosBean templateTypeAndTemplateVo : templateTypeAndTemplateVos) {
                    //获取到模块3,模块4的子模块,如果当前的值为标题为空，那么为这个空的edittext
                    if (TextUtils.isEmpty(templateTypeAndTemplateVo.getName())) {
                        helper.setText(R.id.et_id, templateTypeAndTemplateVo.getContent());
                        //设置监听
                        setTextChangeListener((EditText) helper.getView(R.id.et_id), layoutPosition);
                    }
                }
                break;
            case 5:
                String subtitle = item.getSubtitle();
                if (!TextUtils.isEmpty(subtitle)) {
                    helper.setText(R.id.tv_fbt_id, subtitle);
                }
                break;
            case 6:
                EditText et_type_id = helper.getView(R.id.et_type_id);
                EditText et_hl_id = helper.getView(R.id.et_hl_id);
                setTextChangeListener(et_type_id, layoutPosition);
                setTextChangeListener(et_hl_id, layoutPosition);
                break;
            case 7:
                EditText et_xh_id = helper.getView(R.id.et_xh_id);
                setTextChangeListener(et_xh_id, layoutPosition);
                break;
            default:
                break;
        }
    }

    private void setTextChangeListener(EditText editText, final int layoutPosition) {
        editText.addTextChangedListener(new TextListener(editText.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                //得到所有的小布局值
                List<TemplateTypeAndTemplateVosBean> templateTypeAndTemplateVos = getData().get(layoutPosition).getTemplateTypeAndTemplateVos();
                switch (layoutId) {
                    case R.id.et_wd_id:
                        change(templateTypeAndTemplateVos, "温度(℃)", s);
                        break;
                    case R.id.et_sd_id:
                        change(templateTypeAndTemplateVos, "湿度(%)", s);
                        break;
                    case R.id.et_anv_id:
                        change(templateTypeAndTemplateVos, "AN(V)", s);
                        break;
                    case R.id.et_bnv_id:
                        change(templateTypeAndTemplateVos, "BN(V)", s);
                        break;
                    case R.id.et_cnv_id:
                        change(templateTypeAndTemplateVos, "CN(V)", s);
                        break;
                    case R.id.et_id:
                        //此时的type为null或者空
                        change(templateTypeAndTemplateVos, null, s);
                        break;
                    case R.id.et_type_id:
                        change(templateTypeAndTemplateVos, "Type(K)", s);
                        break;
                    case R.id.et_hl_id:
                        change(templateTypeAndTemplateVos, "含量(%)", s);
                        break;
                    case R.id.et_xh_id:
                        change(templateTypeAndTemplateVos, "型号(K)", s);
                        break;
                    case R.id.et_bgjl_id:
                        change(templateTypeAndTemplateVos, "曝光记录(mAs)", s);
                        break;
                }
            }
        });
    }

    private void change(List<TemplateTypeAndTemplateVosBean> templateTypeAndTemplateVos, String type, Editable s) {
        for (TemplateTypeAndTemplateVosBean templateTypeAndTemplateVo : templateTypeAndTemplateVos) {
            //根据不同的名称，设置不同的值
            if (TextUtils.isEmpty(templateTypeAndTemplateVo.getName()) && type == null) {
                //如果当前为空的话
                templateTypeAndTemplateVo.setContent(s.toString());
                break;
            }
            if (TextUtils.equals(templateTypeAndTemplateVo.getName(), type)) {
                templateTypeAndTemplateVo.setContent(s.toString());
                break;
            }
        }
    }


}
