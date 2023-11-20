package com.ruoyi.student.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.FirstAnalysisMapper;
import com.ruoyi.student.domain.FirstAnalysis;
import com.ruoyi.student.service.IFirstAnalysisService;

/**
 * 一级目录分析Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-14
 */
@Service
public class FirstAnalysisServiceImpl implements IFirstAnalysisService 
{
    @Autowired
    private FirstAnalysisMapper firstAnalysisMapper;

    /**
     * 查询一级目录分析
     * 
     * @param id 一级目录分析主键
     * @return 一级目录分析
     */
    @Override
    public FirstAnalysis selectFirstAnalysisById(Long id)
    {
        return firstAnalysisMapper.selectFirstAnalysisById(id);
    }

    /**
     * 查询一级目录分析列表
     * 
     * @param firstAnalysis 一级目录分析
     * @return 一级目录分析
     */
    @Override
    public List<FirstAnalysis> selectFirstAnalysisList(FirstAnalysis firstAnalysis)
    {
        return firstAnalysisMapper.selectFirstAnalysisList(firstAnalysis);
    }

    /**
     * 新增一级目录分析
     */
    @Override
    public void insertFirstAnalysis(String userId, String positionId)
    {
        firstAnalysisMapper.insertFirstAnalysis(userId, positionId);
    }

    /**
     * 修改一级目录分析
     * 
     * @param firstAnalysis 一级目录分析
     * @return 结果
     */
    @Override
    public int updateFirstAnalysis(FirstAnalysis firstAnalysis)
    {
        return firstAnalysisMapper.updateFirstAnalysis(firstAnalysis);
    }

    /**
     * 批量删除一级目录分析
     * 
     * @param ids 需要删除的一级目录分析主键
     * @return 结果
     */
    @Override
    public int deleteFirstAnalysisByIds(Long[] ids)
    {
        return firstAnalysisMapper.deleteFirstAnalysisByIds(ids);
    }

    /**
     * 删除一级目录分析信息
     * 
     * @param id 一级目录分析主键
     * @return 结果
     */
    @Override
    public int deleteFirstAnalysisById(Long id)
    {
        return firstAnalysisMapper.deleteFirstAnalysisById(id);
    }

    @Override
    public List<FirstAnalysis> selectFirstAnalysisByPositionId(String positionId) {
        FirstAnalysis firstAnalysis = new FirstAnalysis();
        LocalDate currentDate = LocalDate.now();
        //获取前一天日期
        LocalDate previousDay = currentDate.minusDays(1);
        // 转换为java.util.Date
        Date previousDayDate = Date.from(previousDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        firstAnalysis.setPostitionId(positionId);
        firstAnalysis.setDeadlineDate(previousDayDate);
        return firstAnalysisMapper.selectFirstAnalysisList(firstAnalysis);
    }
}
