package com.ruoyi.student.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.student.domain.Ctatlogue;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.dto.TargetPositionDTO;
import com.ruoyi.student.domain.vo.SecondaryCtatlogue;
import com.ruoyi.student.domain.vo.TargetPositionInfoVO;
import com.ruoyi.student.mapper.TargetPositionMapper;
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.service.ICtatlogueService;
import com.ruoyi.student.service.ISkillsInfoService;
import com.ruoyi.student.service.ITargetPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 岗位管理Service业务层处理
 * 
 * @author lh
 * @date 2023-11-07
 */
@Service
public class TargetPositionServiceImpl implements ITargetPositionService 
{
    @Autowired
    private TargetPositionMapper targetPositionMapper;

    @Autowired
    private ISkillsInfoService  skillsInfoService;

    @Autowired
    private ICtatlogueService ctatlogueService;
    /**
     * 查询岗位管理
     * 
     * @param positionId 岗位管理主键
     * @return 岗位管理
     */
    @Override
    public TargetPositionInfoVO selectTargetPositionByPositionId(String positionId)
    {
        TargetPositionInfoVO targetPositionInfoVO = new TargetPositionInfoVO();

        TargetPosition targetPosition = targetPositionMapper.selectTargetPositionByPositionId(positionId);
        String id = targetPosition.getPositionId();
        targetPositionInfoVO.setPositionName(targetPosition.getPositionName());
        targetPositionInfoVO.setPositionId(id);
        targetPositionInfoVO.setState(targetPosition.getState());

        //岗位目标所属二级目录
        List<SecondaryCtatlogue> selectSecondaryCtatlogueList = ctatlogueService.selectSecondaryCtatlogueList();
        //具体岗位目标
        List<SkillsInfo> skillsInfoList =skillsInfoService.selectSkillsInfoByPositionId(id);
        for(SecondaryCtatlogue secondaryCtatlogue:selectSecondaryCtatlogueList){
            ArrayList<SkillsInfo> skillsInfos = new ArrayList<>();
            for (SkillsInfo skillsInfo:skillsInfoList){
                if(secondaryCtatlogue.getCatalogueId().equals(skillsInfo.getCtatlogueId())){
                    skillsInfos.add(skillsInfo);
                }
            }
            secondaryCtatlogue.setSkillsInfoList(skillsInfos);
        }
        targetPositionInfoVO.setCtatlogueList(selectSecondaryCtatlogueList);
        return targetPositionInfoVO;
    }


    /**
     * 新增岗位
     * @param targetPositionDTO 岗位
     * @return
     */
    @Override
    @Transactional
    public int addTargetPosition(TargetPositionDTO targetPositionDTO) {
        String userId = SecurityUtils.getUserId().toString();
        //TODO 添加岗位名称
        String positionId = IdUtils.fastSimpleUUID();
        TargetPosition targetPosition = new TargetPosition();
        targetPosition.setPositionId(positionId);
        targetPosition.setPositionName(targetPositionDTO.getPositionName());
        targetPosition.setCreateBy(userId);
        targetPosition.setState(targetPositionDTO.getState());
        //TODO 添加岗位技能详情
        if(targetPositionMapper.insertTargetPosition(targetPosition)==1){
            List<SkillsInfo> skillsInfoList = targetPositionDTO.getSkillsInfoList();
            System.out.println(skillsInfoList);
            for (SkillsInfo skillsInfo:skillsInfoList){
                skillsInfo.setTargetPositionId(positionId);
                skillsInfo.setCreateBy(userId);
                System.out.println("岗位技能详情:"+skillsInfo);
                skillsInfoService.insertSkillsInfo(skillsInfo);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int updateskillsInfo(SkillsInfo skillsInfo) {
        //判断是否发布
        String targetPositionId = skillsInfo.getTargetPositionId();
        TargetPosition targetPosition = targetPositionMapper.selectTargetPositionByPositionId(targetPositionId);
        if(targetPosition.getState().equals(0)){
            //是否已经修改过
            if(skillsInfo.getModificationsNumber()>0){
                return 0;
            }
            skillsInfo.setModificationsNumber(1);
            return skillsInfoService.updateSkillsInfo(skillsInfo);
        }
        return skillsInfoService.updateSkillsInfo(skillsInfo);
    }

    /**
     * 学生自评
     * @param skillsInfo
     * @return
     */
    @Override
    public int evaluateskills(SkillsInfo skillsInfo) {
//        return skillsInfoService.evaluateskills(skillsInfo);
        return 0;
    }

    /**
     * 查询岗位管理列表
     * 
     * @param targetPosition 岗位管理
     * @return 岗位管理
     */
    @Override
    public List<TargetPosition> selectTargetPositionList(TargetPosition targetPosition)
    {
        return targetPositionMapper.selectTargetPositionListByUserId(SecurityUtils.getUserId().toString());
    }

    /**
     * 新增岗位管理
     * 
     * @param targetPosition 岗位管理
     * @return 结果
     */
    @Override
    public int insertTargetPosition(TargetPosition targetPosition)
    {
        targetPosition.setCreateTime(DateUtils.getNowDate());
        return targetPositionMapper.insertTargetPosition(targetPosition);
    }

    /**
     * 修改岗位管理
     * 
     * @param targetPosition 岗位管理
     * @return 结果
     */
    @Override
    public int updateTargetPosition(TargetPosition targetPosition)
    {
        return targetPositionMapper.updateTargetPosition(targetPosition);
    }

    /**
     * 批量删除岗位管理
     * 
     * @param positionIds 需要删除的岗位管理主键
     * @return 结果
     */
    @Override
    public int deleteTargetPositionByPositionIds(Long[] positionIds)
    {
        return targetPositionMapper.deleteTargetPositionByPositionIds(positionIds);
    }

    /**
     * 删除岗位管理信息
     * 
     * @param positionId 岗位管理主键
     * @return 结果
     */
    @Override
    public int deleteTargetPositionByPositionId(Long positionId)
    {
        return targetPositionMapper.deleteTargetPositionByPositionId(positionId);
    }

}
