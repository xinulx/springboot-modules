package com.springboot.tag.label.internal.dao;


import com.springboot.dao.hibernate.IMockDao;
import com.springboot.tag.entity.LabelEO;

import java.util.List;

public interface ILabelDao extends IMockDao<LabelEO> {

  public List<LabelEO> getTree(Long pid);

  public LabelEO getById(Long id);

  public List<LabelEO> getByName(String name);

  public List<LabelEO> getByName(String name, Long pid);

  public LabelEO getOneByName(String name, Long id);

  public Object saveLabel(LabelEO eo);

  public Object updateLabel(LabelEO eo);

  public Object delLabel(Long id);

  public Object updateLabelConfig(Long id, String config);

  public Long childCount(Long pid);

  public Object updateLabel(Long isParent, Long id);
}
