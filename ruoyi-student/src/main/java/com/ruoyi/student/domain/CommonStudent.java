package com.ruoyi.student.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 公用 学生信息对象 common_student
 * 
 * @author lihong
 * @date 2023-11-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonStudent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学生主键id */
    private Long sId;

    /** 所属学院代码 */
    @Excel(name = "所属学院代码")
    private String collegeCode;

    /** 所属校区名称 */
    @Excel(name = "所属校区名称")
    private String campusName;

    /** 所属校区代码 */
    @Excel(name = "所属校区代码")
    private String campusCode;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String professional;

    /** 班级名称 */
    @Excel(name = "班级名称")
    private String classname;

    /** 学号 */
    @Excel(name = "学号")
    private String sNum;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 所属学院 */
    @Excel(name = "所属学院")
    private String college;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String politics;

    /** 年级 */
    @Excel(name = "年级")
    private String grade;

    /** 培养层次 */
    @Excel(name = "培养层次")
    private String cultivationLevel;

    /** 班级代码 */
    @Excel(name = "班级代码")
    private String classCode;

    /** 用户id(登录表) */
    @Excel(name = "用户id(登录表)")
    private Long userid;


    private Long peopleNum;


}
