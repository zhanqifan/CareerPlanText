package com.ruoyi.student.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.SkillsInfoMapper;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.service.ISkillsInfoService;

/**
 * 技能详情Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-08
 */
@Service
public class SkillsInfoServiceImpl implements ISkillsInfoService 
{
    @Autowired
    private SkillsInfoMapper skillsInfoMapper;

    /**
     * 查询技能详情
     * 
     * @param id 技能详情主键
     * @return 技能详情
     */
    @Override
    public SkillsInfo selectSkillsInfoById(Integer id)
    {
        return skillsInfoMapper.selectSkillsInfoById(id);
    }

    /**
     * 查询技能详情列表
     * 
     * @param skillsInfo 技能详情
     * @return 技能详情
     */
    @Override
    public List<SkillsInfo> selectSkillsInfoList(SkillsInfo skillsInfo)
    {
        return skillsInfoMapper.selectSkillsInfoList(skillsInfo);
    }

    /**
     * 新增技能详情
     * 
     * @param skillsInfo 技能详情
     * @return 结果
     */
    @Override
    public int insertSkillsInfo(SkillsInfo skillsInfo)
    {
        skillsInfo.setCreateTime(DateUtils.getNowDate());
        return skillsInfoMapper.insertSkillsInfo(skillsInfo);
    }

    /**
     * 修改技能详情
     * 
     * @param skillsInfo 技能详情
     * @return 结果
     */
    @Override
    public int updateSkillsInfo(SkillsInfo skillsInfo)
    {
        return skillsInfoMapper.updateSkillsInfo(skillsInfo);
    }

    /**
     * 批量删除技能详情
     * 
     * @param ids 需要删除的技能详情主键
     * @return 结果
     */
    @Override
    public int deleteSkillsInfoByIds(Integer[] ids)
    {
        return skillsInfoMapper.deleteSkillsInfoByIds(ids);
    }

    /**
     * 删除技能详情信息
     * 
     * @param id 技能详情主键
     * @return 结果
     */
    @Override
    public int deleteSkillsInfoById(Integer id)
    {
        return skillsInfoMapper.deleteSkillsInfoById(id);
    }

    /**
     * 根据岗位id查询岗位的具体信息
     * @param id
     * @return
     */
    @Override
    public List<SkillsInfo> selectSkillsInfoByPositionId(String id) {
        return skillsInfoMapper.selectSkillsInfoByPositionId(id);
    }
}
