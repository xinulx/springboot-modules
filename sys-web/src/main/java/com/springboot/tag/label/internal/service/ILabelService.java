package com.springboot.tag.label.internal.service;


import com.springboot.service.common.IMockService;
import com.springboot.tag.entity.LabelEO;
import com.springboot.vo.LabelVO;

import java.util.List;

public interface ILabelService extends IMockService<LabelEO> {

  public List<LabelVO> getTree(Long pid);

  public LabelEO getById(Long id);

  public List<LabelVO> getByName(String name);

  public Object saveLabel(LabelEO eo);

  public Object updateLabel(LabelEO eo);

  public Object delLabel(Long id);

  public Object updateLabelConfig(Long id, String config);

  public Object copyLabel(Long id, String labelName, String labelNotes);

  public Long childCount(Long pid);

}
