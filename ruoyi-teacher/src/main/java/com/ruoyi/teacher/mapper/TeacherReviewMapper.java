package com.ruoyi.teacher.mapper;

import java.util.List;
import com.ruoyi.teacher.domain.TeacherReview;

/**
 * 教师批阅Mapper接口
 * 
 * @author lihong
 * @date 2023-11-10
 */
public interface TeacherReviewMapper 
{
    /**
     * 查询教师批阅
     * 
     * @param id 教师批阅主键
     * @return 教师批阅
     */
    public TeacherReview selectTeacherReviewById(Long id);

    /**
     * 查询教师批阅列表
     * 
     * @param teacherReview 教师批阅
     * @return 教师批阅集合
     */
    public List<TeacherReview> selectTeacherReviewList(TeacherReview teacherReview);

    /**
     * 新增教师批阅
     * 
     * @param teacherReview 教师批阅
     * @return 结果
     */
    public int insertTeacherReview(TeacherReview teacherReview);

    /**
     * 修改教师批阅
     * 
     * @param teacherReview 教师批阅
     * @return 结果
     */
    public int updateTeacherReview(TeacherReview teacherReview);

    /**
     * 删除教师批阅
     * 
     * @param id 教师批阅主键
     * @return 结果
     */
    public int deleteTeacherReviewById(Long id);

    /**
     * 批量删除教师批阅
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTeacherReviewByIds(Long[] ids);
}
