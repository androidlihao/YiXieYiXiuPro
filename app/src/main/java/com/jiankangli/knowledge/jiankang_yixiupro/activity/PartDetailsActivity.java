package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.OrderDetailsAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Adapter.Recycler_SparePart_imagleAdapter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SpareParts;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.partOrderdisplayBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.sparePartBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.AssetUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.DicUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.MapBeanUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtil;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 锦绣前程 on 2018/9/17.
 */

/**
 * 备件详情页面
 */
public class PartDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.rc_order_details_id)
    RecyclerView rcOrderDetailsId;
    @BindView(R.id.rc_part_details_id)
    RecyclerView rcPartDetailsId;
    @BindView(R.id.rc_img_id)
    RecyclerView rcImgId;
    @BindView(R.id.tv_fault_des_id)
    TextView tvFaultDesId;
    @BindView(R.id.fl_content_id)
    FrameLayout flContentId;
    private SpareParts spareParts;
    private OrderDetailsAdapter orderDetailsAdapter;
    private partOrderdisplayBean displayBean;
    private Recycler_SparePart_imagleAdapter recycler_imagleAdapter;
    private OrderDetailsAdapter partAdapter;
    private LinearLayout llContentId;
    private EditText etPartStatusId;
    private TextView tvPartStatusId;
    private Button btnRepairArrvalId;
    private TextView tvPartfinishStatusId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_part_details_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //让布局向上移来显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        spareParts = (SpareParts) getIntent().getSerializableExtra("part");
        initView();
        changeUI();
        initData();
    }

    private void changeUI() {
        flContentId.removeAllViews();
        switch (spareParts.getAccessoryStatus()) {
            case 1:
                addMiddleTitle(this, "审批中");
                flContentId.setVisibility(View.GONE);
                break;
            case 2:
                addMiddleTitle(this, "配送中");
                View inflate = LayoutInflater.from(this).inflate(R.layout.part_arrival_layout, null);
                llContentId = (LinearLayout) inflate.findViewById(R.id.ll_content_id);
                etPartStatusId = (EditText) inflate.findViewById(R.id.et_part_status_id);
                tvPartStatusId = (TextView) inflate.findViewById(R.id.tv_part_status_id);
                btnRepairArrvalId = (Button) inflate.findViewById(R.id.btn_repair_arrval_id);
                initListtener();
                flContentId.addView(inflate);
                break;
            case 3:
                addMiddleTitle(this, "缺货");
                flContentId.setVisibility(View.GONE);
                break;
            case 4:
                addMiddleTitle(this, "配送完成");
                View view = LayoutInflater.from(this).inflate(R.layout.part_finish_layout, null);
                tvPartfinishStatusId = (TextView) view.findViewById(R.id.tv_partfinish_status_id);
                flContentId.addView(view);
                break;

        }
    }

    private void initListtener() {
        etPartStatusId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvPartStatusId.setText((500 - s.length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnRepairArrvalId.setOnClickListener(this);
    }

    /**
     * 初始化view
     */
    private void initView() {
        orderDetailsAdapter = new OrderDetailsAdapter(R.layout.item_part_details);
        rcOrderDetailsId.setLayoutManager(new LinearLayoutManager(this));
        rcOrderDetailsId.setAdapter(orderDetailsAdapter);
        //
        partAdapter = new OrderDetailsAdapter(R.layout.item_details);
        rcPartDetailsId.setLayoutManager(new LinearLayoutManager(this));
        rcPartDetailsId.setAdapter(partAdapter);
        //图片
        recycler_imagleAdapter = new Recycler_SparePart_imagleAdapter(R.layout.imagle_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcImgId.setLayoutManager(layoutManager);
        rcImgId.setAdapter(recycler_imagleAdapter);


    }

    /**
     * 获取数据
     */
    private void initData() {
        InputStream is = AssetUtil.getIsFromAssets("partOrderdisplayConfig.json", this);
        JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
        displayBean = new Gson().fromJson(jsonReader, partOrderdisplayBean.class);
        RetrofitManager.create(ApiService.class)
                .getSparePartById(getJson())
                .compose(RxSchedulers.<BaseEntity<sparePartBean>>io2main())
                .as(AutoDispose.<BaseEntity<sparePartBean>>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<BaseEntity<sparePartBean>>() {
                    @Override
                    public void _onNext(BaseEntity<sparePartBean> baseEntity) {
                        if (baseEntity.isSuccess()) {
                            tvFaultDesId.setText(baseEntity.data.getRemark());
                            Map<String, Object> map = MapBeanUtil.object2Map(baseEntity.data);
                            List<HashMap<String, String>> list = new LinkedList<>();
                            for (String key : displayBean.getDisplayBean()) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                if (key.contentEquals("workNumber")) {
                                    map.put(key, SPUtil.getInstance(getApplicationContext()).getString("workNumber"));
                                }
                                hashMap.put(DicUtil.getKeyOrValue(key, getApplicationContext()), map.get(key) == null ? "" : map.get(key).toString());
                                list.add(hashMap);
                            }
                            orderDetailsAdapter.setNewData(list);
                            List<HashMap<String, String>> lists = new LinkedList<>();
                            for (String key : displayBean.getPartdisplayBean()) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                if (key.contentEquals("foreignAid")) {
                                    if (TextUtils.equals(map.get(key).toString(), "1")) {
                                        map.put(key, "需要");
                                    } else {
                                        map.put(key, "不需要");
                                    }
                                }
                                hashMap.put(DicUtil.getKeyOrValue(key, getApplicationContext()), map.get(key) == null ? "" : map.get(key).toString());
                                lists.add(hashMap);
                            }
                            partAdapter.setNewData(lists);
                            List<sparePartBean.AccessoryPicVosBean> accessoryPicVos = baseEntity.data.getAccessoryPicVos();
                            if (accessoryPicVos == null) {
                                accessoryPicVos = new ArrayList<>();
                            }
                            recycler_imagleAdapter.setNewData(accessoryPicVos);
                            if (spareParts.getAccessoryStatus() == 4) {
                                String accBackInfo = baseEntity.data.getAccBackInfo();
                                if (TextUtils.isEmpty(accBackInfo)) {
                                    tvPartfinishStatusId.setText(accBackInfo);
                                }
                            }
                        }
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        Log.i("TAG", "_onError: ");
                    }
                });

    }

    /**
     * 获取工单详情Json数据
     *
     * @return
     */
    private String getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", spareParts.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        return js;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_repair_arrval_id:
                if (btnRepairArrvalId.getText().equals("备件到货")) {
                    btnRepairArrvalId.setText("提交");
                    llContentId.setVisibility(View.VISIBLE);
                } else {
                    //执行提交
                    JsonObject jsonObject = new JsonObject();
                    Object userId = SPUtil.getInstance(getApplicationContext()).getString("userId");
                    try {
                        JsonObject js = new JsonObject();
                        jsonObject.addProperty("id", spareParts.getId() + "");
                        jsonObject.addProperty("accBackInfo", etPartStatusId.getText().toString());
                        jsonObject.addProperty("updateUserId", (String) userId);
                        js.add("data", jsonObject);
                        RetrofitManager.create(ApiService.class)
                                .updateSparePart(Base64.encodeToString(js.toString().getBytes(), Base64.NO_WRAP))
                                .compose(RxSchedulers.<BaseEntity>io2main())
                                .as(AutoDispose.<BaseEntity>autoDisposable(
                                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                                .subscribe(new RxSubscriber<BaseEntity>() {
                                    @Override
                                    public void _onNext(BaseEntity baseEntity) {
                                        ToastUtil.showShortSafe(baseEntity.msg, getApplicationContext());
                                        if (baseEntity.isSuccess()) {
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void _onError(Throwable e, String msg) {
                                        ToastUtil.showShortSafe(msg, getApplicationContext());
                                    }
                                });
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }


}
