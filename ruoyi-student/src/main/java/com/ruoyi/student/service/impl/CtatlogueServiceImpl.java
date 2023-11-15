package com.ruoyi.student.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.student.domain.vo.SecondaryCtatlogue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.CtatlogueMapper;
import com.ruoyi.student.domain.Ctatlogue;
import com.ruoyi.student.service.ICtatlogueService;

/**
 * 技能目录Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-08
 */
@Service
public class CtatlogueServiceImpl implements ICtatlogueService 
{
    @Autowired
    private CtatlogueMapper ctatlogueMapper;

    /**
     * 查询技能目录
     * 
     * @param catalogueId 技能目录主键
     * @return 技能目录
     */
    @Override
    public Ctatlogue selectCtatlogueByCatalogueId(Long catalogueId)
    {
        return ctatlogueMapper.selectCtatlogueByCatalogueId(catalogueId);
    }

    /**
     * 查询技能目录列表
     * 
     * @param ctatlogue 技能目录
     * @return 技能目录
     */
    @Override
    public List<Ctatlogue> selectCtatlogueList(Ctatlogue ctatlogue)
    {
        List<Ctatlogue> ctatlogues = ctatlogueMapper.selectCtatlogueList(ctatlogue);
        List<Ctatlogue> collect = ctatlogues.stream()
                .filter(a -> a.getParentId().equals("0"))
                .peek(m -> m.setChild(getChildren(m, ctatlogues)))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * 递归查询子节点
     ** @return 节点分类列表
     */
    private List<Ctatlogue> getChildren(Ctatlogue root, List<Ctatlogue> all) {
       return all.stream()
                .filter(m -> m.getParentId().equals(root.getCatalogueId().toString()))
                .peek(m -> m.setChild(getChildren(m, all)))
                .collect(Collectors.toList());
    }

    /**
     * 新增技能目录
     * 
     * @param ctatlogue 技能目录
     * @return 结果
     */
    @Override
    public int insertCtatlogue(Ctatlogue ctatlogue)
    {
//        ctatlogue.setCreateTime(DateUtils.getNowDate());
        return ctatlogueMapper.insertCtatlogue(ctatlogue);
    }

    /**
     * 修改技能目录
     * 
     * @param ctatlogue 技能目录
     * @return 结果
     */
    @Override
    public int updateCtatlogue(Ctatlogue ctatlogue)
    {
        return ctatlogueMapper.updateCtatlogue(ctatlogue);
    }

    /**
     * 批量删除技能目录
     * 
     * @param catalogueIds 需要删除的技能目录主键
     * @return 结果
     */
    @Override
    public int deleteCtatlogueByCatalogueIds(Long[] catalogueIds)
    {
        return ctatlogueMapper.deleteCtatlogueByCatalogueIds(catalogueIds);
    }

    /**
     * 删除技能目录信息
     * 
     * @param catalogueId 技能目录主键
     * @return 结果
     */
    @Override
    public int deleteCtatlogueByCatalogueId(Long catalogueId)
    {
        return ctatlogueMapper.deleteCtatlogueByCatalogueId(catalogueId);
    }

    @Override
    public List<SecondaryCtatlogue> selectSecondaryCtatlogueList() {
        return ctatlogueMapper.selectSecondaryCtatlogueList();
    }

    @Override
    public List<Ctatlogue> selectCtatlogue(Object o) {
        return ctatlogueMapper.selectCtatlogueList(null);
    }
}
