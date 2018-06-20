package com.jiankangli.knowledge.jiankang_yixiupro.net;




import com.jiankangli.knowledge.jiankang_yixiupro.bean.Status;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 李浩 on 2018/5/17.
 */

public interface ApiService {
     //登录接口
     @FormUrlEncoded
     @POST("engineer/login.do")
     Observable<String> sendLogin(@Field("jsonString")String jsonString);

     //获取验证码接口接口
     @FormUrlEncoded
     @POST("common/getcode.do")
     Observable<String> getCode(@Field("jsonString")String jsonString);

     //修改密码接口
     @FormUrlEncoded
     @POST("engineer/resetPassWord.do")
     Observable<String> resetPsd(@Field("jsonString")String jsonString);
     //修改用户状态接口
     @FormUrlEncoded
     @POST("engineer/changeEngineerStatus.do")
     Observable<Status> changeStatu(@Field("jsonString")String jsonString);
     //上传用户头像接口
     @FormUrlEncoded
     @POST("engineer/uploadHeadPic.do")
     Observable<String> submitHead(@Field("jsonString")String jsonString,@Field("data")File file);
     //获取关于我们的介绍接口
     @POST("common/aboutUs.do")
     Observable<String> getContents();
     //意见反馈地址
     @FormUrlEncoded
     @POST("common/feedback.do")
     Observable<String> submitfeed(@Field("jsonString")String jsonString);
     //我的留言
     @FormUrlEncoded
     @POST("engineer/lookMsgList.do")
     Observable<String> getMyMsg(@Field("jsonString")String jsonString);
     //修改密码接口
     @FormUrlEncoded
     @POST("engineer/updatePassWord.do")
     Observable<String> changePsd(@Field("jsonString")String jsonString);
}
