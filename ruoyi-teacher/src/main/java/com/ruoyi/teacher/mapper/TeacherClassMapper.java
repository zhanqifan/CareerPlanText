package com.ruoyi.teacher.mapper;

import java.util.List;
import com.ruoyi.teacher.domain.TeacherClass;
import org.apache.ibatis.annotations.Mapper;

/**
 * 教师所教的班级Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-10
 */
@Mapper
public interface TeacherClassMapper 
{
    /**
     * 查询教师所教的班级
     * 
     * @param teacherId 教师所教的班级主键
     * @return 教师所教的班级
     */
    public List<TeacherClass> selectTeacherClassByTeacherId(String teacherId);

    /**
     * 查询教师所教的班级列表
     * 
     * @param teacherClass 教师所教的班级
     * @return 教师所教的班级集合
     */
    public List<TeacherClass> selectTeacherClassList(TeacherClass teacherClass);

    /**
     * 新增教师所教的班级
     * 
     * @param teacherClass 教师所教的班级
     * @return 结果
     */
    public int insertTeacherClass(TeacherClass teacherClass);

    /**
     * 修改教师所教的班级
     * 
     * @param teacherClass 教师所教的班级
     * @return 结果
     */
    public int updateTeacherClass(TeacherClass teacherClass);

    /**
     * 删除教师所教的班级
     * 
     * @param teacherId 教师所教的班级主键
     * @return 结果
     */
    public int deleteTeacherClassByTeacherId(Long teacherId);

    /**
     * 批量删除教师所教的班级
     * 
     * @param teacherIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTeacherClassByTeacherIds(Long[] teacherIds);
}
