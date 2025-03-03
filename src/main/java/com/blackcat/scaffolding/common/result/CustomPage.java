package com.blackcat.scaffolding.common.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自定义查询分页返回内容
 * @author : zhangdahui  2024/8/26 上午10:36
 */
@Data
@NoArgsConstructor
public class CustomPage {

    /** 当前页数 */
    private int pageNow;
    /** 每页显示记录的条数 */
    private int pageSize;
    /** 总的记录条数 */
    private int totalCount;
    /** 本页的数据列表 */
    private List<Object> dataList;
    /** 总的页数 */
    private int totalPageCount;

    /**
     * 快速创建对象
     * @param dataList 数据
     * @param totalCount 总记录数
     * @param pageNow 当前页
     * @param pageSize 每页显示记录的条数
     */
    public CustomPage(List<Object> dataList,int totalCount, int pageNow, int pageSize) {
        this.dataList = dataList;
        this.totalCount = totalCount;
        this.pageNow = pageNow;
        this.pageSize = pageSize;
        // 取得总页数，总页数=总记录数/每页显示记录的条数
        if(totalCount!=0){
            int totalPage = totalCount / pageSize;
            this.totalPageCount = (totalCount % pageSize == 0) ? totalPage : totalPage + 1;
        }
    }

    /**
     * 将mybatis plus分页对象转换成系统分页对象
     * @param mybatisPage mybatis plus分页对象
     */
    public CustomPage(Page mybatisPage){
        this.totalCount = (int) mybatisPage.getTotal();
        this.pageNow = (int) mybatisPage.getCurrent();
        this.pageSize = (int) mybatisPage.getSize();
        this.dataList = mybatisPage.getRecords();
        this.totalPageCount = (int) mybatisPage.getPages();
    }

    /**
     * 快速创建分页对象
     * @param mybatisPage  mybatis plus分页对象
     */
    public static CustomPage mybatisPage(Page mybatisPage){
        return new CustomPage(mybatisPage);
    }
}
