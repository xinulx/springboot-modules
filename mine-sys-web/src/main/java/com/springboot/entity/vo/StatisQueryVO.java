package com.springboot.entity.vo;

public class StatisQueryVO {

    private String type;//查询类型

    private String name;// 数据项导入名称

    private Integer seriesCount;// 系列数，标识月、季度、年的统计数，如6表示统计前6个月或6个季度或6年的数据

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(Integer seriesCount) {
        this.seriesCount = seriesCount;
    }
}
