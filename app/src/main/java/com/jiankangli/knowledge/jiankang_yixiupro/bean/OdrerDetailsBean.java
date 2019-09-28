package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-09-13 19:13
 * @description :
 */
public class OdrerDetailsBean {


    /**
     * id : 1497
     * hospitalName : 武汉大学附属医院
     * factoryNo : null
     * workNumber : 2018007
     * orderNo : 20190913001
     * projectCode : null
     * contractNo : null
     * deviceModel : CCCC
     * sectionName : null
     * devicePhone : null
     * deviceName : CTR
     * deviceBrand :
     * deviceNo : null
     * deviceTypeCode : null
     * repairName : 李浩-1
     * temporaryNum :
     * keepTime : null
     * bookTime : 2019-09-13 17:52~17:52
     * address : null
     * servicerName : null
     * contractStatus : null
     * repairType : null
     * remark : 不知道
     * orderPicVos : [{"id":120,"type":1,"picUrl":"c:/sjhadj/asda/sdf","createTime":null},{"id":121,"type":1,"picUrl":"c:/sjhadj/asda/sdf","createTime":null},{"id":123,"type":1,"picUrl":"c:/sjhadja/sdf","createTime":1528957510000},{"id":190,"type":1,"picUrl":"http://192.168.1.3//images/201801/healthPowerf74fafa79bc640b9aa44432a76bf5c49.png","createTime":1531902612000},{"id":191,"type":1,"picUrl":"c:/skdjak/csds/sdsa","createTime":null},{"id":192,"type":1,"picUrl":"http://192.168.1.3//images/201801/healthPowerf74fafa79bc640b9aa44432a76bf5c49.png","createTime":null},{"id":204,"type":1,"picUrl":"c:/sjhadj/asda/sdf","createTime":1532597193000}]
     */

    private int id;
    private String hospitalName;
    private Object factoryNo;
    private String workNumber;
    private String orderNo;
    private Object projectCode;
    private Object contractNo;
    private String deviceModel;
    private Object sectionName;
    private Object devicePhone;
    private String deviceName;
    private String deviceBrand;
    private Object deviceNo;
    private Object deviceTypeCode;
    private String repairName;
    private String temporaryNum;
    private Object keepTime;
    private String bookTime;
    private Object address;
    private Object servicerName;
    private Object contractStatus;
    private Object repairType;
    private String remark;
    private List<OrderPicVosBean> orderPicVos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Object getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(Object factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Object getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Object projectCode) {
        this.projectCode = projectCode;
    }

    public Object getContractNo() {
        return contractNo;
    }

    public void setContractNo(Object contractNo) {
        this.contractNo = contractNo;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public Object getSectionName() {
        return sectionName;
    }

    public void setSectionName(Object sectionName) {
        this.sectionName = sectionName;
    }

    public Object getDevicePhone() {
        return devicePhone;
    }

    public void setDevicePhone(Object devicePhone) {
        this.devicePhone = devicePhone;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public Object getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(Object deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Object getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(Object deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    public String getTemporaryNum() {
        return temporaryNum;
    }

    public void setTemporaryNum(String temporaryNum) {
        this.temporaryNum = temporaryNum;
    }

    public Object getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(Object keepTime) {
        this.keepTime = keepTime;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getServicerName() {
        return servicerName;
    }

    public void setServicerName(Object servicerName) {
        this.servicerName = servicerName;
    }

    public Object getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Object contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Object getRepairType() {
        return repairType;
    }

    public void setRepairType(Object repairType) {
        this.repairType = repairType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OrderPicVosBean> getOrderPicVos() {
        return orderPicVos;
    }

    public void setOrderPicVos(List<OrderPicVosBean> orderPicVos) {
        this.orderPicVos = orderPicVos;
    }

    public static class OrderPicVosBean {
        /**
         * id : 120
         * type : 1
         * picUrl : c:/sjhadj/asda/sdf
         * createTime : null
         */

        private int id;
        private int type;
        private String picUrl;
        private Object createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }
}
