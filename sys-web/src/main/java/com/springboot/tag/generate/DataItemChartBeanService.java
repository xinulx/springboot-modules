package com.springboot.tag.generate;

import com.alibaba.fastjson.JSONObject;
import com.springboot.dao.business.IReleaseDataDao;
import com.springboot.tag.common.AbstractLabelService;
import com.springboot.vo.StatisQueryVO;
import com.springboot.vo.StatisResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取数据项图标数据查询<br/>
 * 按导入数据项名称查找最新、进度、年度数据
 *
 * 最新:根据数据项导入名称获取最近6个月的数据，有无不拘
 * 进度:根据当前月份匹配【3.6.9.12】获取最近6季度的数据
 * 年度:根据当前年份获取最近6年的数据
 * @author wangshibao <br/>
 * @version v1.0 <br/>
 * @date 2018-11-06<br/>
 */
@Component
public class DataItemChartBeanService extends AbstractLabelService {

    @Autowired
    private IReleaseDataDao releaseDataDao;

    @Override
    public Object getObject(JSONObject paramObj) {
        // 0-最新 1-进度 2-年度
        String type = paramObj.getString("type");
        String name = paramObj.getString("name");//图例名称
        String importName = paramObj.getString("importName");
        Integer seriesCount = paramObj.getInteger("seriesCount");

        StatisQueryVO vo = new StatisQueryVO();
        vo.setType(type);
        vo.setName(importName);
        vo.setSeriesCount(seriesCount);
        List<StatisResultVO> resultVOS = releaseDataDao.getChartData(vo);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("legendName",name);
        List xData = new ArrayList();//日期
        List yValue = new ArrayList();//绝对量
        List ySpeed = new ArrayList();// 增速
        for(StatisResultVO resultVO:resultVOS){
            xData.add(resultVO.getDate());
            yValue.add(resultVO.getValue());
            ySpeed.add(resultVO.getSpeed());
        }
        map.put("type",type);
        map.put("xData",xData);
        map.put("yValue",yValue);
        map.put("ySpeed",ySpeed);
        return map;
    }
}
