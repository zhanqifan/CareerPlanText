package com.ruoyi.sms.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms.tencent-cloud")
public class SmsTencentCloudProperties {
    private String secretId;
    private String secretKey;
    private String signName;
    private String smsSdkAppid;
    private SmsTemplateProperties template;
    private Integer weight = 8;
}
