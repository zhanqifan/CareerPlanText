package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.DataAnalysis;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生数据分析Mapper接口
 * 
 * @author lihong
 * @date 2023-11-12
 */
@Mapper
public interface DataAnalysisMapper 
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
     * 删除学生数据分析
     * 
     * @param id 学生数据分析主键
     * @return 结果
     */
    public int deleteDataAnalysisById(Long id);

    /**
     * 批量删除学生数据分析
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataAnalysisByIds(Long[] ids);

    /**
     * 根据岗位id查询统计结果
     * @param positionId
     * @return
     */
    DataAnalysis selectDataAnalysisListByPositionId(String positionId);

    DataAnalysis selectIsMainDataAnalyByUserName(String sNum);
}
