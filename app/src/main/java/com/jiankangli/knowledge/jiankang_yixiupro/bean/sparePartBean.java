package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-09-26 21:34
 * @description :备件详情
 * */
public class sparePartBean {

    /**
     * id : 345
     * orderNo : 20180928019
     * accessoryName : We
     * accessoryNo : null
     * serialNum : null
     * number : 3
     * foreignAid : 0
     * commodityLink : null
     * supplier : null
     * needTime : null
     * unitPrice : null
     * returnNumber : null
     * remark : Hdh
     * name : 夏主任
     * deviceName : GEUS
     * deviceModel : GELogiq 7
     * deviceNo : 7000000003
     * arrivalTime : null
     * accBackInfo : Gdhd
     * accRemark : null
     * accessoryPicVos : [{"id":null,"accessoryId":null,"picUrl":"http://192.168.1.3//images/201809/e5d853cf-8d73-4bbf-a9b6-e49acda27e56.jpg"}]
     */

    private int id;
    private String orderNo;
    private String accessoryName;
    private Object accessoryNo;
    private Object serialNum;
    private String number;
    private int foreignAid;
    private Object commodityLink;
    private Object supplier;
    private Object needTime;
    private Object unitPrice;
    private Object returnNumber;
    private String remark;
    private String name;
    private String deviceName;
    private String deviceModel;
    private String deviceNo;
    private Object arrivalTime;
    private String accBackInfo;
    private Object accRemark;
    private List<AccessoryPicVosBean> accessoryPicVos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public Object getAccessoryNo() {
        return accessoryNo;
    }

    public void setAccessoryNo(Object accessoryNo) {
        this.accessoryNo = accessoryNo;
    }

    public Object getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Object serialNum) {
        this.serialNum = serialNum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getForeignAid() {
        return foreignAid;
    }

    public void setForeignAid(int foreignAid) {
        this.foreignAid = foreignAid;
    }

    public Object getCommodityLink() {
        return commodityLink;
    }

    public void setCommodityLink(Object commodityLink) {
        this.commodityLink = commodityLink;
    }

    public Object getSupplier() {
        return supplier;
    }

    public void setSupplier(Object supplier) {
        this.supplier = supplier;
    }

    public Object getNeedTime() {
        return needTime;
    }

    public void setNeedTime(Object needTime) {
        this.needTime = needTime;
    }

    public Object getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Object unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Object getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(Object returnNumber) {
        this.returnNumber = returnNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Object getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Object arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getAccBackInfo() {
        return accBackInfo;
    }

    public void setAccBackInfo(String accBackInfo) {
        this.accBackInfo = accBackInfo;
    }

    public Object getAccRemark() {
        return accRemark;
    }

    public void setAccRemark(Object accRemark) {
        this.accRemark = accRemark;
    }

    public List<AccessoryPicVosBean> getAccessoryPicVos() {
        return accessoryPicVos;
    }

    public void setAccessoryPicVos(List<AccessoryPicVosBean> accessoryPicVos) {
        this.accessoryPicVos = accessoryPicVos;
    }

    public static class AccessoryPicVosBean {
        /**
         * id : null
         * accessoryId : null
         * picUrl : http://192.168.1.3//images/201809/e5d853cf-8d73-4bbf-a9b6-e49acda27e56.jpg
         */

        private Object id;
        private Object accessoryId;
        private String picUrl;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getAccessoryId() {
            return accessoryId;
        }

        public void setAccessoryId(Object accessoryId) {
            this.accessoryId = accessoryId;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
