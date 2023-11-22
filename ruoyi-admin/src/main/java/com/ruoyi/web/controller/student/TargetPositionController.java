package com.ruoyi.web.controller.student;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.dto.TargetPositionDTO;
import com.ruoyi.student.domain.vo.TargetPositionVO;
import com.ruoyi.student.service.ISkillsInfoService;
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

    @Resource
    private ISkillsInfoService skillsInfoService;

    /**
     * 查询岗位管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TargetPosition targetPosition)
    {
        startPage();
        targetPosition.setCreateBy(SecurityUtils.getUsername());

        List<TargetPosition> list = targetPositionService.selectTargetPositionList(targetPosition);
        List<TargetPositionVO> collect = list.stream().map(target -> {
            TargetPositionVO targetPositionVO = new TargetPositionVO();
            TargetPositionVO positionVO = skillsInfoService.CalculationCompletionRate(target.getPositionId());
            BeanUtils.copyProperties(positionVO, targetPositionVO);
            targetPositionVO.setPositionId(target.getPositionId());
            targetPositionVO.setPositionName(target.getPositionName());
            targetPositionVO.setIsMain(target.getIsMain());
            targetPositionVO.setReviewsNumber(target.getReviewsNumber());
            targetPositionVO.setState(target.getState());
            targetPositionVO.setModificationsNumber(target.getModificationsNumber());
            targetPositionVO.setCreateBy(target.getCreateBy());
            targetPositionVO.setCreateTime(target.getCreateTime());
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
     * 设置主目标
     */
    @GetMapping("/setPrimaryTarget/{positionId}")
    public AjaxResult setPrimaryTarget(@PathVariable("positionId") String positionId){
        return toAjax(targetPositionService.setPrimaryTarget(positionId));
    }

    /**
     * 发布草稿岗位
     */
    @GetMapping("/publish/{positionId}")
    public AjaxResult publishPosition(@PathVariable("positionId") String positionId){
        return toAjax(targetPositionService.publishPosition(positionId));
    }

    /**
     * 删除岗位管理
     */
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{positionIds}")
    public AjaxResult remove(@PathVariable String positionIds)
    {
        return toAjax(targetPositionService.deleteTargetPositionByPositionId(positionIds));
    }
    /**
     * 删除技能
     */
    @DeleteMapping("/skills/{skillsId}")
    public AjaxResult removeSkills(@PathVariable("skillsId") String skillsId){
        return toAjax(skillsInfoService.deleteSkillsInfoById(skillsId));
    }
}
