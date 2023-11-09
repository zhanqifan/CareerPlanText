package com.ruoyi.student.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.student.domain.Ctatlogue;
import com.ruoyi.student.domain.SkillsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetPositionInfoVO {
    /** 岗位id */
    private String positionId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String positionName;

    /** 目标状态(0:已发布，1:已废止，2:草稿) */
    @Excel(name = "目标状态(0:已发布，1:已废止，2:草稿)")
    private Integer state;


    /**二级目录*/
    private List<SecondaryCtatlogue> ctatlogueList;

}
