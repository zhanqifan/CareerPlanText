package com.ruoyi.teacher.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherClass {

    /**教师id*/
    private String teacherId;

    /**学院id*/
    private String College;

    /**系部id*/
    private String Professional;

    /**年级*/
    private String grade;

    /**班级id*/
    private String className;


}
