package com.ruoyi.teacher.domain.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstAnalysisVO {
    /** 一级目录名称 */
    @Excel(name = "一级目录名称")
    private String firstName;

    /** 一级目录完成率 */
    @Excel(name = "一级目录完成率")
    private double firstAverageCompletionRate;
}
