package com.ruoyi.student.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipContent {
    /**id*/
    private Long id;
    /**技能id*/
    private String skillsId;

    /**内容*/
    private String content;
}
