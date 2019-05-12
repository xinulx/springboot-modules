package com.springboot.service.hibernate.site.impl;

import com.springboot.dao.business.site.IColumnDao;
import com.springboot.entity.business.ColumnEO;
import com.springboot.service.common.impl.MockService;
import com.springboot.service.hibernate.site.IColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("columnService")
public class ColumnServiceImpl extends MockService<ColumnEO> implements IColumnService {

    @Autowired
    private IColumnDao columnDao;

    @Override
    public List<ColumnEO> getAllColumns() {
        return columnDao.getAllColumns();
    }
}
