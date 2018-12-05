package com.springboot.dao.business;

import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.datadictionary.DataDictEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.vo.DataDictPageVO;

public interface IDataDictDao extends IBaseDao<DataDictEO> {

    /**
     * @param pageVO
     * @return
     * @Description 按条件获取数据字典分页
     */
    public Pagination getPage(DataDictPageVO pageVO);

    /**
     * @param code
     * @return
     * @Description 根据code编码获取数据字典
     */
    public DataDictEO getDataDictByCode(String code);

    /**
     * @param code
     * @Description 根据code标记字典是否被使用
     */
    public void markUsed(String code, Integer isUsed);

    public void changeUsed(Long id, Integer isUsed);
}
