/**
 * Copyright (c) 2021-2028, iron.guo 郭成杰 (jiedreams@sina.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.beluga.notice.sms;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.beluga.notice.constant.AliyunConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;


@Slf4j
public class SMSClient implements SMS {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    public void setEndpoint(String endpoint) {this.endpoint = endpoint;}

    public void setAccessKey(String accessKey) {this.accessKey = accessKey;}

    public void setSecretKey(String secretKey) {this.secretKey = secretKey; }

    public SMSClient() {}

    public SMSClient(String endpoint, String accessKey, String secretKey) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        log.info("endpoint:{} accessKey:{} secretKey:{} ", endpoint, accessKey, secretKey);
    }


    /**
     * 发送阿里云短信
     * @param params
     * @return
     * @throws ClientException
     */
    @Override
    public boolean sendSMS(SMSEntity params) throws ClientException{
        Assert.notNull(this.endpoint, "'endpoint' must be not null");
        Assert.notNull(this.accessKey, "'accessKeyId' must be not null");
        Assert.notNull(this.secretKey, "'accessKeySecret' must be not null");
        /**
         * 连接阿里云
         */
        DefaultProfile profile = DefaultProfile.getProfile(this.endpoint, this.accessKey, this.secretKey);
        IAcsClient client = new DefaultAcsClient(profile);
        /**
         * 构建请求
         */
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(AliyunConstant.smsDomain);//不需要改
        request.setSysVersion(AliyunConstant.smsVersion);//不需要改，指定官方版本
        request.setSysAction(AliyunConstant.smsAction);
        /**
         * 自定义参数 (手机号，验证码，签名，模板）
         */
        request.putQueryParameter("PhoneNumbers", params.getPhone());
        request.putQueryParameter("SignName", params.getSignName());
        request.putQueryParameter("TemplateCode", params.getTemplateCode());
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params.getSmsParams()));
        CommonResponse response = client.getCommonResponse(request);
        return response.getHttpResponse().isSuccess();
    }
}
