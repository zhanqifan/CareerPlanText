package com.ruoyi.teacher.domain.vo;

import com.ruoyi.student.domain.TargetPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVO {

    /**学生id*/
    private String studentId;

    /**学生姓名*/
    private String studentName;

    /**二级学院*/
    private String college;

    /**系部*/
    private String professional;

    /**年级*/
    private String grade;

    /**班级*/
    private String classname;

    /**培养层次*/
    private String cultivationLevel;

    /**主目标*/
    private TargetPosition mainTarget;

    /**次目标*/
    private TargetPosition SecondaryTarget;

    /**废弃目标*/
    private List<TargetPosition> AbandonedTarget;


}
