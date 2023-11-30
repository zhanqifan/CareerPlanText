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

    /**学院*/
    private String college;

    /**系部*/
    private String professional;

    /**年级*/
    private String grade;

    /**班级*/
    private String className;
}
