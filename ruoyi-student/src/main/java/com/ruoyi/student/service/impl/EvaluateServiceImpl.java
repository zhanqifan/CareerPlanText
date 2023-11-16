package com.ruoyi.student.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.constant.EvaluateTypeConstants;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.dto.EvaluateDTO;
import com.ruoyi.student.service.ICtatlogueService;
import com.ruoyi.student.service.ISkillsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.EvaluateMapper;
import com.ruoyi.student.domain.Evaluate;
import com.ruoyi.student.service.IEvaluateService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评价Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-08
 */
@Service
public class EvaluateServiceImpl implements IEvaluateService 
{
    @Autowired
    private EvaluateMapper evaluateMapper;

    @Autowired
    private ISkillsInfoService skillsInfoService;

    /**
     * 查询评价
     * 
     * @param evaluateId 评价主键
     * @return 评价
     */
    @Override
    public Evaluate selectEvaluateByEvaluateId(Integer evaluateId)
    {
        return evaluateMapper.selectEvaluateByEvaluateId(evaluateId);
    }

    /**
     * 查询评价列表
     * 
     * @param evaluate 评价
     * @return 评价
     */
    @Override
    public List<Evaluate> selectEvaluateList(Evaluate evaluate)
    {
        return evaluateMapper.selectEvaluateList(evaluate);
    }

    /**
     * 新增评价
     * 
     * @param evaluateDTO 评价
     * @return 结果
     */
    @Override
    @Transactional
    public int insertEvaluate(EvaluateDTO evaluateDTO)
    {
        //更新目标状态
        SkillsInfo skillsInfo = new SkillsInfo();
        skillsInfo.setId(evaluateDTO.getSkillsId());
        skillsInfo.setCompletionStatus(evaluateDTO.getCompletionStatus());
        skillsInfo.setEvaluateState(1);
        skillsInfo.setCompleteTime(evaluateDTO.getCompleteTime());
        skillsInfoService.updateSkillsInfo(skillsInfo);
        //存储自评内容
        Evaluate evaluate = new Evaluate();
        evaluate.setType(EvaluateTypeConstants.STUDENTS);
        evaluate.setSkillsId(evaluateDTO.getSkillsId());
        evaluate.setContent(evaluateDTO.getContent());
        evaluate.setCreateBy(SecurityUtils.getUsername());
        evaluate.setCreateTime(DateUtils.getNowDate());
        return evaluateMapper.insertEvaluate(evaluate);
    }

    /**
     * 修改评价
     * 
     * @param evaluateDTO 评价
     * @return 结果
     */
    @Override
    public int updateEvaluate(EvaluateDTO evaluateDTO)
    {
        Evaluate evaluate = new Evaluate();
        evaluate.setEvaluateId(evaluateDTO.getEvaluateId());
        evaluate.setContent(evaluateDTO.getContent());
        evaluate.setUpdateTime(DateUtils.getNowDate());
        return evaluateMapper.updateEvaluate(evaluate);
    }

    /**
     * 批量删除评价
     * 
     * @param evaluateIds 需要删除的评价主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteEvaluateByEvaluateIds(Integer evaluateIds)
    {
        //修改目标自评状态
        Evaluate evaluate = evaluateMapper.selectEvaluateBySkillsId(evaluateIds);
        SkillsInfo skillsInfo = new SkillsInfo();
        skillsInfo.setEvaluateState(0);
        skillsInfo.setId(evaluate.getSkillsId());
        skillsInfoService.updateSkillsInfo(skillsInfo);
        //删除自评
        return evaluateMapper.deleteEvaluateByEvaluateIds(evaluateIds);
    }

    /**
     * 删除评价信息
     * 
     * @param evaluateId 评价主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateByEvaluateId(Integer evaluateId)
    {
        return evaluateMapper.deleteEvaluateByEvaluateId(evaluateId);
    }

    /**
     * 根据目标id查询自评内容
     * @param skillsId
     * @return
     */
    @Override
    public Evaluate selectEvaluateBySkillsId(Integer skillsId) {

        return evaluateMapper.selectEvaluateBySkillsId(skillsId);
    }
}
