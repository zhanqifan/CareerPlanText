package com.ruoyi.web.controller.student;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import org.aspectj.weaver.loadtime.Aj;
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
import com.ruoyi.student.domain.DataAnalysis;
import com.ruoyi.student.service.IDataAnalysisService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生数据分析Controller
 * 
 * @author lihong
 * @date 2023-11-12
 */
@RestController
@RequestMapping("/student/analysis")
public class DataAnalysisController extends BaseController
{
    @Autowired
    private IDataAnalysisService dataAnalysisService;


    /**
     * 学生查看本人数据分析结果
     */
    @GetMapping
    public AjaxResult getDataAnalysis(){
        return success(dataAnalysisService.getDataAnalysis(SecurityUtils.getUsername()));
    }

    /**
     * 查询学生数据分析列表
     */
    @PreAuthorize("@ss.hasPermi('student:DataAnalysis:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataAnalysis dataAnalysis)
    {
        startPage();
        List<DataAnalysis> list = dataAnalysisService.selectDataAnalysisList(dataAnalysis);
        return getDataTable(list);
    }

    /**
     * 导出学生数据分析列表
     */
    @PreAuthorize("@ss.hasPermi('student:DataAnalysis:export')")
    @Log(title = "学生数据分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataAnalysis dataAnalysis)
    {
        List<DataAnalysis> list = dataAnalysisService.selectDataAnalysisList(dataAnalysis);
        ExcelUtil<DataAnalysis> util = new ExcelUtil<DataAnalysis>(DataAnalysis.class);
        util.exportExcel(response, list, "学生数据分析数据");
    }

    /**
     * 获取学生数据分析详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:DataAnalysis:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dataAnalysisService.selectDataAnalysisById(id));
    }

    /**
     * 新增学生数据分析
     */
    @PreAuthorize("@ss.hasPermi('student:DataAnalysis:add')")
    @Log(title = "学生数据分析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataAnalysis dataAnalysis)
    {
        return toAjax(dataAnalysisService.insertDataAnalysis(dataAnalysis));
    }

    /**
     * 修改学生数据分析
     */
    @PreAuthorize("@ss.hasPermi('student:DataAnalysis:edit')")
    @Log(title = "学生数据分析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataAnalysis dataAnalysis)
    {
        return toAjax(dataAnalysisService.updateDataAnalysis(dataAnalysis));
    }

    /**
     * 删除学生数据分析
     */
    @PreAuthorize("@ss.hasPermi('student:DataAnalysis:remove')")
    @Log(title = "学生数据分析", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dataAnalysisService.deleteDataAnalysisByIds(ids));
    }
}
