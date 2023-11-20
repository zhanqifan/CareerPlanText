package com.ruoyi.teacher.service.impl;

import cn.hutool.extra.tokenizer.engine.analysis.AnalysisResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.student.domain.*;
import com.ruoyi.student.domain.dto.CollegeAnalysisDTO;
import com.ruoyi.student.service.*;
import com.ruoyi.student.system.service.ISysDeptService;
import com.ruoyi.teacher.domain.vo.CollegeAnalysisVO;
import com.ruoyi.teacher.domain.vo.CompletionRateRange;
import com.ruoyi.teacher.domain.vo.FirstAnalysisVO;
import com.ruoyi.teacher.domain.vo.MoonCompletionsNumVO;
import com.ruoyi.teacher.service.TeacherDataAnalysisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
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
    private ICtatlogueService ctatlogueService;

    @Resource
    private ICommonStudentService commonStudentService;
    @Resource
    private IFirstAnalysisService firstAnalysisService;

    /**
     * 查询学院分析结构
     */
    @Override
    public CollegeAnalysisVO getCollegeAnalysis(CollegeAnalysisDTO collegeAnalysisDTO) {
        List<CommonStudent> commonStudents = commonStudentService.selectCommonStudentListByCollegeAnalysis(collegeAnalysisDTO);
        return this.test(commonStudents,collegeAnalysisDTO.getIsMain());
    }

    public CollegeAnalysisVO test(List<CommonStudent> commonStudentList,Integer isMain) {
        //发布人数(ok)
        long numberPublishers = 0;
        //发布率（ok）
        double publishingRate;
        //人均项目数（ok）
        long numberProjectsPerCapita;
        //平均完成率(ok)
        double averageCompletionRate;
        //年度平均项目数(ok)
        long annualAverageNumberProjects;
        //年度完成率(od)
        double annualAverageCompletionRate;
        //完成率分布
        List<CompletionRateRange> completionRateRanges = null;
        //完成数
        long completionsNum = 0L;
        // 未完成数
        long unfinishedNum = 0;
        // 超时完成数
        long timeoutCompletionsNum = 0;
        // 提前完成数
        long beforeCompletionsNum = 0;
        // 按时完成数
        long justCompletionsNum = 0;
        //未完成过期数
        long expiredTargetNum = 0;
        //未完成未过期数
        long notExpiredTargetNum = 0;
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
        long annualNumberProjects = 0L;
        //总完成率
        AtomicReference<Double> totalCompletionRate = new AtomicReference<>(0.00);
        //年度总完成率
        AtomicReference<Double> annualTotalCompletionRate = new AtomicReference<>(0.00);
        // 获取当前年份
        int targetYear = LocalDate.now().getYear();
        CollegeAnalysisVO analysis = new CollegeAnalysisVO();
        //所选学生一级目录分析
        ArrayList<FirstAnalysisVO> firstAnalysisVOS = new ArrayList<>();

        // 获取今年的开始日期和结束日期
        // 获取今年的开始日期和结束日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = calendar.getTime();
        calendar.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        Date endDate = calendar.getTime();
        ArrayList<CompletionRateRange> completionRateRangeArrayList = new ArrayList<>();
        ArrayList<DataAnalysis> dataAnalysisArrayList = new ArrayList<>();
        //设置截至日期
        collegeAnalysisVO.setDeadlineDate(deadlineDate);
        totalNumberStudents += commonStudentList.size();
        for (CommonStudent student : commonStudentList) {
            String sNum = student.getSNum();
            //查询学生所发布的目标数
            TargetPosition targetPosition = new TargetPosition();
            targetPosition.setCreateBy(sNum);
            targetPosition.setState(1);
            targetPosition.setIsMain(isMain);
            //todo  统计近6月完成人次数
            List<DataAnalysis> dataAnalyses = this.numberCompletedMonths(student, deadlineDate);
            if (dataAnalyses.size() != 0) {
                dataAnalysisArrayList.addAll(dataAnalyses);
            }
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
                annualNumberProjects += thisYearPositions.size();
                // 使用 CompletableFuture 开启异步执行
                CompletableFuture<Void> asyncTask = CompletableFuture.runAsync(() -> {
                    // 统计年度总完成率
                    annualTotalCompletionRate.updateAndGet(v -> new Double((double) (v + this.getTotalCompletionRate(deadlineDate, annualTotalCompletionRate.get(), thisYearPositions))));
                    // 统计总完成率
                    totalCompletionRate.updateAndGet(v -> new Double((double) (v + this.getTotalCompletionRate(deadlineDate, totalCompletionRate.get(), targetPositionList))));
                    // 统计完成率分布
                    completionRateRangeArrayList.addAll(this.completionRateRanges(targetPositionList, deadlineDate));
                });

                // 平均时效性分析异步执行
                CompletableFuture<CollegeAnalysisVO> analysisTask = CompletableFuture.supplyAsync(() -> {
                    return this.averageTimeAnalysis(deadlineDate, targetPositionList);
                });

                // 统计分类平均完成率明细异步执行
                CompletableFuture<List<FirstAnalysisVO>> classificationTask = CompletableFuture.supplyAsync(() -> {
                    return this.averageCompletionRateOfClassification(deadlineDate, targetPositionList);
                });

                try {
                    // 等待所有异步任务完成
                    CompletableFuture.allOf(asyncTask, analysisTask, classificationTask).join();

                    // 获取异步任务的结果
                    CollegeAnalysisVO analysisResult = analysisTask.get();
                    List<FirstAnalysisVO> firstAnalysisVOList = classificationTask.get();

                    // 处理异步任务的结果
                    completionsNum += analysisResult.getCompletionsNum();
                    unfinishedNum += analysisResult.getUnfinishedNum();
                    timeoutCompletionsNum += analysisResult.getTimeoutCompletionsNum();
                    justCompletionsNum += analysisResult.getJustCompletionsNum();
                    beforeCompletionsNum += analysisResult.getBeforeCompletionsNum();
                    expiredTargetNum += analysisResult.getExpiredTargetNum();
                    notExpiredTargetNum += analysisResult.getNotExpiredTargetNum();
                    // 处理分类平均完成率明细
                    firstAnalysisVOS.addAll(this.averageCompletionRateOfClassification(deadlineDate, targetPositionList));

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace(); // 处理异常
                }
//                //统计年度总完成率
//                annualTotalCompletionRate += this.getTotalCompletionRate(deadlineDate, annualTotalCompletionRate, thisYearPositions);
//                //统计总完成率
//                totalCompletionRate += this.getTotalCompletionRate(deadlineDate, totalCompletionRate, targetPositionList);
//                //统计完成率分布
//                completionRateRangeArrayList.addAll(this.completionRateRanges(targetPositionList, deadlineDate));
//                //平均时效性分析
//                analysis = this.averageTimeAnalysis(deadlineDate, targetPositionList);
//                completionsNum += analysis.getCompletionsNum();
//                unfinishedNum += analysis.getUnfinishedNum();
//                timeoutCompletionsNum += analysis.getTimeoutCompletionsNum();
//                justCompletionsNum += analysis.getJustCompletionsNum();
//                beforeCompletionsNum += analysis.getBeforeCompletionsNum();
//                expiredTargetNum += analysis.getExpiredTargetNum();
//                notExpiredTargetNum += analysis.getNotExpiredTargetNum();
//                //统计分类平均完成率明细
//                firstAnalysisVOS.addAll(this.averageCompletionRateOfClassification(deadlineDate, targetPositionList));
            }
        }
        //发布率:发布人数/学生总人数
        publishingRate = Math.round(((double) numberPublishers / totalNumberStudents) * 100.0) / 100.0;
        //人均项目数:项目总数/学生总人数
        numberProjectsPerCapita = totalNumberProjects / totalNumberStudents;
        //年度平均项目数:年度项目数/学生总人数
        annualAverageNumberProjects=annualNumberProjects / totalNumberStudents;
        //平均完成率
        averageCompletionRate = Math.round((totalCompletionRate.get() /totalNumberStudents)*100.0)/100.0;
        //年度平均完成率
        annualAverageCompletionRate=Math.round((annualTotalCompletionRate.get() /totalNumberStudents)*100.0)/100.0;
        //统计所选学生的一级目录分析
        List<FirstAnalysisVO>  studentData= this.firstNameTotalCompletionRate(firstAnalysisVOS,numberPublishers);
        //统计全校学生的一级目录分析
        List<FirstAnalysisVO> schoolData = this.AllStudents(deadlineDate);
        //统计完成率分布
        List<CompletionRateRange> completionRateRanges1 = StatisticsCompletionRateRangeArrayList(completionRateRangeArrayList);
        //统计近6月完成数
        List<MoonCompletionsNumVO> moonCompletionsNumVOS = StatisticsMoonCloseCompletionsNumVO(dataAnalysisArrayList);
        //封装返回值
        collegeAnalysisVO.setPublishingRate(publishingRate);
        collegeAnalysisVO.setNumberPublishers(numberPublishers);
        collegeAnalysisVO.setNumberProjectsPerCapita(numberProjectsPerCapita);
        collegeAnalysisVO.setAnnualAverageNumberProjects(annualAverageNumberProjects);
        collegeAnalysisVO.setAverageCompletionRate(averageCompletionRate);
        collegeAnalysisVO.setAnnualAverageCompletionRate(annualAverageCompletionRate);
