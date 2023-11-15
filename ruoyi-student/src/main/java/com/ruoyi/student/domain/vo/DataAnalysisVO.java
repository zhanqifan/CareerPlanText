package com.ruoyi.student.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.student.domain.DataAnalysis;
import com.ruoyi.student.domain.FirstAnalysis;
import com.ruoyi.student.domain.SubAnalysis;
import com.ruoyi.student.domain.TargetPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataAnalysisVO extends DataAnalysis {

    /**岗位列表*/
    private List<TargetPosition> targetPositionList;

    /**一级目录统计结果*/
    private List<FirstAnalysis> firstAnalysisList;

    /**二级目录统计结果*/
    private List<SubAnalysis> subAnalysisList;

    /**月完成目标数 */
    private List<MoonCloseCompletionsNumVO>  moonCloseCompletionsNumVOS;

    /**同年级系聚类分析*/
    private List<ClusterAnalysisVo> clusterAnalysisVos;
}


