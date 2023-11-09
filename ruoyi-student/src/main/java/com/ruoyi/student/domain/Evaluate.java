package com.ruoyi.student.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 评价对象 evaluate
 * 
 * @author lihong
 * @date 2023-11-08
 */
public class Evaluate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评价id */
    private Integer evaluateId;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String content;

    /** 评价类型（students：学生自评，teacher:教师批阅） */
    @Excel(name = "评价类型", readConverterExp = "s=tudents：学生自评，teacher:教师批阅")
    private String type;

    /** 目标id */
    @Excel(name = "目标id")
    private Integer skillsId;

    public void setEvaluateId(Integer evaluateId) 
    {
        this.evaluateId = evaluateId;
    }

    public Integer getEvaluateId() 
    {
        return evaluateId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setSkillsId(Integer skillsId) 
    {
        this.skillsId = skillsId;
    }

    public Integer getSkillsId() 
    {
        return skillsId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("evaluateId", getEvaluateId())
            .append("content", getContent())
            .append("type", getType())
            .append("skillsId", getSkillsId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
