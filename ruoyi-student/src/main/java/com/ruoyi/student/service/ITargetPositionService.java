package com.ruoyi.student.service;

import java.util.List;

import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.domain.dto.TargetPositionDTO;
import com.ruoyi.student.domain.vo.TargetPositionInfoVO;

/**
 * 岗位管理Service接口
 * 
 * @author lh
 * @date 2023-11-07
 */
public interface ITargetPositionService 
{
    /**
     * 查询岗位管理
     * 
     * @param positionId 岗位管理主键
     * @return 岗位管理
     */
    public TargetPositionInfoVO selectTargetPositionByPositionId(String positionId);

    /**
     * 新增岗位管理
     * 
     * @param targetPosition 岗位管理
     * @return 结果
     */
    public int insertTargetPosition(TargetPosition targetPosition);

    /**
     * 修改岗位管理
     * 
     * @param targetPosition 岗位管理
     * @return 结果
     */
    public int updateTargetPosition(TargetPosition targetPosition);

    /**
     * 批量删除岗位管理
     * 
     * @param positionIds 需要删除的岗位管理主键集合
     * @return 结果
     */
    public int deleteTargetPositionByPositionIds(Long[] positionIds);

    /**
     * 删除岗位管理信息
     * 
     * @param positionId 岗位管理主键
     * @return 结果
     */
    public int deleteTargetPositionByPositionId(Long positionId);

    int addTargetPosition(TargetPositionDTO targetPositionDTO);

    int updateskillsInfo(SkillsInfo skillsInfo);

    int repealPositionId(String positionId);

    List<TargetPosition> selectTargetPositionListByUserName(String createBy);

    List<TargetPosition> selectgetAbandonedTargetByStudentId(String studentId);

    /**
     * 根据学生id查询岗位列表
     * */
    List<TargetPosition> selectTargetPositionListByUserId(String studentId);

    /**
     * 查询所有岗位
     * */
    List<TargetPosition> selectTargetPositionList(TargetPosition targetPosition);

    TargetPosition selectMainTargetPositionListByUserName(String sNum);
}
