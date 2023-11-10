package com.ruoyi.web.controller.student;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.student.domain.dto.EvaluateDTO;
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
import com.ruoyi.student.domain.Evaluate;
import com.ruoyi.student.service.IEvaluateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评价Controller
 * 
 * @author lihong
 * @date 2023-11-08
 */
@RestController
@RequestMapping("/students/evaluate")
public class EvaluateController extends BaseController
{
    @Autowired
    private IEvaluateService evaluateService;

    /**
     * 查询评价列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Evaluate evaluate)
    {
        startPage();
        List<Evaluate> list = evaluateService.selectEvaluateList(evaluate);
        return getDataTable(list);
    }

    /**
     * 导出评价列表
     */
    @Log(title = "评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Evaluate evaluate)
    {
        List<Evaluate> list = evaluateService.selectEvaluateList(evaluate);
        ExcelUtil<Evaluate> util = new ExcelUtil<Evaluate>(Evaluate.class);
        util.exportExcel(response, list, "评价数据");
    }

    /**
     * 获取评价详细信息
     */
    @GetMapping(value = "/{skillsId}")
    public AjaxResult getInfo(@PathVariable("skillsId") Integer skillsId)
    {
        return success(evaluateService.selectEvaluateBySkillsId(skillsId));
    }

    /**
     * 新增评价
     */
    @Log(title = "评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvaluateDTO evaluateDTO)
    {
        return toAjax(evaluateService.insertEvaluate(evaluateDTO));
    }

    /**
     * 修改评价
     */
    @Log(title = "评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvaluateDTO evaluateDTO)
    {
        return toAjax(evaluateService.updateEvaluate(evaluateDTO));
    }

    /**
     * 删除评价
     */
    @Log(title = "评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{evaluateId}")
    public AjaxResult remove(@PathVariable Integer evaluateId)
    {
        return toAjax(evaluateService.deleteEvaluateByEvaluateIds(evaluateId));
    }
}
