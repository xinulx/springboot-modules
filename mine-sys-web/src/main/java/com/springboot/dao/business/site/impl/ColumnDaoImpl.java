package com.springboot.dao.business.site.impl;

import com.springboot.dao.business.site.IColumnDao;
import com.springboot.dao.hibernate.impl.MockDao;
import com.springboot.entity.business.ColumnEO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("columnDao")
public class ColumnDaoImpl extends MockDao<ColumnEO> implements IColumnDao {
    @Override
    public List<ColumnEO> getAllColumns() {
        StringBuilder hql = new StringBuilder(" from ColumnEO c");
        hql.append(" where c.recordStatus = ?");
        hql.append(" order by c.num");
        return getEntities(hql.toString(),new Object[]{ColumnEO.RecordStatus.Normal.name()},10000);
    }
}
