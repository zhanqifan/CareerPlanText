package com.ruoyi.student.domain;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 岗位管理对象 target_position
 * 
 * @author lh
 * @date 2023-11-07
 */
public class TargetPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 岗位id */
    private String positionId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String positionName;

    /** 目标状态(0:已发布，1:已废止，2:草稿) */
    @Excel(name = "目标状态(0:已发布，1:已废止，2:草稿)")
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



    public void setPositionId(String positionId)
    {
        this.positionId = positionId;
    }

    public String getPositionId()
    {
        return positionId;
    }
    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getPositionName()
    {
        return positionName;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }
    public void setIsMain(Integer isMain) 
    {
        this.isMain = isMain;
    }

    public Integer getIsMain() 
    {
        return isMain;
    }
    public void setModificationsNumber(Integer modificationsNumber) 
    {
        this.modificationsNumber = modificationsNumber;
    }

    public Integer getModificationsNumber() 
    {
        return modificationsNumber;
    }
    public void setReviewsNumber(Integer reviewsNumber) 
    {
        this.reviewsNumber = reviewsNumber;
    }

    public Integer getReviewsNumber() 
    {
        return reviewsNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("positionId", getPositionId())
            .append("positionName", getPositionName())
            .append("state", getState())
            .append("isMain", getIsMain())
            .append("modificationsNumber", getModificationsNumber())
            .append("reviewsNumber", getReviewsNumber())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
