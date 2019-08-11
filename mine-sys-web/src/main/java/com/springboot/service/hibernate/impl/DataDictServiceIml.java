package com.springboot.service.hibernate.impl;

import com.springboot.common.business.CommonDictCache;
import com.springboot.common.util.SysLog;
import com.springboot.dao.business.IDataDictDao;
import com.springboot.entity.business.CmsLogEO;
import com.springboot.entity.business.DataDictEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.impl.BaseService;
import com.springboot.service.hibernate.IDataDictItemService;
import com.springboot.service.hibernate.IDataDictService;
import com.springboot.entity.vo.DataDictPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dataDictService")
public class DataDictServiceIml extends BaseService<DataDictEO> implements
        IDataDictService {

    @Autowired
    private IDataDictDao dataDictDao;

    @Autowired
    private IDataDictItemService dataDictItemService;

    @Override
    public Pagination getPage(DataDictPageVO pageVO) {
        return dataDictDao.getPage(pageVO);
    }

    @Override
    public DataDictEO getDataDictByCode(String code) {
        return dataDictDao.getDataDictByCode(code);
    }

    @Override
    public void deleteDict(Long dictId) {
        dataDictDao.delete(DataDictEO.class, dictId);
        dataDictItemService.deleteItemByDictId(dictId);
        SysLog.log("删除数据字典 >> ID：" + dictId, "DataDictEO", CmsLogEO.Operation.Delete.toString());
        CommonDictCache.refresh();
    }

    @Override
    public void markUsed(String code, Integer isUsed) {
        dataDictDao.markUsed(code, isUsed);
    }

    @Override
    public void changeUsed(Long id) {
        DataDictEO _eo = getEntity(DataDictEO.class, id);
        if (_eo.getIsUsed() == 1) {
            dataDictDao.changeUsed(id, 0);
        } else {
            dataDictDao.changeUsed(id, 1);
        }
    }

}
