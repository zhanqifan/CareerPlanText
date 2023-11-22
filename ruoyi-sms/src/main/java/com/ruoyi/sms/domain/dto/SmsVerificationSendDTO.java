package com.ruoyi.sms.domain.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SmsVerificationSendDTO {
    @NotBlank(message = "验证码类型不能为空")
    private String type;
//    @NotBlank(message = "手机号不能为空", groups = {OrderGroup.First.class})
//    @PhoneNumber(message = "手机号格式错误", groups = {OrderGroup.Second.class})
    private String phone;
}
