package com.ruoyi.teacher.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    /**学号/姓名*/
    private String userName;

    /**学院id*/
    private Long collegeId;

    /**系部id*/
    private Long professionalId;

    /**年级*/
    private String grade;

    /**班级id*/
    private Long classId;
}
