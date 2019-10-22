package com.jiankangli.knowledge.jiankang_yixiupro.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseActivity;
import com.jiankangli.knowledge.jiankang_yixiupro.R;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSchedulers;
import com.jiankangli.knowledge.jiankang_yixiupro.RxHelper.RxSubscriber;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PdfBean;
import com.jiankangli.knowledge.jiankang_yixiupro.net.ApiService;
import com.jiankangli.knowledge.jiankang_yixiupro.net.RetrofitManager;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.BaseJsonUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.SPUtils;
import com.jiankangli.knowledge.jiankang_yixiupro.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

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
            jsonObject.put("userId", SPUtils.get(this, "userId", -1 + ""));
            jsonObject.put("pdfType", pdfType);
            jsonObject.put("workOrderId", workOrderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String js = BaseJsonUtils.Base64String(jsonObject);
        commonLoading.show();
        RetrofitManager.create(ApiService.class)
                .getWorkOrderPdf(js)
                .compose(RxSchedulers.<BaseEntity<PdfBean>>io2main())
                .subscribe(new RxSubscriber<BaseEntity<PdfBean>>() {
                    @Override
                    public void _onNext(BaseEntity<PdfBean> pdfBeanBaseEntity) {
                        if (pdfBeanBaseEntity.isSuccess()){
                            String pdfUrl = pdfBeanBaseEntity.data.getPdfUrl();

                            pdfView.fromUri(Uri.parse(pdfUrl))
                                    .enableSwipe(true) // allows to block changing pages using swipe
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true)
                                    .defaultPage(0)
                                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                                    .password(null)
                                    .scrollHandle(null)
                                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                                    // spacing between pages in dp. To define spacing color, set view background
                                    .spacing(0)
                                    .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                                    .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                                    .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
                                    .pageSnap(false) // snap pages to screen boundaries
                                    .pageFling(false) // make a fling change only a single page like ViewPager
                                    .nightMode(false) // toggle night mode
                                    .load();
                        }else {
                            ToastUtil.showShortSafe("获取PDF文件出错",OrderPdfActivity.this);
                        }
                        commonLoading.dismiss();
                    }

                    @Override
                    public void _onError(Throwable e, String msg) {
                        ToastUtil.showShortSafe("获取PDF文件出错",OrderPdfActivity.this);
                        commonLoading.dismiss();
                    }

                });
    }

}
