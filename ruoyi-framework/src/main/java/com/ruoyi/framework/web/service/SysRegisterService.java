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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.student.system.service.ISysConfigService;
import com.ruoyi.student.system.service.ISysUserService;

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



    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        //TODO  发短信接收验证码
        Long code = null;
        String msg = "" , username = registerBody.getUserName() , password = registerBody.getPassword();
        String phonenumber = registerBody.getPhonenumber();
        //存储验证码设置过期时间为5分钟
        redisCache.setCacheObject(registerBody.getPhonenumber(),code,5, TimeUnit.MINUTES);
        if(StringUtils.isNull(phonenumber)){
            if(registerBody.getCode()!= code){
               return msg="验证码错误";
            }
            return msg="验证码已过期";
        }

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
            userService.updateUser(user);
        }else {
            msg="用户："+username+"未导入系统，请联系管理员";
        }
        return msg;


//        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();
//        SysUser sysUser = new SysUser();
//        sysUser.setUserName(username);
//
//        // 验证码开关
//        boolean captchaEnabled = configService.selectCaptchaEnabled();
//        if (captchaEnabled)
//        {
//            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
//        }
//
//        if (StringUtils.isEmpty(username))
//        {
//            msg = "用户名不能为空";
//        }
//        else if (StringUtils.isEmpty(password))
//        {
//            msg = "用户密码不能为空";
//        }
//        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
//                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
//        {
//            msg = "账户长度必须在2到20个字符之间";
//        }
//        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
//                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
//        {
//            msg = "密码长度必须在5到20个字符之间";
//        }
//        else if (!userService.checkUserNameUnique(sysUser))
//        {
//            msg = "保存用户'" + username + "'失败，注册账号已存在";
//        }
//        else
//        {
//            sysUser.setNickName(username);
//            sysUser.setPassword(SecurityUtils.encryptPassword(password));
//            boolean regFlag = userService.registerUser(sysUser);
//            if (!regFlag)
//            {
//                msg = "注册失败,请联系系统管理人员";
//            }
//            else
//            {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
//            }
//        }
//        return msg;
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
