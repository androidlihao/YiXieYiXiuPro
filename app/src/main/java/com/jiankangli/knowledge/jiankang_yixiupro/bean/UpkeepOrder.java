package com.jiankangli.knowledge.jiankang_yixiupro.bean;

/**
 * Created by 李浩 on 2018/6/26.
 */

public class UpkeepOrder {

    /**
     * id : 1216
     * hospitalName : 北京慈铭奥亚月坛门诊部有限公司
     * orderStatus : null
     * reportTime : 2018-06-19 09:08:32
     * factoryNo :
     * repairStatus : 保养
     * deviceNo : 2000000093
     * bookTime : 2018-06-19 09:50~10:00
     * auditStatus : null
     * serviceConfirmStatus : null
     * listStatus : 5
     */

    private int id;
    private String hospitalName;
    private Object orderStatus;
    private String reportTime;
    private String factoryNo;
    private String repairStatus;
    private String deviceNo;
    private String bookTime;
    private Object auditStatus;
    private Object serviceConfirmStatus;
    private int listStatus;

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

    public Object getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Object orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
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

    public Object getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Object auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Object getServiceConfirmStatus() {
        return serviceConfirmStatus;
    }

    public void setServiceConfirmStatus(Object serviceConfirmStatus) {
        this.serviceConfirmStatus = serviceConfirmStatus;
    }

    public int getListStatus() {
        return listStatus;
    }

    public void setListStatus(int listStatus) {
        this.listStatus = listStatus;
    }
}
