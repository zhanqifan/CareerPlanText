package com.ruoyi.teacher.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.student.domain.FirstAnalysis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeAnalysisVO {

    /**发布人数*/
    private Long numberPublishers;

    /**发布率*/
    private Double publishingRate;

    /**人均项目数*/
    private Long   NumberProjectsPerCapita;

    /**平均完成率*/
    private Double averageCompletionRate;

    /**年度平均项目数*/
    private Long  AnnualAverageNumberProjects;

    /**年度完成率*/
    private Double  annualAverageCompletionRate;

    /** 统计截至日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadlineDate;

    /**完成率分布*/
    private List<CompletionRateRange> completionRateRangeList;

    /**全校数据*/
    private   List<FirstAnalysis> schoolData;

    /**所选学生数据*/
    private List<FirstAnalysis>  studentData;

    /** 完成数 */
    private Long completionsNum;

    /** 未完成数 */
    private Long unfinishedNum;

    /** 超时完成数 */
    private Long timeoutCompletionsNum;

    /** 提前完成数 */
    private Long beforeCompletionsNum;

    /** 按时完成数 */
    private Long justCompletionsNum;

    /** 未完成过期数 */
    private Long expiredTargetNum;

    /** 未完成未过期数 */
    private Long notExpiredTargetNum;

    /**近6月完成人数*/
    private List<MoonCompletionsNumVO> moonCompletionsNumVOList;


}

