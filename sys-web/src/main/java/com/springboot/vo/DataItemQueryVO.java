package com.springboot.vo;

public class DataItemQueryVO extends PageQueryVO {

    private String name;//数据项或数据项导入名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
