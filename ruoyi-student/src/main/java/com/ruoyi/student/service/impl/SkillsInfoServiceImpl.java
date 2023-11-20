package com.ruoyi.student.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.student.domain.vo.TargetPositionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.SkillsInfoMapper;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.service.ISkillsInfoService;

import javax.annotation.Resource;

/**
 * 技能详情Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-08
 */
@Service
public class SkillsInfoServiceImpl implements ISkillsInfoService 
{
    @Resource
    private SkillsInfoMapper skillsInfoMapper;

    /**
     * 查询技能详情
     * 
     * @param id 技能详情主键
     * @return 技能详情
     */
    @Override
    public SkillsInfo selectSkillsInfoById(Integer id)
    {
        return skillsInfoMapper.selectSkillsInfoById(id);
    }

    /**
     * 查询技能详情列表
     * 
     * @param skillsInfo 技能详情
     * @return 技能详情
     */
    @Override
    public List<SkillsInfo> selectSkillsInfoList(SkillsInfo skillsInfo)
    {
        return skillsInfoMapper.selectSkillsInfoList(skillsInfo);
    }

    /**
     * 新增技能详情
     * 
     * @param skillsInfo 技能详情
     * @return 结果
     */
    @Override
    public int insertSkillsInfo(SkillsInfo skillsInfo)
    {
        return skillsInfoMapper.insertSkillsInfo(skillsInfo);
    }

    /**
     * 修改技能详情
     * 
     * @param skillsInfo 技能详情
     * @return 结果
     */
    @Override
    public int updateSkillsInfo(SkillsInfo skillsInfo)
    {
        return skillsInfoMapper.updateSkillsInfo(skillsInfo);
    }

    /**
     * 批量删除技能详情
     * 
     * @param ids 需要删除的技能详情主键
     * @return 结果
     */
    @Override
    public int deleteSkillsInfoByIds(Integer[] ids)
    {
        return skillsInfoMapper.deleteSkillsInfoByIds(ids);
    }

    /**
     * 删除技能详情信息
     * 
     * @param id 技能详情主键
     * @return 结果
     */
    @Override
    public int deleteSkillsInfoById(Integer id)
    {
        return skillsInfoMapper.deleteSkillsInfoById(id);
    }

    /**
     * 根据岗位id查询岗位的具体信息
     * @param id
     * @return
     */
    @Override
    public List<SkillsInfo> selectSkillsInfoByPositionId(String id) {
        return skillsInfoMapper.selectSkillsInfoByPositionId(id);
    }

    @Override
    public List<SkillsInfo> selectSkillsInfoByUnfinished(String positionId) {
        SkillsInfo skillsInfo = new SkillsInfo();
        skillsInfo.setTargetPositionId(positionId);
        skillsInfo.setCompletionStatus(0);
        return skillsInfoMapper.selectSkillsInfoList(skillsInfo);
    }

    @Override
    public List<SkillsInfo> selectSkillsInfoByCompletions(String positionId) {
        SkillsInfo skillsInfo = new SkillsInfo();
        skillsInfo.setTargetPositionId(positionId);
        skillsInfo.setCompletionStatus(1);
        return skillsInfoMapper.selectSkillsInfoList(skillsInfo);
    }

    @Override
    public void AnalyzeCatalogData(String positionId) {

    }

    @Override
    public SkillsInfo selectSkillsInfoBycreateBy(String sNum) {
        return skillsInfoMapper.selectSkillsInfoBycreateBy(sNum);
    }

    @Override
    public TargetPositionVO CalculationCompletionRate(String positionId) {
        List<SkillsInfo> skillsInfoList = skillsInfoMapper.selectSkillsInfoByPositionId(positionId);
        //总体完成率
        TargetPositionVO targetPositionVO = new TargetPositionVO();
        double completionRate = 0;
        double a=0.00;
        if(!skillsInfoList.isEmpty()){
            for (SkillsInfo skillsInfo:skillsInfoList){
                a+=skillsInfo.getCompletionStatus();
            }
            completionRate=a/skillsInfoList.size();
        }
        targetPositionVO.setCompletionRate(completionRate);
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        Date currentDate = calendar.getTime();
        // 计算1-6月和7-12月的截止和完成情况
        int projects1To6 = 0;
        int completedProjects1To6 = 0;
        int projects7To12 = 0;
        int completedProjects7To12 = 0;
        for (SkillsInfo skillsInfo : skillsInfoList) {
            // 获取项目的结束时间和完成时间
            Date endTime = skillsInfo.getEndTime();
            Date completeTime = skillsInfo.getCompleteTime();
            // 检查是否为本年度的项目
            calendar.setTime(endTime);
            int projectYear = calendar.get(Calendar.YEAR);
            if (projectYear == currentYear) {
                if (calendar.get(Calendar.MONTH) < 6) {
                    // 1-6月的项目
                    projects1To6++;
                    if (completeTime != null) {
                        completedProjects1To6++;
                    }
                } else {
                    // 7-12月的项目
                    projects7To12++;
                    if (completeTime != null) {
                        completedProjects7To12++;
                    }
                }
            }
        }
        // 计算完成率
        double completionRate1To6 = calculateCompletionRate(projects1To6, completedProjects1To6);
        double completionRate7To12 = calculateCompletionRate(projects7To12, completedProjects7To12);
        targetPositionVO.setCompletedQuantity1((long) projects1To6);
        targetPositionVO.setCompletionRate1(completionRate1To6);
        targetPositionVO.setCompletedQuantity2((long) projects7To12);
        targetPositionVO.setCompletionRate2(completionRate7To12);
        return targetPositionVO;
    }

    private static double calculateCompletionRate(int totalProjects, int completedProjects) {
        if (totalProjects == 0) {
            return 0.0;
        }
        return (double) completedProjects / totalProjects;
    }
}
