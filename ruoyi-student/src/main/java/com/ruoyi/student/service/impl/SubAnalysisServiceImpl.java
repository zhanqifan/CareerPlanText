package com.ruoyi.student.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.SubAnalysisMapper;
import com.ruoyi.student.domain.SubAnalysis;
import com.ruoyi.student.service.ISubAnalysisService;

/**
 * 目录分析Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-12
 */
@Service
public class SubAnalysisServiceImpl implements ISubAnalysisService 
{
    @Autowired
    private SubAnalysisMapper subAnalysisMapper;

    /**
     * 查询目录分析
     * 
     * @param id 目录分析主键
     * @return 目录分析
     */
    @Override
    public SubAnalysis selectSubAnalysisById(Long id)
    {
        return subAnalysisMapper.selectSubAnalysisById(id);
    }

    /**
     * 查询目录分析列表
     * 
     * @param subAnalysis 目录分析
     * @return 目录分析
     */
    @Override
    public List<SubAnalysis> selectSubAnalysisList(SubAnalysis subAnalysis)
    {
        return subAnalysisMapper.selectSubAnalysisList(subAnalysis);
    }

    /**
     * 新增目录分析
     * @return 结果
     */
    @Override
    public int insertSubAnalysis(String positionId)
    {
        return subAnalysisMapper.insertSubAnalysis(positionId);
    }

    /**
     * 修改目录分析
     * 
     * @param subAnalysis 目录分析
     * @return 结果
     */
    @Override
    public int updateSubAnalysis(SubAnalysis subAnalysis)
    {
        return subAnalysisMapper.updateSubAnalysis(subAnalysis);
    }

    /**
     * 批量删除目录分析
     * 
     * @param ids 需要删除的目录分析主键
     * @return 结果
     */
    @Override
    public int deleteSubAnalysisByIds(Long[] ids)
    {
        return subAnalysisMapper.deleteSubAnalysisByIds(ids);
    }

    /**
     * 删除目录分析信息
     * 
     * @param id 目录分析主键
     * @return 结果
     */
    @Override
    public int deleteSubAnalysisById(Long id)
    {
        return subAnalysisMapper.deleteSubAnalysisById(id);
    }
}
