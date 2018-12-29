package com.springboot.dao.business.impl;

import com.springboot.common.busi.BaseRunTimeException;
import com.springboot.dao.business.IReleaseDataDao;
import com.springboot.dao.hibernate.impl.BaseDao;
import com.springboot.entity.business.ReleaseDataEO;
import com.springboot.entity.hibernate.Pagination;
import com.springboot.entity.vo.ReleaseDataQueryVO;
import com.springboot.entity.vo.StatisQueryVO;
import com.springboot.entity.vo.StatisResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("ReleaseDataDao")
public class ReleaseDataDaoImpl extends BaseDao<ReleaseDataEO> implements IReleaseDataDao {
    @Override
    public Pagination getPage(ReleaseDataQueryVO vo) {
        List values = new ArrayList();
        StringBuffer hql = new StringBuffer(" from ReleaseDataEO where 1=1 ");
        if (StringUtils.isNotEmpty(vo.getName())) {
            hql.append(" and itemName like ?");
            values.add("%".concat(vo.getName()).concat("%"));
        }
        if (vo.getPeriodDate() != null) {
            hql.append(" and periodDate = ?");
            values.add(vo.getPeriodDate());
        }
        if (StringUtils.isNotEmpty(vo.getSortField())) {
            hql.append(" order by " + vo.getSortField() + " " + vo.getSortOrder());
        } else {
            hql.append(" order by createDate desc");
        }
        return getPagination(vo.getPageIndex(), vo.getPageSize(), hql.toString(), values.toArray());
    }

    @Override
    public Long getHasDataCount(Map<String, Object> map) {
        Calendar c = Calendar.getInstance();
        map.put("endDate", c.getTime());
        c.add(Calendar.MONTH, -6);
        map.put("startDate", c.getTime());
        StringBuffer hql = new StringBuffer("from ReleaseDataEO where itemName =:itemImportName");
        hql.append(" and updateDate >:startDate");
        hql.append(" and updateDate <:endDate");
        Long count = getCount(hql.toString(), map);
        return count == null ? 0 : count;
    }

    @Override
    public List<StatisResultVO> getChartData(StatisQueryVO vo) {
        // 参数列表
        List<StatisResultVO> resultData = new ArrayList<StatisResultVO>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemName", vo.getName());
        String type = vo.getType();
        Integer seriesCount = vo.getSeriesCount();
        if ("0".equals(type)) {
            // 最新
            getLastChartData(resultData, map, seriesCount);
        } else if ("1".equals(type)) {
            // 进度
            getProgressData(resultData, map, seriesCount);
        } else if ("2".equals(type)) {
            // 年度
            getYearData(resultData, map, seriesCount);
        } else {
            throw new BaseRunTimeException("查询类型[type]错误，参考值0-最新数据 1-进度数据 2-年度数据");
        }
        // 因为数据查询日期都是从大到小，前台是从小到大（ps：js的list.reverse()方法也可以）
        Collections.reverse(resultData);
        return resultData;
    }

    private void getYearData(List<StatisResultVO> resultData, Map<String, Object> map, Integer seriesCount) {
        StringBuffer hql = new StringBuffer("select sum(unitValue) as value,sum(growth) as speed,substring(periodDate,1,4) as date from ReleaseDataEO where itemName=:itemName");
        hql.append(" and substring(periodDate,1,4) =:periodDate");
        hql.append(" group by periodDate order by periodDate");
        Calendar calendar = Calendar.getInstance();
        String dateStr = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
        Integer year = Integer.parseInt(dateStr.substring(0, 4));
        for (int i = 0; i < seriesCount; i++) {
            year--;
            // substring后左边的值是字符串所以此参数要传字符串类型
            map.put("periodDate", year.toString());
            List<StatisResultVO> resultVOS = (List<StatisResultVO>) getBeansByHql(hql.toString(), map, StatisResultVO.class, 1);
            if (resultVOS == null || resultVOS.size() == 0) {
                StatisResultVO statisResultVO = new StatisResultVO();
                statisResultVO.setDate(year.toString());
                statisResultVO.setSpeed("0");
                statisResultVO.setValue("0");
                resultData.add(statisResultVO);
            } else {
                resultData.add(resultVOS.get(0));
            }
        }
    }

    private void getProgressData(List<StatisResultVO> resultData, Map<String, Object> map, Integer seriesCount) {
        StringBuffer hql = new StringBuffer("select sum(unitValue) as value,sum(growth) as speed,concat(periodDate,'') as date from ReleaseDataEO where itemName=:itemName");
        hql.append(" and periodDate =:periodDate");
        hql.append(" group by periodDate order by periodDate");
        Calendar calendar = Calendar.getInstance();
        String dateStr = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
        Integer month = Integer.parseInt(dateStr.substring(4, 6));
        // 取进度日期，日期为3.6.9.12月
        if (month % 3 == 0) {
            calendar.add(Calendar.MONTH, 0);
        } else if (month % 3 == 2) {
            calendar.add(Calendar.MONTH, -2);
        } else if (month % 3 == 1) {
            calendar.add(Calendar.MONTH, -1);
        }
        for (int i = 0; i < seriesCount; i++) {
            if (i == 0) {
                calendar.add(Calendar.MONTH, -3 * i);
            } else {
                calendar.add(Calendar.MONTH, -3);
            }
            dateStr = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
            map.put("periodDate", Integer.parseInt(dateStr));
            List<StatisResultVO> resultVOS = (List<StatisResultVO>) getBeansByHql(hql.toString(), map, StatisResultVO.class, 1);
            if (resultVOS == null || resultVOS.size() == 0) {
                StatisResultVO statisResultVO = new StatisResultVO();
                statisResultVO.setDate(dateStr);
                statisResultVO.setSpeed("0");
                statisResultVO.setValue("0");
                resultData.add(statisResultVO);
            } else {
                resultData.addAll(resultVOS);
            }
        }
    }

    private void getLastChartData(List<StatisResultVO> resultData, Map<String, Object> map, Integer seriesCount) {
        StringBuffer hql = new StringBuffer("select sum(unitValue) as value,sum(growth) as speed,concat(periodDate,'') as date from ReleaseDataEO where itemName=:itemName");
        hql.append(" and updateDate >:startDate");
        hql.append(" and updateDate <:endDate");
        hql.append(" and periodDate =:periodDate");
        hql.append(" group by periodDate order by periodDate");
        Calendar c = Calendar.getInstance();
        map.put("endDate", c.getTime());
        c.add(Calendar.MONTH, -6);
        map.put("startDate", c.getTime());
        for (int i = 0; i < seriesCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -i);
            String dateStr = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
            map.put("periodDate", Integer.parseInt(dateStr));
            List<StatisResultVO> resultVOS = (List<StatisResultVO>) getBeansByHql(hql.toString(), map, StatisResultVO.class, 1);
            if (resultVOS == null || resultVOS.size() == 0) {
                StatisResultVO statisResultVO = new StatisResultVO();
                statisResultVO.setDate(dateStr);
                statisResultVO.setSpeed("0");
                statisResultVO.setValue("0");
                resultData.add(statisResultVO);
            } else {
                resultData.addAll(resultVOS);
            }
        }
    }
}
