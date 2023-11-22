package com.ruoyi.student.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 评价对象 evaluate
 * 
 * @author lihong
 * @date 2023-11-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评价id */
    private Integer evaluateId;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String content;

    /** 评价类型（students：学生自评，mapper:教师批阅） */
    @Excel(name = "评价类型", readConverterExp = "s=tudents：学生自评，mapper:教师批阅")
    private String type;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    /** 目标id */
    @Excel(name = "目标id")
    private String skillsId;


}
