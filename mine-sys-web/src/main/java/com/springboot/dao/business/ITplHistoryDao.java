package com.springboot.dao.business;


import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.ParamDto;
import com.springboot.entity.business.TemplateHistoryEO;
import com.springboot.entity.hibernate.Pagination;

public interface ITplHistoryDao extends IBaseDao<TemplateHistoryEO> {

    public Pagination getPageEOs(ParamDto paramDto);

    public Object getEOList();

    public Object getEOById(Long id);

    public Object getEOByTplId(Long id);

    public Object addEO(TemplateHistoryEO eo);

    public Object editEO(TemplateHistoryEO eo);

    public Object delEO(Long id);

    public Long getLastVersion(Long tempId);
}
