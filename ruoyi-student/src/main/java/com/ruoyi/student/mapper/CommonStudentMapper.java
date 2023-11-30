package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.domain.dto.CollegeAnalysisDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公用 学生信息Mapper接口
 * 
 * @author lihong
 * @date 2023-11-09
 */
@Mapper
public interface CommonStudentMapper 
{
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
     * 删除公用 学生信息
     * 
     * @param sId 公用 学生信息主键
     * @return 结果
     */
    public int deleteCommonStudentBySId(Long sId);

    /**
     * 批量删除公用 学生信息
     * 
     * @param sIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommonStudentBySIds(Long[] sIds);

    CommonStudent selectCommonStudentBySNum(String SName);

    List<CommonStudent> selectCollege();

    List<CommonStudent> selectProfessional();

    List<CommonStudent> selectClassList();

    List<CommonStudent> selectCommonStudentListByCollegeAnalysis(CollegeAnalysisDTO commonStudent);

    List<CommonStudent> selectCollegeCountList();

    CommonStudent selectCommonStudentByUserName(String sNum);

    List<CommonStudent> QueryStudentsTaughtByTeacherId(@Param("commonStudentList") List<CommonStudent> commonStudentList, @Param("teacherId") String teacherId);

    List<CommonStudent> selectCommonStudentByClassNameList(@Param("classNameList") List<String> classNameList);

}
