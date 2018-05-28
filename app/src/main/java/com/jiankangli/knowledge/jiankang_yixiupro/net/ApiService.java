package com.jiankangli.knowledge.jiankang_yixiupro.net;




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
}
