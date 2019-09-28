package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-09-19 22:11
 * @description :电子工单保存请求参数
 * */
public class SaveOrderBean {

    /**
     * id : 1
     * userId : 1
     * workOrderId : 1114
     * serviceReasons : hhhh
     * serviceContent : hhhh
     * serviceResults : hhh
     * serviceAdvice : 意见不错！
     * serviceEnd1 : 1
     * leaveTime : 2018-6-1 14:23
     * travelToTime : 1.2
     * travelBackTime : 2.3
     * bulbBrand : NERBA
     * bulbModel : M
     * bulbHeatCapacity : sajh
     * bulbInspectionAmount : 10
     * bulbNumberRecords : 12
     * orderPicVos : [{"type":2,"picUrl":"c:/sjhadj/asda/sdf"},{"type":3,"picUrl":"c:/sjhadja/sdf"},{"type":4,"picUrl":"c:/sjhadja/sdf"}]
     */

    private int id;
    private int userId;
    private int workOrderId;
    private String serviceReasons;
    private String serviceContent;
    private String serviceResults;
    private String serviceAdvice;
    private int serviceEnd1;
    private String leaveTime;
    private double travelToTime;
    private double travelBackTime;
    private String bulbBrand;
    private String bulbModel;
    private String bulbHeatCapacity;
    private int bulbInspectionAmount;
    private int bulbNumberRecords;
    private List<OrderPicVosBean> orderPicVos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getServiceReasons() {
        return serviceReasons;
    }

    public void setServiceReasons(String serviceReasons) {
        this.serviceReasons = serviceReasons;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getServiceResults() {
        return serviceResults;
    }

    public void setServiceResults(String serviceResults) {
        this.serviceResults = serviceResults;
    }

    public String getServiceAdvice() {
        return serviceAdvice;
    }

    public void setServiceAdvice(String serviceAdvice) {
        this.serviceAdvice = serviceAdvice;
    }

    public int getServiceEnd1() {
        return serviceEnd1;
    }

    public void setServiceEnd1(int serviceEnd1) {
        this.serviceEnd1 = serviceEnd1;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public double getTravelToTime() {
        return travelToTime;
    }

    public void setTravelToTime(double travelToTime) {
        this.travelToTime = travelToTime;
    }

    public double getTravelBackTime() {
        return travelBackTime;
    }

    public void setTravelBackTime(double travelBackTime) {
        this.travelBackTime = travelBackTime;
    }

    public String getBulbBrand() {
        return bulbBrand;
    }

    public void setBulbBrand(String bulbBrand) {
        this.bulbBrand = bulbBrand;
    }

    public String getBulbModel() {
        return bulbModel;
    }

    public void setBulbModel(String bulbModel) {
        this.bulbModel = bulbModel;
    }

    public String getBulbHeatCapacity() {
        return bulbHeatCapacity;
    }

    public void setBulbHeatCapacity(String bulbHeatCapacity) {
        this.bulbHeatCapacity = bulbHeatCapacity;
    }

    public int getBulbInspectionAmount() {
        return bulbInspectionAmount;
    }

    public void setBulbInspectionAmount(int bulbInspectionAmount) {
        this.bulbInspectionAmount = bulbInspectionAmount;
    }

    public int getBulbNumberRecords() {
        return bulbNumberRecords;
    }

    public void setBulbNumberRecords(int bulbNumberRecords) {
        this.bulbNumberRecords = bulbNumberRecords;
    }

    public List<OrderPicVosBean> getOrderPicVos() {
        return orderPicVos;
    }

    public void setOrderPicVos(List<OrderPicVosBean> orderPicVos) {
        this.orderPicVos = orderPicVos;
    }

    public static class OrderPicVosBean {
        /**
         * type : 2
         * picUrl : c:/sjhadj/asda/sdf
         */

        private int type;
        private String picUrl;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
