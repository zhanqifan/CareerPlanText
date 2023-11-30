package com.ruoyi.teacher.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDTO {

    /**学院id*/
    private String college;

    /**系部id*/
    private String professional;

    /**班级id*/
    private String className;
}
