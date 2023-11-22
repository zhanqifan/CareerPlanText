package com.ruoyi.sms.service;


import com.ruoyi.sms.domain.dto.SmsVerificationSendDTO;

/**
 * 主义验证码服务
 * 短信验证码服务
 *
 * @author iForday
 * @date 2023/04/16
 */
public interface ISmsVerificationCodeService {
    /**
     * 发送短信验证码
     *
     * @param smsVerificationSendDTO 短信验证发送dto
//     * @param ip                     IP地址
     */
//    public void send(SmsVerificationSendDTO smsVerificationSendDTO, String ip);
    public void send(SmsVerificationSendDTO smsVerificationSendDTO);

    /**
     * 验证短信验证码
     *
     * @param phone 手机号
//     * @param type  类型
     * @param code  验证码
     */
//    public void verify(String phone, String type, String code);
    public void verify(String phone,  String code);
}
