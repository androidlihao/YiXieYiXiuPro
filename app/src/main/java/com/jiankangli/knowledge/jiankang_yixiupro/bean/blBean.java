package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-11-09 21:07
 * @description :维修补录请求参数实体类
 */
public class blBean {

    /**
     * deviceNo : 9000000002
     * userId : 305
     * engineerName : spiderMain
     * engineerPhone : 1325462544
     * arrivalTime : 2018-6-6 9:59
     * faultType : 1
     * whetherExamine : 1
     * serviceRecodeVos : [{"serviceTime":"2018-6-6","startTime":"14:07","endTime":"16:07","roadTime":2.3,"equipmentStatus":1},{"serviceTime":"2018-6-7","startTime":"14:07","endTime":"16:07","roadTime":2.3,"equipmentStatus":1},{"serviceTime":"2018-6-8","startTime":"14:07","endTime":"16:07","roadTime":2.3,"equipmentStatus":1}]
     * electronOrderVos : {"orderPicVos":[{"type":2,"picUrl":"/asjdhjas/sadajh/asd"},{"type":3,"picUrl":"/asjdhjas/sadajh/asd"},{"type":4,"picUrl":"/asjdhjas/sadajh/asd"},{"type":5,"picUrl":"/asjdhjas/sadajh/asd"}],"serviceReasons":"服务原因","serviceContent":"服务内容","serviceResults":"服务结果","serviceAdvice":"意见不错！","serviceEnd1":1,"bulbBrand":"品牌","bulbModel":"型号","bulbHeatCapacity":"热容量","bulbNumberRecords":10,"bulbInspectionAmount":20,"travelToTime":1.2,"travelBackTime":2}
     * spareParts : [{"accessoryName":"备件1","accessoryNo":"001","number":10,"accRemark":"备件的描述！"},{"accessoryName":"备件2","accessoryNo":"002","number":10,"accRemark":"备件的描述！"},{"accessoryName":"备件3","accessoryNo":"003","number":10,"accRemark":"备件的描述！"}]
     */

    private String deviceNo;
    private int userId;
    private String engineerName;
    private String engineerPhone;
    private String arrivalTime;
    private String leaveTime;//离开场地时间
    private int faultType;
    private int whetherExamine;
    private ElectronOrderVosBean electronOrderVos;
    private List<ServiceRecodeVosBean> serviceRecodeVos;
    private List<SparePartsBean> spareParts;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getEngineerPhone() {
        return engineerPhone;
    }

