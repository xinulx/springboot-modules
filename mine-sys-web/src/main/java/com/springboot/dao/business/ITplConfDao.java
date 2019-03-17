package com.springboot.dao.business;


import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.ParamDto;
import com.springboot.entity.business.TemplateConfEO;
import com.springboot.entity.hibernate.Pagination;

import java.util.List;

public interface ITplConfDao extends IBaseDao<TemplateConfEO> {

    public Pagination getPageEOList(ParamDto paramDto);

    public Object getEOList(Long siteId, String type);

    public Object getByTemptype(Long siteId, String tempType);

    public List<TemplateConfEO> getSpecialById(Long siteId, Long specialId, String tempType);

    public Object getVrtpls();

    public Object getEOById(Long id);

    public Object addEO(TemplateConfEO eo);

    public Object delEO(Long id);

    public Object editEO(TemplateConfEO eo);

    public void updateTempFile(String path, Long id);

    public Object getList(String stationId, String tempType);
}
