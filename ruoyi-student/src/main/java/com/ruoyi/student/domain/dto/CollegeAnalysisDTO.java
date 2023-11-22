package com.ruoyi.student.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeAnalysisDTO {

    /**学院id*/
    private List<String> college;

    /**年级*/
    private List<String> grade;

    /**系部id*/
    private List<String> professionalName;

    /**是否是主目标（1:主目标，0：非主目标）*/
    private Integer isMain;

    /**培养层次*/
    private String cultivationLevel;

}
