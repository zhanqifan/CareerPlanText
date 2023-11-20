package com.ruoyi.student.service.impl;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.student.domain.*;
import com.ruoyi.student.domain.vo.ClusterAnalysisVo;
import com.ruoyi.student.domain.vo.DataAnalysisVO;
import com.ruoyi.student.domain.vo.MoonCloseCompletionsNumVO;
import com.ruoyi.student.service.*;
import com.ruoyi.student.system.service.ISysDeptService;
import com.ruoyi.student.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.DataAnalysisMapper;

import javax.annotation.Resource;

/**
 * 学生数据分析Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-12
 */
@Service
public class DataAnalysisServiceImpl implements IDataAnalysisService 
{
    @Autowired
    private DataAnalysisMapper dataAnalysisMapper;


    @Autowired
    private ISkillsInfoService skillsInfoService;

    @Autowired
    private ISysUserService sysUserService;

    @Resource
    private ISysDeptService sysDeptService;

    @Resource
    private  ICtatlogueService ctatlogueService;

    @Resource
    private ICommonStudentService commonStudentService;

    @Resource
    private IFirstAnalysisService firstAnalysisService;

    @Resource
    private ISubAnalysisService subAnalysisService;



    /**
     * 查询学生数据分析列表
     *
     * @param dataAnalysis 学生数据分析
     * @return 学生数据分析
     */
    @Override
    public List<DataAnalysis> selectDataAnalysisList(DataAnalysis dataAnalysis)
    {
        return dataAnalysisMapper.selectDataAnalysisList(dataAnalysis);
    }



