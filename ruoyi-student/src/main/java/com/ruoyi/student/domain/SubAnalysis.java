package com.ruoyi.student.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /** 一级目录id */
    @Excel(name = "一级目录id")
    private Long firstId;

    /** 一级目录完成率 */
    @Excel(name = "一级目录完成率")
    private String firstCompletionRate;

    /** 二级目录id */
    @Excel(name = "二级目录id")
    private Long secondaryId;

    /** 二级目标数 */
    @Excel(name = "二级目标数")
    private Long secondaryTargetNum;

    /** 二级完成数 */
    @Excel(name = "二级完成数")
    private Long secondaryCompletionNum;

    /** 二级完成率 */
    @Excel(name = "二级完成率")
    private String secondaryCompletionRate;

}
