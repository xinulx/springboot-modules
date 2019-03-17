package com.springboot.dao.business.impl;

import com.springboot.dao.business.IMenuRelDao;
import com.springboot.dao.hibernate.impl.MockDao;
import com.springboot.entity.business.MenuRelEO;
import org.springframework.stereotype.Repository;

@Repository("menuRelDao")
public class MenuRelDaoImpl extends MockDao<MenuRelEO> implements IMenuRelDao {

}
