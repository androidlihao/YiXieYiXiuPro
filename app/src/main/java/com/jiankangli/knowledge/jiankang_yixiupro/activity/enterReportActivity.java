package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.Constant.DbConstant;
import com.jiankangli.knowledge.jiankang_yixiupro.Fragment.enter_report.enter_report_1_fragment;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MaintainDataBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.greendao.GreenDaoContext;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ActivityUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DialogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GreenDaoUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.GsonUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lihao
 * @date 2019-10-22 12:25
 * @description :录入报告，用单Activity多fragment来做这个功能
 */
public class enterReportActivity extends BaseActivity {


    public SingleMaintainOrderBean singleMaintainOrderBean;
    public UpkeepOrder order;
    public boolean isFrist;
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
        isFrist = getIntent().getExtras().getBoolean("isFrist");
        tvEnteringId.setVisibility(View.GONE);
        order = (UpkeepOrder) getIntent().getExtras().getSerializable("order");
        initFragment();
        ActivityUtils.getInstance().addActivity(this);
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
                    ActivityCompat.finishAfterTransition(enterReportActivity.this);
                }
            });
        }
    }
    @Override
    public void onBackPressedSupport() {
        closeDialog();
    }
    private void initFragment() {
        if (findFragment(enter_report_1_fragment.class) == null) {
            // 加载根Fragment
            loadRootFragment(R.id.fl_content, enter_report_1_fragment.newInstance());
            //全局改变Fragment的动画
            setFragmentAnimator(new DefaultHorizontalAnimator());
        }
    }

    /**
     * 获取工单详情Json数据
     *
     * @return
     */
    public String getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
            jsonObject.put("workOrderId", order.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }

    public SingleMaintainOrderBean getSingleMaintainOrderBean() {
        return singleMaintainOrderBean;
    }

    public void setSingleMaintainOrderBean(SingleMaintainOrderBean singleMaintainOrderBean) {
        this.singleMaintainOrderBean = singleMaintainOrderBean;
    }

    public void save() {
        //保存数据到本地数据库
        try {
            GreenDaoUtil greenDaoUtil = GreenDaoUtil.getInstance(new GreenDaoContext(getApplicationContext(), DbConstant.NEW_BY_DB));
            MaintainDataBean maintainDataBean = new MaintainDataBean();
            maintainDataBean.setId(Long.valueOf(order.getId()));
            maintainDataBean.setJsonObject(GsonUtil.GsonString(singleMaintainOrderBean));
            greenDaoUtil.getDaoSession().getMaintainDataBeanDao().insertOrReplace(maintainDataBean);
            ToastUtil.showShortSafe("保存成功", this);
        } catch (Exception e) {
            ToastUtil.showShortSafe("保存失败", this);
        }

    }

    public void upload() {
        DialogUtil.showPositiveDialog(this, "提示", "是否提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveSingleMaintainOrderBean();
            }
        });
    }

    public void saveSingleMaintainOrderBean() {
        if (singleMaintainOrderBean.getOrderPicVo() != null) {
            String picUrl = singleMaintainOrderBean.getOrderPicVo().getPicUrl();
            if (!picUrl.startsWith("http://")) {
                uploadPic();
            } else {
                uploadData();
            }
        }

    }

    /**
     * 上传签名图片，然后再提交数据到服务器
     */
    private void uploadPic() {
        File file = new File(singleMaintainOrderBean.getOrderPicVo().getPicUrl());
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
                        SingleMaintainOrderBean.OrderPicVoBean orderPicVo = singleMaintainOrderBean.getOrderPicVo();
                        if (picUrlBeanBaseEntity.isSuccess()) {
                            orderPicVo.setPicUrl(picUrlBeanBaseEntity.data.getUrl());
                        } else {
                            orderPicVo.setPicUrl("");
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        singleMaintainOrderBean.getOrderPicVo().setPicUrl("");
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
            if (!isFrist) {
                //电子工单id
                jsonObject.put("id", singleMaintainOrderBean.getId());
            }
            jsonObject.put("servicingTime", singleMaintainOrderBean.getServicingTime());
            jsonObject.put("softwareVersion", singleMaintainOrderBean.getSoftwareVersion());
            jsonObject.put("loadingTime", singleMaintainOrderBean.getLoadingTime());
            JSONObject jsonObject1 = new JSONObject();
            //工单Id
            jsonObject1.put("id", order.getId());
            jsonObject1.put("deviceModel", singleMaintainOrderBean.getDeviceModel());
            jsonObject.put("workOrder", jsonObject1);
            jsonObject.put("patientFlow", singleMaintainOrderBean.getPatientFlow());
            jsonObject.put("problemHandling", singleMaintainOrderBean.getProblemHandling());
            JSONObject jsonObject2 = new JSONObject();
            if (singleMaintainOrderBean.getOrderPicVo() != null) {
                jsonObject2.put("picUrl", singleMaintainOrderBean.getOrderPicVo().getPicUrl());
                jsonObject.put("orderPicVo", jsonObject2);
            }
            //模板数据
            JSONArray jsonArray = new JSONArray();
            for (SingleMaintainOrderBean.WorkTemplateVosBean workTemplateVo : singleMaintainOrderBean.getWorkTemplateVos()) {
                //根据模板填充值
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("templateId", workTemplateVo.getTemplateId());
                jsonObject3.put("templateTypeId", workTemplateVo.getTemplateTypeId());
                jsonObject3.put("templateInfoId", workTemplateVo.getTemplateInfoId());
                JSONArray jsonArray1 = new JSONArray();
                for (SingleMaintainOrderBean.WorkTemplateVosBean.TemplateTypeAndTemplateVosBean templateTypeAndTemplateVo : workTemplateVo.getTemplateTypeAndTemplateVos()) {
                    //子模板
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("id", templateTypeAndTemplateVo.getId());
                    jsonObject4.put("content", TextUtils.isEmpty(templateTypeAndTemplateVo.getContent()) ? "" : templateTypeAndTemplateVo.getContent());
                    jsonArray1.put(jsonObject4);
                }
                jsonObject3.put("templateTypeAndTemplateVos", jsonArray1);
                jsonArray.put(jsonObject3);
            }
            jsonObject.put("workTemplateVos", jsonArray);
            jsonObject.put("leaveTime", singleMaintainOrderBean.getLeaveTime());
            jsonObject.put("travelToTime", singleMaintainOrderBean.getTravelToTime());
            jsonObject.put("travelBackTime", singleMaintainOrderBean.getTravelBackTime());
            Log.i("TAG", "uploadData: ");
            //开始执行提交过程
            RetrofitManager.create(ApiService.class)
                    .saveMaintainOrder(BaseJsonUtils.Base64String(jsonObject))
                    .compose(RxSchedulers.<BaseEntity>io2main())
                    .as(AutoDispose.<BaseEntity>autoDisposable(
                            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                    .subscribe(new RxSubscriber<BaseEntity>() {
                        @Override
                        public void _onNext(BaseEntity baseEntity) {
                            if (baseEntity.isSuccess()) {
                                ToastUtil.showShortSafe("提交上报成功", getApplicationContext());
                                //然后跳转到对应的服务审核界面
                                if (isFrist) {
                                    //添加，直接跳转到服务确认界面
                                    Intent intent = new Intent(enterReportActivity.this, UpkeepServiceConfirmPageEchoActivity.class);
                                    intent.putExtra("order", order);
                                    startActivity(intent);
                                    ActivityUtils.getInstance().finishAllActivity();
                                } else {
                                    //跳转到正在审核界面
                                    Intent intent = new Intent(enterReportActivity.this, UpkeepOrderDetailsActivity.class);
                                    order.setListStatus(5);
                                    intent.putExtra("order", order);
                                    startActivity(intent);
                                    ActivityUtils.getInstance().finishAllActivity();
                                }
                            } else {
                                ToastUtil.showShortSafe(baseEntity.msg, getApplicationContext());
                            }
                        }

                        @Override
                        public void _onError(Throwable e, String msg) {
                            ToastUtil.showShortSafe("保存失败", getApplicationContext());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
