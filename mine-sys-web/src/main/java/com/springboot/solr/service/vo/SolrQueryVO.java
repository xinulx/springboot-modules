package com.springboot.solr.service.vo;

public class SolrQueryVO {
    /**
     * 查询字符串
     */
    private String query;

    /**
     * 分页页码
     */
    private Integer pageIndex = 0;

    /**
     * 分页大小
     */
    private Long pageSize = 10L;

    /**
     * 排序方式
     */
    private String sort;

    /**
     * 返回的字段列表
     */
    private String fieldList;

    /**
     * 过滤条件
     */
    private String filterQuery;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getFieldList() {
        return fieldList;
    }

    public void setFieldList(String fieldList) {
        this.fieldList = fieldList;
    }

    public String getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }
}
