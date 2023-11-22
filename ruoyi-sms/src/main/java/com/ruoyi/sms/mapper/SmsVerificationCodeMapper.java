package com.ruoyi.sms.mapper;


import com.ruoyi.sms.domain.SmsVerificationCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface SmsVerificationCodeMapper  {


    List<SmsVerificationCode> selectSmsVerificationCodeList(SmsVerificationCode smsVerificationCode1);

    int insertSmsVerificationCode(SmsVerificationCode verificationCode);

    void updateSmsVerificationCode(SmsVerificationCode smsVerificationCode);

    SmsVerificationCode selectSmsVerificationCodeOne(SmsVerificationCode smsVerificationCode1);

    int delete(Date createTime);
}
