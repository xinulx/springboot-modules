package com.springboot.service.hibernate.impl;

import com.springboot.common.busi.RightDictCache;
import com.springboot.common.util.AppUtil;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.common.util.SysLog;
import com.springboot.dao.business.IDataDictItemDao;
import com.springboot.entity.business.CmsLogEO;
import com.springboot.entity.business.DataDictEO;
import com.springboot.entity.business.DataDictItemEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.impl.BaseService;
import com.springboot.service.hibernate.IDataDictItemService;
import com.springboot.service.hibernate.IDataDictService;
import com.springboot.entity.vo.DataSortVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dataDictItemService")
public class DataDictItemServiceImpl extends BaseService<DataDictItemEO> implements IDataDictItemService {

    @Autowired
    private IDataDictItemDao dataDictItemDao;
    @Autowired
    private IDataDictService dataDictService;

    @Override
    public List<DataDictItemEO> getListByDictId(Long dictId) {
        return dataDictItemDao.getListByDictId(dictId);
    }

    @Override
    public DataDictItemEO getDataDictItemByCode(String code, Long dictId) {
        return dataDictItemDao.getDataDictItemByCode(code, dictId);
    }

    @Override
    public Pagination getPageByDictId(Long pageIndex, Integer pageSize, Long dictId, String name) {
        return dataDictItemDao.getPageByDictId(pageIndex, pageSize, dictId, name);
    }

    @Override
    public void deleteItemByDictId(Long dictId) {
        dataDictItemDao.deleteItemByDictId(dictId);
        SysLog.log("删除数据字典项 >> ID：" + dictId, "DataDictItemEO", CmsLogEO.Operation.Delete.toString());
        RightDictCache.refresh();
    }

    @Override
    public Integer getMaxItem(Long dictId) {
        DataSortVO vo = dataDictItemDao.getMaxItem(dictId);
        if (AppUtil.isEmpty(vo.getSort())) {
            return 0;
        }
        return vo.getSort();
    }

    @Override
    public void setDefault(Long itemId, Long dictId, Integer flag) {
        Long siteId = LoginPersonUtil.getSiteId();
        if (flag == 1) {
            dataDictItemDao.updateDefault(itemId, flag);
            dataDictItemDao.updateDefault(dictId, itemId, 0, siteId);
        } else {
            dataDictItemDao.updateDefault(itemId, 0);
        }
    }

    @Override
    public void updateHide(Long itemId, Integer flag) {
        dataDictItemDao.updateHide(itemId, flag);
    }


    @Override
    public void updateItem(DataDictItemEO itemEO) {
        Long siteId = LoginPersonUtil.getSiteId();
        if (itemEO.getIsDefault() == 1) {
            dataDictItemDao.updateDefault(itemEO.getDataDicId(), itemEO.getItemId(), 0, siteId);
        }
        updateEntity(itemEO);
        RightDictCache.refresh();
        SysLog.log("修改数据字典项 >> 数据字典：" + itemEO.getDataDicName() + "，字典项：" + itemEO.getName(), "DataDictItemEO", CmsLogEO.Operation.Update.toString());
    }

    @Override
    public Long saveItem(DataDictItemEO itemEO) {
        Long siteId = LoginPersonUtil.getSiteId();
        Long id = saveEntity(itemEO);
        if (itemEO.getIsDefault() == 1) {
            dataDictItemDao.updateDefault(itemEO.getDataDicId(), id, 0, siteId);
        }
        RightDictCache.refresh();
        SysLog.log("保存数据字典项 >> 数据字典：" + itemEO.getDataDicName() + "，字典项：" + itemEO.getName(), "DataDictItemEO", CmsLogEO.Operation.Add.toString());
        return id;

    }

    @Override
    public DataDictItemEO getDataDictItemByCode(String code, String dataCode) {
        DataDictEO dataEO = dataDictService.getDataDictByCode(dataCode);
        return dataDictItemDao.getDataDictItemByCode(code, dataEO.getDictId());
    }

    @Override
    public List<DataDictItemEO> getItemList(Long dictId) {
        return dataDictItemDao.getItemList(dictId);
    }

}
