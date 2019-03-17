package com.springboot.service.hibernate;


import com.springboot.entity.business.ParamDto;
import com.springboot.entity.business.TemplateHistoryEO;
import com.springboot.entity.mongo.ContentMongoEO;
import com.springboot.service.common.IBaseService;

public interface ITplHistoryService extends IBaseService<TemplateHistoryEO> {

    public Object getEOList();

    public Object getEOById(Long id);

    public Object getEOByTplId(ParamDto paramDto);

    public Object addEO(TemplateHistoryEO eo);

    public Object delEO(Long id);

    public TemplateHistoryEO saveTplContent(ContentMongoEO eo);

    public Long getLastVersion(Long tempId);

}
