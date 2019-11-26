package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @author lihao
 * @date 2019-11-26 11:55
 * @description :搜索历史记录实体类
 * */
@Entity(nameInDb = "historyBean")
public class historyBean implements Serializable {
    private String searchText;
    @Id(autoincrement = true)
    private Long id;
    private String type;
    private static final long serialVersionUID = 6193688880347625803L;
    @Generated(hash = 1239283945)
    public historyBean(String searchText, Long id, String type) {
        this.searchText = searchText;
        this.id = id;
        this.type = type;
    }
    @Generated(hash = 739169108)
    public historyBean() {
    }
    public String getSearchText() {
        return this.searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
