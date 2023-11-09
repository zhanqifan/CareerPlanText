package com.ruoyi.student.service;

import java.util.List;
import com.ruoyi.student.domain.Evaluate;
import com.ruoyi.student.domain.dto.EvaluateDTO;

/**
 * 评价Service接口
 * 
 * @author lihong
 * @date 2023-11-08
 */
public interface IEvaluateService 
{
    /**
     * 查询评价
     * 
     * @param evaluateId 评价主键
     * @return 评价
     */
    public Evaluate selectEvaluateByEvaluateId(Integer evaluateId);

    /**
     * 查询评价列表
     * 
     * @param evaluate 评价
     * @return 评价集合
     */
    public List<Evaluate> selectEvaluateList(Evaluate evaluate);

    /**
     * 新增评价
     * 
     * @param evaluate 评价
     * @return 结果
     */
    public int insertEvaluate(EvaluateDTO evaluate);

    /**
     * 修改评价
     * 
     * @param evaluate 评价
     * @return 结果
     */
    public int updateEvaluate(Evaluate evaluate);

    /**
     * 批量删除评价
     * 
     * @param evaluateIds 需要删除的评价主键集合
     * @return 结果
     */
    public int deleteEvaluateByEvaluateIds(Integer[] evaluateIds);

    /**
     * 删除评价信息
     * 
     * @param evaluateId 评价主键
     * @return 结果
     */
    public int deleteEvaluateByEvaluateId(Integer evaluateId);
}
