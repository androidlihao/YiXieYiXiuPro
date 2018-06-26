package com.jiankangli.knowledge.jiankang_yixiupro.net;




import com.jiankangli.knowledge.jiankang_yixiupro.bean.MsgCenter;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Status;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
     @Multipart
     @POST("engineer/uploadHeadPic.do")
     Observable<String> submitHead(@Part("jsonString")String jsonString, @Part MultipartBody.Part file);

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
     //获取维修最新信息
     @FormUrlEncoded
     @POST("engineer/getnotice.do")
     Observable<String> getnotice(@Field("jsonString")String jsonString);
     //获取维修工单列表信息
     @FormUrlEncoded
     @POST("repairWorkOrder/getList.do")
     Observable<String> getRepairOrder(@Field("jsonString")String jsonString);
     //获取消息中心列表
     @FormUrlEncoded
     @POST("engineer/messageList.do")
     Observable<MsgCenter> getMsgCenter(@Field("jsonString")String jsonString);
}
