package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PdfBean;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import io.reactivex.functions.Function;

/**
 * @author lihao
 * @date 2019-10-14 18:58
 * @description :工单详情PDF控件
 */
public class OrderPdfActivity extends BaseActivity {

    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.tv_id)
    TextView tvId;
    private int pdfType;
    private int workOrderId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMiddleTitle(this, "工单详情");
        Bundle extras = getIntent().getExtras();
        pdfType = extras.getInt("pdfType");
        workOrderId = extras.getInt("workOrderId");
        getPdf();
    }

    /**
     * 获取PDF文件
     */
    private void getPdf() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SPUtil.getInstance(getApplicationContext()).getString("userId"));
            jsonObject.put("pdfType", pdfType);
            jsonObject.put("workOrderId", workOrderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        commonLoading.show();
        RetrofitManager.create(ApiService.class)
                .getWorkOrderPdf(js)
                .map(new Function<BaseEntity<PdfBean>, InputStream>() {
                    @Override
                    public InputStream apply(BaseEntity<PdfBean> pdfBeanBaseEntity) throws Exception {
                        InputStream stream = null;
                        if (pdfBeanBaseEntity.isSuccess()) {
                            URL url = new URL(pdfBeanBaseEntity.data.getPdfUrl());
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setConnectTimeout(60000);
                            urlConnection.setReadTimeout(60000);
                            urlConnection.connect();
                            stream = urlConnection.getInputStream();
                        }
                        return stream;
                    }
                })
                .compose(RxSchedulers.<InputStream>io2main())
                .as(AutoDispose.<InputStream>autoDisposable(
                        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new RxSubscriber<InputStream>() {
                    @Override
                    public void _onNext(InputStream stream) {
                        if (stream != null) {
                            pdfView.fromStream(stream)
                                    .enableSwipe(true)
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true)
                                    .defaultPage(0)
                                    .enableAnnotationRendering(false)
                                    .enableAntialiasing(true)//改善低分辨率屏幕上的渲染
                                    .password(null)
                                    .scrollHandle(null)
                                    .onPageChange(new OnPageChangeListener() { //获取当前页数以及总页数，不需要则不用调用
                                        @Override
                                        public void onPageChanged(int page, int pageCount) { //page : 当前展示页数， pagecount:总页数
                                            tvId.setText((page+1)+"/"+pageCount);
                                            LogUtil.e(page + "");
                                        }
                                    })
                                    .load();
                        } else {
                            ToastUtil.showShortSafe("获取PDF文件出错", OrderPdfActivity.this);
                        }
                        commonLoading.dismiss();
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe(msg, OrderPdfActivity.this);
                        commonLoading.dismiss();
                    }

                });
    }
}
