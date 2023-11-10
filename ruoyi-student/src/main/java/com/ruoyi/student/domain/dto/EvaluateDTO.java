package com.ruoyi.student.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateDTO {

    /** 目标id */
    @Excel(name = "目标id")
    private Integer skillsId;

    private Integer evaluateId;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String content;

    /** 完成状态 */
    @Excel(name = "完成状态 0未完成，1已完成")
    private Integer completionStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;


}
