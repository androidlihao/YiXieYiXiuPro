package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.repair_order;

import android.arch.lifecycle.Lifecycle;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.RxPresenter;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SpareParts;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.LogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * @author lihao
 * @date 2019-09-13 15:12
 * @description :
 */
public class RepairOrderPresenter extends RxPresenter<RepairOrderContract.View> implements RepairOrderContract.Presenter{

    /**
     * 获取工单列表
     */
    @Override
    public void getRepairOrders() {
        RetrofitManager.create(ApiService.class)
                .getRepairOrder(getJson())
                .compose(RxSchedulers.<BaseEntity<List<RepairOrder>>>io2main())
                .as(AutoDispose.<BaseEntity<List<RepairOrder>>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(((BaseActivity) mView.getContext()).getLifecycle(), Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<List<RepairOrder>>>() {
                    @Override
                    public void _onNext(BaseEntity<List<RepairOrder>> listBaseEntity) {
                        if (listBaseEntity.isSuccess()){
                            mView.setNewData(listBaseEntity.data);
                        }else {
                            ToastUtil.showShortSafe(listBaseEntity.msg,mView.getContext());
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg,mView.getContext());
                    }
                });

    }
    /**
     * 获取json数据
     */
    public String getJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtils.get(mView.getContext(), "userId", -1 + ""));
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("pageNum", mView.getcurrentPage());
            jsonObject.put("page", jsonObject1);
            int electronicStatus = mView.getCurrentElectronic()+1;
            jsonObject.put("electronicStatus", electronicStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }
}
