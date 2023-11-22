package com.ruoyi.framework.web.service;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.sms.service.ISmsVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.student.system.service.ISysConfigService;
import com.ruoyi.student.system.service.ISysUserService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 注册校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private ISmsVerificationCodeService iSmsVerificationCodeService;



    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        String code = registerBody.getCode().toString();
        //校验验证码是否正确
        String msg = "" , username = registerBody.getUserName() , password = registerBody.getPassword();
        String phonenumber = registerBody.getPhonenumber();
        iSmsVerificationCodeService.verify(phonenumber,code);
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(registerBody.getNickName());
        sysUser.setPhonenumber(registerBody.getPhonenumber());
        if(StringUtils.isEmpty(username)){
            msg = "用户名不能为空";
        }else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }else if(password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH){
            msg = "密码长度必须在5到20个字符之间";
        }
        SysUser user = userService.selectUserByUserName(username);
        if(StringUtils.isNotNull(user)){
            user.setUserType("01");
            user.setPassword(SecurityUtils.encryptPassword(password));
            userService.updateUser(user);
            msg="学号/工号为："+username+"的用户认证成功";
        }else {
            msg="学号/工号为："+username+"的用户未导入系统，请联系管理员";
        }
        return msg;

    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
}
