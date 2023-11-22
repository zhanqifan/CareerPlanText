package com.ruoyi.teacher.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeRanking {

    /**学院名称*/
    private String collegeName;
    /**完成率*/
    private Long CompletionRate;
}
