package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/22.
 */

public class MsgCenter {

    /**
     * code : success
     * data : {"list":[{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180508002]","egginerName":"郭航","egginerUserId":383,"id":1502,"orderNo":"20180508002","readFlag":"0","sendTime":"2018-05-08 10:58:03","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1149},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180508001]","egginerName":"郭航","egginerUserId":383,"id":1501,"orderNo":"20180508001","readFlag":"0","sendTime":"2018-05-08 10:52:12","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1148},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180506001]","egginerName":"郭航","egginerUserId":383,"id":1500,"orderNo":"20180506001","readFlag":"0","sendTime":"2018-05-06 19:02:52","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1147},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180425001]","egginerName":"郭航","egginerUserId":383,"id":1323,"orderNo":"20180425001","readFlag":"1","sendTime":"2018-04-25 10:13:53","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1146},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180124005]","egginerName":"郭航","egginerUserId":383,"id":1313,"orderNo":"20180124005","readFlag":"0","sendTime":"2018-01-24 15:34:09","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1138},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180124004]","egginerName":"郭航","egginerUserId":383,"id":1312,"orderNo":"20180124004","readFlag":"0","sendTime":"2018-01-24 14:22:34","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1137},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180124003]","egginerName":"郭航","egginerUserId":383,"id":1311,"orderNo":"20180124003","readFlag":"0","sendTime":"2018-01-24 14:11:40","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1136},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180124002]","egginerName":"郭航","egginerUserId":383,"id":1310,"orderNo":"20180124002","readFlag":"0","sendTime":"2018-01-24 11:20:03","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1135},{"chatId":0,"content":"您有新的工单需要处理，工单号为：[20180124001]","egginerName":"郭航","egginerUserId":383,"id":1309,"orderNo":"20180124001","readFlag":"0","sendTime":"2018-01-24 11:07:58","sendUserId":1,"sysType":1,"type":"3","useFlag":"1","workOrderId":1134}]}
     * msg : 成功
     * time : 2018-06-22 06:33:40
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
             * chatId : 0
             * content : 您有新的工单需要处理，工单号为：[20180508002]
             * egginerName : 郭航
             * egginerUserId : 383
             * id : 1502
             * orderNo : 20180508002
             * readFlag : 0
             * sendTime : 2018-05-08 10:58:03
             * sendUserId : 1
             * sysType : 1
             * type : 3
             * useFlag : 1
             * workOrderId : 1149
             */

            private int chatId;
            private String content;
            private String egginerName;
            private int egginerUserId;
            private int id;
            private String orderNo;
            private String readFlag;
            private String sendTime;
            private int sendUserId;
            private int sysType;
            private String type;
            private String useFlag;
            private int workOrderId;

            public int getChatId() {
                return chatId;
            }

            public void setChatId(int chatId) {
                this.chatId = chatId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getEgginerName() {
                return egginerName;
            }

            public void setEgginerName(String egginerName) {
                this.egginerName = egginerName;
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

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getReadFlag() {
                return readFlag;
            }

            public void setReadFlag(String readFlag) {
                this.readFlag = readFlag;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public int getSendUserId() {
                return sendUserId;
            }

            public void setSendUserId(int sendUserId) {
                this.sendUserId = sendUserId;
            }

            public int getSysType() {
                return sysType;
            }

            public void setSysType(int sysType) {
                this.sysType = sysType;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUseFlag() {
                return useFlag;
            }

            public void setUseFlag(String useFlag) {
                this.useFlag = useFlag;
            }

            public int getWorkOrderId() {
                return workOrderId;
            }

            public void setWorkOrderId(int workOrderId) {
                this.workOrderId = workOrderId;
            }
        }
    }
}
