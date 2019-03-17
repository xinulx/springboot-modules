package com.springboot.service.hibernate.impl;

import com.springboot.dao.business.ITplHistoryDao;
import com.springboot.entity.business.ParamDto;
import com.springboot.entity.business.TemplateHistoryEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.mongo.ContentMongoEO;
import com.springboot.service.common.impl.BaseService;
import com.springboot.service.hibernate.ITplConfService;
import com.springboot.service.hibernate.ITplHistoryService;
import com.springboot.service.mongodb.impl.MongoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service("tplHistoryService")
public class TplHistoryService extends BaseService<TemplateHistoryEO> implements ITplHistoryService {

    @Autowired
    private ITplHistoryDao tplHistoryDao;

    @Autowired
    private ITplConfService tplConfService;

    @Autowired
    private MongoServiceImpl mongoService;

    @Override
    public Object getEOList() {
        return tplHistoryDao.getEOList();
    }

    @Override
    public Object getEOById(Long id) {
        return tplHistoryDao.getEOById(id);
    }

    @Override
    public Object getEOByTplId(ParamDto paramDto) {
        Pagination page = tplHistoryDao.getPageEOs(paramDto);
        return page;
    }

    @Override
    public Object addEO(TemplateHistoryEO eo) {
        return tplHistoryDao.addEO(eo);
    }

    @Override
    public Object delEO(Long id) {
        return tplHistoryDao.delEO(id);
    }

    @Override
    public TemplateHistoryEO saveTplContent(ContentMongoEO eo) {
        TemplateHistoryEO thEO = new TemplateHistoryEO();
        thEO.setTempId(eo.getId());
        thEO.setTempContent(eo.getContent());
        thEO.setTypeCode(eo.getType());
        this.saveEntity(thEO);
        mongoService.save(eo);
        return thEO;
    }

    @Override
    public Long getLastVersion(Long tempId) {
        return tplHistoryDao.getLastVersion(tempId);
    }
}
