package com.ruoyi.common.utils;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 分页工具类
 * 
 * @author ruoyi
 */

@Data
public class PageUtil<T> {
    private List<T> data;        // 查询结果数据
    private int currentPage;      // 当前页码
    private int pageSize;         // 每页显示的记录数
    private int totalCount;       // 总记录数

    public PageUtil(List<T> allData, int currentPage, int pageSize, int totalCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        // 计算当前页面的数据范围
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(currentPage * pageSize, totalCount);

        // 获取当前页面的数据
        this.data = allData.subList(startIndex, endIndex);
    }


    public List<T> getData() {
        return data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

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


}
