package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * @author lihao
 * @date 2019-11-14 16:51
 * @description :保养补录请求参数实体类
 * */
public class upkeepBlBean {

    /**
     * whetherExamine : 1
     * deviceNo : 9000000002
     * engineerName : spiderMain
     * engineerPhone : 1325462544
     * arrivalTime : 2018-6-6 9:59
     * hospitaAddress : 北京市 朝阳区  术间
     * contractNo : 055421522
     * deviceModel : T5
     * sectionName : 手术室
     * deviceName : 超声
     * deviceBrand : 迈瑞
     * name : 吴爽
     * servicerName : 李来云
     * userId : 305
     * servicingTime : 1
     * softwareVersion : 1.0.1
     * patientFlow : 50
     * problemHandling : 有问题
     * orderPicVo : {"type":5,"picUrl":"c:/skdjak/csds/sdsa"}
     * workTemplateVos : [{"templateId":1,"templateTypeId":1,"templateInfoId":4,"templateTypeAndTemplateVos":[{"id":40,"content":"2018-9-10 测试1"},{"id":41,"content":"2018-9-10 测试2"}]},{"templateId":1,"templateTypeId":1,"templateInfoId":4,"templateTypeAndTemplateVos":[{"id":42,"content":"2018-9-10 测试3"},{"id":43,"content":"2018-9-10 测试4"}]}]
     * travelToTime : 1.2
     * travelBackTime : 3.2
     * leaveTime : 2018-7-19 12:00
     * serviceRecodeVos : [{"serviceTime":"1:05","startTime":"9:25","endTime":"12:00","roadTime":1.2},{"serviceTime":"1:00","startTime":"13:00","endTime":"15:00","roadTime":2}]
     */

    private int whetherExamine;
    private String deviceNo;
    private String engineerName;
    private String engineerPhone;
    private String arrivalTime;
    private String hospitaAddress;
    private String contractNo;
    private String deviceModel;
    private String sectionName;
    private String deviceName;
    private String deviceBrand;
    private String name;
    private String servicerName;
    private int userId;
    private int servicingTime;
    private String softwareVersion;
    private int patientFlow;
    private String problemHandling;
    private OrderPicVoBean orderPicVo;
    private double travelToTime;
    private double travelBackTime;
    private String leaveTime;
    private String loadingTime;//装机日期
    private String hospitalName;//医院名称
    private String templateCode;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    private List<WorkTemplateVosBean> workTemplateVos;
    private List<ServiceRecodeVosBean> serviceRecodeVos;

    public int getWhetherExamine() {
        return whetherExamine;
    }

    public void setWhetherExamine(int whetherExamine) {
        this.whetherExamine = whetherExamine;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
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

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getHospitaAddress() {
        return hospitaAddress;
    }

    public void setHospitaAddress(String hospitaAddress) {
        this.hospitaAddress = hospitaAddress;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServicerName() {
        return servicerName;
    }

    public void setServicerName(String servicerName) {
        this.servicerName = servicerName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServicingTime() {
        return servicingTime;
    }

    public void setServicingTime(int servicingTime) {
        this.servicingTime = servicingTime;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public int getPatientFlow() {
        return patientFlow;
    }

    public void setPatientFlow(int patientFlow) {
        this.patientFlow = patientFlow;
    }

    public String getProblemHandling() {
        return problemHandling;
    }

    public void setProblemHandling(String problemHandling) {
        this.problemHandling = problemHandling;
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
         * picUrl : c:/skdjak/csds/sdsa
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
