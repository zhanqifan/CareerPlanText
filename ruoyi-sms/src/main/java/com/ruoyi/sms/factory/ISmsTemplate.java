package com.ruoyi.sms.factory;

/**
 * 短信模板
 *
 * @author Lion Li
 * @version 4.2.0
 */
public interface ISmsTemplate {

    /**
     * 发送验证码
     *
     * @param phones 手机
     * @param code   代码
     */
    public void sendVerificationCode(String phones, String code);

    public Integer getWeight();

    public String getProvider();
}
