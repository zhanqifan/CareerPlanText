package com.ruoyi.web.controller.teacher;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.student.service.IDataAnalysisService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/teacher/analysis")
public class TeacherDataAnalysisController extends BaseController {

    @Resource
    private IDataAnalysisService dataAnalysisService;


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

    @PostMapping
    public AjaxResult getCollegeAnalysis(){
        return success();
    }
}
