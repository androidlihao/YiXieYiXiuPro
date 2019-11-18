package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.bl_repair;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.LocalePicAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseFragment;
import com.jiankangli.knowledge.jiankang_yixiupro.Listeners.TextListener;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.activity.repairBackTrackingActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.LogUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


/**
 * @author lihao
 * @date 2019-10-22 14:46
 * @description :录入工单第一个界面
 */
public class bl_repair_3_fragment extends BaseFragment {


    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.tv_count_id)
    TextView tvCountId;
    @BindView(R.id.rc_photo_id)
    RecyclerView rcPhotoId;


    private com.jiankangli.knowledge.jiankang_yixiupro.bean.blBean blBean;
    private int currentPos;
    private LocalePicAdapter localePicAdapter;
    private View mFootView;

    public static bl_repair_3_fragment newInstance(Bundle args) {
        bl_repair_3_fragment fragment = new bl_repair_3_fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void CreatePresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.bl_repair_3_fragment_layout;
    }

    @Override
    protected void initView() {
        blBean = ((repairBackTrackingActivity) getActivity()).blBean;
        Bundle arguments = getArguments();
        if (arguments!=null){
            currentPos = arguments.getInt("currentPos");
        }
        initRecycler();
    }

    private void initRecycler() {
        rcPhotoId.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        localePicAdapter = new LocalePicAdapter(R.layout.item_locale_pic);
        rcPhotoId.setAdapter(localePicAdapter);
        mFootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_pic_adapter_foot, null);
        List<blBean.ElectronOrderVosBean.OrderPicVosBean> orderPicVos = null;
        if (blBean.getElectronOrderVos()==null||blBean.getElectronOrderVos().getOrderPicVos()==null) {
            orderPicVos=new ArrayList<>();
        }else {
            orderPicVos=blBean.getElectronOrderVos().getOrderPicVos();
        }
        ArrayList<LocalMedia> localMedia1 = new ArrayList<>();
        for (blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVo : orderPicVos) {
            if (orderPicVo.getType()==currentPos+1){
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(orderPicVo.getPicUrl());
                localMedia1.add(localMedia);
            }
        }
        localePicAdapter.setNewData(localMedia1);
        if (localePicAdapter.getData().size()!=5){
            localePicAdapter.addFooterView(mFootView);
        }

        mFootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(getActivity()); // where this is an Activity instance
                rxPermissions.requestEach(
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                try {
                                    if (permission.granted) {
                                        //打开
                                        selectphoto();
                                    } else if (permission.shouldShowRequestPermissionRationale) {

                                    } else {
                                        ToastUtil.showShortSafe("请进入设置界面打开读取本地文件权限", getActivity());
                                    }
                                } catch (Exception e) {
                                    Log.i("TAG", "ISPermission: " + e);
                                }
                            }
                        });
            }
        });
        localePicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_close) {
                    localePicAdapter.remove(position);
                    setPic();
                    if (localePicAdapter.getFooterLayoutCount() == 0) {
                        localePicAdapter.addFooterView(mFootView);
                    }
                }
            }
        });
    }
    public void selectphoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMedia(localePicAdapter.getData())
                .maxSelectNum(5)
                .forResult(PictureConfig.TYPE_IMAGE);

    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        etId.addTextChangedListener(new TextListener(etId.getId()) {
            @Override
            public void onTextChange(int layoutId, Editable s) {
                tvCountId.setText(s.length() + "/2000");
                blBean.ElectronOrderVosBean electronOrderVos = blBean.getElectronOrderVos()==null?new blBean.ElectronOrderVosBean():
                        blBean.getElectronOrderVos();
                switch (currentPos) {
                    case 1:
                        electronOrderVos.setServiceReasons(etId.getText().toString());
                        break;
                    case 2:
                        electronOrderVos.setServiceContent(etId.getText().toString());
                        break;
                    case 3:
                        electronOrderVos.setServiceResults(etId.getText().toString());
                        break;
                    case 4:
                        electronOrderVos.setServiceAdvice(etId.getText().toString());
                        break;
                }
                blBean.setElectronOrderVos(electronOrderVos);
            }
        });
    }


    public void toNext() {
        //跳转到下一界面
        if (currentPos==4){
            LogUtil.e("");
            start(bl_repair_4_fragment.newInstance());
        }else {
            Bundle bundle = new Bundle();
            bundle.putInt("currentPos", currentPos+1);
            //将数据存起来
            start(bl_repair_3_fragment.newInstance(bundle));
        }

    }

    @Override
    public void onSupportVisible() {
        changeUi();
        super.onSupportVisible();
    }

    private void changeUi() {
        String title = null;
        blBean.ElectronOrderVosBean electronOrderVos = blBean.getElectronOrderVos();
        switch (currentPos) {
            case 1:
                title = "服务原因";
                if (electronOrderVos!=null&&electronOrderVos.getServiceReasons()!=null){
                    etId.setText(electronOrderVos.getServiceReasons());
                }
                break;
            case 2:
                title = "服务内容";
                if (electronOrderVos!=null&&electronOrderVos.getServiceContent()!=null){
                    etId.setText(electronOrderVos.getServiceContent());
                }
                break;
            case 3:
                title = "服务结果";
                if (electronOrderVos!=null&&electronOrderVos.getServiceResults()!=null){
                    etId.setText(electronOrderVos.getServiceResults());
                }
                break;
            case 4:
                title = "服务建议";
                rcPhotoId.setVisibility(View.GONE);
                if (electronOrderVos!=null&&electronOrderVos.getServiceAdvice()!=null){
                    etId.setText(electronOrderVos.getServiceAdvice());
                }
                break;
        }
        ((repairBackTrackingActivity) getActivity()).changeTitle(title);
        etId.setHint("请输入" + title + "，不超过2000字");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //返回的图片集合
        if (requestCode == PictureConfig.TYPE_IMAGE && resultCode == RESULT_OK) {
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            localePicAdapter.setNewData(localMedia);
            setPic();
            if (localMedia.size() == 5) {
                localePicAdapter.removeFooterView(mFootView);
            }
        } else if (resultCode == RESULT_CANCELED) {

        }

    }

    private void setPic() {
        //设置服务原因
        blBean.ElectronOrderVosBean electronOrderVos = blBean.getElectronOrderVos()==null?new blBean.ElectronOrderVosBean():
                blBean.getElectronOrderVos();
        //设置图片
        List<blBean.ElectronOrderVosBean.OrderPicVosBean>
                orderPicVosBeans = electronOrderVos.getOrderPicVos() == null ? new ArrayList<blBean.ElectronOrderVosBean.OrderPicVosBean>()
                :electronOrderVos.getOrderPicVos();
        //先清除当前的图片，然后设置当前的图片
        for (int i = 0; i < orderPicVosBeans.size(); i++) {
            blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVosBean = orderPicVosBeans.get(i);
            if (orderPicVosBean.getType() == currentPos+1) {
                orderPicVosBeans.remove(orderPicVosBean);
                i--;
            }
        }
        for (LocalMedia datum : localePicAdapter.getData()) {
             blBean.ElectronOrderVosBean.OrderPicVosBean orderPicVosBean=new blBean.ElectronOrderVosBean.OrderPicVosBean();
             orderPicVosBean.setPicUrl(datum.getPath());
             orderPicVosBean.setType(currentPos+1);
             orderPicVosBeans.add(orderPicVosBean);
        }
        electronOrderVos.setOrderPicVos(orderPicVosBeans);
        blBean.setElectronOrderVos(electronOrderVos);
    }
}
