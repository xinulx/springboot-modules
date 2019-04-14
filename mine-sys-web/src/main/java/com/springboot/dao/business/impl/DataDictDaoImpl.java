package com.springboot.dao.business.impl;

import com.springboot.common.util.AppUtil;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.dao.business.IDataDictDao;
import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.dao.hibernate.impl.BaseDao;
import com.springboot.entity.business.DataDictEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.DataDictPageVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("dataDictDao")
public class DataDictDaoImpl extends BaseDao<DataDictEO> implements
        IBaseDao<DataDictEO>, IDataDictDao {

    @Override
    public Pagination getPage(DataDictPageVO pageVO) {
        StringBuffer hql = new StringBuffer("from DataDictEO where 1=1");
        Map<String, Object> map = new HashMap<String, Object>();
        if (!AppUtil.isEmpty(pageVO.getName())) {
            hql.append(" and name like :name escape'\\'");
            map.put("name", "%".concat(pageVO.getName()).concat("%"));
        }
        if (!AppUtil.isEmpty(pageVO.getCode())) {
            hql.append(" and code like :code escape '\\'");
            map.put("code", "%".concat(pageVO.getCode()).concat("%"));
        }

        if (!LoginPersonUtil.isRoot()) {
            hql.append(" and 1=1");
        } else if (LoginPersonUtil.isSuperAdmin() || LoginPersonUtil.isSiteAdmin()) {
            hql.append(" and isOpen=1");
        } else {
            hql.append(" and 1!=1");
        }
        hql.append(" order by name desc");
        return getPagination(pageVO.getPageIndex(), pageVO.getPageSize(), hql.toString(), map);
    }

    @Override
    public DataDictEO getDataDictByCode(String code) {
        String hql = "from DataDictEO where code=?";
        return getEntityByHql(hql, new Object[]{code});
    }

    @Override
    public void markUsed(String code, Integer isUsed) {
        String hql = "update DataDictEO set isUsed=? where code=?";
        executeUpdateByHql(hql, new Object[]{isUsed, code});
    }

    @Override
    public void changeUsed(Long id, Integer isUsed) {
        String hql = "update DataDictEO set isUsed=? where dictId=?";
        executeUpdateByHql(hql, new Object[]{isUsed, id});
    }

}
