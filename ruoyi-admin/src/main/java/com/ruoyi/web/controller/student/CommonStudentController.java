package com.ruoyi.web.controller.student;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.service.ICommonStudentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公用 学生信息Controller
 * 
 * @author lihong
 * @date 2023-11-09
 */
@RestController
@RequestMapping("/student/student")
public class CommonStudentController extends BaseController
{
    @Autowired
    private ICommonStudentService commonStudentService;

    /**
     * 查询公用 学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:student:list')")
    @GetMapping("/list")
    public TableDataInfo list(CommonStudent commonStudent)
    {
        startPage();
        List<CommonStudent> list = commonStudentService.selectCommonStudentList(commonStudent);
        return getDataTable(list);
    }

    /**
     * 导出公用 学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:student:export')")
    @Log(title = "公用 学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommonStudent commonStudent)
    {
        List<CommonStudent> list = commonStudentService.selectCommonStudentList(commonStudent);
        ExcelUtil<CommonStudent> util = new ExcelUtil<CommonStudent>(CommonStudent.class);
        util.exportExcel(response, list, "公用 学生信息数据");
    }

    /**
     * 获取公用 学生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:student:query')")
    @GetMapping(value = "/{sId}")
    public AjaxResult getInfo(@PathVariable("sId") Long sId)
    {
        return success(commonStudentService.selectCommonStudentBySId(sId));
    }

    /**
     * 新增公用 学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:student:add')")
    @Log(title = "公用 学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommonStudent commonStudent)
    {
        return toAjax(commonStudentService.insertCommonStudent(commonStudent));
    }

    /**
     * 修改公用 学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:student:edit')")
    @Log(title = "公用 学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommonStudent commonStudent)
    {
        return toAjax(commonStudentService.updateCommonStudent(commonStudent));
    }

    /**
     * 删除公用 学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:student:remove')")
    @Log(title = "公用 学生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sIds}")
    public AjaxResult remove(@PathVariable Long[] sIds)
    {
        return toAjax(commonStudentService.deleteCommonStudentBySIds(sIds));
    }
}
