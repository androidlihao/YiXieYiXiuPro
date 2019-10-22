package com.jiankangli.knowledge.jiankang_yixiupro.bean;

/**
 * @author lihao
 * @date 2019-09-29 23:00
 * @description :
 */
public class messagePushBean {

    /**
     * typeString : 巡检
     * workOrderId : 1466
     * content : 您填写的工单号为: [20180927017] 审核失败请重新填写
     * orderStatus : 6
     * time : 1538043543511
     */

    private String typeString;
    private int workOrderId;
    private String content;
    private int orderStatus;
    private long time;

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
