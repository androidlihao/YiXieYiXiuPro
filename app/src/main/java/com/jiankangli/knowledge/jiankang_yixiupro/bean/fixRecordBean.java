package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-09-28 15:43
 * @description :
 */
public class fixRecordBean {


    /**
     * id : 1511
     * orderNo : 20191015001
     * faultType : 3
     * accessoryName : null
     * arrivalTime : 2019-10-15 23:29
     * serviceRecordMapArray : [{"startTime":"23:29","id":369,"serviceTime":"2019-10-15","roadTime":2,"equipmentStatus":2,"endTime":"23:31"}]
     */

    private int id;
    private String orderNo;
    private int faultType;
    private String accessoryName;
    private String arrivalTime;
    private List<ServiceRecordMapArrayBean> serviceRecordMapArray;

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

    public int getFaultType() {
        return faultType;
    }

    public void setFaultType(int faultType) {
        this.faultType = faultType;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<ServiceRecordMapArrayBean> getServiceRecordMapArray() {
        return serviceRecordMapArray;
    }

    public void setServiceRecordMapArray(List<ServiceRecordMapArrayBean> serviceRecordMapArray) {
        this.serviceRecordMapArray = serviceRecordMapArray;
    }

    public static class ServiceRecordMapArrayBean {
        /**
         * startTime : 23:29
         * id : 369
         * serviceTime : 2019-10-15
         * roadTime : 2.0
         * equipmentStatus : 2
         * endTime : 23:31
         */

        private String startTime;
        private int id;
        private String serviceTime;
        private String roadTime;
        private int equipmentStatus;
        private String endTime;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public String getRoadTime() {
            return roadTime;
        }

        public void setRoadTime(String roadTime) {
            this.roadTime = roadTime;
        }

        public int getEquipmentStatus() {
            return equipmentStatus;
        }

        public void setEquipmentStatus(int equipmentStatus) {
            this.equipmentStatus = equipmentStatus;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
