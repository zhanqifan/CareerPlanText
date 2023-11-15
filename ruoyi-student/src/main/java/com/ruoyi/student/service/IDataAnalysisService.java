package com.ruoyi.student.service;

import java.util.List;
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
     * 查询学生数据分析
     * 
     * @param id 学生数据分析主键
     * @return 学生数据分析
     */
    public DataAnalysis selectDataAnalysisById(Long id);

    /**
     * 查询学生数据分析列表
     * 
     * @param dataAnalysis 学生数据分析
     * @return 学生数据分析集合
     */
    public List<DataAnalysis> selectDataAnalysisList(DataAnalysis dataAnalysis);

    /**
     * 新增学生数据分析
     * 
     * @param dataAnalysis 学生数据分析
     * @return 结果
     */
    public int insertDataAnalysis(DataAnalysis dataAnalysis);

    /**
     * 修改学生数据分析
     * 
     * @param dataAnalysis 学生数据分析
     * @return 结果
     */
    public int updateDataAnalysis(DataAnalysis dataAnalysis);

    /**
     * 批量删除学生数据分析
     * 
     * @param ids 需要删除的学生数据分析主键集合
     * @return 结果
     */
    public int deleteDataAnalysisByIds(Long[] ids);

    /**
     * 删除学生数据分析信息
     * 
     * @param id 学生数据分析主键
     * @return 结果
     */
    public int deleteDataAnalysisById(Long id);

    /**
     * 学生查看本人数据分析结果
     */
    DataAnalysisVO getDataAnalysis(String userId);

}