    /**
     * 学生查看本人的数据分析结果
     * */
    @Override
    public DataAnalysisVO getDataAnalysis(String userId,String positionId) {
        DataAnalysisVO dataAnalysisVO = new DataAnalysisVO();
        TargetPosition targetPosition = new TargetPosition();
        targetPosition.setCreateBy(userId);
        //设置主目标详情
        LocalDate currentDate = LocalDate.now();
        //获取前一天日期
        LocalDate previousDay = currentDate.minusDays(1);
        // 转换为java.util.Date
        Date previousDayDate = Date.from(previousDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //统计月完成目标数
        List<MoonCloseCompletionsNumVO> moonCloseCompletionsNumVOS = this.moonCloseCompletionsNum(positionId);
        dataAnalysisVO.setMoonCloseCompletionsNumVOS(moonCloseCompletionsNumVOS);
        DataAnalysis dataAnalysis = new DataAnalysis();
        dataAnalysis.setPositionId(positionId);
        dataAnalysis.setDeadlineDate(previousDayDate);
        List<DataAnalysis> dataAnalysisList = dataAnalysisMapper.selectDataAnalysisList(dataAnalysis);
        BeanUtils.copyProperties(dataAnalysisList.get(0), dataAnalysisVO);
        //统计同系同年级数据
        ArrayList<ClusterAnalysisVo> clusterAnalysisVoArrayList = this.ClusterAnalysis(userId);
        ClusterAnalysisVo clusterAnalysisVo = new ClusterAnalysisVo();
        clusterAnalysisVo.setIsMyself(1);
        clusterAnalysisVo.setTargetNum(dataAnalysisList.get(0).getTargetNum());
        clusterAnalysisVo.setCompletionProgress(dataAnalysisList.get(0).getCompletionRate());
        clusterAnalysisVoArrayList.add(clusterAnalysisVo);
        dataAnalysisVO.setClusterAnalysisVos(clusterAnalysisVoArrayList);
        //查询所有目录
        List<Ctatlogue> ctatlogueList = ctatlogueService.selectCtatlogue(null);
        //一级目录统计结果
        List<FirstAnalysis> firstAnalysisList = firstAnalysisService.selectFirstAnalysisByPositionId(positionId);
        //二级目录统计结果
        SubAnalysis subAnalysis = new SubAnalysis();
        subAnalysis.setPostitionId(positionId);
        subAnalysis.setDeadlineDate(previousDayDate);
        List<SubAnalysis> subAnalyses = subAnalysisService.selectSubAnalysisList(subAnalysis);
        ArrayList<FirstAnalysis> firstAnalysisArrayList = new ArrayList<>();
        ArrayList<SubAnalysis> subAnalysisArrayList = new ArrayList<>();
        for (Ctatlogue ctatlogue : ctatlogueList) {
            if (Objects.equals(ctatlogue.getParentId(), "0")) {
                FirstAnalysis firstAnalysis = new FirstAnalysis();
                this.setFirstAnalysisData(firstAnalysis, ctatlogue, positionId, firstAnalysisList);
                firstAnalysisArrayList.add(firstAnalysis);
            } else {
                SubAnalysis analysis = new SubAnalysis();
                this.setSubAnalysisData(analysis, ctatlogue, subAnalyses);
                subAnalysisArrayList.add(analysis);
            }
        }
        dataAnalysisVO.setFirstAnalysisList(firstAnalysisArrayList);
        dataAnalysisVO.setSubAnalysisList(subAnalysisArrayList);
        return dataAnalysisVO;
    }

    @Override
    public List<DataAnalysis> selectDataAnalysisListByCreateBy(String sNum) {
        return dataAnalysisMapper.selectDataAnalysisListByCreateBy(sNum);
    }

    @Override
    public List<DataAnalysis> selectDataAnalysisListByCompleted(DataAnalysis dataAnalysis) {
        return dataAnalysisMapper.selectDataAnalysisListByCompleted(dataAnalysis);
    }

    /***
     *统计一级目录分析结果
     */
    private void setFirstAnalysisData(FirstAnalysis firstAnalysis, Ctatlogue ctatlogue, String positionId, List<FirstAnalysis> firstAnalysisList) {
        boolean found = false;
        for (FirstAnalysis first : firstAnalysisList) {
            if (Objects.equals(first.getFirstId(), ctatlogue.getCatalogueId())) {
                firstAnalysis.setFirstId(first.getFirstId());
                firstAnalysis.setPostitionId(positionId);
                firstAnalysis.setFirstName(first.getFirstName());
                firstAnalysis.setFirstCompletionRate(first.getFirstCompletionRate());
                found = true;
                break;
            }
        }
        if (!found) {
            firstAnalysis.setFirstId(ctatlogue.getCatalogueId());
            firstAnalysis.setFirstName(ctatlogue.getCatalogueName());
            firstAnalysis.setFirstCompletionRate("0");
        }
    }

    /***
     *统计二级目录分析结果
     */
    private void setSubAnalysisData(SubAnalysis subAnalysis, Ctatlogue ctatlogue, List<SubAnalysis> subAnalyses) {
        boolean found = false;
        for (SubAnalysis analysis : subAnalyses) {
            if (Objects.equals(analysis.getSecondaryId(), ctatlogue.getCatalogueId())) {
                subAnalysis.setSecondaryId(analysis.getSecondaryId());
                subAnalysis.setSecondaryName(analysis.getSecondaryName());
                subAnalysis.setSecondaryTargetNum(analysis.getSecondaryTargetNum());
                subAnalysis.setSecondaryCompletionNum(analysis.getSecondaryCompletionNum());
                subAnalysis.setSecondaryCompletionRate(analysis.getSecondaryCompletionRate());
                found = true;
                break;
            }
        }
        if (!found) {
            subAnalysis.setSecondaryId(ctatlogue.getCatalogueId());
            subAnalysis.setSecondaryName(ctatlogue.getCatalogueName());
            subAnalysis.setSecondaryTargetNum(0L);
            subAnalysis.setSecondaryCompletionNum(0L);
            subAnalysis.setSecondaryCompletionRate("0");
        }
    }

    /**
     * 月完成目标数
     */
    private List<MoonCloseCompletionsNumVO> moonCloseCompletionsNum(String positionId){
        //查询岗位目标列表
        List<SkillsInfo> skillsInfoList = skillsInfoService.selectSkillsInfoByPositionId(positionId);
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 统计前12个月和当前月份
        List<MoonCloseCompletionsNumVO> moonCloseCompletionsNumVOS = new ArrayList<>();
        for (int i = -12; i <=11; i++) {
            MoonCloseCompletionsNumVO moonCloseCompletionsNumVO = new MoonCloseCompletionsNumVO();
            LocalDate startDate = currentDate.minusMonths(i).withDayOfMonth(1);
            LocalDate endDate = currentDate.minusMonths(i).withDayOfMonth(startDate.lengthOfMonth());
            // 进行统计
            Map<String, Long> result = countProjects(skillsInfoList, startDate, endDate);
            // 输出结果
            moonCloseCompletionsNumVO.setMoon(startDate.getYear()+"-"+startDate.getMonthValue());
            moonCloseCompletionsNumVO.setMoonClose(result.get("totalProjects"));
            moonCloseCompletionsNumVO.setMoonCompletionsNum(result.get("completedProjects"));
            moonCloseCompletionsNumVOS.add(moonCloseCompletionsNumVO);
        }
        return moonCloseCompletionsNumVOS;
    }

    private static Map<String, Long> countProjects(List<SkillsInfo> skillsInfoList, LocalDate startDate, LocalDate endDate) {
        Map<String, Long> result = new HashMap<>();
        long totalProjects = 0L;
        long completedProjects = 0L;
        for (SkillsInfo skillsInfo : skillsInfoList) {
            LocalDate localDate = skillsInfo.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDate != null && localDate.isAfter(startDate.minusDays(1)) && localDate.isBefore(endDate.plusDays(1))) {
                totalProjects++;
                if (skillsInfo.getCompletionStatus() == 1) {
                    completedProjects++;
                }
            }
        }
        result.put("totalProjects", totalProjects);
        result.put("completedProjects", completedProjects);
        return result;
    }

