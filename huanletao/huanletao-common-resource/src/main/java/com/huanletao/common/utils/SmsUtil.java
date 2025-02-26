package com.huanletao.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Random;

public class SmsUtil {
    /**
     *
     * @param PhoneNumbers 电话号码
     * @param  msgCode 验证码
     * @return 1 发送成功 0 发送失败
     * @throws ClientException
     */
public static int sendYanZhengMa(String PhoneNumbers,String msgCode) {
       //初始化ascClient需要的几个参数
       final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
       final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
       final String accessKeyId = "LTAI4FsZ29Q5tPMXiwG79BdH";//你的accessKeyId,参考本文档步骤2
       final String accessKeySecret = "OoofssD3zvAv9TjRbAETPZ7ZeKQrpU";//你的accessKeySecret，参考本文档步骤2
       final String TemplateCode ="SMS_174278363"; //短信模板id
       String SignName="欢乐淘";    //短信签名
               //初始化ascClient,暂时不支持多region（请勿修改）
       IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
               accessKeySecret);

    try {
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    } catch (ClientException e) {
        return 0;
    }
    IAcsClient acsClient = new DefaultAcsClient(profile);
       //组装请求对象
       SendSmsRequest request = new SendSmsRequest();
       //使用post提交
       request.setMethod(MethodType.POST);
       //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
       request.setPhoneNumbers( PhoneNumbers);

       //必填:短信签名-可在短信控制台中找到
       request.setSignName(SignName);
       //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
       request.setTemplateCode(TemplateCode);
       //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
       //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
       request.setTemplateParam("{\"code\":\"" + msgCode + "\"}");
       //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
       //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
       //request.setSmsUpExtendCode("90997");
       //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
      //  request.setOutId("yourOutId");
       //请求失败这里会抛ClientException异常
    SendSmsResponse sendSmsResponse = null;
    try {
        sendSmsResponse = acsClient.getAcsResponse(request);
    } catch (ClientException e) {
        return  0;
    }
    if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return 1;//请求成功返回验证码
       }
               return 0;
   }

         /**
         * 生成随机的6位数，短信验证码
         * @return 验证码
        */
    public static String getMsgCode() {
                 int n = 6;
                StringBuilder code = new StringBuilder();
               Random ran = new Random();
               for (int i = 0; i < n; i++) {
                        code.append(Integer.valueOf(ran.nextInt(10)).toString());
                    }
               return code.toString();
            }
}
