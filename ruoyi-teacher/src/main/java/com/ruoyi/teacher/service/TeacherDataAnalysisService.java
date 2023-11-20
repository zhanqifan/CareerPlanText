package com.ruoyi.teacher.service;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.teacher.domain.dto.CollegeAnalysisDTO;
import com.ruoyi.teacher.domain.vo.CollegeAnalysisVO;

public interface TeacherDataAnalysisService {

    /**
     *查询学院分析
     */
    CollegeAnalysisVO getCollegeAnalysis(CollegeAnalysisDTO collegeAnalysisDTO);

}
