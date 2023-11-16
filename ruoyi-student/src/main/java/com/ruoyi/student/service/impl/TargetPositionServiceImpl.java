package com.ruoyi.student.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;

import com.ruoyi.student.domain.InternshipContent;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.dto.TargetPositionDTO;
import com.ruoyi.student.domain.vo.SecondaryCtatlogue;
import com.ruoyi.student.domain.vo.TargetPositionInfoVO;
import com.ruoyi.student.mapper.TargetPositionMapper;
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.service.ICtatlogueService;
import com.ruoyi.student.service.IInternshipContentService;
import com.ruoyi.student.service.ISkillsInfoService;
import com.ruoyi.student.service.ITargetPositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 岗位管理Service业务层处理
 * 
 * @author lh
 * @date 2023-11-07
 */
@Service
public class TargetPositionServiceImpl implements ITargetPositionService 
{
    @Resource
    private TargetPositionMapper targetPositionMapper;

    @Autowired
    private ISkillsInfoService  skillsInfoService;

    @Autowired
    private ICtatlogueService ctatlogueService;

    @Resource
    private IInternshipContentService iInternshipContentService;
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
                    skillsInfo.setContent(iInternshipContentService.selectInternshipContentBySkillsInfoId(skillsInfo.getId()).getContent());
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
        String username = SecurityUtils.getUsername();
        String positionId = IdUtils.fastSimpleUUID();
        TargetPosition targetPosition = new TargetPosition();
        targetPosition.setPositionId(positionId);
        targetPosition.setPositionName(targetPositionDTO.getPositionName());
        targetPosition.setCreateBy(username);
        targetPosition.setState(targetPositionDTO.getState());
        if(targetPositionMapper.insertTargetPosition(targetPosition)==1){
            List<SkillsInfo> skillsInfoList = targetPositionDTO.getSkillsInfoList();
            for (SkillsInfo skillsInfo:skillsInfoList){
                //添加技能详情
                String skillsInfoId = IdUtils.fastSimpleUUID();
                skillsInfo.setId(skillsInfoId);
                skillsInfo.setFirstId(skillsInfo.getFirstId());
                skillsInfo.setTargetPositionId(positionId);
                skillsInfo.setTakeRole(skillsInfo.getTakeRole());
                skillsInfo.setCreateBy(username);
                //添加实习/实践内容
                if(StringUtils.isNotNull(skillsInfo.getContent())){
                    InternshipContent internshipContent = new InternshipContent();
                    internshipContent.setSkillsId(skillsInfoId);
                    internshipContent.setContent(skillsInfo.getContent());
                    iInternshipContentService.insertInternshipContent(internshipContent);
                }
                skillsInfoService.insertSkillsInfo(skillsInfo);
            }
            return 1;
        }
        return 0;
    }
    @Override
    @Transactional
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
            //修改实习/实践内容
            String id = skillsInfo.getId();
            InternshipContent internshipContent = new InternshipContent();
            internshipContent.setSkillsId(id);
            internshipContent.setContent(skillsInfo.getContent());
            iInternshipContentService.updateInternshipContent(internshipContent);
            return skillsInfoService.updateSkillsInfo(skillsInfo);
        }
        return 0;
    }



    /**
     * 废止目标
     * @param positionId
     * @return
     */
    @Override
    public int repealPositionId(String positionId) {
        List<TargetPosition> targetPositions = targetPositionMapper.selectTargetPositionListByUserId(SecurityUtils.getUserId().toString());
        //判断是否只有两个岗位
        if (targetPositions.size()==2){
            for (TargetPosition targetPosition:targetPositions){
                //更改另一个目标的状态
                if(!targetPosition.getPositionId().equals(positionId)){
                    System.out.println(targetPosition);
                    targetPosition.setIsMain(1);
                    targetPositionMapper.updateTargetPosition(targetPosition);
                }
            }
        }
        TargetPosition targetPosition = new TargetPosition();
        targetPosition.setPositionId(positionId);
        targetPosition.setState(0);
        targetPosition.setIsMain(0);
        return targetPositionMapper.updateTargetPosition(targetPosition);
    }

    @Override
    public List<TargetPosition> selectTargetPositionListByUserName(String createBy) {
        return targetPositionMapper.selectTargetPositionListByUserName(createBy);
    }

    /**
     * 查看学生废弃目标
     * @param studentId
     * @return
     */
    @Override
    public List<TargetPosition> selectgetAbandonedTargetByStudentId(String studentId) {
        return targetPositionMapper.selectgetAbandonedTargetByStudentId(studentId);
    }

    /**
     * 根据学生id查询岗位列表
     * 
     * @return 岗位管理
     */
    @Override
    public List<TargetPosition> selectTargetPositionListByUserId(String UserId) {
        return targetPositionMapper.selectTargetPositionListByUserId(UserId);
    }

    /**
     * 查询所有岗位列表
     */
    @Override
    public List<TargetPosition> selectTargetPositionList(TargetPosition targetPosition) {
        return targetPositionMapper.selectTargetPositionList(targetPosition);
    }

    @Override
    public TargetPosition selectMainTargetPositionListByUserName(String sNum) {
        return targetPositionMapper.selectMainTargetPositionListByUserName(sNum);
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
//        return targetPositionMapper.deleteTargetPositionByPositionIds(positionIds);
        return 0;
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
