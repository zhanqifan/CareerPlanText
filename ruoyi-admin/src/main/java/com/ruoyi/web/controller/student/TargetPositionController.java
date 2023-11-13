package com.ruoyi.web.controller.student;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.dto.TargetPositionDTO;
import com.ruoyi.student.domain.vo.TargetPositionVO;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.BeanUtils;
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
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.service.ITargetPositionService;

/**
 * 岗位管理Controller
 * 
 * @author lh
 * @date 2023-11-07
 */
@RestController
@RequestMapping("/student/position")
public class TargetPositionController extends BaseController
{
    @Autowired
    private ITargetPositionService targetPositionService;

    /**
     * 查询岗位管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TargetPosition targetPosition)
    {
        startPage();
        List<TargetPosition> list = targetPositionService.selectTargetPositionList(targetPosition);
        List<TargetPositionVO> collect = list.stream().map(target -> {
            TargetPositionVO targetPositionVO = new TargetPositionVO();
            BeanUtils.copyProperties(target, targetPositionVO);
            //TODO 完成率计算
            return targetPositionVO;
        }).collect(Collectors.toList());
        return getDataTable(collect);
    }

    /**
     * 学生添加目标岗位
     * @param targetPositionDTO
     * @return
     */
    @PostMapping
    public AjaxResult addTargetPosition(@RequestBody TargetPositionDTO targetPositionDTO){
        return toAjax(targetPositionService.addTargetPosition(targetPositionDTO));
    }

    /**
     * 获取岗位管理详细信息
     */
    @GetMapping(value = "/{positionId}")
    public AjaxResult getInfo(@PathVariable("positionId") String positionId)
    {
        return success(targetPositionService.selectTargetPositionByPositionId(positionId));
    }

    /**
     * 学生修改岗位管理
     */
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SkillsInfo skillsInfo)
    {
        return toAjax(targetPositionService.updateskillsInfo(skillsInfo));
    }

    /**
     * 废止目标
     * @param positionId
     * @return
     */
    @GetMapping("/repeal/{positionId}")
    public AjaxResult repeal(@PathVariable("positionId") String positionId){
        return toAjax(targetPositionService.repealPositionId(positionId));
    }


    /**
     * 导出岗位管理列表
     */
    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TargetPosition targetPosition)
    {
        List<TargetPosition> list = targetPositionService.selectTargetPositionList(targetPosition);
        ExcelUtil<TargetPosition> util = new ExcelUtil<TargetPosition>(TargetPosition.class);
        util.exportExcel(response, list, "岗位管理数据");
    }


    /**
     * 删除岗位管理
     */
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{positionIds}")
    public AjaxResult remove(@PathVariable Long[] positioIds)
    {
        return toAjax(targetPositionService.deleteTargetPositionByPositionIds(positioIds));
    }
}