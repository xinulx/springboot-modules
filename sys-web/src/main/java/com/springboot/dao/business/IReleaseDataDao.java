package com.springboot.dao.business;

import com.springboot.dao.hibernate.IBaseDao;
import com.springboot.entity.business.ReleaseDataEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ReleaseDataQueryVO;
import com.springboot.entity.vo.StatisQueryVO;
import com.springboot.entity.vo.StatisResultVO;

import java.util.List;
import java.util.Map;

public interface IReleaseDataDao extends IBaseDao<ReleaseDataEO> {

    Pagination getPage(ReleaseDataQueryVO vo);

    Long getHasDataCount(Map<String, Object> map);

    List<StatisResultVO> getChartData(StatisQueryVO vo);
}
