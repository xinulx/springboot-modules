package com.springboot.dao.business;

import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.DataItemEO;
import com.springboot.entity.vo.DataItemQueryVO;

public interface IDataItemDao extends IBaseDao<DataItemEO> {

    Object getPage(DataItemQueryVO vo);
}
