package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 李浩 on 2018/6/28.
 */

public class Chat {

    /**
     * code : success
     * data : {"list":[{"chatId":"24","content":"倪敏空间","headUrl":"/images/201806/healthPowerf29adfdfaacb4c66ac558041cc384951.png","id":271,"name":"郭航","reciveUserId":1,"sendTime":"2018-06-28 17:46:08","sendUserId":383,"tChat":"","useflag":"1"}]}
     * msg : 成功
     * time : 2018-06-28 05:46:28
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

        public static class ListBean implements MultiItemEntity {
            /**
             * chatId : 24
             * content : 倪敏空间
             * headUrl : /images/201806/healthPowerf29adfdfaacb4c66ac558041cc384951.png
             * id : 271
             * name : 郭航
             * reciveUserId : 1
             * sendTime : 2018-06-28 17:46:08
             * sendUserId : 383
             * tChat :
             * useflag : 1
             */

            private String chatId;
            private String content;
            private String headUrl;
            private int id;
            private String name;
            private int reciveUserId;
            private String sendTime;
            private int sendUserId;
            private String tChat;
            private String useflag;

            public String getChatId() {
                return chatId;
            }

            public void setChatId(String chatId) {
                this.chatId = chatId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getReciveUserId() {
                return reciveUserId;
            }

            public void setReciveUserId(int reciveUserId) {
                this.reciveUserId = reciveUserId;
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

            public String getTChat() {
                return tChat;
            }

            public void setTChat(String tChat) {
                this.tChat = tChat;
            }

            public String getUseflag() {
                return useflag;
            }

            public void setUseflag(String useflag) {
                this.useflag = useflag;
            }

            @Override
            public int getItemType() {
                return 0;
            }
        }
    }
}
