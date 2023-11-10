package com.ruoyi.student.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.CommonStudentMapper;
import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.service.ICommonStudentService;

/**
 * 公用 学生信息Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-09
 */
@Service
public class CommonStudentServiceImpl implements ICommonStudentService 
{
    @Autowired
    private CommonStudentMapper commonStudentMapper;

    /**
     * 查询公用 学生信息
     * 
     * @param sId 公用 学生信息主键
     * @return 公用 学生信息
     */
    @Override
    public CommonStudent selectCommonStudentBySId(Long sId)
    {
        return commonStudentMapper.selectCommonStudentBySId(sId);
    }

    /**
     * 构建学院组织架构
     */


    /**
     * 查询公用 学生信息列表
     * 
     * @param commonStudent 公用 学生信息
     * @return 公用 学生信息
     */
    @Override
    public List<CommonStudent> selectCommonStudentList(CommonStudent commonStudent)
    {
        return commonStudentMapper.selectCommonStudentList(commonStudent);
    }

    /**
     * 新增公用 学生信息
     * 
     * @param commonStudent 公用 学生信息
     * @return 结果
     */
    @Override
    public int insertCommonStudent(CommonStudent commonStudent)
    {
        return commonStudentMapper.insertCommonStudent(commonStudent);
    }

    /**
     * 修改公用 学生信息
     * 
     * @param commonStudent 公用 学生信息
     * @return 结果
     */
    @Override
    public int updateCommonStudent(CommonStudent commonStudent)
    {
        return commonStudentMapper.updateCommonStudent(commonStudent);
    }

    /**
     * 批量删除公用 学生信息
     * 
     * @param sIds 需要删除的公用 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonStudentBySIds(Long[] sIds)
    {
        return commonStudentMapper.deleteCommonStudentBySIds(sIds);
    }

    /**
     * 删除公用 学生信息信息
     * 
     * @param sId 公用 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonStudentBySId(Long sId)
    {
        return commonStudentMapper.deleteCommonStudentBySId(sId);
    }

    @Override
    public CommonStudent selectCommonStudentBySNum(String userName) {
        return commonStudentMapper.selectCommonStudentBySNum(userName);
    }
}
