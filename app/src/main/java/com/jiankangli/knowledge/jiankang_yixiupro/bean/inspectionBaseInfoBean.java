package com.jiankangli.knowledge.jiankang_yixiupro.bean;

/**
 * @author lihao
 * @date 2019-11-09 15:55
 * @description :
 */
public class inspectionBaseInfoBean {

    /**
     * hospitalName : 解放军425医院
     * factoryNo : 123
     * workNumber : 2018007
     * orderNo : 20180928022-2
     * hospitaAddress : 海南 三亚 三亚市 三亚湾路80号
     * projectCode : 123
     * contractNo : 123124
     * deviceModel : GELogiq 7
     * sectionName : 测试
     * devicePhone : 13876668327
     * devicePhoneArray : {}
     * deviceName : GEUS
     * deviceBrand : GE
     * deviceNo : 7000000003
     * bookTime : 2019-11-06 23:30~23:30
     * servicerName : 业务人员
     * contractStatus : null
     * repairType : 单次维系
     */

    private String hospitalName;
    private String factoryNo;
    private String workNumber;
    private String orderNo;
    private String hospitaAddress;
    private String projectCode;
    private String contractNo;
    private String deviceModel;
    private String sectionName;
    private String devicePhone;
    private DevicePhoneArrayBean devicePhoneArray;
    private String deviceName;
    private String deviceBrand;
    private String deviceNo;
    private String bookTime;
    private String servicerName;
    private Object contractStatus;
    private String repairType;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
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

    public String getHospitaAddress() {
        return hospitaAddress;
    }

    public void setHospitaAddress(String hospitaAddress) {
        this.hospitaAddress = hospitaAddress;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getDevicePhone() {
        return devicePhone;
    }

    public void setDevicePhone(String devicePhone) {
        this.devicePhone = devicePhone;
    }

    public DevicePhoneArrayBean getDevicePhoneArray() {
        return devicePhoneArray;
    }

    public void setDevicePhoneArray(DevicePhoneArrayBean devicePhoneArray) {
        this.devicePhoneArray = devicePhoneArray;
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

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getServicerName() {
        return servicerName;
    }

    public void setServicerName(String servicerName) {
        this.servicerName = servicerName;
    }

    public Object getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Object contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public static class DevicePhoneArrayBean {
    }
}
