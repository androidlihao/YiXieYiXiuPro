package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.io.Serializable;

/**
 * Created by 李浩 on 2018/6/27.
 */

public class PollingOrder implements Serializable {

    /**
     * id : 1218
     * hospitalName : 舞钢市职工医院
     * orderStatus : 2
     * reportTime : 2018-06-19 14:45:22
     * factoryNo :
     * auditStatus : 0
     * serviceConfirmStatus : null
     * listStatus : 2
     * deviceNo : 9000000002
     * repairStatus : 巡检
     * bookTime : 2018-06-20 14:40~14:45
     */

    private int id;
    private String hospitalName;
    private int orderStatus;
    private String reportTime;
    private String factoryNo;
    private int auditStatus;
    private Object serviceConfirmStatus;
    private int listStatus;
    private String deviceNo;
    private String repairStatus;
    private String bookTime;

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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
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

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
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

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }
}
