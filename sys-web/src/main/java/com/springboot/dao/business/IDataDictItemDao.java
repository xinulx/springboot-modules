package com.springboot.dao.business;


import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.datadictionary.DataDictItemEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.vo.DataSortVO;

import java.util.List;

public interface IDataDictItemDao extends IBaseDao<DataDictItemEO> {

    /**
     * @param DictId
     * @return
     * @Description 根据id获取字典项集合
     */
    public List<DataDictItemEO> getListByDictId(Long DictId);

    /**
     * @param code
     * @return
     * @Description 根据编码code获取字典项集合
     */
    public DataDictItemEO getDataDictItemByCode(String code, Long dictId);

    /**
     * @param pageIndex
     * @param pageSize
     * @param dictId
     * @param name
     * @return
     * @Description 根据字典ID获取字典项分页
     */
    public Pagination getPageByDictId(Long pageIndex, Integer pageSize, Long dictId, String name);

    /**
     * @param dictId
     * @Description 根据dictId删除字典项
     */
    public void deleteItemByDictId(Long dictId);

    /**
     * @param dictId
     * @return
     * @Description 根据dictid获取该项最大排序
     */
    public DataSortVO getMaxItem(Long dictId);

    /**
     * @param dictId
     * @param itemId
     * @param flag
     * @Description 修改默认值
     */
    public void updateDefault(Long dictId, Long itemId, Integer flag, Long siteId);

    /**
     * @param itemId
     * @param flag
     * @Description 根据itemId修改默认值
     */
    public void updateDefault(Long itemId, Integer flag);

    /**
     * 修改隐藏状态
     *
     * @param itemId
     */
    public void updateHide(Long itemId, Integer flag);

    public List<DataDictItemEO> getItemList(Long dictId);
}
