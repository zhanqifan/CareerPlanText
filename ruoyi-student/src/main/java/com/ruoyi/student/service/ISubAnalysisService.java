package com.ruoyi.student.service;

import java.util.List;
import com.ruoyi.student.domain.SubAnalysis;

/**
 * 目录分析Service接口
 * 
 * @author ruoyi
 * @date 2023-11-12
 */
public interface ISubAnalysisService 
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
     * @param
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
     * 批量删除目录分析
     * 
     * @param ids 需要删除的目录分析主键集合
     * @return 结果
     */
    public int deleteSubAnalysisByIds(Long[] ids);

    /**
     * 删除目录分析信息
     * 
     * @param id 目录分析主键
     * @return 结果
     */
    public int deleteSubAnalysisById(Long id);
}
