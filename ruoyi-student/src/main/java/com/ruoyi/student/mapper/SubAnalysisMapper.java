package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.SubAnalysis;

/**
 * 目录分析Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-12
 */
public interface SubAnalysisMapper 
{
    /**
     * 查询目录分析
     * 
     * @param id 目录分析主键
     * @return 目录分析
     */
    public SubAnalysis selectSubAnalysisById(Long id);

    /**
     * 查询目录分析列表
     * 
     * @param subAnalysis 目录分析
     * @return 目录分析集合
     */
    public List<SubAnalysis> selectSubAnalysisList(SubAnalysis subAnalysis);

    /**
     * 新增目录分析
     * 
     * @param positionId 目录分析
     * @return 结果
     */
    public int insertSubAnalysis(String positionId);

    /**
     * 修改目录分析
     * 
     * @param subAnalysis 目录分析
     * @return 结果
     */
    public int updateSubAnalysis(SubAnalysis subAnalysis);

    /**
     * 删除目录分析
     * 
     * @param id 目录分析主键
     * @return 结果
     */
    public int deleteSubAnalysisById(Long id);

    /**
     * 批量删除目录分析
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubAnalysisByIds(Long[] ids);
}
