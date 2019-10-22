package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-10-15 23:03
 * @description :
 */
public class maintainOrderRecordBean {

    /**
     * id : 1213
     * orderNo : 20180614001
     * accessoryName : null
     * arrivalTime : 12:15
     * serviceRecordMapArray : [{"startTime":"9:25","id":83,"serviceTime":"2018-07-18","roadTime":1.2,"endTime":"12:00"},{"startTime":"13:00","id":84,"serviceTime":"2018-07-19","roadTime":2,"endTime":"15:00"},{"startTime":"9:25","id":85,"serviceTime":"1:05","roadTime":1.2,"endTime":"12:00"},{"startTime":"13:00","id":86,"serviceTime":"1:00","roadTime":2,"endTime":"15:00"}]
     */

    private int id;
    private String orderNo;
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
         * startTime : 9:25
         * id : 83
         * serviceTime : 2018-07-18
         * roadTime : 1.2
         * endTime : 12:00
         */

        private String startTime;
        private int id;
        private String serviceTime;
        private String roadTime;
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

        public void setRoadTime(String  roadTime) {
            this.roadTime = roadTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
