package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.repair_order;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BasePresenter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseView;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;

import java.util.List;

/**
 *维修工单view层
 */
public interface RepairOrderContract {

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
        void setNewData(List<RepairOrder> repairOrders);
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
