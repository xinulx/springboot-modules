package com.springboot.entity.vo;


public class ReleaseDataQueryVO extends PageQueryVO {

    private String name;//指标名称

    public Integer getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Integer periodDate) {
        this.periodDate = periodDate;
    }

    private Integer periodDate;//发布年月

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
