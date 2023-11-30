package com.ruoyi.common.utils;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.sql.SqlUtil;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 分页工具类
 * 
 * @author ruoyi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtils extends PageHelper
{

    private List<T> data;        // 查询结果数据
    private int currentPage;      // 当前页码
    private int pageSize;         // 每页显示的记录数
    private int totalCount;

    public int getTotalPages() {
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    public int getStartIndex() {
        return (currentPage - 1) * pageSize;
    }

    public int getEndIndex() {
        return Math.min(currentPage * pageSize, totalCount);
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public boolean hasNextPage() {
        return currentPage < getTotalPages();
    }


    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }



    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }


}
