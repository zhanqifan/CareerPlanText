package com.ruoyi.student.quartz;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.student.domain.*;
import com.ruoyi.student.mapper.DataAnalysisMapper;
import com.ruoyi.student.service.ICtatlogueService;
import com.ruoyi.student.service.ISkillsInfoService;
import com.ruoyi.student.service.ISubAnalysisService;
import com.ruoyi.student.service.ITargetPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.*;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author lihong
 * @date 2023/11/12
 */
public class timingCalculation {

    @Resource
    private DataAnalysisMapper dataAnalysisMapper;

    @Resource
    private ITargetPositionService targetPositionService;

    @Resource
    private ISkillsInfoService skillsInfoService;

    @Resource
    private ICtatlogueService ctatlogueService;

    @Resource
    private ISubAnalysisService subAnalysisService;

    @Transactional
    public void TimingStatistics(){
        //查询出岗位列表
        List<TargetPosition> targetPositions = targetPositionService.selectTargetPositionList(null);
        for (TargetPosition targetPosition:targetPositions){
            DataAnalysis dataAnalysis = new DataAnalysis();
            String positionId = targetPosition.getPositionId();
            //岗位id
            dataAnalysis.setPositionId(positionId);
            //创建者id
            dataAnalysis.setCreateBy(targetPosition.getCreateBy());
            //计算发布时长
            Date createTime = targetPosition.getCreateTime();
            LocalDateTime givenDateTime = LocalDateTime.ofInstant(createTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime currentDateTime = LocalDateTime.now();
            Duration duration = Duration.between(givenDateTime, currentDateTime);
            long days = duration.toDays();
            dataAnalysis.setReleaseDuration(Long.toString(days));
            //统计截至日期
            dataAnalysis.setDeadlineDate(new Date());
            //查询每个岗位的目标数
            List<SkillsInfo> skillsInfoList = skillsInfoService.selectSkillsInfoByPositionId(positionId);
            dataAnalysis.setTargetNum((long) skillsInfoList.size());
            //查询未完成目标列表数
            List<SkillsInfo> UnfinishedSkillsInfos=skillsInfoService.selectSkillsInfoByUnfinished(positionId);
            dataAnalysis.setUnfinishedNum((long) UnfinishedSkillsInfos.size());
            //查询已完成目标列表数
            List<SkillsInfo> CompletionsSkillsInfos=skillsInfoService.selectSkillsInfoByCompletions(positionId);
            dataAnalysis.setCompletionsNum((long) CompletionsSkillsInfos.size());
            //岗位总完成率
            if(StringUtils.isNotEmpty(skillsInfoList)){
                dataAnalysis.setCompletionRate(String.valueOf(CompletionsSkillsInfos.size()/skillsInfoList.size()));
            }
            //统计年度/月份截至数
            int targetYear = LocalDate.now().getYear(); // 获取当前年份
            Month targetMonth = LocalDate.now().getMonth(); // 获取当前月份
            long yearCount = skillsInfoList.stream()
                    .map(SkillsInfo::getEndTime)
                    .map(date -> date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                    .filter(localDate -> localDate.getYear() == targetYear)
                    .count();
            long monthCount = skillsInfoList.stream()
                    .map(SkillsInfo::getEndTime)
                    .map(date -> date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                    .filter(localDate -> localDate.getYear() == targetYear && localDate.getMonth() == targetMonth)
                    .count();
            dataAnalysis.setYearClose(String.valueOf(yearCount));
            dataAnalysis.setMoonClose(String.valueOf(monthCount));
            // 计算年初至今的任务完成数量和总任务数量
            long completedTasks = skillsInfoList.stream()
                    .filter(skill -> {
                        LocalDate completionDate = skill.getCompleteTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                        return completionDate.getYear() == targetYear;
                    })
                    .count();
            long totalTasks = skillsInfoList.size();
            // 计算年度完成率
            double completionRate = (double) completedTasks / totalTasks * 100;
            dataAnalysis.setYearCompletionRate(String.valueOf(completionRate));
            //统计提前未完成，按时完成和超时完成的个数
            long aheadCount = CompletionsSkillsInfos.stream()
                    .filter(skill -> skill.getCompleteTime().before(skill.getStartTime()))
                    .count();
            long onTimeCount = CompletionsSkillsInfos.stream()
                    .filter(skill -> !skill.getCompleteTime().before(skill.getStartTime()) &&
                            !skill.getCompleteTime().after(skill.getEndTime()))
                    .count();
            long overdueCount = CompletionsSkillsInfos.stream()
                    .filter(skill -> skill.getCompleteTime().after(skill.getEndTime()))
                    .count();
            dataAnalysis.setTimeoutCompletionsNum(overdueCount);
            dataAnalysis.setBeforeCompletionsNum(aheadCount);
            dataAnalysis.setJustCompletionsNum(onTimeCount);
            //统计未完成并且过期，未完成没有过期的个数
            long expiredNum = UnfinishedSkillsInfos.stream()
                    .filter(skill -> skill.getEndTime().before(java.sql.Timestamp.valueOf(currentDateTime)))
                    .count();
            long notExpiredNum = UnfinishedSkillsInfos.size() - overdueCount;
            dataAnalysis.setExpiredTargetNum(expiredNum);
            dataAnalysis.setNotExpiredTargetNum(notExpiredNum);
            dataAnalysisMapper.insertDataAnalysis(dataAnalysis);
        }
    }

    private void SubAnalysis(String positionId){
        subAnalysisService.insertSubAnalysis(positionId);
    }

}


