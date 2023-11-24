package com.ruoyi.student.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 技能详情对象 skills_info
 * 
 * @author lihong
 * @date 2023-11-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /**行号*/
    private Integer lineNumber;

    /**父目录id*/
    @Excel(name = "一级目录id")
    private Long firstId;

    /** 二级目录id */
    @Excel(name = "二级目录id")
    private Long ctatlogueId;

    /** 技能名称 */
    @Excel(name = "技能名称")
    private String skillsName;

    /**担任角色*/
    @Excel(name = "担任角色")
    private String takeRole;

    /** 岗位id */
    @Excel(name = "岗位id")
    private String targetPositionId;


    /** 修改次数 */
    @Excel(name = "修改次数")
    private Integer modificationsNumber;

    /**自评状态*/
    @Excel(name = "自评状态")
    private Integer evaluateState;

    /**完成状态*/
    @Excel(name = "完成状态")
    private Integer completionStatus;


    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;


    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**实习/实践内容*/
    private String content;




}
