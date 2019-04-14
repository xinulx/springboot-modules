package com.springboot.dao.business.impl;

import com.springboot.common.util.AppUtil;
import com.springboot.dao.business.ITplHistoryDao;
import com.springboot.dao.hibernate.impl.BaseDao;
import com.springboot.entity.hibernate.ParamDto;
import com.springboot.entity.business.TemplateHistoryEO;
import com.springboot.entity.hibernate.Pagination;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tplHistoryDao")
public class TplHistoryDao extends BaseDao<TemplateHistoryEO> implements ITplHistoryDao {

    @Override
    public Pagination getPageEOs(ParamDto paramDto) {
        Long pageIndex = paramDto.getPageIndex();
        Integer pageSize = paramDto.getPageSize();

        return this.getPagination(pageIndex, pageSize, getOrderSql("from TemplateHistoryEO T where T.tempId = ?", paramDto), new Object[]{paramDto.getId()});
    }

    public String getOrderSql(String sql, ParamDto paramDto) {

        StringBuffer sb = new StringBuffer(sql + " order by");
        sb.append(" ");
        sb.append(AppUtil.isEmpty(paramDto.getSortField()) ? "createDate" : paramDto.getSortField());
        sb.append(" ");
        sb.append(AppUtil.isEmpty(paramDto.getSortOrder()) ? "desc" : paramDto.getSortOrder());

        return sb.toString();

    }

    @Override
    public Object getEOList() {
        return this.getEntitiesByHql("from TemplateHistoryEO", new Object[]{});
    }

    @Override
    public Object getEOById(Long id) {
        List<TemplateHistoryEO> list = this.getEntitiesByHql("from TemplateHistoryEO T where T.id = ?", new Object[]{id});
        if (list.size() > 0)
            return list.get(0);
        else
            return new TemplateHistoryEO();
    }

    @Override
    public Object getEOByTplId(Long id) {
        return this.getEntitiesByHql("from TemplateHistoryEO T where T.tempId = ?", new Object[]{id});
    }

    @Override
    public Object addEO(TemplateHistoryEO eo) {
        return this.save(eo);
    }

    @Override
    public Object editEO(TemplateHistoryEO eo) {
        this.update(eo);
        return null;
    }

    @Override
    public Object delEO(Long id) {
        return this.executeUpdateBySql("DELETE FROM CMS_TEMPLATE_HISTORY WHERE ID = ?", new Object[]{id});
    }

    @Override
    public Long getLastVersion(Long tempId) {
        String sql = "SELECT MAX(ID) FROM CMS_TEMPLATE_HISTORY WHERE TEMP_ID = " + tempId;
        SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
        List list = sqlQuery.list();
        return list.get(0) == null ? 0L : Long.parseLong(list.get(0).toString());
    }
}
