package com.ruoyi.student.mapper;

import java.util.List;

import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.vo.FirstCtatlogueAnalysisVO;
import org.apache.ibatis.annotations.Param;

/**
 * 技能详情Mapper接口
 * 
 * @author lihong
 * @date 2023-11-08
 */
public interface SkillsInfoMapper 
{
    /**
     * 查询技能详情
     * 
     * @param id 技能详情主键
     * @return 技能详情
     */
    public SkillsInfo selectSkillsInfoById(String id);

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
     * 删除技能详情
     * 
     * @param id 技能详情主键
     * @return 结果
     */
    public int deleteSkillsInfoById(String id);

    /**
     * 批量删除技能详情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkillsInfoByIds(Integer[] ids);

    List<SkillsInfo> selectSkillsInfoByPositionId(String id);

    List<SkillsInfo> selectSkillsInfoByUnfinished(String positionId);

    SkillsInfo selectSkillsInfoBycreateBy(String sNum);

    List<FirstCtatlogueAnalysisVO> StatisticsFirstCtatlogueAnalysisVO(@Param("studentIdList") List<String> studentIdList);
}
