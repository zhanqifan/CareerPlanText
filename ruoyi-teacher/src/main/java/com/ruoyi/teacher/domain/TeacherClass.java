package com.ruoyi.teacher.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherClass {

    /**教师id*/
    private Long teacherId;

    /**学院id*/
    private Long CollegeId;

    /**系部id*/
    private Long ProfessionalId;

    /**年级*/
    private String grade;

    /**班级id*/
    private Long ClassId;


}
