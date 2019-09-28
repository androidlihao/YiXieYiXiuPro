package com.jiankangli.knowledge.jiankang_yixiupro.Fragment.spare_parts;

import com.jiankangli.knowledge.jiankang_yixiupro.Base.BasePresenter;
import com.jiankangli.knowledge.jiankang_yixiupro.Base.BaseView;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SpareParts;

import java.util.List;

/**
 *维修工单view层
 */
public interface SparePartsContract {

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
        void setNewData(List<SpareParts> spareParts);
    }


    interface Presenter extends BasePresenter<View> {
        /**
         * 获取列表详情
         */
        void getRepairOrders();

    }
}
