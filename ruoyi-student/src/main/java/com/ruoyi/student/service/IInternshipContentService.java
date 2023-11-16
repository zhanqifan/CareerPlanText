package com.ruoyi.student.service;

import com.ruoyi.student.domain.InternshipContent;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-11-16
 */
public interface IInternshipContentService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public InternshipContent selectInternshipContentById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param internshipContent 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<InternshipContent> selectInternshipContentList(InternshipContent internshipContent);

    /**
     * 新增【请填写功能名称】
     * 
     * @param internshipContent 【请填写功能名称】
     * @return 结果
     */
    public int insertInternshipContent(InternshipContent internshipContent);

    /**
     * 修改【请填写功能名称】
     * 
     * @param internshipContent 【请填写功能名称】
     * @return 结果
     */
    public int updateInternshipContent(InternshipContent internshipContent);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteInternshipContentByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteInternshipContentById(Long id);

    InternshipContent selectInternshipContentBySkillsInfoId(String id);
}
