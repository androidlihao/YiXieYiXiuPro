package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.io.Serializable;

/**
 * @author lihao
 * @date 2019-09-13 17:19
 * @description :
 */
public class SpareParts implements Serializable {
    private int id;
    private String applyTime;
    private String accessoryName;
    private int accessoryStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public int getAccessoryStatus() {
        return accessoryStatus;
    }

    public void setAccessoryStatus(int accessoryStatus) {
        this.accessoryStatus = accessoryStatus;
    }
}