    /**
     *同年级聚类分析
     * */
    private ArrayList<ClusterAnalysisVo>  ClusterAnalysis(String userName){
        //查询出同系同年级的学生的主目标岗位
        SysUser sysUser = sysUserService.selectUserByUserName(userName);
        //学生所在的年级
        String grade = sysUser.getGrade();
        //学生所在的专业名称
        SysDept sysDept = sysDeptService.selectDeptById(SecurityUtils.getDeptId());
        String parentName = sysDept.getParentName();
        //学生所在的学院id
        Long parentId = sysDept.getParentId();
        //查询同专业同年级的学生
        CommonStudent commonStudent = new CommonStudent();
        commonStudent.setCollegeCode(String.format("%03d", parentId));
        commonStudent.setGrade(grade);
        commonStudent.setProfessional(parentName);
        List<CommonStudent> commonStudents = commonStudentService.selectCommonStudentList(commonStudent);
        //统计这些学生的项目数和完成率
        ArrayList<ClusterAnalysisVo> clusterAnalysisVoArrayList = new ArrayList<>();
        for (CommonStudent student:commonStudents){
            String sNum = student.getSNum();
            if(!sNum.equals(userName)){
                //查询学生的主目标
                ClusterAnalysisVo clusterAnalysisVo = new ClusterAnalysisVo();
                DataAnalysis dataAnalysis=dataAnalysisMapper.selectIsMainDataAnalyByUserName(sNum);
                clusterAnalysisVo.setTargetNum(dataAnalysis.getTargetNum());
                clusterAnalysisVo.setIsMyself(0);
                clusterAnalysisVo.setCompletionProgress(dataAnalysis.getCompletionRate());
                clusterAnalysisVoArrayList.add(clusterAnalysisVo);
            }
        }

        return clusterAnalysisVoArrayList;
    }
}

