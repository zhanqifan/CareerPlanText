package com.ruoyi.teacher.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoonCompletionsNumVO {

    /**月份*/
    private String moon;

    /**月完成人数*/
    private Long moonCompletionsPersonNum;
}
