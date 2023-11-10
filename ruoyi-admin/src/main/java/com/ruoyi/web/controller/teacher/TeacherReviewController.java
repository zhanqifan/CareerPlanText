//package com.ruoyi.web.controller.teacher;
//
//import java.util.List;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.ruoyi.common.annotation.Log;
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.common.enums.BusinessType;
//import com.ruoyi.teacher.domain.TeacherReview;
//import com.ruoyi.teacher.service.ITeacherReviewService;
//import com.ruoyi.common.utils.poi.ExcelUtil;
//import com.ruoyi.common.core.page.TableDataInfo;
//
///**
// * 教师批阅Controller
// *
// * @author lihong
// * @date 2023-11-10
// */
//@RestController
//@RequestMapping("/teacher/review")
//public class TeacherReviewController extends BaseController
//{
//    @Autowired
//    private ITeacherReviewService teacherReviewService;
//
//    /**
//     * 查询教师批阅列表
//     */
//    @PreAuthorize("@ss.hasPermi('teacher:review:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(TeacherReview teacherReview)
//    {
//        startPage();
//        List<TeacherReview> list = teacherReviewService.selectTeacherReviewList(teacherReview);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出教师批阅列表
//     */
//    @PreAuthorize("@ss.hasPermi('teacher:review:export')")
//    @Log(title = "教师批阅", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, TeacherReview teacherReview)
//    {
//        List<TeacherReview> list = teacherReviewService.selectTeacherReviewList(teacherReview);
//        ExcelUtil<TeacherReview> util = new ExcelUtil<TeacherReview>(TeacherReview.class);
//        util.exportExcel(response, list, "教师批阅数据");
//    }
//
//    /**
//     * 获取教师批阅详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('teacher:review:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id)
//    {
//        return success(teacherReviewService.selectTeacherReviewById(id));
//    }
//
//    /**
//     * 新增教师批阅
//     */
//    @PreAuthorize("@ss.hasPermi('teacher:review:add')")
//    @Log(title = "教师批阅", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody TeacherReview teacherReview)
//    {
//        return toAjax(teacherReviewService.insertTeacherReview(teacherReview));
//    }
//
//    /**
//     * 修改教师批阅
//     */
//    @PreAuthorize("@ss.hasPermi('teacher:review:edit')")
//    @Log(title = "教师批阅", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody TeacherReview teacherReview)
//    {
//        return toAjax(teacherReviewService.updateTeacherReview(teacherReview));
//    }
//
//    /**
//     * 删除教师批阅
//     */
//    @PreAuthorize("@ss.hasPermi('teacher:review:remove')")
//    @Log(title = "教师批阅", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(teacherReviewService.deleteTeacherReviewByIds(ids));
//    }
//}
