package com.ruoyi.student.service;

import java.util.List;
import com.ruoyi.student.domain.Ctatlogue;
import com.ruoyi.student.domain.vo.SecondaryCtatlogue;

/**
 * 技能目录Service接口
 * 
 * @author lihong
 * @date 2023-11-08
 */
public interface ICtatlogueService 
{
    /**
     * 查询技能目录
     * 
     * @param catalogueId 技能目录主键
     * @return 技能目录
     */
    public Ctatlogue selectCtatlogueByCatalogueId(Long catalogueId);

    /**
     * 查询技能目录列表
     * 
     * @param ctatlogue 技能目录
     * @return 技能目录集合
     */
    public List<Ctatlogue> selectCtatlogueList(Ctatlogue ctatlogue);

    /**
     * 新增技能目录
     * 
     * @param ctatlogue 技能目录
     * @return 结果
     */
    public int insertCtatlogue(Ctatlogue ctatlogue);

    /**
     * 修改技能目录
     * 
     * @param ctatlogue 技能目录
     * @return 结果
     */
    public int updateCtatlogue(Ctatlogue ctatlogue);

    /**
     * 批量删除技能目录
     * 
     * @param catalogueIds 需要删除的技能目录主键集合
     * @return 结果
     */
    public int deleteCtatlogueByCatalogueIds(Long[] catalogueIds);

    /**
     * 删除技能目录信息
     * 
     * @param catalogueId 技能目录主键
     * @return 结果
     */
    public int deleteCtatlogueByCatalogueId(Long catalogueId);

    List<SecondaryCtatlogue> selectSecondaryCtatlogueList();

    List<Ctatlogue> selectCtatlogue(Object o);
}
