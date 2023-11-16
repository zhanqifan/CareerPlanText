package com.ruoyi.teacher.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletionRateRange {
    /**完成率范围*/
    private String range;

    /**人数*/
    private Long peopleNum;

}
