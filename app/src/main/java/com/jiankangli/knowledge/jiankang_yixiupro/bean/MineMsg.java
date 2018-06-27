package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/20.
 */

public class MineMsg {

    /**
     * code : success
     * data : {"list":[{"content":"刚刚","customerNews":"","egginerUseflag":"1","egginerUserId":383,"id":25,"isTrue":"1","orderNo":"20180506001","originatorId":381,"originatorName":"Guohang","recvieName":"郭航","sendTime":"2018-06-14 13:47:38","tChatDetails":[],"type":"1","useflag":"1","workOrderId":1147,"workOrderStatus":"3","yesOrNo":"0"},{"content":"徐徐","customerNews":"","egginerUseflag":"1","egginerUserId":383,"id":24,"isTrue":"1","orderNo":"20180508001","originatorId":381,"originatorName":"Guohang","recvieName":"郭航","sendTime":"2018-06-11 19:25:48","tChatDetails":[],"type":"1","useflag":"1","workOrderId":1148,"workOrderStatus":"3","yesOrNo":"0"}]}
     * msg : 成功
     * time : 2018-06-27 05:25:45
     */

    private String code;
    private DataBean data;
    private String msg;
    private String time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * content : 刚刚
             * customerNews :
             * egginerUseflag : 1
             * egginerUserId : 383
             * id : 25
             * isTrue : 1
             * orderNo : 20180506001
             * originatorId : 381
             * originatorName : Guohang
             * recvieName : 郭航
             * sendTime : 2018-06-14 13:47:38
             * tChatDetails : []
             * type : 1
             * useflag : 1
             * workOrderId : 1147
             * workOrderStatus : 3
             * yesOrNo : 0
             */

            private String content;
            private String customerNews;
            private String egginerUseflag;
            private int egginerUserId;
            private int id;
            private String isTrue;
            private String orderNo;
            private int originatorId;
            private String originatorName;
            private String recvieName;
            private String sendTime;
            private String type;
            private String useflag;
            private int workOrderId;
            private String workOrderStatus;
            private String yesOrNo;
            private List<?> tChatDetails;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCustomerNews() {
                return customerNews;
            }

            public void setCustomerNews(String customerNews) {
                this.customerNews = customerNews;
            }

            public String getEgginerUseflag() {
                return egginerUseflag;
            }

            public void setEgginerUseflag(String egginerUseflag) {
                this.egginerUseflag = egginerUseflag;
            }

            public int getEgginerUserId() {
                return egginerUserId;
            }

            public void setEgginerUserId(int egginerUserId) {
                this.egginerUserId = egginerUserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIsTrue() {
                return isTrue;
            }

            public void setIsTrue(String isTrue) {
                this.isTrue = isTrue;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getOriginatorId() {
                return originatorId;
            }

            public void setOriginatorId(int originatorId) {
                this.originatorId = originatorId;
            }

            public String getOriginatorName() {
                return originatorName;
            }

            public void setOriginatorName(String originatorName) {
                this.originatorName = originatorName;
            }

            public String getRecvieName() {
                return recvieName;
            }

            public void setRecvieName(String recvieName) {
                this.recvieName = recvieName;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUseflag() {
                return useflag;
            }

            public void setUseflag(String useflag) {
                this.useflag = useflag;
            }

            public int getWorkOrderId() {
                return workOrderId;
            }

            public void setWorkOrderId(int workOrderId) {
                this.workOrderId = workOrderId;
            }

            public String getWorkOrderStatus() {
                return workOrderStatus;
            }

            public void setWorkOrderStatus(String workOrderStatus) {
                this.workOrderStatus = workOrderStatus;
            }

            public String getYesOrNo() {
                return yesOrNo;
            }

            public void setYesOrNo(String yesOrNo) {
                this.yesOrNo = yesOrNo;
            }

            public List<?> getTChatDetails() {
                return tChatDetails;
            }

            public void setTChatDetails(List<?> tChatDetails) {
                this.tChatDetails = tChatDetails;
            }
        }
    }
}