//        collegeAnalysisVO.setCompletionRateRangeList(completionRateRanges);
        collegeAnalysisVO.setBeforeCompletionsNum(beforeCompletionsNum);
        collegeAnalysisVO.setCompletionsNum(completionsNum);
        collegeAnalysisVO.setJustCompletionsNum(justCompletionsNum);
        collegeAnalysisVO.setTimeoutCompletionsNum(timeoutCompletionsNum);
        collegeAnalysisVO.setUnfinishedNum(unfinishedNum);
        collegeAnalysisVO.setExpiredTargetNum(expiredTargetNum);
        collegeAnalysisVO.setNotExpiredTargetNum(notExpiredTargetNum);
        collegeAnalysisVO.setDeadlineDate(deadlineDate);
        collegeAnalysisVO.setStudentData(studentData);
        collegeAnalysisVO.setSchoolData(schoolData);
        collegeAnalysisVO.setCompletionRateRangeList(completionRateRanges1);
        collegeAnalysisVO.setMoonCompletionsNumVOList(moonCompletionsNumVOS);
        return collegeAnalysisVO;
    }

    /**
     * 近6月完成项目数
     */
    public List<DataAnalysis> numberCompletedMonths(CommonStudent student, Date deadlineDate){
        String sNum = student.getSNum();
        //查询学生所发布的目标数
        DataAnalysis dataAnalysis = new DataAnalysis();
        dataAnalysis.setCreateBy(sNum);
        dataAnalysis.setDeadlineDate(deadlineDate);
        return dataAnalysisService.selectDataAnalysisListByCompleted(dataAnalysis);
    }

    private List<MoonCompletionsNumVO> StatisticsMoonCloseCompletionsNumVO(List<DataAnalysis> dataAnalysisList){
        // 获取当前时间
        Date currentDate = new Date();
        // 创建月份和完成数量的映射
        Map<String, Integer> monthCompletionsNumMap = new HashMap<>();
        // 计算前6个月的时间范围
        for (int i = 0; i < 6; i++) {
            Date startDate = getFirstDayOfMonth(currentDate, -i);
            Date endDate = getLastDayOfMonth(currentDate, -i);
            // 统计每个月份的完成数量
            int completionsNum = countCompletionsNum(dataAnalysisList, startDate, endDate);
            // 将结果放入映射中
            String month = formatDate(startDate);
            monthCompletionsNumMap.put(month, completionsNum);
        }
        // 创建结果列表
        return monthCompletionsNumMap.entrySet().stream()
                .map(entry -> new MoonCompletionsNumVO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }


    /**
     *统计分类平均完成率明细
     */
    public List<FirstAnalysisVO> averageCompletionRateOfClassification(Date deadlineDat,List<TargetPosition> targetPositionList){
        // 查询一级目录
        List<Ctatlogue> ctatlogueList = ctatlogueService.selectCtatlogueList(new Ctatlogue("0"));
        List<FirstAnalysisVO> studentData = ctatlogueList.stream()
                .map(ctatlogue1 -> new FirstAnalysisVO(ctatlogue1.getCatalogueName(), 0.00))
                .collect(Collectors.toCollection(ArrayList::new));
        // 统计每个学生发布岗位的一级目录完成率的数据
        List<FirstAnalysis> firstAnalysisList = targetPositionList.stream()
                .flatMap(position -> firstAnalysisService.selectFirstAnalysisList(
                        new FirstAnalysis(position.getPositionId(),deadlineDat)).stream())
                .collect(Collectors.toList());
        // 更新学生数据的完成率
        for (FirstAnalysisVO studentDatum : studentData) {
            double averageCompletionRate = firstAnalysisList.stream()
                    .filter(firstAnalysis -> studentDatum.getFirstName().equals(firstAnalysis.getFirstName()))
                    .mapToDouble(firstAnalysis -> Double.parseDouble(firstAnalysis.getFirstCompletionRate()))
                    .average()
                    .orElse(0.00);
            studentDatum.setFirstAverageCompletionRate(averageCompletionRate);
        }
        return studentData;
    }

    /**
     * 全校学生一级目录分析结果
     */
    public List<FirstAnalysisVO>  AllStudents(Date deadlineDat){
        TargetPosition targetPosition = new TargetPosition();
        targetPosition.setState(1);
        List<TargetPosition> targetPositionList = targetPositionService.selectTargetPositionList(targetPosition);
        // 使用流和集合操作获取不同的 CreateBy 值
        Set<String> uniqueCreateByValues = targetPositionList.stream()
                .map(TargetPosition::getCreateBy)
                .collect(Collectors.toSet());
        List<FirstAnalysisVO> firstAnalysisVOS = this.averageCompletionRateOfClassification(deadlineDat, targetPositionList);
        return this.firstNameTotalCompletionRate(firstAnalysisVOS,uniqueCreateByValues.size());
    }

    private List<FirstAnalysisVO> firstNameTotalCompletionRate(List<FirstAnalysisVO> firstAnalysisVOList,long numberPublishers){
        Map<String, Double> firstNameTotalCompletionRate = firstAnalysisVOList.stream()
                .collect(Collectors.groupingBy(FirstAnalysisVO::getFirstName,
                        Collectors.summingDouble((FirstAnalysisVO::getFirstAverageCompletionRate))));
        return firstNameTotalCompletionRate.entrySet().stream()
                .map(entry -> new FirstAnalysisVO(entry.getKey(), entry.getValue()/numberPublishers))
                .collect(Collectors.toList());
    }

    private List<CompletionRateRange> StatisticsCompletionRateRangeArrayList(List<CompletionRateRange> completionRateRangeArrayList){
        Map<String, Integer> rangeTotalPeopleNum = completionRateRangeArrayList.stream()
                .collect(Collectors.groupingBy(CompletionRateRange::getRange,
                        Collectors.summingInt(CompletionRateRange::getPeopleNum)));
        return rangeTotalPeopleNum.entrySet().stream()
                .map(entry -> new CompletionRateRange(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

    }

    /**
     * 平均时效性分析
     */
    private CollegeAnalysisVO averageTimeAnalysis(Date deadlineDate,List<TargetPosition> targetPositionList){
        // 完成数
         long completionsNum = 0;
        // 未完成数
        long unfinishedNum=0;
        // 超时完成数
        long timeoutCompletionsNum=0;
        // 提前完成数
        long beforeCompletionsNum = 0;
        // 按时完成数
        long justCompletionsNum = 0;
        //未完成过期数
        long expiredTargetNum=0;
        //未完成未过期数
        long notExpiredTargetNum=0;

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
            if(StringUtils.isNotNull(dataAnalysis1)){
                //统计提前完成
                beforeCompletionsNum += dataAnalysis1.getBeforeCompletionsNum();
                //统计按时完成数
                justCompletionsNum += dataAnalysis1.getJustCompletionsNum();
                //统计超时完成数
                timeoutCompletionsNum+=dataAnalysis1.getTimeoutCompletionsNum();
                //统计未完成过期数
                expiredTargetNum+=dataAnalysis1.getExpiredTargetNum();
                //统计未完成未过期数
                notExpiredTargetNum+=dataAnalysis1.getNotExpiredTargetNum();
                //统计未完成数
                unfinishedNum+=dataAnalysis1.getUnfinishedNum();
                //完成数
                completionsNum+=dataAnalysis1.getCompletionsNum();
            }
        }
        //未完成数
        collegeAnalysisVO.setBeforeCompletionsNum(beforeCompletionsNum);
        collegeAnalysisVO.setCompletionsNum(completionsNum);
        collegeAnalysisVO.setJustCompletionsNum(justCompletionsNum);
        collegeAnalysisVO.setTimeoutCompletionsNum(timeoutCompletionsNum);
        collegeAnalysisVO.setUnfinishedNum(unfinishedNum);
        collegeAnalysisVO.setExpiredTargetNum(expiredTargetNum);
        collegeAnalysisVO.setNotExpiredTargetNum(notExpiredTargetNum);
        return collegeAnalysisVO;
    }

    /**
     * 计算完成率
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
            completionRateRange.setPeopleNum((entry.getValue()));
            completionRateRanges.add(completionRateRange);
        }
        return completionRateRanges;
    }

    static void updateMap(Map<String, Integer> map, String key) {
        // 更新 Map 中指定 key 的值
        map.put(key, map.get(key) + 1);
    }

    private static int countCompletionsNum(List<DataAnalysis> dataAnalysisList, Date startDate, Date endDate) {
        // 使用流和集合操作，筛选在指定时间范围内的数据，并计算数量
        return (int) dataAnalysisList.stream()
                .filter(dataAnalysis -> {
                    Date completionTime = dataAnalysis.getCompletionTime();
                    return !completionTime.before(startDate) && !completionTime.after(endDate);
                })
                .count();
    }

    private static Date getFirstDayOfMonth(Date date, int monthsToAdd) {
        // 获取指定月份的第一天
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.add(java.util.Calendar.MONTH, monthsToAdd);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    private static Date getLastDayOfMonth(Date date, int monthsToAdd) {
        // 获取指定月份的最后一天
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.add(java.util.Calendar.MONTH, monthsToAdd + 1);
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    private static String formatDate(Date date) {
        // 使用 SimpleDateFormat 进行日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(date);
    }

}