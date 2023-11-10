package com.ruoyi.teacher.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 教师批阅对象 teacher_review
 * 
 * @author lihong
 * @date 2023-11-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 批阅id */
    private Long id;

    /** 岗位id */
    @Excel(name = "岗位id")
    private String positionId;

    /** 批阅内容 */
    @Excel(name = "批阅内容")
    private String content;

    private String createBy;


}
