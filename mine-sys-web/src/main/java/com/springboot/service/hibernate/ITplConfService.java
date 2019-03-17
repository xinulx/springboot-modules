package com.springboot.service.hibernate;

import com.springboot.entity.business.ParamDto;
import com.springboot.entity.business.TemplateConfEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.IBaseService;

import java.util.List;

public interface ITplConfService extends IBaseService<TemplateConfEO> {

    public Pagination getPageEOList(ParamDto paramDto);

    public Object getEOList(Long siteId, String type);

    public Object getByTemptype(Long siteId, String tempType);

    public List<TemplateConfEO> getSpecialById(Long siteId, Long specialId, String tempType);

    /**
     * 获取公共模板
     *
     * @return
     */
    public Object getVrtpls();

    public Object getEOById(Long id);

    public List<TemplateConfEO> saveEO(TemplateConfEO eo);

    public Object addEO(TemplateConfEO eo);

    public Object delEO(Long id);

    public Object editEO(TemplateConfEO eo);

    public String readFile(String file);

    public void updateTempFile(String path, Long id);

    public Object getList(String stationId, String tempType);
}
