package com.ruoyi.sms.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms.aliyun")
public class SmsAliyunProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private SmsTemplateProperties template;
    private Integer weight = 2;
}
