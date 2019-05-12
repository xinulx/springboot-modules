package com.springboot.dao.business.site;

import com.springboot.dao.hibernate.IMockDao;
import com.springboot.entity.business.ColumnEO;

import java.util.List;

public interface IColumnDao extends IMockDao<ColumnEO> {
    List<ColumnEO> getAllColumns();
}
