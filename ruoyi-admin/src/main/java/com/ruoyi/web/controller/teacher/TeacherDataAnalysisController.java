package com.ruoyi.web.controller.teacher;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.student.domain.dto.CollegeAnalysisDTO;
import com.ruoyi.student.service.IDataAnalysisService;
import com.ruoyi.teacher.service.TeacherDataAnalysisService;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/teacher/analysis")
public class TeacherDataAnalysisController extends BaseController {

    @Resource
    private IDataAnalysisService dataAnalysisService;

    @Resource
    private TeacherDataAnalysisService teacherDataAnalysisService;




    /**
     * 教师查看学生的岗位数据分析
     * @param studentId
     * @param positionId
     * @return
     */
    @GetMapping
    public AjaxResult getDataStudentAnalysis(@Param("studentId") String studentId,
                                             @Param("positionId") String positionId)
    {
        return success(dataAnalysisService.getDataAnalysis(studentId,positionId));
    }

    /**
     *查询学院分析结构
     */
    @PostMapping
    public AjaxResult getCollegeAnalysis(@RequestBody CollegeAnalysisDTO collegeAnalysisDTO){
        return success(teacherDataAnalysisService.getCollegeAnalysis(collegeAnalysisDTO));
    }

}
