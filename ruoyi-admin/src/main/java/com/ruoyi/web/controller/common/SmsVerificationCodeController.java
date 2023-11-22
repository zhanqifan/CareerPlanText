package com.ruoyi.web.controller.common;


import cn.hutool.extra.servlet.JakartaServletUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.sms.domain.dto.SmsVerificationSendDTO;
import com.ruoyi.sms.service.ISmsVerificationCodeService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sms/verification_code")
//@SaIgnore
public class SmsVerificationCodeController extends BaseController {
    @Resource
    private ISmsVerificationCodeService smsVerificationCodeService;

    @PostMapping("/send")
    public AjaxResult send(HttpServletRequest request, @RequestBody SmsVerificationSendDTO smsVerificationSendDTO)
    {
        smsVerificationCodeService.send(smsVerificationSendDTO);
        return success();
    }
}
