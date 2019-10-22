package com.jiankangli.knowledge.jiankang_yixiupro.bean;

/**
 * @author lihao
 * @date 2019-09-29 21:57
 * @description :服务确认信息
 */
public class serviceConfirmBean {

    /**
     * id : 1213
     * temporaryNum : 13260034562
     * queryCode : fde0a77a
     * servicerPhone : 13260034562
     */

    private int id;
    private String temporaryNum;
    private String queryCode;
    private String servicerPhone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemporaryNum() {
        return temporaryNum;
    }

    public void setTemporaryNum(String temporaryNum) {
        this.temporaryNum = temporaryNum;
    }

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public String getServicerPhone() {
        return servicerPhone;
    }

    public void setServicerPhone(String servicerPhone) {
        this.servicerPhone = servicerPhone;
    }
}
