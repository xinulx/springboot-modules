package com.springboot.service.hibernate.site;

import com.springboot.entity.business.ColumnEO;
import com.springboot.service.common.IMockService;

import java.util.List;

public interface IColumnService extends IMockService<ColumnEO> {

    List<ColumnEO> getAllColumns();
}
