package com.ruoyi.sms.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SmsVerificationCode {
    private Long id;
    private String phone;
    private String code;
    private String type;
    private String provider;
    private Integer tryTimes;
    private String status;
    private String ip;
    private Date createTime;
    private Date updateTime;
}
