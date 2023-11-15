package com.ruoyi.student.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoonCloseCompletionsNumVO {

    /**月份*/
    private String moon;

    /**月截止数*/
    private Long moonClose;

    /**月完成数*/
    private Long moonCompletionsNum;



}
