package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-09-28 15:43
 * @description :
 */
public class fixRecordBean {

    /**
     * id : 1114
     * orderNo : 20170919004
     * faultType : 0
     * accessoryName : 发发发,gdhdhw,规划好风光,官方 v 个人过,白天不方便,spidermain
     * arrivalTime : null
     * serviceRecodeVos : [{"serviceTime":"123","startTime":"123","endTime":"123","roadTime":123,"equipmentStatus":1},{"serviceTime":"213","startTime":"12","endTime":"123","roadTime":213,"equipmentStatus":1}]
     */

    private int id;
    private String orderNo;
    private int faultType;
    private String accessoryName;
    private String arrivalTime;
    private List<ServiceRecodeVosBean> serviceRecodeVos;

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

    public List<ServiceRecodeVosBean> getServiceRecodeVos() {
        return serviceRecodeVos;
    }

    public void setServiceRecodeVos(List<ServiceRecodeVosBean> serviceRecodeVos) {
        this.serviceRecodeVos = serviceRecodeVos;
    }

    public static class ServiceRecodeVosBean {
        /**
         * serviceTime : 123
         * startTime : 123
         * endTime : 123
         * roadTime : 123
         * equipmentStatus : 1
         */

        private String serviceTime;
        private String startTime;
        private String endTime;
        private String roadTime;
        private int equipmentStatus=1;

        public String getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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
    }
}
