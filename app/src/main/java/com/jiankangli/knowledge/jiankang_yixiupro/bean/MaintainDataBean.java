package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.json.JSONObject;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author lihao
 * @date 2019-10-29 16:47
 * @description :
 */
@Entity(nameInDb = "maintainDB")
public class MaintainDataBean {
    @Id
    private Long id;
    private String jsonObject;
    @Generated(hash = 13207683)
    public MaintainDataBean(Long id, String jsonObject) {
        this.id = id;
        this.jsonObject = jsonObject;
    }
    @Generated(hash = 347095011)
    public MaintainDataBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJsonObject() {
        return this.jsonObject;
    }
    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }
    
}
