package com.jiankangli.knowledge.jiankang_yixiupro.bean;

import java.util.List;

public class AliasBean {

    private List<AliasBeanBean> aliasBean;

    public List<AliasBeanBean> getAliasBean() {
        return aliasBean;
    }

    public void setAliasBean(List<AliasBeanBean> aliasBean) {
        this.aliasBean = aliasBean;
    }

    public static class AliasBeanBean {
        /**
         * key : GEOID
         * value : 系统编号
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static AliasBeanBean getAliasBeanBean(AliasBean aliasBean, String s){
        for (AliasBeanBean aliasBeanBean : aliasBean.getAliasBean()) {
            if (aliasBeanBean.key.equals(s) || aliasBeanBean.getValue().equals(s)) {
                return aliasBeanBean;
            }
        }
        return null;
    }
}
