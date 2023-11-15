package com.ruoyi.student.service;

import java.util.List;
import com.ruoyi.student.domain.CommonStudent;

/**
 * 公用 学生信息Service接口
 * 
 * @author lihong
 * @date 2023-11-09
 */
public interface ICommonStudentService 
{



    public String BuildDepartment();

    /**
     * 查询公用 学生信息
     *
     * @param sId 公用 学生信息主键
     * @return 公用 学生信息
     */
    public CommonStudent selectCommonStudentBySId(Long sId);

    /**
     * 查询公用 学生信息列表
     *
     * @param commonStudent 公用 学生信息
     * @return 公用 学生信息集合
     */
    public List<CommonStudent> selectCommonStudentList(CommonStudent commonStudent);

    /**
     * 新增公用 学生信息
     *
     * @param commonStudent 公用 学生信息
     * @return 结果
     */
    public int insertCommonStudent(CommonStudent commonStudent);

    /**
     * 修改公用 学生信息
     *
     * @param commonStudent 公用 学生信息
     * @return 结果
     */
    public int updateCommonStudent(CommonStudent commonStudent);

    /**
     * 批量删除公用 学生信息
     *
     * @param sIds 需要删除的公用 学生信息主键集合
     * @return 结果
     */
    public int deleteCommonStudentBySIds(Long[] sIds);

    /**
     * 删除公用 学生信息信息
     *
     * @param sId 公用 学生信息主键
     * @return 结果
     */
    public int deleteCommonStudentBySId(Long sId);

    CommonStudent selectCommonStudentBySNum(String userName);
}
