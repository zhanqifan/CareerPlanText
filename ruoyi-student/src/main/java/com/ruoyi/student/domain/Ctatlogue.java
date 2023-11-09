package com.ruoyi.student.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 技能目录对象
 * 
 * @author lihong
 * @date 2023-11-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ctatlogue
{
    private static final long serialVersionUID = 1L;

    /** 目录id */
    private Long catalogueId;

    /** 目录名称 */
    @Excel(name = "目录名称")
    private String catalogueName;

    /**父节点id*/
    private String parentId;

    private List<Ctatlogue> child;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;



}
