package com.ruoyi.student.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClusterAnalysisVo {

    /**目标总数*/
    private Long targetNum;

    /**是否是自己（0不是是，1:是）*/
    private Integer isMyself;

    /**目标完成进度*/
    private String completionProgress;


}
