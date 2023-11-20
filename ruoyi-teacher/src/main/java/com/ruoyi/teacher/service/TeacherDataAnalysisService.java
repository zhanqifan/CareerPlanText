package com.ruoyi.teacher.service;

import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.domain.dto.CollegeAnalysisDTO;
import com.ruoyi.teacher.domain.vo.CollegeAnalysisVO;

import java.util.List;

public interface TeacherDataAnalysisService {

    /**
     *查询学院分析
     */
    CollegeAnalysisVO getCollegeAnalysis(CollegeAnalysisDTO collegeAnalysisDTO);

}
