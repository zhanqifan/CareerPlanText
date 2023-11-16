package com.ruoyi.student.service.impl;

import java.util.List;

import com.ruoyi.student.domain.InternshipContent;
import com.ruoyi.student.mapper.InternshipContentMapper;
import com.ruoyi.student.service.IInternshipContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-16
 */
@Service
public class InternshipContentServiceImpl implements IInternshipContentService
{
    @Autowired
    private InternshipContentMapper internshipContentMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public InternshipContent selectInternshipContentById(Long id)
    {
        return internshipContentMapper.selectInternshipContentById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param internshipContent 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<InternshipContent> selectInternshipContentList(InternshipContent internshipContent)
    {
        return internshipContentMapper.selectInternshipContentList(internshipContent);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param internshipContent 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertInternshipContent(InternshipContent internshipContent)
    {
        return internshipContentMapper.insertInternshipContent(internshipContent);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param internshipContent 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateInternshipContent(InternshipContent internshipContent)
    {
        return internshipContentMapper.updateInternshipContent(internshipContent);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteInternshipContentByIds(Long[] ids)
    {
        return internshipContentMapper.deleteInternshipContentByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteInternshipContentById(Long id)
    {
        return internshipContentMapper.deleteInternshipContentById(id);
    }

    @Override
    public InternshipContent selectInternshipContentBySkillsInfoId(String id) {
        return internshipContentMapper.selectInternshipContentBySkillsInfoId(id);
    }
}
