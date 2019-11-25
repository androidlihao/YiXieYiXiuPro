package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-11-25 19:55
 * @description :补录保养实体类
 */
public class pollingBlBean {

    /**
     * userId : 305
     * hospitaAddress : 河南 平顶山 舞钢市 舞钢市健康路中段地址
     * contractNo : null
     * deviceModel : GE 0.35T Ovation
     * sectionName : 磁共振室
     * deviceName : GEMR
     * deviceBrand : GE
     * deviceNo : 9000000002
     * name : 张飞
     * whetherExamine : 1
     * workTemplateVos : [{"templateId":1,"templateTypeId":1,"templateInfoId":4,"templateTypeAndTemplateVos":[{"id":40,"content":"2018-9-10 测试1"},{"id":41,"content":"2018-9-10 测试2"}]},{"templateId":1,"templateTypeId":1,"templateInfoId":4,"templateTypeAndTemplateVos":[{"id":42,"content":"2018-9-10 测试3"},{"id":43,"content":"2018-9-10 测试4"}]}]
     * servicingTime : 1
     * orderPicVo : {"type":5,"picUrl":"c:/sdjh/补录"}
     * travelToTime : 1.2
     * travelBackTime : 3.2
     * leaveTime : 2018-7-19 12:00
     * serviceRecodeVos : [{"serviceTime":"1:05","startTime":"9:25","endTime":"12:00","roadTime":1.2},{"serviceTime":"1:00","startTime":"13:00","endTime":"15:00","roadTime":2}]
     */

    private int userId;
    private String hospitaAddress;
    private Object contractNo;
    private String deviceModel;
    private String sectionName;
    private String deviceName;
    private String deviceBrand;
    private String deviceNo;
    private String name;
    private int whetherExamine;
    private int servicingTime;
    private OrderPicVoBean orderPicVo;
    private double travelToTime;
    private double travelBackTime;
    private String leaveTime;
    private List<WorkTemplateVosBean> workTemplateVos;
    private List<ServiceRecodeVosBean> serviceRecodeVos;
    private String hospitalName;
    private String arrivalTime;

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHospitaAddress() {
        return hospitaAddress;
    }

    public void setHospitaAddress(String hospitaAddress) {
        this.hospitaAddress = hospitaAddress;
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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWhetherExamine() {
        return whetherExamine;
    }

    public void setWhetherExamine(int whetherExamine) {
        this.whetherExamine = whetherExamine;
    }

    public int getServicingTime() {
        return servicingTime;
    }

    public void setServicingTime(int servicingTime) {
        this.servicingTime = servicingTime;
    }

    public OrderPicVoBean getOrderPicVo() {
        return orderPicVo;
    }

    public void setOrderPicVo(OrderPicVoBean orderPicVo) {
        this.orderPicVo = orderPicVo;
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

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public List<WorkTemplateVosBean> getWorkTemplateVos() {
        return workTemplateVos;
    }

    public void setWorkTemplateVos(List<WorkTemplateVosBean> workTemplateVos) {
        this.workTemplateVos = workTemplateVos;
    }

    public List<ServiceRecodeVosBean> getServiceRecodeVos() {
        return serviceRecodeVos;
    }

    public void setServiceRecodeVos(List<ServiceRecodeVosBean> serviceRecodeVos) {
        this.serviceRecodeVos = serviceRecodeVos;
    }

    public static class OrderPicVoBean {
        /**
         * type : 5
         * picUrl : c:/sdjh/补录
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

    public static class WorkTemplateVosBean {
        /**
         * templateId : 1
         * templateTypeId : 1
         * templateInfoId : 4
         * templateTypeAndTemplateVos : [{"id":40,"content":"2018-9-10 测试1"},{"id":41,"content":"2018-9-10 测试2"}]
         */

        private int templateId;
        private int templateTypeId;
        private int templateInfoId;
        private String title;
        private String subtitle;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        private List<TemplateTypeAndTemplateVosBean> templateTypeAndTemplateVos;

        public int getTemplateId() {
            return templateId;
        }

        public void setTemplateId(int templateId) {
            this.templateId = templateId;
        }

        public int getTemplateTypeId() {
            return templateTypeId;
        }

        public void setTemplateTypeId(int templateTypeId) {
            this.templateTypeId = templateTypeId;
        }

        public int getTemplateInfoId() {
            return templateInfoId;
        }

        public void setTemplateInfoId(int templateInfoId) {
            this.templateInfoId = templateInfoId;
        }

        public List<TemplateTypeAndTemplateVosBean> getTemplateTypeAndTemplateVos() {
            return templateTypeAndTemplateVos;
        }

        public void setTemplateTypeAndTemplateVos(List<TemplateTypeAndTemplateVosBean> templateTypeAndTemplateVos) {
            this.templateTypeAndTemplateVos = templateTypeAndTemplateVos;
        }

        public static class TemplateTypeAndTemplateVosBean {
            /**
             * id : 40
             * content : 2018-9-10 测试1
             */

            private int id;
            private String content;
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }

    public static class ServiceRecodeVosBean {
        /**
         * serviceTime : 1:05
         * startTime : 9:25
         * endTime : 12:00
         * roadTime : 1.2
         */

        private String serviceTime;
        private String startTime;
        private String endTime;
        private String roadTime;

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
    }
}
