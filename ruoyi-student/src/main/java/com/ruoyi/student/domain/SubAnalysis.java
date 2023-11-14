package com.ruoyi.student.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 目录分析对象 sub_analysis
 * 
 * @author ruoyi
 * @date 2023-11-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubAnalysis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 岗位id */
    @Excel(name = "岗位id")
    private String postitionId;


    /** 二级目录id */
    @Excel(name = "二级目录id")
    private Long secondaryId;

    /**二级目录名称*/
    private String secondaryName;

    /** 二级目标数量 */
    @Excel(name = "二级目标数")
    private Long secondaryTargetNum;

    /** 二级完成数 */
    @Excel(name = "二级完成数")
    private Long secondaryCompletionNum;

    /** 二级完成率 */
    @Excel(name = "二级完成率")
    private String secondaryCompletionRate;

    /**截至时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadlineDate;

}
