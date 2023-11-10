package com.ruoyi.teacher.domain.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    /**岗位id*/
    @Excel(name = "岗位id")
    private String positionId;

    @Excel(name = "批阅内容")
    private String content;
}
