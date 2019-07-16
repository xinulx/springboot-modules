package com.springboot.solr.service;

import com.springboot.solr.service.util.SolrUtil;
import com.springboot.solr.service.vo.SolrIndexVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * solr常用操作
 */
@Slf4j
public class SolrOprDemo {

    @Test
    public void add() {
        List<SolrIndexVO> list = new ArrayList<SolrIndexVO>();
        SolrIndexVO item = new SolrIndexVO();
        item.setId(new Date().getTime() + "");
        item.setAuthor("admin");
        item.setTitle("热烈欢迎习主席视察我市");
        item.setCreateDate(new Date());
        item.setColumnId(19920419L);
        item.setTypeCode("articleNews");
        item.setSiteId(0L);
        item.setRemark("热烈欢迎习主席视察我市，习主席热心慰问退休干部...");
        list.add(item);
        SolrUtil.add(item);
    }
}
