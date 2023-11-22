package com.ruoyi.sms.factory.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.sms.config.properties.SmsTencentCloudProperties;
import com.ruoyi.sms.factory.ISmsTemplate;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;

import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 腾讯云短信
 *
 * @author fzlin
 * @date 2023/04/17
 */
@Slf4j
@Component("tencent_cloud")
public class TencentSmsTemplate implements ISmsTemplate {
    private final SmsTencentCloudProperties properties;

    private final SmsClient client;

    @Autowired
    public TencentSmsTemplate(SmsTencentCloudProperties properties) {
        this.properties = properties;
        Credential credential = new Credential(properties.getSecretId(), properties.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        client = new SmsClient(credential, "ap-nanjing", clientProfile);
    }

    @Override
    public Integer getWeight() {
        return properties.getWeight();
    }

    @Override
    public String getProvider() {
        return "tencent_cloud";
    }

    @Override
    public void sendVerificationCode(String phone, String code) {
//        if (StringUtils.isBlank(phone)) {
//            throw new SystemException("手机号不能为空");
//        }
//        if (StringUtils.isBlank(code)) {
//            throw new SystemException("验证码不能为空");
//        }
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet = { String.format("+86%s", phone) };
        req.setPhoneNumberSet(phoneNumberSet);
        req.setTemplateParamSet(new String[]{code});
        req.setTemplateId(properties.getTemplate().getVerificationCode());
        req.setSignName(properties.getSignName());
        req.setSmsSdkAppId(properties.getSmsSdkAppid());
        try {
            SendSmsResponse resp = client.SendSms(req);
            for (SendStatus sendStatus : resp.getSendStatusSet()) {
                if (!"Ok".equals(sendStatus.getCode())) {
                    throw new ServiceException(String.format("短信发送失败: %s", sendStatus.getMessage()));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("短信发送失败");
        }
    }

}
