package com.ruoyi.sms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.constant.ErrorCodeConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.sms.config.properties.SmsProperties;
import com.ruoyi.sms.domain.SmsVerificationCode;
import com.ruoyi.sms.domain.dto.SmsVerificationSendDTO;
import com.ruoyi.sms.factory.ISmsTemplate;
import com.ruoyi.sms.factory.SmsFactory;
import com.ruoyi.sms.mapper.SmsVerificationCodeMapper;
import com.ruoyi.sms.service.ISmsVerificationCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SmsVerificationCodeServiceImpl implements ISmsVerificationCodeService {
    @Resource
    private SmsProperties smsProperties;
    @Resource
    private SmsFactory smsFactory;
    @Resource
    private SmsVerificationCodeMapper smsVerificationCodeMapper;
    @Override
    public void send(SmsVerificationSendDTO smsVerificationSendDTO) {
        long nowDateTime = new Date().getTime();
        SmsVerificationCode smsVerificationCode1 = new SmsVerificationCode();
        smsVerificationCode1.setPhone(smsVerificationSendDTO.getPhone());
//        smsVerificationCode1.setType(smsVerificationSendDTO.getType());
        smsVerificationCode1.setCreateTime(new Date(nowDateTime - 86400000));
        List<SmsVerificationCode> smsPhoneSendHistory = smsVerificationCodeMapper.selectSmsVerificationCodeList(smsVerificationCode1);
        // int oneMinuteNum = 0;
        int fiveMinuteNum = 0;
        int sixtyMinuteNum = 0;
        int twentyFourHourNum = 0;
        for (SmsVerificationCode smsVerificationCode : smsPhoneSendHistory) {
            long sendDateTime = smsVerificationCode.getCreateTime().getTime();
            // 1分钟
            if (nowDateTime - sendDateTime < 60000) {
                // oneMinuteNum++;
                throw new ServiceException(
                        String.format("%d秒后可再次发送验证码", Math.round((60000 - (nowDateTime - sendDateTime))) / 1000)
                );
            }
            // 5分钟
            if (nowDateTime - sendDateTime < 300000) {
                fiveMinuteNum++;
            }
            // 60分钟
            if (nowDateTime - sendDateTime < 3600000) {
                sixtyMinuteNum++;
            }
            // 24小时
            if (nowDateTime - sendDateTime < 86400000) {
                twentyFourHourNum++;
            }
        }
        if (sixtyMinuteNum >= 15) {
            throw new ServiceException("60分钟内只可发送15条验证码");
        }
        if (twentyFourHourNum >= 30) {
            throw new ServiceException("24小时内只可发送30条验证码");
        }
        // 随机验证码
        String code = RandomUtil.randomNumbers(6);
        // 获取随机发送器
        ISmsTemplate smsTemplate = smsFactory.getRandomSmsFactory();
        // 写入数据库
        SmsVerificationCode verificationCode = new SmsVerificationCode();
        verificationCode.setPhone(smsVerificationSendDTO.getPhone());
        verificationCode.setCode(code);
//        verificationCode.setType(smsVerificationSendDTO.getType());
        verificationCode.setProvider(smsTemplate.getProvider());
        verificationCode.setTryTimes(0);
        verificationCode.setStatus("Active");
        verificationCode.setIp("测试");
        smsVerificationCodeMapper.insertSmsVerificationCode(verificationCode);
        smsTemplate.sendVerificationCode(smsVerificationSendDTO.getPhone(), code);
    }

    @Override
//    public void verify(String phone, String type, String code) {
    public void verify(String phone, String code) {
        SmsVerificationCode smsVerificationCode1 = new SmsVerificationCode();
        smsVerificationCode1.setPhone(phone);
//        smsVerificationCode1.setCode(code);
        smsVerificationCode1.setCreateTime(new Date(new Date().getTime() - 300000));
        System.out.println(smsVerificationCode1);
        SmsVerificationCode smsVerificationCode = smsVerificationCodeMapper.selectSmsVerificationCodeOne(smsVerificationCode1);
        if (ObjectUtil.isNull(smsVerificationCode)) {
            throw new ServiceException("短信验证码未发送或已失效");
        }
        if (!"Active".equals(smsVerificationCode.getStatus())) {
            throw new ServiceException("短信验证码已被使用或已失效");
        }
        try {
            smsVerificationCode.setTryTimes(smsVerificationCode.getTryTimes() + 1);
            if (smsVerificationCode.getTryTimes() > 5) {
                smsVerificationCode.setStatus("Inactive");
                throw new ServiceException("短信验证码达到尝试上限，请重新发送验证码");
            }
            if (!code.equals(smsVerificationCode.getCode())) {
                throw new ServiceException("短信验证码验证失败");
            }
            smsVerificationCode.setStatus("Used");
        } finally {
            smsVerificationCodeMapper.updateSmsVerificationCode(smsVerificationCode);
        }
    }
}
