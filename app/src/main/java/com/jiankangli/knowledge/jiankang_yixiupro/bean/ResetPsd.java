package com.jiankangli.knowledge.jiankang_yixiupro.bean;

/**
 * Created by 李浩 on 2018/5/24.
 */

public class ResetPsd {

    /**
     * code : success
     * data : {"city":"大兴区","county":"","headPicUrl":"/healthPower/resource/images/gcs.png","hospitalName":"","id":203,"levelId":11,"levelName":"一般","password":"e10adc3949ba59abbe56e057f20f883e","phoneNumber":"13755794643","province":"北京","remark":"","repairtypeName":"CT","status":"1","typeId":"4","useFlag":"1","userId":433,"userName":"李浩","userid":433}
     * msg : 成功
     * time : 2018-05-24 04:30:18
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
        /**
         * city : 大兴区
         * county :
         * headPicUrl : /healthPower/resource/images/gcs.png
         * hospitalName :
         * id : 203
         * levelId : 11
         * levelName : 一般
         * password : e10adc3949ba59abbe56e057f20f883e
         * phoneNumber : 13755794643
         * province : 北京
         * remark :
         * repairtypeName : CT
         * status : 1
         * typeId : 4
         * useFlag : 1
         * userId : 433
         * userName : 李浩
         * userid : 433
         */

        private String city;
        private String county;
        private String headPicUrl;
        private String hospitalName;
        private int id;
        private int levelId;
        private String levelName;
        private String password;
        private String phoneNumber;
        private String province;
        private String remark;
        private String repairtypeName;
        private String status;
        private String typeId;
        private String useFlag;
        private int userId;
        private String userName;
        private int userid;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getHeadPicUrl() {
            return headPicUrl;
        }

        public void setHeadPicUrl(String headPicUrl) {
            this.headPicUrl = headPicUrl;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRepairtypeName() {
            return repairtypeName;
        }

        public void setRepairtypeName(String repairtypeName) {
            this.repairtypeName = repairtypeName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getUseFlag() {
            return useFlag;
        }

        public void setUseFlag(String useFlag) {
            this.useFlag = useFlag;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }
    }
}
