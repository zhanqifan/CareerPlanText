package com.ruoyi.student.service.impl;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.student.domain.*;
import com.ruoyi.student.domain.vo.DataAnalysisVO;
import com.ruoyi.student.domain.vo.MoonCloseCompletionsNumVO;
import com.ruoyi.student.service.*;
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
    private ITargetPositionService targetPositionService;

    @Autowired
    private ISkillsInfoService skillsInfoService;

    @Resource
    private  ICtatlogueService ctatlogueService;


    @Resource
    private IFirstAnalysisService firstAnalysisService;

    @Resource
    private ISubAnalysisService subAnalysisService;
    /**
     * 查询学生数据分析
     * 
     * @param id 学生数据分析主键
     * @return 学生数据分析
     */
    @Override
    public DataAnalysis selectDataAnalysisById(Long id)
    {
        return dataAnalysisMapper.selectDataAnalysisById(id);
    }


    /**
     * 定时计算学生数据分析
     */


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
     * 新增学生数据分析
     * 
     * @param dataAnalysis 学生数据分析
     * @return 结果
     */
    @Override
    public int insertDataAnalysis(DataAnalysis dataAnalysis)
    {
        dataAnalysis.setCreateTime(DateUtils.getNowDate());
        return dataAnalysisMapper.insertDataAnalysis(dataAnalysis);
    }

    /**
     * 修改学生数据分析
     * 
     * @param dataAnalysis 学生数据分析
     * @return 结果
     */
    @Override
    public int updateDataAnalysis(DataAnalysis dataAnalysis)
    {
        return dataAnalysisMapper.updateDataAnalysis(dataAnalysis);
    }

    /**
     * 批量删除学生数据分析
     * 
     * @param ids 需要删除的学生数据分析主键
     * @return 结果
     */
    @Override
    public int deleteDataAnalysisByIds(Long[] ids)
    {
        return dataAnalysisMapper.deleteDataAnalysisByIds(ids);
    }

    /**
     * 删除学生数据分析信息
     * 
     * @param id 学生数据分析主键
     * @return 结果
     */
    @Override
    public int deleteDataAnalysisById(Long id)
    {
        return dataAnalysisMapper.deleteDataAnalysisById(id);
    }

    /**
     * 学生查看本人的数据分析结果
     * */
    @Override
    public DataAnalysisVO getDataAnalysis(String userId) {
        DataAnalysisVO dataAnalysisVO = new DataAnalysisVO();
        TargetPosition targetPosition = new TargetPosition();
        targetPosition.setCreateBy(userId);
        List<TargetPosition> targetPositions = targetPositionService.selectTargetPositionListByUserId(userId);
        dataAnalysisVO.setTargetPositionList(targetPositions);
        for (TargetPosition position:targetPositions){
            //设置主目标详情
            if(position.getIsMain()==1){
                LocalDate currentDate = LocalDate.now();
                //获取前一天日期
                LocalDate previousDay = currentDate.minusDays(1);
                // 转换为java.util.Date
                Date previousDayDate = Date.from(previousDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
                String positionId = position.getPositionId();
                //统计月完成目标数
                List<MoonCloseCompletionsNumVO> moonCloseCompletionsNumVOS = this.moonCloseCompletionsNum(positionId);
                dataAnalysisVO.setMoonCloseCompletionsNumVOS(moonCloseCompletionsNumVOS);
                DataAnalysis dataAnalysis = new DataAnalysis();
                dataAnalysis.setPositionId(positionId);
                dataAnalysis.setDeadlineDate(previousDayDate);
                List<DataAnalysis> dataAnalysisList = dataAnalysisMapper.selectDataAnalysisList(dataAnalysis);
                BeanUtils.copyProperties(dataAnalysisList.get(0),dataAnalysisVO);
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
               }
            }
        return dataAnalysisVO;
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
    private void  ClusterAnalysis(){

    }
}

