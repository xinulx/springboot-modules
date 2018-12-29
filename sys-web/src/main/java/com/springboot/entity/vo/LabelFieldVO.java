package com.springboot.entity.vo;

public class LabelFieldVO {

  private String fieldname;

  private String datatype;

  private String allowval;

  private String defaultval;

  private String description;

  public String getFieldname() {
    return fieldname;
  }

  public void setFieldname(String fieldname) {
    this.fieldname = fieldname;
  }

  public String getDatatype() {
    return datatype;
  }

  public void setDatatype(String datetype) {
    this.datatype = datetype;
  }

  public String getAllowval() {
    return allowval;
  }

  public void setAllowval(String allowval) {
    this.allowval = allowval;
  }

  public String getDefaultval() {
    return defaultval;
  }

  public void setDefaultval(String defaultval) {
    this.defaultval = defaultval;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
