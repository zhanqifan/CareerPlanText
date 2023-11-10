package com.ruoyi.teacher.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDTO {

    /**学院id*/
    private Long collegeId;

    /**系部id*/
    private Long professionalId;

    /**班级id*/
    private Long classId;
}
