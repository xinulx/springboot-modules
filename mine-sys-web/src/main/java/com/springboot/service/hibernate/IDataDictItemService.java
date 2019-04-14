package com.springboot.service.hibernate;


import com.springboot.entity.business.DataDictItemEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.IBaseService;

import java.util.List;

public interface IDataDictItemService extends IBaseService<DataDictItemEO> {

    /**
     * @param dictId
     * @return
     * @Description 根据字典ID获取字典项集合
     */
    public List<DataDictItemEO> getListByDictId(Long dictId);

    /**
     * @param code
     * @return
     * @Description 根据code编码获取字典项集合
     */
    public DataDictItemEO getDataDictItemByCode(String code, Long dictId);

    /**
     * @param @param  code
     * @param @param  dataCode
     * @param @return 设定文件
     * @return DataDictItemEO    返回类型
     * @throws
     * @Title: getDataDictItemByCode
     * @Description: TODO
     */
    public DataDictItemEO getDataDictItemByCode(String code, String dataCode);

    /**
     * @param pageIndex
     * @param pageSize
     * @param dictId
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
    public Integer getMaxItem(Long dictId);

    /**
     * @param itemId
     * @param dictId
     * @param flag
     * @Description 修改默认值
     */
    public void setDefault(Long itemId, Long dictId, Integer flag);

    /**
     * 修改隐藏状态
     */
    public void updateHide(Long itemId, Integer flag);

    /**
     * @param itemEO
     * @Description 保存修改处理
     */
    public void updateItem(DataDictItemEO itemEO);

    /**
     * @param itemEO
     * @Description 保存增加处理
     */
    public Long saveItem(DataDictItemEO itemEO);

    public List<DataDictItemEO> getItemList(Long dictId);
}
