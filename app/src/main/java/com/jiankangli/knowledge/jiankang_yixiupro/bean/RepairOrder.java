package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.io.Serializable;

/**
 * Created by 李浩 on 2018/6/21.
 */

public class RepairOrder implements Serializable {


    /**
     * id : 1213
     * hospitalName : 北京慈铭奥亚月坛门诊部有限公司
     * orderStatus : null
     * reportTime : 2018-06-14 11:09:21
     * factoryNo :
     * repairStatus : 维修
     * deviceNo : 2000000094
     * auditStatus : null
     * serviceConfirmStatus : null
     * listStatus : 2
     */

    private int id;
    private String hospitalName;
    private Object orderStatus;
    private String reportTime;
    private String factoryNo;
    private String repairStatus;
    private String deviceNo;
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
