package com.jiankangli.knowledge.jiankang_yixiupro.net;




import com.jiankangli.knowledge.jiankang_yixiupro.bean.BannerBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.BaseEntity;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.ElectronOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MsgCenter;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.MsgDetils;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.OdrerDetailsBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PdfBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PicUrlBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.PollingOrder;

import com.jiankangli.knowledge.jiankang_yixiupro.bean.RecodeWorkOrderBeasInfo;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RecodemaintainOrderBeasInfo;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.RepairOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SingleMaintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.SpareParts;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.Status;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.TemplateListBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.UpkeepOrder;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.fixRecordBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.inspectionBaseInfoBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.maintainOrderBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.maintainOrderRecordBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.messagePushBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.serviceConfirmBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.sparePartBean;
import com.jiankangli.knowledge.jiankang_yixiupro.bean.workEvaluationBean;


import java.util.List;

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
     Observable<BaseEntity<List<RepairOrder>>> getRepairOrder(@Field("jsonString")String jsonString);
     /**
      * 获取备件列表
      * @param jsonString
      * @return
      */
     @FormUrlEncoded
     @POST("sparePart/getPageList.do?")
     Observable<BaseEntity<List<SpareParts>>> getSpareParts(@Field("jsonString")String jsonString);
     //获取消息中心列表
     @FormUrlEncoded
     @POST("engineer/messageList.do")
     Observable<MsgCenter> getMsgCenter(@Field("jsonString")String jsonString);
     //获取保养工单列表信息
     @FormUrlEncoded
     @POST("maintainOrder/getMaintainOrderList.do")
     Observable<BaseEntity<List<UpkeepOrder>>> getUpkeepOrder(@Field("jsonString")String jsonString);
     //获取巡检列表工单信息
     @FormUrlEncoded
     @POST("inspectionWorkOrder/getInspectionWorkOrderList.do")
     Observable<BaseEntity<List<PollingOrder>>> getPollingOrder(@Field("jsonString")String jsonString);
     //消息详情地址
     @FormUrlEncoded
     @POST("engineer/messageInfo.do")
     Observable<MsgDetils> getMsgDetils(@Field("jsonString")String jsonString);
     //清空留言列表接口
     @FormUrlEncoded
     @POST("engineer/deleteMsgList.do")
     Observable<String> ClearMMBList(@Field("jsonString")String jsonString);
     //获得在线聊天信息接口
     @FormUrlEncoded
     @POST("engineer/lookOnLineMsg.do")
     Observable<String> getOnlineMsg(@Field("jsonString")String jsonString);
     //提交留言接口
     @FormUrlEncoded
     @POST("engineer/sendOnLineMsg.do")
     Observable<String> SubmitMMB(@Field("jsonString")String jsonString);

     /**
      * 获取工单详情
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/getRepairWorkOrderBaseInfo.do")
     Observable<BaseEntity<OdrerDetailsBean>> getRepairWorkOrderBaseInfo(@Field("jsonString")String jsonString);
     /**
      * 获取保养工单基础信息
      */
     @FormUrlEncoded
     @POST("maintainOrder/getBaseOrderInfo.do")
     Observable<BaseEntity<maintainOrderBean>> getmaintainOrderInfo(@Field("jsonString")String jsonString);
     /**
      *获取巡检工单基础信息
      */
     @FormUrlEncoded
     @POST("inspectionWorkOrder/getBaseOrderInfo.do")
     Observable<BaseEntity<inspectionBaseInfoBean>> getinspectionOrderInfo(@Field("jsonString")String jsonString);
     /**
      * 获取轮播图列表
      */
     @POST("banner/list.do")
     Observable<BaseEntity<List<BannerBean>>> getBannerList();
     /**
      * 保养-根据id获取电子工单信息
      */
     @FormUrlEncoded
     @POST("maintainOrder/getSingleMaintainOrder.do")
     Observable<BaseEntity<SingleMaintainOrderBean>> getSingleMaintainOrder(@Field("jsonString")String jsonString);
     /**
      * 巡检-根据电子工单id获取信息
      */
     @FormUrlEncoded
     @POST("inspectionWorkOrder/getSingleInspectionOrder.do")
     Observable<BaseEntity<SingleMaintainOrderBean>> getSingleInspectionOrder(@Field("jsonString")String jsonString);
     /**
      * 获取保养/巡检模版列表
      */
     @FormUrlEncoded
     @POST("template/getTemplateList.do ")
     Observable<BaseEntity<List<TemplateListBean>>> getTemplateList(@Field("jsonString")String jsonString);
     /**
      * 获取备件信息
      */
     @FormUrlEncoded
     @POST("sparePart/getSparePartById.do")
     Observable<BaseEntity<sparePartBean>> getSparePartById(@Field("jsonString")String jsonString);
     /**
      * 修改备件返回状态
      */
     @FormUrlEncoded
     @POST("sparePart/updateSparePart.do")
     Observable<BaseEntity> updateSparePart(@Field("jsonString")String jsonString);
     /**
      * 开始维修
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/updateWorkOrder.do")
     Observable<BaseEntity> updateWorkOrder(@Field("jsonString")String jsonString);
     /**
      * 开始保养
      */
     @FormUrlEncoded
     @POST("maintainOrder/startMaintinOrder.do")
     Observable<BaseEntity> startMaintinOrder(@Field("jsonString")String jsonString);
     /**
      * 开始巡检
      */
     @FormUrlEncoded
     @POST("inspectionWorkOrder/startInspection.do")
     Observable<BaseEntity> startInspection(@Field("jsonString")String jsonString);
     /**
      * 维修-单个工单回显
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/showElectronOrder.do")
     Observable<BaseEntity<ElectronOrderBean>> showElectronOrder(@Field("jsonString")String jsonString);

     /**
      * 图片上传
      */
     @Multipart
     @POST("pic/uploadImage.do")
     Observable<BaseEntity<PicUrlBean>> uploadImage(@Part MultipartBody.Part file);

     /**
      *
      */
     /**
      * 维修-单个工单回显
      */
     @FormUrlEncoded
     @POST("sparePart/addSparePart.do")
     Observable<BaseEntity> addSparePart(@Field("jsonString")String jsonString);
     /**
      * 维修巡检保养-服务记录
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/addServiceRecode.do")
     Observable<BaseEntity> addServiceRecode(@Field("jsonString")String jsonString);
     /**
      * 保存维修工单
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/entryElectronOrder.do")
     Observable<BaseEntity> entryElectronOrder(@Field("jsonString")String jsonString);

     /**
      * 获取维修服务记录回显
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/getServiceRecodeList.do")
     Observable<BaseEntity<fixRecordBean>> getServiceRecodeList(@Field("jsonString")String jsonString);
     /**
      * 保养/巡检服务记录信息回显
      */
     @FormUrlEncoded
     @POST("maintainOrder/getServiceRecordList.do")
     Observable<BaseEntity<maintainOrderRecordBean>> getmaintainServiceRecordList(@Field("jsonString")String jsonString);
     /**
      * 获取审核失败原因
      */
     @FormUrlEncoded
     @POST("work/getFailureCause.do")
     Observable<BaseEntity> getFailureCause(@Field("jsonString")String jsonString);

     /**
      * 维修服务确认回显数据
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/serviceConfirmPageEcho.do")
     Observable<BaseEntity<serviceConfirmBean>> serviceConfirmPageEcho(@Field("jsonString")String jsonString);
     /**
      * 保养服务确认回显
      */
     @FormUrlEncoded
     @POST("maintainOrder/serviceConfirmPageEcho.do")
     Observable<BaseEntity<serviceConfirmBean>> maintainOrderserviceConfirmPageEcho(@Field("jsonString")String jsonString);
     /**
      * 巡检服务确认回显
      */
     @FormUrlEncoded
     @POST("inspectionWorkOrder/serviceConfirmPageEcho.do")
     Observable<BaseEntity<serviceConfirmBean>> inspectionserviceConfirmPageEcho(@Field("jsonString")String jsonString);
     /**
      * 维修服务确认
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/serviceConfirm.do")
     Observable<BaseEntity<List<OdrerDetailsBean>>> serviceConfirm(@Field("jsonString")String jsonString);
     /**
      * 保养服务确认
      */
     @FormUrlEncoded
     @POST("maintainOrder/serviceConfirm.do")
     Observable<BaseEntity> maintainOrderserviceConfirm(@Field("jsonString")String jsonString);
     /**
      * 巡检服务确认
      */
     @FormUrlEncoded
     @POST("inspectionWorkOrder/serviceConfirm.do")
     Observable<BaseEntity> inspectionWorkOrderserviceConfirm(@Field("jsonString")String jsonString);
     /**
      * 查询工单评价状态
      * @param jsonString
      * @return
      */
     @FormUrlEncoded
     @POST("work/queryWorkEvaluation.do")
     Observable<BaseEntity<workEvaluationBean>> queryWorkEvaluation(@Field("jsonString")String jsonString);

     /**
      * 短信提醒
      * @param jsonString
      * @return
      */
     @FormUrlEncoded
     @POST("phone/sendMessage.do")
     Observable<BaseEntity> sendMessage(@Field("jsonString")String jsonString);

     /**
      * 获取推送的消息
      */
     @FormUrlEncoded
     @POST("electronicMessage/messagePush.do")
     Observable<BaseEntity<List<messagePushBean>>> messagePush(@Field("jsonString")String jsonString);

     /**
      * 获取PDF文件
      */
     @FormUrlEncoded
     @POST("pdf/getWorkOrderPdf.do")
     Observable<BaseEntity<PdfBean>> getWorkOrderPdf(@Field("jsonString")String jsonString);

     /**
      * 录入保养报告
      */
     @FormUrlEncoded
     @POST("maintainOrder/saveMaintainOrder.do")
     Observable<BaseEntity> saveMaintainOrder(@Field("jsonString")String jsonString);
     /**
      * 录入巡检报告
      */
     @FormUrlEncoded
     @POST("inspectionWorkOrder/saveInspectionOrder.do")
     Observable<BaseEntity> saveInspectionOrder(@Field("jsonString")String jsonString);
     /**
      * 生成维修工单
      */
     @FormUrlEncoded
     @POST("maintainOrder/orderConversion.do")
     Observable<BaseEntity> orderConversion(@Field("jsonString")String jsonString);
     /**
      * 获取维修补录回显
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/getRecodeWorkOrderBeasInfo.do")
     Observable<BaseEntity<RecodeWorkOrderBeasInfo>> getRecodeWorkOrderBeasInfo(@Field("jsonString")String jsonString);
     /**
      * 保养-补录基础信息回显
      */
     @FormUrlEncoded
     @POST("maintainOrder/recodeOrderInfoEcho.do")
     Observable<BaseEntity<RecodemaintainOrderBeasInfo>> getrecodeOrderInfoEcho(@Field("jsonString")String jsonString);
     /**
      * 维修-补录
      */
     @FormUrlEncoded
     @POST("repairWorkOrder/recodeWorkOrder.do")
     Observable<BaseEntity> recodeWorkOrder(@Field("jsonString")String jsonString);
     /**
      * 保养补录
      */
     @FormUrlEncoded
     @POST("maintainOrder/recodeMaintainOrderEntry.do")
     Observable<BaseEntity<Double>> recodeMaintainOrderEntry(@Field("jsonString")String jsonString);
}
