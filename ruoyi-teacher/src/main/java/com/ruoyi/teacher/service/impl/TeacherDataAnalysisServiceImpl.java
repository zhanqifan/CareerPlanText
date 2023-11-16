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
import com.ruoyi.teacher.service.TeacherDataAnalysisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        long numberPublishers = 0L;
        //发布率（ok）
        double publishingRate = 0.00;
        //人均项目数（ok）
        long NumberProjectsPerCapita = 0L;
        //平均完成率
        Double averageCompletionRate = 0.00;
        //年度平均项目数
        Long AnnualAverageNumberProjects = 0L;
        //年度完成率
        Double AnnualCompletionRate = 0.00;
        //统计截至日期(ok)
        Date deadlineDate;
        CollegeAnalysisVO collegeAnalysisVO = new CollegeAnalysisVO();
        // 获取前一天的日期
        LocalDate currentDate = LocalDate.now();
        LocalDate previousDay = currentDate.minusDays(1);
        deadlineDate = Date.from(previousDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //学生总数
        long TotalNumberStudents = 0L;
        //项目总数
        long TotalNumberProjects = 0L;
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
                TotalNumberStudents += commonStudentList.size();
                for (CommonStudent student : commonStudentList) {
                    String sNum = student.getSNum();
                    //查询学生所发布的目标数
                    TargetPosition targetPosition = new TargetPosition();
                    targetPosition.setCreateBy(sNum);
                    targetPosition.setState(1);
                    List<TargetPosition> targetPositions = targetPositionService.selectTargetPositionList(targetPosition);
                    if (targetPositions != null && !targetPositions.isEmpty()) {
                        //这个学生有发布 所以 目标发布人数+1
                        numberPublishers++;
                        //统计项目数
                        TotalNumberProjects += targetPositions.size();
                    }
                }
            }
        }
        //发布率:发布人数/学生总人数
        publishingRate = Math.round(((double) numberPublishers / TotalNumberStudents) * 100.0) / 100.0;
        //人均项目数:项目总数/学生总人数
        NumberProjectsPerCapita = TotalNumberProjects / TotalNumberStudents;



        //封装返回值
        collegeAnalysisVO.setPublishingRate(publishingRate);
        collegeAnalysisVO.setNumberPublishers(numberPublishers);
        collegeAnalysisVO.setNumberProjectsPerCapita(NumberProjectsPerCapita);


//            for (Long collegeId : collegeIdList) {
//                CommonStudent commonStudent = new CommonStudent();
//                commonStudent.setCollegeCode(String.format("%03d", collegeId));
//                List<CommonStudent> commonStudentList = commonStudentService.selectCommonStudentList(commonStudent);
//                studentNum += commonStudentList.size();
//                for (CommonStudent student : commonStudentList) {
//                    String sNum = student.getSNum();
//                    TargetPosition targetPosition = new TargetPosition();
//                    targetPosition.setCreateBy(sNum);
//                    targetPosition.setState(1);
//                    List<TargetPosition> targetPositions = targetPositionService.selectTargetPositionList(targetPosition);
//                    DataAnalysis dataAnalysis1 = new DataAnalysis();
//                    dataAnalysis1.setCreateBy(sNum);
//                    dataAnalysis1.setDeadlineDate(previousDayDate);
//                    List<DataAnalysis> dataAnalysisList = dataAnalysisService.selectDataAnalysisList(dataAnalysis1);
//                    if (!dataAnalysisList.isEmpty()) {
//                        //统计发布人数
//                        publishersPersonNum++;
//                        //统计发布目标
//                        perCapitaNum += dataAnalysisList.size();
//                        //统计目标完成率
//                        for (DataAnalysis dataAnalysis : dataAnalysisList) {
//                            completionRateS += Double.parseDouble(dataAnalysis.getCompletionRate());
//                        }
//                    }
//                    publishingNum += targetPositions.size();
//                    yearNum += targetPositions.stream()
//                            .map(TargetPosition::getCreateTime)
//                            .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
//                            .filter(localDate -> localDate.getYear() == targetYear)
//                            .count();
//                    for (TargetPosition targetPosition1 : targetPositions) {
//                        LocalDate createTime = targetPosition1.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                        if (createTime.isAfter(startOfYear) && createTime.isBefore(endOfYear)) {
//                            String positionId = targetPosition1.getPositionId();
//                            DataAnalysis dataAnalysis = getDataAnalysisByPositionId(dataAnalysisList, positionId);
//                            if (dataAnalysis != null) {
//                                totalCompletionRate += Double.parseDouble(dataAnalysis.getCompletionRate());
//                                projectCount++;
//                            }
//                        }
//                    }
//                }
//            }
//            publishingRate = String.valueOf((double) publishersPersonNum / studentNum);
//            perCapitaNum = publishersPersonNum == 0 ? 0 : publishingNum / publishersPersonNum;
//            // 创建 DecimalFormat 对象，定义格式
//            DecimalFormat decimalFormat = new DecimalFormat("#.##");
//            // 格式化 double 值，保留两位小数
//            averageCompletionRate = decimalFormat.format((publishersPersonNum == 0 ? 0 : completionRateS / publishersPersonNum));
//
//            collegeAnalysisVO.setPublishersNum(publishersPersonNum);
//            collegeAnalysisVO.setPerPublishNum(perCapitaNum);
//            collegeAnalysisVO.setPublishingRate(publishingRate);
//            collegeAnalysisVO.setYearNum(yearNum);
//            collegeAnalysisVO.setAverageCompletionRate(averageCompletionRate);
//            collegeAnalysisVO.setYearCompletionRate(String.valueOf(projectCount));
//        } else {
//            List<DataAnalysis> dataAnalyses = dataAnalysisService.selectDataAnalysisList(null);
//        }
//        return collegeAnalysisVO;
//    }
//
//    private static DataAnalysis getDataAnalysisByPositionId(List<DataAnalysis> dataAnalysisList, String positionId) {
//        return dataAnalysisList.stream()
//                .filter(dataAnalysis -> dataAnalysis.getPositionId().equals(positionId))
//                .findFirst()
//                .orElse(null);
//    }

        return collegeAnalysisVO;
    }
}