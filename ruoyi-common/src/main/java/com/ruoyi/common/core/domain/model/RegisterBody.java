package com.ruoyi.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册对象
 * 
 * @author ruoyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBody
{
    /**登录名称*/
    private String userName;
    /**姓名*/
    private String nickName;
    /**电话*/
    private String phonenumber;
    /**验证码*/
    private Long code;
    /**密码*/
    private  String password;
    /**重复密码*/
    private String duplicatePassword;
}
