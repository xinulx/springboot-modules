package com.springboot.service.hibernate.impl;

import com.springboot.common.util.AppUtil;
import com.springboot.common.util.DocFileManage;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.dao.business.ITplConfDao;
import com.springboot.entity.hibernate.ParamDto;
import com.springboot.entity.business.TemplateConfEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.impl.BaseService;
import com.springboot.service.hibernate.ITplConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("tplConfService")
public class TplConfService extends BaseService<TemplateConfEO> implements ITplConfService {

    @Autowired
    private ITplConfDao tplConfDao;

    @Override
    public Pagination getPageEOList(ParamDto paramDto) {
        return tplConfDao.getPageEOList(paramDto);
    }

    @Override
    public Object getEOList(Long siteId, String type) {
        return tplConfDao.getEOList(siteId, type);
    }

    @Override
    public Object getByTemptype(Long siteId, String tempType) {
        return tplConfDao.getByTemptype(siteId, tempType);
    }

    @Override
    public List<TemplateConfEO> getSpecialById(Long siteId, Long specialId, String tempType) {
        return tplConfDao.getSpecialById(siteId, specialId, tempType);
    }

    @Override
    public Object getVrtpls() {
        return tplConfDao.getVrtpls();
    }

    @Override
    public Object getEOById(Long id) {
        return tplConfDao.getEOById(id);
    }

    @Override
    public List<TemplateConfEO> saveEO(TemplateConfEO eo) {
        List<TemplateConfEO> list = new ArrayList<TemplateConfEO>();
        Long siteId = LoginPersonUtil.getSiteId();
        if (!AppUtil.isEmpty(eo.getSiteId())) {
            siteId = eo.getSiteId();
        }
        eo.setSiteId(siteId);
        this.saveEntity(eo);
        list.add(eo);
        return list;
    }

    @Override
    public Object addEO(TemplateConfEO eo) {
        return tplConfDao.addEO(eo);
    }

    @Override
    public Object delEO(Long id) {
        return tplConfDao.delEO(id);
    }

    @Override
    public Object editEO(TemplateConfEO eo) {
        return tplConfDao.editEO(eo);
    }

    public String readFile(String file) {
        String msg = null;
        try {
            msg = DocFileManage.readFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public void updateTempFile(String path, Long id) {
        tplConfDao.updateTempFile(path, id);
    }

    @Override
    public Object getList(String stationId, String tempType) {
        return tplConfDao.getList(stationId, tempType);
    }


}
