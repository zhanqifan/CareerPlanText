package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.TargetPosition;

/**
 * 岗位管理Mapper接口
 * 
 * @author lh
 * @date 2023-11-07
 */
public interface TargetPositionMapper 
{
    /**
     * 查询岗位管理
     * 
     * @param positionId 岗位管理主键
     * @return 岗位管理
     */
    public TargetPosition selectTargetPositionByPositionId(String positionId);

    /**
     * 查询岗位管理列表
     * 
     * @return 岗位管理集合
     */
    public List<TargetPosition> selectTargetPositionList();
    /**
     * 根据学生id查询岗位列表
     * */
    public List<TargetPosition> selectTargetPositionListByUserId(String userId);


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
     * 删除岗位管理
     * 
     * @param positionId 岗位管理主键
     * @return 结果
     */
    public int deleteTargetPositionByPositionId(Long positionId);

    List<TargetPosition> selectTargetPositionListByUserName(String createBy);

    List<TargetPosition> selectgetAbandonedTargetByStudentId(String studentId);

    TargetPosition selectMainTargetPositionListByUserName(String sNum);
}
