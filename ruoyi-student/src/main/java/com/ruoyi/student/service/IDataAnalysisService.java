package com.ruoyi.student.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.student.domain.DataAnalysis;
import com.ruoyi.student.domain.vo.DataAnalysisVO;

/**
 * 学生数据分析Service接口
 * 
 * @author lihong
 * @date 2023-11-12
 */
public interface IDataAnalysisService 
{


    /**
     * 查询学生数据分析列表
     * 
     * @param dataAnalysis 学生数据分析
     * @return 学生数据分析集合
     */
    public List<DataAnalysis> selectDataAnalysisList(DataAnalysis dataAnalysis);

    /**
     * 学生查看本人数据分析结果
     */
    DataAnalysisVO getDataAnalysis(String userId,String positionId);


    List<DataAnalysis> selectDataAnalysisListByCreateBy(String sNum);
}
