package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.polling;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BasePresenter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseView;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;

import java.util.List;

/**
 *保养工单
 * */
public interface PollingOrderContract {

    interface View extends BaseView {
        /**
         * 当前搜索页数
         * @return
         */
        int getcurrentPage();
        /**
         * 当前所在地的界面
         */
        int getCurrentElectronic();
        /**
         * 设置数据
         */
        void setNewData(List<PollingOrder> pollingOrders);
        /**
         * 刷新加载完成
         */
        void stop();
    }


    interface Presenter extends BasePresenter<View> {
        /**
         * 获取列表详情
         */
        void getRepairOrders();

    }
}
