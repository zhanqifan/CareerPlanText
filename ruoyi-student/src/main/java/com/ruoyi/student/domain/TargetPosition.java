package com.ruoyi.student.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 岗位管理对象 target_position
 * 
 * @author lh
 * @date 2023-11-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 岗位id */
    private String positionId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String positionName;

    /** 目标状态(1:已发布，0:已废止，2:草稿) */
    @Excel(name = "目标状态(1:已发布，0:已废止，2:草稿)")
    private Integer state;

    /** 是否是组目标（0:主目标，1:非主目标） */
    @Excel(name = "是否是主目标", readConverterExp = "0=:主目标，1:非主目标")
    private Integer isMain;

    /** 修改次数 */
    @Excel(name = "修改次数")
    private Integer modificationsNumber;

    /** 批阅次数 */
    @Excel(name = "批阅次数")
    private Integer reviewsNumber;




}
