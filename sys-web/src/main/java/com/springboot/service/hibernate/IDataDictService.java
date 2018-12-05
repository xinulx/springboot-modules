package com.springboot.service.hibernate;


import com.springboot.entity.business.datadictionary.DataDictEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.service.common.IBaseService;
import com.springboot.vo.DataDictPageVO;

public interface IDataDictService extends IBaseService<DataDictEO> {

    /**
     * @param pageVO
     * @return
     * @Description 根据条件获取数据字典分页
     */
    public Pagination getPage(DataDictPageVO pageVO);

    /**
     * @param code
     * @return
     * @Description 根据code编码获取数据字典
     */
    public DataDictEO getDataDictByCode(String code);

    /**
     * @param dictId
     * @Description 删除字典及其字典项
     */
    public void deleteDict(Long dictId);

    /**
     * @param code
     * @param isUsed
     * @Description
     * @author Hewbing
     * @date 2015年8月31日 下午3:49:45
     */
    public void markUsed(String code, Integer isUsed);


    public void changeUsed(Long id);

/*	 public void saveDataDict(DataDictEO dataDictEO);
	 
	 public void updataDataDict(DataDictEO dataDictEO);*/
}
