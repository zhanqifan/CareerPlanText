package com.ruoyi.web.controller.teacher;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtil;
import com.ruoyi.student.service.ITargetPositionService;
import com.ruoyi.teacher.domain.dto.ClassDTO;
import com.ruoyi.teacher.domain.dto.StudentDTO;
import com.ruoyi.teacher.domain.dto.ReviewDTO;
import com.ruoyi.teacher.domain.vo.StudentVO;
import com.ruoyi.teacher.service.ITeacherReviewService;
import com.ruoyi.teacher.service.TeacherClassService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     */
    @GetMapping("/list")
    public TableDataInfo list(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              StudentDTO studentDTO){
        List<StudentVO> studentVOS = teacherClassService.selectStudent(studentDTO);
        PageUtil<StudentVO> studentVOPageUtil = new PageUtil<>(studentVOS, pageNum, pageSize, studentVOS.size());
        return getDataTable(studentVOPageUtil.getData(),studentVOPageUtil.getTotalPages());
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
