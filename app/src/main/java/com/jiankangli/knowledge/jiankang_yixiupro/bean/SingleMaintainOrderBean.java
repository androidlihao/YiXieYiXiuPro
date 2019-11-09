package com.jiankangli.knowledge.jiankang_yixiupro.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * @author lihao
 * @date 2019-10-23 17:22
 * @description :回显信息
 */
public class SingleMaintainOrderBean {

    /**
     * id : 289
     * servicingTime : 2
     * softwareVersion : 你名字
     * patientFlow : 2
     * loadingTime : 2019-10-15
     * workOrder : {"workOrderId":0,"userId":0,"id":1513,"deviceModel":"GELogiq 7","startTime":null}
     * problemHandling : 来咯咯
     * engineerSignedTime : null
     * workTemplateVos : [{"templateInfoId":137,"templateId":7,"templateTypeId":1,"title":"模版 1","subtitle":"","id":null,"workTemplateId":null,"name":null,"content":null,"infoId":137,"templateTypeAndTemplateVos":[{"id":101,"workTemplateId":3518,"name":"AN(V)","content":null},{"id":102,"workTemplateId":3519,"name":"BN(V)","content":null},{"id":103,"workTemplateId":3520,"name":"CN(V)","content":null},{"id":104,"workTemplateId":3521,"name":"结果","content":"0"}]},{"templateInfoId":138,"templateId":7,"templateTypeId":2,"title":"模版 2","subtitle":"","id":null,"workTemplateId":null,"name":null,"content":null,"infoId":138,"templateTypeAndTemplateVos":[{"id":105,"workTemplateId":3522,"name":"温度(℃)","content":null},{"id":106,"workTemplateId":3523,"name":"湿度(%)","content":null},{"id":107,"workTemplateId":3524,"name":"结果","content":"0"}]},{"templateInfoId":139,"templateId":7,"templateTypeId":3,"title":"模版 3","subtitle":"","id":null,"workTemplateId":null,"name":null,"content":null,"infoId":139,"templateTypeAndTemplateVos":[{"id":108,"workTemplateId":3525,"name":"","content":null},{"id":109,"workTemplateId":3526,"name":"结果","content":"0"}]},{"templateInfoId":140,"templateId":7,"templateTypeId":4,"title":"模版 4","subtitle":"","id":null,"workTemplateId":null,"name":null,"content":null,"infoId":140,"templateTypeAndTemplateVos":[{"id":110,"workTemplateId":3527,"name":null,"content":null}]},{"templateInfoId":141,"templateId":7,"templateTypeId":5,"title":"模版 5","subtitle":"还行吧","id":null,"workTemplateId":null,"name":null,"content":null,"infoId":141,"templateTypeAndTemplateVos":[{"id":111,"workTemplateId":3528,"name":"结果","content":"0"},{"id":118,"workTemplateId":3529,"name":null,"content":null}]},{"templateInfoId":142,"templateId":7,"templateTypeId":6,"title":"模版 6","subtitle":"","id":null,"workTemplateId":null,"name":null,"content":null,"infoId":142,"templateTypeAndTemplateVos":[{"id":112,"workTemplateId":3530,"name":"Type(K)","content":null},{"id":113,"workTemplateId":3531,"name":"含量(%)","content":null},{"id":114,"workTemplateId":3532,"name":"结果","content":"0"}]}]
     * orderPicVo : {"id":37834,"type":5,"picUrl":"http://192.168.1.3//images/201910/a0ad85cf-fda3-4656-b3b9-648d91016c61.jpg","createTime":null}
     * travelToTime : 2.0
     * travelBackTime : 3.0
     * leaveTime : 2019-10-15 21:55
     * hospitalName : null
     */
    @Id
    private int id;
    private int servicingTime;
    private String softwareVersion;
    private int patientFlow;
    private String loadingTime;
    private WorkOrderBean workOrder;
    private String problemHandling;
    private Object engineerSignedTime;
    private OrderPicVoBean orderPicVo;
    private double travelToTime;
    private double travelBackTime;
    private String leaveTime;
    private Object hospitalName;
    private List<WorkTemplateVosBean> workTemplateVos;
    private String DeviceModel;

