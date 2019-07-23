package com.springboot.solr.service;

import com.alibaba.fastjson.JSON;
import com.springboot.solr.service.util.SolrUtil;
import com.springboot.solr.service.vo.SolrIndexVO;
import com.springboot.solr.service.vo.SolrQueryVO;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * solr常用操作
 */
public class SolrOprDemo {

    @Test
    public void addDoc() {
        Map item = new HashMap();
        String id = Long.toString(new Date().getTime());
        System.out.println(id);
        item.put("id", id);
        item.put("author", "admin123");
        item.put("title", "热烈欢迎习主席视察我市");
        item.put("createDate", new Date());
        item.put("columnId", 19920419L);
        item.put("typeCode", "articleNews");
        item.put("siteId", 0L);
        item.put("remark", "热烈欢迎习主席视察我市，习主席热心慰问退休干部...");
        SolrUtil.addDocument(item);
    }

    @Test
    public void add() {
        List<SolrIndexVO> list = new ArrayList<SolrIndexVO>();
        SolrIndexVO item = new SolrIndexVO();
        String id = Long.toString(new Date().getTime());
        System.out.println(id);
        item.setId(id);
        item.setAuthor("admin");
        item.setTitle("热烈欢迎习主席视察我市");
        item.setCreateDate(new Date());
        item.setColumnId(19920419L);
        item.setTypeCode("articleNews");
        item.setSiteId(0L);
        item.setRemark("热烈欢迎习主席视察我市，习主席热心慰问退休干部...");
        list.add(item);
        SolrUtil.add(item);
        SolrUtil.add(list);
    }

    @Test
    public void delete() {
        SolrUtil.delete("1563867014870");
    }

    @Test
    public void deleteByQuery() {
        Map param = new HashMap();
        param.put("author","admin123");
        SolrUtil.deleteByQuery(param);
    }

    @Test
    public void query() {
        SolrQueryVO queryVO = new SolrQueryVO();
        queryVO.setQuery("id:change.me");
        List<SolrIndexVO> result = SolrUtil.query(queryVO);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void querySharding() {
        SolrQueryVO queryVO = new SolrQueryVO();
        queryVO.setQuery("id:change.me");
        List<SolrInputDocument> result = (List<SolrInputDocument>) SolrUtil.querySharding(queryVO);
        System.out.println(JSON.toJSONString(result));
    }
}
