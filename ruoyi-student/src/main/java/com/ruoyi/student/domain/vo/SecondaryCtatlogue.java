package com.ruoyi.student.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.student.domain.SkillsInfo;
import com.ruoyi.student.service.impl.SkillsInfoServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondaryCtatlogue {

    /** 目录id */
    private Long catalogueId;

    /** 目录名称 */
    @Excel(name = "目录名称")
    private String catalogueName;

    private List<SkillsInfo> skillsInfoList;

    public void addSkillsInfo(SkillsInfo skillsInfo){
        this.skillsInfoList.add(skillsInfo);
    }

}
