package com.jiankangli.knowledge.jiankang_yixiupro.bean;


import java.util.List;

/**
 * @author lihao
 * @date 2019-10-24 16:09
 * @description :巡检，保养模板集合
 * */
public class TemplateListBean{

    /**
     * templateInfoId : 44
     * templateId : 1
     * templateTypeId : 1
     * title : 配电箱、稳压器电压检查
     * sort : 0
     * subtitle :
     * templateInfoTypeId : null
     * templateTypeList : [{"id":6,"name":"AN(V)","parentId":1,"flag":1,"createUserId":null,"createTime":null,"url":null}]
     */

    private int templateInfoId;
    private int templateId;
    private int templateTypeId;
    private String title;
    private int sort;
    private String subtitle;
    private Object templateInfoTypeId;
    private List<TemplateTypeListBean> templateTypeList;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Object getTemplateInfoTypeId() {
        return templateInfoTypeId;
    }

    public void setTemplateInfoTypeId(Object templateInfoTypeId) {
        this.templateInfoTypeId = templateInfoTypeId;
    }

    public List<TemplateTypeListBean> getTemplateTypeList() {
        return templateTypeList;
    }

    public void setTemplateTypeList(List<TemplateTypeListBean> templateTypeList) {
        this.templateTypeList = templateTypeList;
    }

    public static class TemplateTypeListBean {
        /**
         * id : 6
         * name : AN(V)
         * parentId : 1
         * flag : 1
         * createUserId : null
         * createTime : null
         * url : null
         */

        private int id;
        private String name;
        private int parentId;
        private int flag;
        private Object createUserId;
        private Object createTime;
        private Object url;

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

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public Object getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Object createUserId) {
            this.createUserId = createUserId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }
}
