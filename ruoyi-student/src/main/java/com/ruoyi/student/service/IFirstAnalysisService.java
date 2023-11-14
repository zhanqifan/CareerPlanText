package com.ruoyi.student.service;

import java.util.List;
import com.ruoyi.student.domain.FirstAnalysis;

/**
 * 一级目录分析Service接口
 * 
 * @author lihong
 * @date 2023-11-14
 */
public interface IFirstAnalysisService 
{
    /**
     * 查询一级目录分析
     * 
     * @param id 一级目录分析主键
     * @return 一级目录分析
     */
    public FirstAnalysis selectFirstAnalysisById(Long id);

    /**
     * 查询一级目录分析列表
     * 
     * @param firstAnalysis 一级目录分析
     * @return 一级目录分析集合
     */
    public List<FirstAnalysis> selectFirstAnalysisList(FirstAnalysis firstAnalysis);

    /**
     * 新增一级目录分析
     * 
     * @param firstAnalysis 一级目录分析
     * @return 结果
     */
    public int insertFirstAnalysis(String positionId);

    /**
     * 修改一级目录分析
     * 
     * @param firstAnalysis 一级目录分析
     * @return 结果
     */
    public int updateFirstAnalysis(FirstAnalysis firstAnalysis);

    /**
     * 批量删除一级目录分析
     * 
     * @param ids 需要删除的一级目录分析主键集合
     * @return 结果
     */
    public int deleteFirstAnalysisByIds(Long[] ids);

    /**
     * 删除一级目录分析信息
     * 
     * @param id 一级目录分析主键
     * @return 结果
     */
    public int deleteFirstAnalysisById(Long id);
}
