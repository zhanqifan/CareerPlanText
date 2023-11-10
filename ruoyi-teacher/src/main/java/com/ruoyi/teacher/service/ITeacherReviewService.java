package com.ruoyi.teacher.service;

import java.util.List;
import com.ruoyi.teacher.domain.TeacherReview;
import com.ruoyi.teacher.domain.dto.ReviewDTO;

/**
 * 教师批阅Service接口
 * 
 * @author lihong
 * @date 2023-11-10
 */
public interface ITeacherReviewService 
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
    public int insertTeacherReview(ReviewDTO teacherReview);

    /**
     * 修改教师批阅
     * 
     * @param teacherReview 教师批阅
     * @return 结果
     */
    public int updateTeacherReview(TeacherReview teacherReview);

    /**
     * 批量删除教师批阅
     * 
     * @param ids 需要删除的教师批阅主键集合
     * @return 结果
     */
    public int deleteTeacherReviewByIds(Long[] ids);

    /**
     * 删除教师批阅信息
     * 
     * @param id 教师批阅主键
     * @return 结果
     */
    public int deleteTeacherReviewById(Long id);
}
