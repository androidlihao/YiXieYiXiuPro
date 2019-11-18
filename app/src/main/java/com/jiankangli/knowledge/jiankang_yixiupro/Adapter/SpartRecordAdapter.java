package com.jiankangli.knowledge.jiankang_yixiupro.Adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiankangli.knowledge.jiankang_yixiupro.Listeners.TextListener;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.fixRecordBean;

import java.util.List;


/**
 * @author lihao
 * @date 2019-09-28 15:44
 * @description :备件使用记录
 */
public class SpartRecordAdapter extends BaseQuickAdapter<blBean.SparePartsBean, BaseViewHolder> {

    public SpartRecordAdapter(int layoutResId, @Nullable List<blBean.SparePartsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final blBean.SparePartsBean item) {
        final int layoutPosition = helper.getLayoutPosition();
        helper.addOnClickListener(R.id.btn_delete_id);
        helper.setText(R.id.et_spartName_id, TextUtils.isEmpty(item.getAccessoryName())?"":item.getAccessoryName());
        helper.setText(R.id.et_spartNo_id, TextUtils.isEmpty(item.getAccessoryNo())?"":item.getAccessoryNo());
        int number1 = item.getNumber();
        helper.setText(R.id.et_count_id, number1+"");
        helper.setText(R.id.et_remark_id, TextUtils.isEmpty(item.getAccRemark())?"":item.getAccRemark());

        final EditText name = helper.getView(R.id.et_spartName_id);
        final EditText no = helper.getView(R.id.et_spartNo_id);
        final EditText number = helper.getView(R.id.et_count_id);
        final EditText remark = helper.getView(R.id.et_remark_id);
        name.addTextChangedListener(new TextListener(name.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                getData().get(layoutPosition).setAccessoryName(s.toString());
            }
        });
        no.addTextChangedListener(new TextListener(no.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                getData().get(layoutPosition).setAccessoryNo(s.toString());
            }
        });
        number.addTextChangedListener(new TextListener(name.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                getData().get(layoutPosition).setNumber(TextUtils.isEmpty(s.toString())?0:Integer.parseInt(s.toString()));
            }
        });
        remark.addTextChangedListener(new TextListener(remark.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                getData().get(layoutPosition).setAccRemark(s.toString());
            }
        });

    }
}
