package com.ruoyi.student.service.impl;

import java.time.*;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.service.ISkillsInfoService;
import com.ruoyi.student.service.ITargetPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.DataAnalysisMapper;
import com.ruoyi.student.domain.DataAnalysis;
import com.ruoyi.student.service.IDataAnalysisService;

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
}
