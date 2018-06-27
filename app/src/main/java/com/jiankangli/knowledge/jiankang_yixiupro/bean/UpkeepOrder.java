package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/26.
 */

public class UpkeepOrder {

    /**
     * code : 200
     * time : 2018-06-19 15:53:29
     * data : [{"id":1215,"hospitalName":"测试数据003","orderStatus":3,"reportTime":"2018-06-14 19:47:42","repairStatus":"保养","factoryNo":"测试数据003","deviceNo":"2000000091","auditStatus":0,"serviceConfirmStatus":1,"listStatus":2},{"id":1216,"hospitalName":"北京慈铭奥亚月坛门诊部有限公司","orderStatus":3,"reportTime":"2018-06-19 09:08:32","repairStatus":"保养","factoryNo":"","deviceNo":"2000000093","auditStatus":1,"serviceConfirmStatus":0,"listStatus":"正在审核"},{"id":1217,"hospitalName":null,"orderStatus":3,"reportTime":null,"repairStatus":"保养","factoryNo":"","deviceNo":"9000000002","auditStatus":0,"serviceConfirmStatus":1,"listStatus":"正在维修||保养||巡检"}]
     * msg : 查询成功！
     */

    private String code;
    private String time;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1215
         * hospitalName : 测试数据003
         * orderStatus : 3
         * reportTime : 2018-06-14 19:47:42
         * repairStatus : 保养
         * factoryNo : 测试数据003
         * deviceNo : 2000000091
         * auditStatus : 0
         * serviceConfirmStatus : 1
         * listStatus : 2
         */
        private int bookTime;
        private int id;
        private String hospitalName;
        private int orderStatus;
        private String reportTime;
        private String repairStatus;
        private String factoryNo;
        private String deviceNo;
        private int auditStatus;
        private int serviceConfirmStatus;
        private int listStatus;

        public int getBookTime() {
            return bookTime;
        }

        public void setBookTime(int bookTime) {
            this.bookTime = bookTime;
        }

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

        public String getRepairStatus() {
            return repairStatus;
        }

        public void setRepairStatus(String repairStatus) {
            this.repairStatus = repairStatus;
        }

        public String getFactoryNo() {
            return factoryNo;
        }

        public void setFactoryNo(String factoryNo) {
            this.factoryNo = factoryNo;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getServiceConfirmStatus() {
            return serviceConfirmStatus;
        }

        public void setServiceConfirmStatus(int serviceConfirmStatus) {
            this.serviceConfirmStatus = serviceConfirmStatus;
        }

        public int getListStatus() {
            return listStatus;
        }

        public void setListStatus(int listStatus) {
            this.listStatus = listStatus;
        }
    }
}
