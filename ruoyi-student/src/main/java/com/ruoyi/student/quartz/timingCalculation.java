package com.ruoyi.student.quartz;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.student.domain.*;
import com.ruoyi.student.mapper.DataAnalysisMapper;
import com.ruoyi.student.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.*;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author lihong
 * @date 2023/11/12
 */
@Component
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
    private IFirstAnalysisService firstAnalysisService;

    @Resource
    private ISubAnalysisService subAnalysisService;


    // 每天凌晨1点执行
//    @Scheduled(cron = "0 0 1 * * ?")
    /* 每5秒执行一次 */
//    @Scheduled(cron = "0/5 * * * * ?")
    @Transactional
    public void TimingStatistics(){
        System.out.println("=============统计任务开始执行=============");
        //查询出岗位列表
        List<TargetPosition> targetPositions = targetPositionService.selectTargetPositionList(null);
        for (TargetPosition targetPosition:targetPositions){
            DataAnalysis dataAnalysis = new DataAnalysis();
            String positionId = targetPosition.getPositionId();
            //是否是主目标
            dataAnalysis.setIsMain(targetPosition.getIsMain());
            //目标状态
            dataAnalysis.setState(targetPosition.getState());
            //统计一级/二级目录的数据
            this.SubAnalysis(positionId);
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
            LocalDate currentDate = LocalDate.now();
            //获取前一天日期
            LocalDate previousDay = currentDate.minusDays(1);
            // 转换为java.util.Date
            Date previousDayDate = Date.from(previousDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dataAnalysis.setDeadlineDate(previousDayDate);
            //dataAnalysis.setDeadlineDate(new Date());
            //查询每个岗位的目标数
            List<SkillsInfo> skillsInfoList = skillsInfoService.selectSkillsInfoByPositionId(positionId);
            int AllSize = skillsInfoList.size();
            dataAnalysis.setTargetNum((long)AllSize);
            //查询已完成目标列表数
            List<SkillsInfo> CompletionsSkillsInfos=skillsInfoService.selectSkillsInfoByCompletions(positionId);
            int CompletionsSkillsInfosSize = CompletionsSkillsInfos.size();
            dataAnalysis.setCompletionsNum((long) CompletionsSkillsInfosSize);
            //查询未完成目标列表数
            List<SkillsInfo> UnfinishedSkillsInfos=skillsInfoService.selectSkillsInfoByUnfinished(positionId);
            int UnfinishedSkillsSize = UnfinishedSkillsInfos.size();
            dataAnalysis.setUnfinishedNum((long) UnfinishedSkillsSize);
            //岗位总完成率
            if(CompletionsSkillsInfosSize!=0){
                double CompletionRate = ((double) CompletionsSkillsInfosSize / AllSize) * 100;
                dataAnalysis.setCompletionRate( String.format("%.2f", CompletionRate));
                if(CompletionRate==100.00){
                    dataAnalysis.setCompletionTime(new Date());
                }
            }else {
                dataAnalysis.setCompletionRate("0");
            }
            int targetYear = LocalDate.now().getYear(); // 获取当前年份
            Month targetMonth = LocalDate.now().getMonth(); // 获取当前月份
            //如果目标不为空
            double completionRate=0;
            long yearCount=0;
            long monthCount=0;
            long totalTasks=0;
            if(StringUtils.isNotEmpty(skillsInfoList)){
                //统计年度/月份截至数
                 yearCount = skillsInfoList.stream()
                        .map(SkillsInfo::getEndTime)
                        .map(date -> date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                        .filter(localDate -> localDate.getYear() == targetYear)
                        .count();
                 monthCount = skillsInfoList.stream()
                        .map(SkillsInfo::getEndTime)
                        .map(date -> date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                        .filter(localDate -> localDate.getYear() == targetYear && localDate.getMonth() == targetMonth)
                        .count();
                dataAnalysis.setYearClose(String.valueOf(yearCount));
                dataAnalysis.setMoonClose(String.valueOf(monthCount));
                // 计算年初至今的任务完成数量和总任务数量
               long completedTasks = skillsInfoList.stream()
                        .filter(skill -> {
                            if(StringUtils.isNotNull(skill.getCompleteTime())){
                                LocalDate completionDate = skill.getCompleteTime().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                                return completionDate.getYear() == targetYear;
                            }
                            return false;
                        })
                        .count();
                 totalTasks = skillsInfoList.size();
                // 计算年度完成率
                completionRate = (double) completedTasks / totalTasks * 100;
                dataAnalysis.setYearCompletionRate(String.format("%.2f", completionRate));
            }else {
                dataAnalysis.setYearClose(String.valueOf(yearCount));
                dataAnalysis.setMoonClose(String.valueOf(monthCount));
                dataAnalysis.setYearCompletionRate(String.valueOf(completionRate));

            }
            //统计提前未完成，按时完成和超时完成的个数
            long notExpiredNum = 0;
            long expiredNum=0;
            long overdueCount=0;
            long onTimeCount=0;
            long aheadCount=0;
            if(StringUtils.isNotEmpty(CompletionsSkillsInfos)){
                aheadCount = CompletionsSkillsInfos.stream()
                        .filter(skill -> skill.getCompleteTime().before(skill.getStartTime()))
                        .count();
                 onTimeCount = CompletionsSkillsInfos.stream()
                        .filter(skill -> !skill.getCompleteTime().before(skill.getStartTime()) &&
                                !skill.getCompleteTime().after(skill.getEndTime()))
                        .count();
                overdueCount = CompletionsSkillsInfos.stream()
                        .filter(skill -> skill.getCompleteTime().after(skill.getEndTime()))
                        .count();
                dataAnalysis.setBeforeCompletionsNum(aheadCount);
                dataAnalysis.setJustCompletionsNum(onTimeCount);
                dataAnalysis.setTimeoutCompletionsNum(overdueCount);
            }else {
                dataAnalysis.setBeforeCompletionsNum(aheadCount);
                dataAnalysis.setJustCompletionsNum(onTimeCount);
                dataAnalysis.setTimeoutCompletionsNum(overdueCount);
            }
            if(StringUtils.isNotEmpty(UnfinishedSkillsInfos)){
                //统计未完成并且过期，未完成没有过期的个数
                expiredNum = UnfinishedSkillsInfos.stream()
                        .filter(skill -> skill.getEndTime().before(java.sql.Timestamp.valueOf(currentDateTime)))
                        .count();
                notExpiredNum = UnfinishedSkillsInfos.size() - overdueCount;
                dataAnalysis.setExpiredTargetNum(expiredNum);
                dataAnalysis.setNotExpiredTargetNum(notExpiredNum);
            }else {
                dataAnalysis.setExpiredTargetNum(expiredNum);
                dataAnalysis.setNotExpiredTargetNum(notExpiredNum);
            }
            dataAnalysisMapper.insertDataAnalysis(dataAnalysis);
        }
        System.out.println("=============统计任务执行完毕=============");
    }

    //目录分析
    private void SubAnalysis(String positionId){
        //二级目录分析
        subAnalysisService.insertSubAnalysis(positionId);
        //一级目录分析
        firstAnalysisService.insertFirstAnalysis(positionId);
    }

}


