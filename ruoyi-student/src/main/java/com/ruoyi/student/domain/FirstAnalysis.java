package com.ruoyi.student.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 一级目录分析对象 first_analysis
 * 
 * @author lihong
 * @date 2023-11-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstAnalysis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 岗位id */
    private String postitionId;

    /** 一级目录id */
    @Excel(name = "一级目录id")
    private Long firstId;

    /** 一级目录名称 */
    @Excel(name = "一级目录名称")
    private String firstName;

    /** 一级目录完成率 */
    @Excel(name = "一级目录完成率")
    private String firstCompletionRate;

    /**截至时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadlineDate;


}
