package com.ruoyi.sms.factory.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.sms.config.properties.SmsAliyunProperties;
import com.ruoyi.sms.factory.ISmsTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("aliyun")
public class AliyunSmsTemplate implements ISmsTemplate {
    private final SmsAliyunProperties properties;

    private final Client client;

    @Autowired
    @SneakyThrows(Exception.class)
    public AliyunSmsTemplate(SmsAliyunProperties properties) {
        this.properties = properties;
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(properties.getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(properties.getAccessKeySecret())
                // 访问的域名
                .setEndpoint("dysmsapi.aliyuncs.com");
        this.client = new Client(config);
    }

    @Override
    public Integer getWeight() {
        return properties.getWeight();
    }

    @Override
    public String getProvider() {
        return "aliyun";
    }

    @Override
    public void sendVerificationCode(String phone, String code) {
//        if (StringUtils.isBlank(phone)) {
//            throw new SystemException("手机号不能为空");
//        }
//        if (StringUtils.isBlank(code)) {
//            throw new SystemException("验证码不能为空");
//        }
        SendSmsRequest req = new SendSmsRequest()
            .setPhoneNumbers(phone)
            .setSignName(properties.getSignName())
            .setTemplateCode(properties.getTemplate().getVerificationCode())
            .setTemplateParam(String.format("{\"code\":\"%s\"}", code));
        SendSmsResponse resp;
        try {
            resp = client.sendSms(req);
        } catch (Exception e) {
//            throw new SystemException(e.getMessage());
//        }
//        if (!"OK".equals(resp.getBody().getCode())) {
//            throw new ServiceException(String.format("短信发送失败: %s", resp.getBody().getMessage()));
        }
    }

}
