package com.ruoyi.student.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生数据分析对象 data_analysis
 * 
 * @author lihong
 * @date 2023-11-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataAnalysis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 岗位id */
    @Excel(name = "岗位id")
    private String positionId;

    /** 目标数 */
    @Excel(name = "目标数")
    private Long targetNum;

    /** 完成率 */
    @Excel(name = "完成率")
    private String completionRate;

    /** 统计截至日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "统计截至日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadlineDate;

    /** 发布时长 */
    @Excel(name = "发布时长")
    private String releaseDuration;

    /** 年度截至数 */
    @Excel(name = "年度截至数")
    private String yearClose;

    /** 月截至数 */
    @Excel(name = "月截至数")
    private String moonClose;

    /** 年度完成率 */
    @Excel(name = "年度完成率")
    private String yearCompletionRate;

    /** 完成数 */
    @Excel(name = "完成数")
    private Long completionsNum;

    /** 未完成数 */
    @Excel(name = "未完成数")
    private Long unfinishedNum;

    /** 超时完成数 */
    @Excel(name = "超时完成数")
    private Long timeoutCompletionsNum;

    /** 提前完成数 */
    @Excel(name = "提前完成数")
    private Long beforeCompletionsNum;

    /** 按时完成数 */
    @Excel(name = "按时完成数")
    private Long justCompletionsNum;

    /** 未完成过期数 */
    @Excel(name = "未完成过期数")
    private Long expiredTargetNum;

    /** 未完成未过期数 */
    @Excel(name = "未完成未过期数")
    private Long notExpiredTargetNum;

}
