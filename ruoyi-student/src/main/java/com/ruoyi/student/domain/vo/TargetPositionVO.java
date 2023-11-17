package com.ruoyi.student.domain.vo;

import com.ruoyi.student.domain.TargetPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetPositionVO extends TargetPosition {

    /**总体完成率*/
    private Double completionRate;

    /**本年度1-6月项目数*/
    private Long CompletedQuantity1;


    /**本年度1-6月完成率*/
    private Double completionRate1;


    /**本年度7-12月项目数*/
    private Long CompletedQuantity2;

    /**本年度7-12月完成率*/
    private Double completionRate2;


}
