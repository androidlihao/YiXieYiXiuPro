package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_upkeep;

import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Listeners.TextListener;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.signActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.upkeepBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.upkeepBlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.TimeUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.squareup.picasso.Picasso;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :提交报告页面
 */
public class bl_upkeep_4_fragment extends BaseFragment {

    @BindView(R.id.tv_select_date_id)
    TextView tvSelectDateId;
    @BindView(R.id.et_go_id)
    EditText etGoId;
    @BindView(R.id.et_back_id)
    EditText etBackId;
    @BindView(R.id.et_part_status_id)
    EditText etPartStatusId;
    @BindView(R.id.tv_part_status_id)
    TextView tvPartStatusId;
    @BindView(R.id.ll_content_id)
    LinearLayout llContentId;
    @BindView(R.id.tv_signature_id)
    TextView tvSignatureId;
    @BindView(R.id.iv_signature_id)
    ImageView ivSignatureId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.btn_next_id)
    Button btnNextId;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    Unbinder unbinder;
    @BindView(R.id.cb_isNeed_id)
    CheckBox cbIsNeedId;

    private upkeepBlBean blBean;
    private TimePickerView pvTime1;

    public static bl_upkeep_4_fragment newInstance() {
        bl_upkeep_4_fragment fragment = new bl_upkeep_4_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_upkeep_4_fragment_layout;
    }

    @Override
    protected void initView() {
        initDialog();
    }

    @Override
    protected void initData() {
        blBean = ((upkeepBackTrackingActivity) getActivity()).blBean;
        tvSelectDateId.setText(blBean.getLeaveTime());
        etGoId.setText(blBean.getTravelToTime() + "");
        etBackId.setText(blBean.getTravelBackTime() + "");
        etPartStatusId.setText(blBean.getProblemHandling());
        if (blBean.getOrderPicVo() != null) {
            String picUrl = blBean.getOrderPicVo().getPicUrl();
            if (picUrl.startsWith("http://")) {
                Picasso.get().load(blBean.getOrderPicVo().getPicUrl())
                        .error(R.drawable.load_error).placeholder(R.drawable.loading)
                        .into(ivSignatureId);
            } else {
                Picasso.get().load(new File(blBean.getOrderPicVo().getPicUrl()))
                        .error(R.drawable.load_error).placeholder(R.drawable.loading)
                        .into(ivSignatureId);
            }
        }

        cbIsNeedId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //标识设备是否正常工作
                if (isChecked) {
                    blBean.setWhetherExamine(1);
                } else {
                    blBean.setWhetherExamine(0);
                }

            }
        });
    }

    @Override
    protected void initListener() {
        etPartStatusId.addTextChangedListener(new TextListener(R.id.et_part_status_id) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                tvPartStatusId.setText(s.length() + "/" + 500);
                //修改问题描述
                blBean.setProblemHandling(s.toString());
            }
        });
        etGoId.addTextChangedListener(new TextListener(R.id.et_go_id) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                try {
                    blBean.setTravelToTime(Double.parseDouble(s.toString()));
                } catch (Exception e) {
                    blBean.setTravelToTime(0);
                }
            }
        });
        etBackId.addTextChangedListener(new TextListener(R.id.et_back_id) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                try {
                    blBean.setTravelBackTime(Double.parseDouble(s.toString()));
                } catch (Exception e) {
                    blBean.setTravelBackTime(0);
                }
            }
        });
    }

    /**
     * 保存数据
     */
    private void save() {
        //设置值
        try {
            SPUtils.put(getContext(), "bybl", GsonUtils.beanTojson(blBean));
            ToastUtil.showShortSafe("保存成功", getActivity());
        } catch (Exception e) {
            ToastUtil.showShortSafe(e + "保存失败", getActivity());
        }
    }

    @OnClick({R.id.btn_save_id, R.id.btn_next_id, R.id.tv_select_date_id, R.id.tv_signature_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_id:
                //保存数据到对应的中
                save();
                break;
            case R.id.btn_next_id:
                upkeepBlBean.OrderPicVoBean orderPicVo1 = blBean.getOrderPicVo();
                boolean isSign = false;
                if (orderPicVo1 != null) {
                    if (orderPicVo1.getType() == 5) {
                        isSign = true;

                    }
                }
                if (!isSign) {
                    ToastUtil.showShortSafe("请签字", getActivity());
                    return;
                }
                String leaveTime = tvSelectDateId.getText().toString();
                if (TextUtils.isEmpty(leaveTime)) {
                    ToastUtil.showShortSafe("请输入离开场地时间", getActivity());
                    return;
                }
                String travelToTime = etGoId.getText().toString();
                if (TextUtils.isEmpty(travelToTime)) {
                    ToastUtil.showShortSafe("请输入差旅时间", getActivity());
                    return;
                }
                String travelBackTime = etBackId.getText().toString();
                if (TextUtils.isEmpty(travelBackTime)) {
                    ToastUtil.showShortSafe("请输入差旅时间", getActivity());
                    return;
                }
                String trim = etPartStatusId.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showShortSafe("请输入问题描述", getActivity());
                    return;
                }
                try {
                    upload();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_select_date_id:
                pvTime1.show();
                break;
            case R.id.tv_signature_id:
                //跳转到签字面板
                Intent intent = new Intent(getActivity(), signActivity.class);
                startActivityForResult(intent, 201);
                break;
        }
    }

    /**
     * 开始上传操作
     */
    private void upload() throws JSONException {
        DialogUtil.showPositiveDialog(getContext(), "提示", "是否提交工单数据", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((upkeepBackTrackingActivity) getActivity()).commonLoading.show();
                if (blBean.getOrderPicVo() != null) {
                    String picUrl = blBean.getOrderPicVo().getPicUrl();
                    if (!picUrl.startsWith("http://")) {
                        uploadPic();
                    } else {
                        uploadData();
                    }
                }

            }
        });

    }

    /**
     * 上传签名图片，然后再提交数据到服务器
     */
    private void uploadPic() {
        File file = new File(blBean.getOrderPicVo().getPicUrl());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("data", file.getName() + ".png", requestBody);
        RetrofitManager.create(ApiService.class)
                .uploadImage(body)
                .compose(RxSchedulers.<BaseEntity<PicUrlBean>>io2main())
                .as(AutoDispose.<BaseEntity<PicUrlBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<PicUrlBean>>() {
                    @Override
                    public void _onNext(BaseEntity<PicUrlBean> picUrlBeanBaseEntity) {
                        upkeepBlBean.OrderPicVoBean orderPicVo = blBean.getOrderPicVo();
                        if (picUrlBeanBaseEntity.isSuccess()) {
                            orderPicVo.setPicUrl(picUrlBeanBaseEntity.data.getUrl());
                        } else {
                            orderPicVo.setPicUrl("");
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        blBean.getOrderPicVo().setPicUrl("");
                    }

                    @Override
                    public void onComplete() {
                        //接下来继续执行上传操作，否则直接进行上传操作
                        uploadData();
                        super.onComplete();
                    }
                });
    }

    /**
     * 提交数据到服务器
     */
    private void uploadData() {
        //开始保存数据
        String s = GsonUtils.beanTojson(blBean);
        try {
            JSONObject jsonObject = new JSONObject(s);
            //开始执行提交过程
            RetrofitManager.create(ApiService.class)
                    .recodeMaintainOrderEntry(BaseJsonUtils.Base64String(jsonObject))
                    .compose(RxSchedulers.<BaseEntity<Double>>io2main())
                    .as(AutoDispose.<BaseEntity<Double>>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new RxSubscriber<BaseEntity<Double>>() {
                        @Override
                        public void _onNext(BaseEntity<Double> baseEntity) {
                            ((upkeepBackTrackingActivity) getActivity()).commonLoading.dismiss();
                            ToastUtil.showShortSafe(baseEntity.msg, getContext());
                            if (baseEntity.isSuccess()) {
                                getActivity().finish();
                                SPUtils.remove(getActivity(), "bybl");
                            }
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            ((upkeepBackTrackingActivity) getActivity()).commonLoading.dismiss();
                            ToastUtil.showShortSafe(msg, getContext());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode && requestCode == 201) {
            String sign = (String) data.getExtras().get("sign");
            Picasso.get().load(new File(sign)).into(ivSignatureId);
            //修改本地数据源
            upkeepBlBean.OrderPicVoBean orderPicVo = blBean.getOrderPicVo() == null ? new upkeepBlBean.OrderPicVoBean() : blBean.getOrderPicVo();
//            orderPicVo.setCreateTime(TimeUtil.getCurrentTime());
            orderPicVo.setType(5);
            orderPicVo.setPicUrl(sign);
            blBean.setOrderPicVo(orderPicVo);
//            blBean.setEngineerSignedTime(TimeUtil.getCurrentTime());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initDialog() {
        //时间选择器
        pvTime1 = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvSelectDateId.setText(TimeUtil.getTimeFormatParseMinute(date));
                //设置离开场地时间
                blBean.setLeaveTime(TimeUtil.getTimeFormatParseMinute(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, false})
                .isCyclic(true)
                .build();
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
