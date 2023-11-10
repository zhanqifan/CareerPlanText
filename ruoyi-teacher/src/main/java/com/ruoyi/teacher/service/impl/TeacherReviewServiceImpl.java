package com.ruoyi.teacher.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.teacher.domain.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.teacher.mapper.TeacherReviewMapper;
import com.ruoyi.teacher.domain.TeacherReview;
import com.ruoyi.teacher.service.ITeacherReviewService;

/**
 * 教师批阅Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-10
 */
@Service
public class TeacherReviewServiceImpl implements ITeacherReviewService 
{
    @Autowired
    private TeacherReviewMapper teacherReviewMapper;

    /**
     * 查询教师批阅
     * 
     * @param id 教师批阅主键
     * @return 教师批阅
     */
    @Override
    public TeacherReview selectTeacherReviewById(Long id)
    {
        return teacherReviewMapper.selectTeacherReviewById(id);
    }

    /**
     * 查询教师批阅列表
     * 
     * @param teacherReview 教师批阅
     * @return 教师批阅
     */
    @Override
    public List<TeacherReview> selectTeacherReviewList(TeacherReview teacherReview)
    {
        return teacherReviewMapper.selectTeacherReviewList(teacherReview);
    }

    /**
     * 新增教师批阅
     * 
     * @param reviewDTO 教师批阅
     * @return 结果
     */
    @Override
    public int insertTeacherReview(ReviewDTO reviewDTO)
    {
        TeacherReview teacherReview = new TeacherReview();
        teacherReview.setContent(reviewDTO.getContent());
        teacherReview.setPositionId(reviewDTO.getPositionId());
        teacherReview.setCreateTime(DateUtils.getNowDate());
        teacherReview.setCreateBy(SecurityUtils.getUserId().toString());
        return teacherReviewMapper.insertTeacherReview(teacherReview);
    }

    /**
     * 修改教师批阅
     * 
     * @param teacherReview 教师批阅
     * @return 结果
     */
    @Override
    public int updateTeacherReview(TeacherReview teacherReview)
    {
        teacherReview.setUpdateTime(DateUtils.getNowDate());
        return teacherReviewMapper.updateTeacherReview(teacherReview);
    }

    /**
     * 批量删除教师批阅
     * 
     * @param ids 需要删除的教师批阅主键
     * @return 结果
     */
    @Override
    public int deleteTeacherReviewByIds(Long[] ids)
    {
        return teacherReviewMapper.deleteTeacherReviewByIds(ids);
    }

    /**
     * 删除教师批阅信息
     * 
     * @param id 教师批阅主键
     * @return 结果
     */
    @Override
    public int deleteTeacherReviewById(Long id)
    {
        return teacherReviewMapper.deleteTeacherReviewById(id);
    }
}
