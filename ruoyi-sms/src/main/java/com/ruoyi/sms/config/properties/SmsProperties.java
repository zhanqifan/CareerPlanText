package com.ruoyi.sms.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SMS短信 配置属性
 *
 * @author Lion Li
 * @version 4.2.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    private Boolean enableCaptchaVerify;
    private List<String> types;
    private SmsAliyunProperties aliyun;

    private SmsTencentCloudProperties tencentCloud;
}