    public String getDeviceModel() {
        return DeviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        DeviceModel = deviceModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public WorkOrderBean getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderBean workOrder) {
        this.workOrder = workOrder;
    }

    public String getProblemHandling() {
        return problemHandling;
    }

    public void setProblemHandling(String problemHandling) {
        this.problemHandling = problemHandling;
    }

    public Object getEngineerSignedTime() {
        return engineerSignedTime;
    }

    public void setEngineerSignedTime(Object engineerSignedTime) {
        this.engineerSignedTime = engineerSignedTime;
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

    public Object getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(Object hospitalName) {
        this.hospitalName = hospitalName;
    }

    public List<WorkTemplateVosBean> getWorkTemplateVos() {
        return workTemplateVos;
    }

    public void setWorkTemplateVos(List<WorkTemplateVosBean> workTemplateVos) {
        this.workTemplateVos = workTemplateVos;
    }

    public static class WorkOrderBean {
        /**
         * workOrderId : 0
         * userId : 0
         * id : 1513
         * deviceModel : GELogiq 7
         * startTime : null
         */

        private int workOrderId;
        private int userId;
        private int id;
        private String deviceModel;
        private Object startTime;

        public int getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(int workOrderId) {
            this.workOrderId = workOrderId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }
    }

    public static class OrderPicVoBean {
        /**
         * id : 37834
         * type : 5
         * picUrl : http://192.168.1.3//images/201910/a0ad85cf-fda3-4656-b3b9-648d91016c61.jpg
         * createTime : null
         */

        private int id;
        private int type;
        private String picUrl;
        private Object createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }

    public static class WorkTemplateVosBean {
        /**
         * templateInfoId : 137
         * templateId : 7
         * templateTypeId : 1
         * title : 模版 1
         * subtitle :
         * id : null
         * workTemplateId : null
         * name : null
         * content : null
         * infoId : 137
         * templateTypeAndTemplateVos : [{"id":101,"workTemplateId":3518,"name":"AN(V)","content":null},{"id":102,"workTemplateId":3519,"name":"BN(V)","content":null},{"id":103,"workTemplateId":3520,"name":"CN(V)","content":null},{"id":104,"workTemplateId":3521,"name":"结果","content":"0"}]
         */

        private int templateInfoId;
        private int templateId;
        private int templateTypeId;
        private String title;
        private String subtitle;
        private Object id;
        private Object workTemplateId;
        private Object name;
        private Object content;
        private int infoId;
        private List<TemplateTypeAndTemplateVosBean> templateTypeAndTemplateVos;

        public int getTemplateInfoId() {
            return templateInfoId;
        }

        public void setTemplateInfoId(int templateInfoId) {
            this.templateInfoId = templateInfoId;
        }

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

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getWorkTemplateId() {
            return workTemplateId;
        }

        public void setWorkTemplateId(Object workTemplateId) {
            this.workTemplateId = workTemplateId;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public int getInfoId() {
            return infoId;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
        }

        public List<TemplateTypeAndTemplateVosBean> getTemplateTypeAndTemplateVos() {
            return templateTypeAndTemplateVos;
        }

        public void setTemplateTypeAndTemplateVos(List<TemplateTypeAndTemplateVosBean> templateTypeAndTemplateVos) {
            this.templateTypeAndTemplateVos = templateTypeAndTemplateVos;
        }

        public static class TemplateTypeAndTemplateVosBean {
            /**
             * id : 101
             * workTemplateId : 3518
             * name : AN(V)
             * content : null
             */

            private int id;
            private int workTemplateId;
            private String name;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getWorkTemplateId() {
                return workTemplateId;
            }

            public void setWorkTemplateId(int workTemplateId) {
                this.workTemplateId = workTemplateId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
