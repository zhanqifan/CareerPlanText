package com.ruoyi.web.controller.teacher;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.student.service.ITargetPositionService;
import com.ruoyi.teacher.domain.dto.ClassDTO;
import com.ruoyi.teacher.domain.dto.StudentDTO;
import com.ruoyi.teacher.domain.dto.ReviewDTO;
import com.ruoyi.teacher.service.ITeacherReviewService;
import com.ruoyi.teacher.service.TeacherClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher/review")
public class PostReviewController extends BaseController {

    @Autowired
    private TeacherClassService teacherClassService;

    @Autowired
    private ITeacherReviewService teacherReviewService;

    @Autowired
    private ITargetPositionService  targetPositionService;

    /**
     * 查看所有学生信息
     * @param studentDTO
     * @return
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody  StudentDTO studentDTO){
        return success(teacherClassService.selectStudent(studentDTO));
    }

    /**
     * 查看学生废弃目标
     */
    @GetMapping("/abandoned/{studentId}")
    public AjaxResult getAbandonedTarget(@PathVariable String studentId) {
        return success(targetPositionService.selectgetAbandonedTargetByStudentId(studentId));
    }
    /**
     * 绑定班级
     */
    @PostMapping("/bindclass")
    public AjaxResult bingClass(@RequestBody ClassDTO classDTO){
        return toAjax(teacherClassService.bingClass(classDTO));
    }

    /**
     * 教师批阅岗位
     * @param teacherReview
     * @return
     */
    @PostMapping
    public AjaxResult add(@RequestBody ReviewDTO teacherReview)
    {
        return toAjax(teacherReviewService.insertTeacherReview(teacherReview));
    }

    /**
     * 查看所有批阅
     * @return
     */
    @GetMapping
    public AjaxResult getTeacherReview(){
        return success(teacherReviewService.selectTeacherReviewList(null));
    }
}