    public void setEngineerPhone(String engineerPhone) {
        this.engineerPhone = engineerPhone;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getFaultType() {
        return faultType;
    }

    public void setFaultType(int faultType) {
        this.faultType = faultType;
    }

    public int getWhetherExamine() {
        return whetherExamine;
    }

    public void setWhetherExamine(int whetherExamine) {
        this.whetherExamine = whetherExamine;
    }

    public ElectronOrderVosBean getElectronOrderVos() {
        return electronOrderVos;
    }

    public void setElectronOrderVos(ElectronOrderVosBean electronOrderVos) {
        this.electronOrderVos = electronOrderVos;
    }

    public List<ServiceRecodeVosBean> getServiceRecodeVos() {
        return serviceRecodeVos;
    }

    public void setServiceRecodeVos(List<ServiceRecodeVosBean> serviceRecodeVos) {
        this.serviceRecodeVos = serviceRecodeVos;
    }

    public List<SparePartsBean> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(List<SparePartsBean> spareParts) {
        this.spareParts = spareParts;
    }

    public static class ElectronOrderVosBean {
        /**
         * orderPicVos : [{"type":2,"picUrl":"/asjdhjas/sadajh/asd"},{"type":3,"picUrl":"/asjdhjas/sadajh/asd"},{"type":4,"picUrl":"/asjdhjas/sadajh/asd"},{"type":5,"picUrl":"/asjdhjas/sadajh/asd"}]
         * serviceReasons : 服务原因
         * serviceContent : 服务内容
         * serviceResults : 服务结果
         * serviceAdvice : 意见不错！
         * serviceEnd1 : 1
         * bulbBrand : 品牌
         * bulbModel : 型号
         * bulbHeatCapacity : 热容量
         * bulbNumberRecords : 10
         * bulbInspectionAmount : 20
         * travelToTime : 1.2
         * travelBackTime : 2.0
         */

        private String serviceReasons;
        private String serviceContent;
        private String serviceResults;
        private String serviceAdvice;
        private int serviceEnd1;
        private String bulbBrand;
        private String bulbModel;
        private String bulbHeatCapacity;
        private int bulbNumberRecords;
        private int bulbInspectionAmount;
        private double travelToTime;
        private double travelBackTime;
        private List<OrderPicVosBean> orderPicVos;

        public String getServiceReasons() {
            return serviceReasons;
        }

        public void setServiceReasons(String serviceReasons) {
            this.serviceReasons = serviceReasons;
        }

        public String getServiceContent() {
            return serviceContent;
        }

        public void setServiceContent(String serviceContent) {
            this.serviceContent = serviceContent;
        }

        public String getServiceResults() {
            return serviceResults;
        }

        public void setServiceResults(String serviceResults) {
            this.serviceResults = serviceResults;
        }

        public String getServiceAdvice() {
            return serviceAdvice;
        }

        public void setServiceAdvice(String serviceAdvice) {
            this.serviceAdvice = serviceAdvice;
        }

        public int getServiceEnd1() {
            return serviceEnd1;
        }

        public void setServiceEnd1(int serviceEnd1) {
            this.serviceEnd1 = serviceEnd1;
        }

        public String getBulbBrand() {
            return bulbBrand;
        }

        public void setBulbBrand(String bulbBrand) {
            this.bulbBrand = bulbBrand;
        }

        public String getBulbModel() {
            return bulbModel;
        }

        public void setBulbModel(String bulbModel) {
            this.bulbModel = bulbModel;
        }

        public String getBulbHeatCapacity() {
            return bulbHeatCapacity;
        }

        public void setBulbHeatCapacity(String bulbHeatCapacity) {
            this.bulbHeatCapacity = bulbHeatCapacity;
        }

        public int getBulbNumberRecords() {
            return bulbNumberRecords;
        }

        public void setBulbNumberRecords(int bulbNumberRecords) {
            this.bulbNumberRecords = bulbNumberRecords;
        }

        public int getBulbInspectionAmount() {
            return bulbInspectionAmount;
        }

        public void setBulbInspectionAmount(int bulbInspectionAmount) {
            this.bulbInspectionAmount = bulbInspectionAmount;
        }

        public double getTravelToTime() {
            return travelToTime;
        }

        public void setTravelToTime(double travelToTime) {
            this.travelToTime = travelToTime;
        }

        public double getTravelBackTime() {
            return travelBackTime;
        }

        public void setTravelBackTime(double travelBackTime) {
            this.travelBackTime = travelBackTime;
        }

        public List<OrderPicVosBean> getOrderPicVos() {
            return orderPicVos;
        }

        public void setOrderPicVos(List<OrderPicVosBean> orderPicVos) {
            this.orderPicVos = orderPicVos;
        }

        public static class OrderPicVosBean {
            /**
             * type : 2
             * picUrl : /asjdhjas/sadajh/asd
             */

            private int type;
            private String picUrl;

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
        }
    }

    public static class ServiceRecodeVosBean {
        /**
         * serviceTime : 2018-6-6
         * startTime : 14:07
         * endTime : 16:07
         * roadTime : 2.3
         * equipmentStatus : 1
         */

        private String serviceTime;
        private String startTime;
        private String endTime;
        private String roadTime;
        private int equipmentStatus;

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

    public static class SparePartsBean {
        /**
         * accessoryName : 备件1
         * accessoryNo : 001
         * number : 10
         * accRemark : 备件的描述！
         */

        private String accessoryName;
        private String accessoryNo;
        private int number=0;
        private String accRemark;

        public String getAccessoryName() {
            return accessoryName;
        }

        public void setAccessoryName(String accessoryName) {
            this.accessoryName = accessoryName;
        }

        public String getAccessoryNo() {
            return accessoryNo;
        }

        public void setAccessoryNo(String accessoryNo) {
            this.accessoryNo = accessoryNo;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getAccRemark() {
            return accRemark;
        }

        public void setAccRemark(String accRemark) {
            this.accRemark = accRemark;
        }
    }
}
