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
import com.ruoyi.student.domain.Ctatlogue;
import com.ruoyi.student.service.ICtatlogueService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 技能目录Controller
 * 
 * @author lihong
 * @date 2023-11-08
 */
@RestController
@RequestMapping("/student/ctatlogue")
public class CtatlogueController extends BaseController
{
    @Autowired
    private ICtatlogueService ctatlogueService;

    /**
     * 查询技能目录列表
     */
    @PreAuthorize("@ss.hasPermi('student:ctatlogue:list')")
    @GetMapping("/list")
    public AjaxResult list(Ctatlogue ctatlogue)
    {
        List<Ctatlogue> list = ctatlogueService.selectCtatlogueList(ctatlogue);
        return success(list);
    }

    /**
     * 导出技能目录列表
     */
    @PreAuthorize("@ss.hasPermi('student:ctatlogue:export')")
    @Log(title = "技能目录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Ctatlogue ctatlogue)
    {
        List<Ctatlogue> list = ctatlogueService.selectCtatlogueList(ctatlogue);
        ExcelUtil<Ctatlogue> util = new ExcelUtil<Ctatlogue>(Ctatlogue.class);
        util.exportExcel(response, list, "技能目录数据");
    }

    /**
     * 获取技能目录详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:ctatlogue:query')")
    @GetMapping(value = "/{catalogueId}")
    public AjaxResult getInfo(@PathVariable("catalogueId") Long catalogueId)
    {
        return success(ctatlogueService.selectCtatlogueByCatalogueId(catalogueId));
    }

    /**
     * 新增技能目录
     */
    @PreAuthorize("@ss.hasPermi('student:ctatlogue:add')")
    @Log(title = "技能目录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Ctatlogue ctatlogue)
    {
        return toAjax(ctatlogueService.insertCtatlogue(ctatlogue));
    }

    /**
     * 修改技能目录
     */
    @PreAuthorize("@ss.hasPermi('student:ctatlogue:edit')")
    @Log(title = "技能目录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Ctatlogue ctatlogue)
    {
        return toAjax(ctatlogueService.updateCtatlogue(ctatlogue));
    }

    /**
     * 删除技能目录
     */
    @PreAuthorize("@ss.hasPermi('student:ctatlogue:remove')")
    @Log(title = "技能目录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{catalogueIds}")
    public AjaxResult remove(@PathVariable Long[] catalogueIds)
    {
        return toAjax(ctatlogueService.deleteCtatlogueByCatalogueIds(catalogueIds));
    }
}
