package com.ruoyi.student.service;

import java.util.List;
import com.ruoyi.student.domain.SkillsInfo;

/**
 * 技能详情Service接口
 * 
 * @author lihong
 * @date 2023-11-08
 */
public interface ISkillsInfoService 
{
    /**
     * 查询技能详情
     * 
     * @param id 技能详情主键
     * @return 技能详情
     */
    public SkillsInfo selectSkillsInfoById(Integer id);

    /**
     * 查询技能详情列表
     * 
     * @param skillsInfo 技能详情
     * @return 技能详情集合
     */
    public List<SkillsInfo> selectSkillsInfoList(SkillsInfo skillsInfo);

    /**
     * 新增技能详情
     * 
     * @param skillsInfo 技能详情
     * @return 结果
     */
    public int insertSkillsInfo(SkillsInfo skillsInfo);

    /**
     * 修改技能详情
     * 
     * @param skillsInfo 技能详情
     * @return 结果
     */
    public int updateSkillsInfo(SkillsInfo skillsInfo);

    /**
     * 批量删除技能详情
     * 
     * @param ids 需要删除的技能详情主键集合
     * @return 结果
     */
    public int deleteSkillsInfoByIds(Integer[] ids);

    /**
     * 删除技能详情信息
     * 
     * @param id 技能详情主键
     * @return 结果
     */
    public int deleteSkillsInfoById(Integer id);

    List<SkillsInfo> selectSkillsInfoByPositionId(String id);

    List<SkillsInfo> selectSkillsInfoByUnfinished(String positionId);

    List<SkillsInfo> selectSkillsInfoByCompletions(String positionId);

    void AnalyzeCatalogData(String positionId);

    SkillsInfo selectSkillsInfoBycreateBy(String sNum);
}
