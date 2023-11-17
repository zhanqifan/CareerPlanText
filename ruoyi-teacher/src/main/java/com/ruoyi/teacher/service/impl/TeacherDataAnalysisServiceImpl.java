package com.ruoyi.teacher.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.domain.DataAnalysis;
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.service.ICommonStudentService;
import com.ruoyi.student.service.IDataAnalysisService;
import com.ruoyi.student.service.ITargetPositionService;
import com.ruoyi.student.system.service.ISysDeptService;
import com.ruoyi.teacher.domain.dto.CollegeAnalysisDTO;
import com.ruoyi.teacher.domain.vo.CollegeAnalysisVO;
import com.ruoyi.teacher.domain.vo.CompletionRateRange;
import com.ruoyi.teacher.service.TeacherDataAnalysisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherDataAnalysisServiceImpl implements TeacherDataAnalysisService {

    @Resource
    private ISysDeptService sysDeptService;

    @Resource
    private ITargetPositionService targetPositionService;

    @Resource
    private IDataAnalysisService dataAnalysisService;

    @Resource
    private ICommonStudentService commonStudentService;

    /**
     * 查询学院分析结构
     */
    @Override
    public CollegeAnalysisVO getCollegeAnalysis(CollegeAnalysisDTO collegeAnalysisDTO) {
        //发布人数(ok)
        long numberPublishers = 0;
        //发布率（ok）
        double publishingRate ;
        //人均项目数（ok）
        long numberProjectsPerCapita ;
        //平均完成率(ok)
        double averageCompletionRate ;
        //年度平均项目数(ok)
        long annualAverageNumberProjects;
        //年度完成率(od)
        double annualAverageCompletionRate;
        //完成率分布
        List<CompletionRateRange> completionRateRanges = null;
        //完成数
        long completionsNum=0L;
        //统计截至日期(ok)
        Date deadlineDate;
        CollegeAnalysisVO collegeAnalysisVO = new CollegeAnalysisVO();
        // 获取前一天的日期
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDay = currentDate.minusDays(1);
        deadlineDate = Date.from(previousDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //学生总数
        long totalNumberStudents = 0L;
        //项目总数
        long totalNumberProjects = 0L;
        //年度项目数
        long annualNumberProjects=0L;
        //总完成率
        double totalCompletionRate=0.00;
        //年度总完成率
        double annualTotalCompletionRate=0.00;
        // 获取当前年份
        int targetYear = LocalDate.now().getYear();
        // 获取今年的开始日期和结束日期
        // 获取今年的开始日期和结束日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = calendar.getTime();
        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endDate = calendar.getTime();
        // 创建 DecimalFormat 对象，
        //设置截至日期
        collegeAnalysisVO.setDeadlineDate(deadlineDate);
        if (StringUtils.isNotEmpty(collegeAnalysisDTO.getCollegeId())
                && StringUtils.isEmpty(collegeAnalysisDTO.getProfessionalId())
                && StringUtils.isEmpty(collegeAnalysisDTO.getGrade())
                && StringUtils.isNull(collegeAnalysisDTO.getIsMain())
                && StringUtils.isNull(collegeAnalysisDTO.getCultivationLevel())) {
            List<Long> collegeIdList = collegeAnalysisDTO.getCollegeId();
            for (Long collegeId : collegeIdList) {
                CommonStudent commonStudent = new CommonStudent();
                commonStudent.setCollegeCode(String.format("%03d", collegeId));
                List<CommonStudent> commonStudentList = commonStudentService.selectCommonStudentList(commonStudent);
                //学生总数
                totalNumberStudents += commonStudentList.size();
                for (CommonStudent student : commonStudentList) {
                    String sNum = student.getSNum();
                    //查询学生所发布的目标数
                    TargetPosition targetPosition = new TargetPosition();
                    targetPosition.setCreateBy(sNum);
                    targetPosition.setState(1);
                    List<TargetPosition> targetPositionList = targetPositionService.selectTargetPositionList(targetPosition);
                    if (targetPositionList != null && !targetPositionList.isEmpty()) {
                        //目标发布人数+1
                        numberPublishers++;
                        //统计已发布项目数
                        totalNumberProjects += targetPositionList.size();
                        //统计年度项目数
                        List<TargetPosition> thisYearPositions = targetPositionList.stream()
                                .filter(tp -> tp.getCreateTime().after(startDate) && tp.getCreateTime().before(endDate))
                                .collect(Collectors.toList());
                        annualNumberProjects+=thisYearPositions.size();
                        //统计年度总完成率
                        annualTotalCompletionRate = this.getTotalCompletionRate(deadlineDate, annualTotalCompletionRate, thisYearPositions);
                        //统计总完成率
                        totalCompletionRate = this.getTotalCompletionRate(deadlineDate, totalCompletionRate, targetPositionList);
                        //统计完成率分布
                        completionRateRanges = this.completionRateRanges(targetPositionList, deadlineDate);
                        //平均时效性分析

                    }
                }
            }
        }
        //发布率:发布人数/学生总人数
        publishingRate = Math.round(((double) numberPublishers / totalNumberStudents) * 100.0) / 100.0;
        //人均项目数:项目总数/学生总人数
        numberProjectsPerCapita = totalNumberProjects / totalNumberStudents;
        //年度平均项目数:年度项目数/学生总人数
        annualAverageNumberProjects=annualNumberProjects / totalNumberStudents;
        //平均完成率
        averageCompletionRate = Math.round((totalCompletionRate/totalNumberStudents)*100.0)/100.0;
        //年度平均完成率
        annualAverageCompletionRate=Math.round((annualTotalCompletionRate/totalNumberStudents)*100.0)/100.0;
        //封装返回值
        collegeAnalysisVO.setPublishingRate(publishingRate);
        collegeAnalysisVO.setNumberPublishers(numberPublishers);
        collegeAnalysisVO.setNumberProjectsPerCapita(numberProjectsPerCapita);
        collegeAnalysisVO.setAnnualAverageNumberProjects(annualAverageNumberProjects);
        collegeAnalysisVO.setAverageCompletionRate(averageCompletionRate);
        collegeAnalysisVO.setAnnualAverageCompletionRate(annualAverageCompletionRate);
        collegeAnalysisVO.setCompletionRateRangeList(completionRateRanges);
        return collegeAnalysisVO;
    }

    /**
     * 平均时效性分析
     */
    private CollegeAnalysisVO averageTimeAnalysis(long totalNumberProjects,Date deadlineDate,List<TargetPosition> targetPositionList){
        // 完成数
         long completionsNum = 0;
        // 未完成数
        long unfinishedNum;
        // 超时完成数
        long timeoutCompletionsNum;
        // 提前完成数
        long beforeCompletionsNum = 0;
        // 按时完成数
        long justCompletionsNum;
        //未完成过期数
        long expiredTargetNum;
        //未完成未过期数
        long notExpiredTargetNum;

        CollegeAnalysisVO collegeAnalysisVO = new CollegeAnalysisVO();
        for(TargetPosition targetPosition:targetPositionList){
            String positionId = targetPosition.getPositionId();
            DataAnalysis dataAnalysis = new DataAnalysis();
            dataAnalysis.setPositionId(positionId);
            dataAnalysis.setDeadlineDate(deadlineDate);
            dataAnalysis.setState(1);
            //一个学生所发布的一个的岗位分析列表
            List<DataAnalysis> dataAnalyses = dataAnalysisService.selectDataAnalysisList(dataAnalysis);
            DataAnalysis dataAnalysis1 = dataAnalyses.get(0);
            if(StringUtils.isNotNull(dataAnalysis1.getCompletionTime())){
                completionsNum++;
                //提前完成
                if(dataAnalysis1.getCompletionTime().getTime()<dataAnalysis1.getCreateTime().getTime()){
                    beforeCompletionsNum++;
                }
            }

        }
        //未完成数
        unfinishedNum=totalNumberProjects-completionsNum;
        return collegeAnalysisVO;
    }

    /**
     * 计算完成率
     * @return
     */
    private double getTotalCompletionRate(Date deadlineDate, double totalCompletionRate, List<TargetPosition> targetPositionList) {
        for(TargetPosition position:targetPositionList){
            String positionId = position.getPositionId();
            DataAnalysis dataAnalysis = new DataAnalysis();
            dataAnalysis.setPositionId(positionId);
            dataAnalysis.setDeadlineDate(deadlineDate);
            dataAnalysis.setState(1);
            //查询已经发布的岗位
            List<DataAnalysis> dataAnalyses = dataAnalysisService.selectDataAnalysisList(dataAnalysis);
            if(!dataAnalyses.isEmpty()){
                //总完成率
                totalCompletionRate += Double.parseDouble(dataAnalyses.get(0).getCompletionRate());
            }
        }
        return totalCompletionRate;
    }

    /**
     * 统计完成率分布
     * @return
     */
    private List<CompletionRateRange> completionRateRanges(List<TargetPosition> targetPositionList,Date deadlineDate){
        ArrayList<CompletionRateRange> completionRateRanges = new ArrayList<>();
        // 创建 Map 用于存储不同 completionRate 范围的创建者数量
        Map<String, Integer> CompletionRateRange = new HashMap<>();
        CompletionRateRange.put("0-20", 0);
        CompletionRateRange.put("21-40", 0);
        CompletionRateRange.put("41-60", 0);
        CompletionRateRange.put("61-80", 0);
        CompletionRateRange.put("81-100", 0);
        for(TargetPosition position:targetPositionList){
            String positionId = position.getPositionId();
            DataAnalysis analysis = new DataAnalysis();
            analysis.setPositionId(positionId);
            analysis.setDeadlineDate(deadlineDate);
            analysis.setState(1);
            //查询已经发布的岗位
            List<DataAnalysis> dataAnalysisList = dataAnalysisService.selectDataAnalysisList(analysis);
            // 遍历 DataAnalysis 列表，进行统计
            for (DataAnalysis dataAnalysis : dataAnalysisList) {
                double completionRate = Double.parseDouble(dataAnalysis.getCompletionRate());
                if (completionRate >= 0 && completionRate <= 20) {
                    updateMap(CompletionRateRange, "0-20");
                } else if (completionRate >= 21 && completionRate <= 40) {
                    updateMap(CompletionRateRange, "21-40");
                } else if (completionRate >= 41 && completionRate <= 60) {
                    updateMap(CompletionRateRange, "41-60");
                } else if (completionRate >= 61 && completionRate <= 80) {
                    updateMap(CompletionRateRange, "61-80");
                } else if (completionRate >= 81 && completionRate <= 100) {
                    updateMap(CompletionRateRange, "81-100");
                }
            }
        }
        for (Map.Entry<String, Integer> entry : CompletionRateRange.entrySet()) {
            CompletionRateRange completionRateRange = new CompletionRateRange();
            completionRateRange.setRange(entry.getKey());
            completionRateRange.setPeopleNum(Long.valueOf(entry.getValue()));
            completionRateRanges.add(completionRateRange);
        }
        return completionRateRanges;
    }

    static void updateMap(Map<String, Integer> map, String key) {
        // 更新 Map 中指定 key 的值
        map.put(key, map.get(key) + 1);
    }

}