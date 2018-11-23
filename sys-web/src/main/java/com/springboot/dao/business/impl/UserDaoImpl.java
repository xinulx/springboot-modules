package com.springboot.dao.business.impl;

import com.springboot.dao.business.IUserDao;
import com.springboot.dao.hibernate.impl.MockDao;
import com.springboot.entity.business.HbUserEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.vo.QueryRoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("userDao")
public class UserDaoImpl extends MockDao<HbUserEO> implements IUserDao {

    @Override
    public Pagination getPage(QueryRoleVO queryRoleVO) {
        StringBuffer hql = new StringBuffer("from HbUserEO where recordStatus =:recordStatus");
        Map<String,Object> map = new HashMap<>();
        map.put("recordStatus",HbUserEO.RecordStatus.Normal.name());
        if(!StringUtils.isEmpty(queryRoleVO.getSearchKey())){
            hql.append(" and userId=:userId");
            map.put("userId",queryRoleVO.getSearchKey());
            hql.append(" or userName like concat(concat('%',:userName),'%')");
            map.put("userName",queryRoleVO.getSearchKey());
        }
        if(queryRoleVO.getOrganId() != null){
            hql.append(" and organId=:organId");
            map.put("organId",queryRoleVO.getOrganId());
        }
        hql.append(" order by createDate desc");
        return getPagination(queryRoleVO.getPageIndex(),queryRoleVO.getPageSize(),hql.toString(),map);
    }
}
