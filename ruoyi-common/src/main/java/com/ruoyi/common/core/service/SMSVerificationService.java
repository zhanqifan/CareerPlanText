package com.ruoyi.common.core.service;

import org.springframework.stereotype.Service;

@Service
public class SMSVerificationService {


    public int SendTextMessage(String phonenumber) {
        //TODO 调用发送短信接口

        //TODO 将验证码存入redis过期时间为5分钟（电话号码:验证码）
        return 0;
    }
}
