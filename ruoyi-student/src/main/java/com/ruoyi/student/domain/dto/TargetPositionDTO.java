package com.ruoyi.student.domain.dto;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.student.domain.InternshipContent;
import com.ruoyi.student.domain.SkillsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetPositionDTO{


    /**一级目录*/
    private Long firstId;

    /** 二级目录id */
    private Long positionId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String positionName;

    /**岗位状态*/
    private Integer state;

    /**技能详情列表*/
    private List<SkillsInfo> skillsInfoList;



}
