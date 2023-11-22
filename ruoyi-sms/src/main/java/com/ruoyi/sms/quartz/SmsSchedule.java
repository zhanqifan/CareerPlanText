package com.ruoyi.sms.quartz;

import com.ruoyi.sms.mapper.SmsVerificationCodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class SmsSchedule {
    @Resource
    private SmsVerificationCodeMapper smsVerificationCodeMapper;


    /**
     * 删除过期短信验证码(每小时的10分执行)
     */
    @Scheduled(cron = "0 10 * * * ?")
    public void deleteExpiredSmsVerificationCode() {
        int count = smsVerificationCodeMapper.delete(new Date(new Date().getTime() - 86400000));
        log.info("[定时任务] 删除过期短信验证码 {} 条", count);
    }
}
