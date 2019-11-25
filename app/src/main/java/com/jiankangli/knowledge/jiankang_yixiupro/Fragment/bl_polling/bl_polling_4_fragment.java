package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_polling;

import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.jiankangli.knowledge.jiankang_yixiupro.activity.inspectionServiceConfirmPageEchoActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.pollingBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.signActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.pollingBlBean;
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
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :提交报告页面
 */
public class bl_polling_4_fragment extends BaseFragment {


    @BindView(R.id.cb_isNeed_id)
    CheckBox cbIsNeedId;
    @BindView(R.id.tv_select_date_id)
    TextView tvSelectDateId;
    @BindView(R.id.et_go_id)
    EditText etGoId;
    @BindView(R.id.et_back_id)
    EditText etBackId;
    @BindView(R.id.tv_signature_id)
    TextView tvSignatureId;
    @BindView(R.id.iv_signature_id)
    ImageView ivSignatureId;
    @BindView(R.id.btn_save_id)
    Button btnSaveId;
    @BindView(R.id.rl_save_id)
    RelativeLayout rlSaveId;
    @BindView(R.id.btn_next_id)
    Button btnNextId;
    private pollingBlBean blBean;
    private TimePickerView pvTime1;

    public static bl_polling_4_fragment newInstance() {
        bl_polling_4_fragment fragment = new bl_polling_4_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_polling_4_fragment_layout;
    }

    @Override
    protected void initView() {
        initDialog();
    }

    @Override
    protected void initData() {
        blBean = ((pollingBackTrackingActivity) getActivity()).blBean;
        tvSelectDateId.setText(blBean.getLeaveTime());
        etGoId.setText(blBean.getTravelToTime() + "");
        etBackId.setText(blBean.getTravelBackTime() + "");
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


    }

    @Override
    protected void initListener() {
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

    /**
     * 保存数据
     */
    private void save() {
        //设置值
        try {
            SPUtils.put(getContext(), "xjbl", GsonUtils.beanTojson(blBean));
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
                pollingBlBean.OrderPicVoBean orderPicVo1 = blBean.getOrderPicVo();
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
                ((pollingBackTrackingActivity) getActivity()).commonLoading.show();
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
                        pollingBlBean.OrderPicVoBean orderPicVo = blBean.getOrderPicVo();
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
                    .recodeinspectionWorkOrderEntry(BaseJsonUtils.Base64String(jsonObject))
                    .compose(RxSchedulers.<BaseEntity<Double>>io2main())
                    .as(AutoDispose.<BaseEntity<Double>>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new RxSubscriber<BaseEntity<Double>>() {
                        @Override
                        public void _onNext(BaseEntity<Double> baseEntity) {
                            ((pollingBackTrackingActivity) getActivity()).commonLoading.dismiss();
                            ToastUtil.showShortSafe(baseEntity.msg, getContext());
                            if (baseEntity.isSuccess()) {
                                Double id=baseEntity.data;
                                PollingOrder pollingOrder=new PollingOrder();
                                pollingOrder.setId(id.intValue());
                                pollingOrder.setOrderStatus(4);
                                Intent intent1 = new Intent(getActivity(), inspectionServiceConfirmPageEchoActivity.class);
                                intent1.putExtra("order", pollingOrder);
                                startActivity(intent1);
                                getActivity().finish();
                                SPUtils.remove(getActivity(), "xjbl");
                            }
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            ((pollingBackTrackingActivity) getActivity()).commonLoading.dismiss();
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
            pollingBlBean.OrderPicVoBean orderPicVo = blBean.getOrderPicVo() == null ? new pollingBlBean.OrderPicVoBean() : blBean.getOrderPicVo();
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
                tvSelectDateId.setText(TimeUtil.getTimeFormatParse(date));
                //设置离开场地时间
                blBean.setLeaveTime(TimeUtil.getTimeFormatParseMinute(date));
            }
            //默认设置为年月日时分秒
        }).setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, true})
                .isCyclic(true)
                .build();
    }

}
