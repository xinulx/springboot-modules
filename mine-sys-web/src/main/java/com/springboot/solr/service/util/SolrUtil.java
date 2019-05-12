package com.springboot.solr.service.util;

import com.springboot.common.util.AppUtil;
import com.springboot.common.util.PropertiesUtil;
import com.springboot.solr.service.vo.SolrIndexVO;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolrUtil {
    private HttpSolrClient solrClient = null;

    public HttpSolrClient getSolrClient() {
        if (solrClient == null) {
            solrClient = new HttpSolrClient(PropertiesUtil.getProperty("spring.data.solr.host"));
        }
        return solrClient;
    }

    public static void main(String[] args) {
        //new SolrUtil().add();
        //new SolrUtil().delete();
        new SolrUtil().update();
        //new SolrUtil().query();
    }

    /**
     * 添加
     */
    public void add() {
        try {
            HttpSolrClient client = getSolrClient();
            List<SolrIndexVO> list = new ArrayList<SolrIndexVO>();
            SolrIndexVO item = new SolrIndexVO();
            item.setId(new Date().getTime()+"");
            item.setAuthor("admin");
            item.setTitle("热烈欢迎习主席视察我市");
            item.setCreateDate(new Date());
            item.setColumnId(19920419L);
            item.setTypeCode("articleNews");
            item.setSiteId(0L);
            item.setRemark("热烈欢迎习主席视察我市，习主席热心慰问退休干部...");
            list.add(item);
            client.addBeans(list);
            client.commit();
            System.out.println("添加完成");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     */
    public void delete() {
        try {
            HttpSolrClient client = getSolrClient();
            client.deleteById("1");
            client.commit();
            System.out.println("删除完成");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改
     **/
    public void update() {
        try {
            HttpSolrClient client = getSolrClient();
            SolrIndexVO item = new SolrIndexVO();
            item.setId("1556964313067");
            item.setTitle("热烈欢迎习主席视察我市...");
            client.addBean(item);
            client.commit();
            System.out.println("修改完成");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     */
    public void query() {
        try {
            HttpSolrClient client = getSolrClient();
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("q", "id:2654348"); // q表示查询字符串
            params.set("start", 0); // start是开始记录数 分页用
            params.set("rows", 10); // rows是返回记录条数 分页用
            params.set("sort", "id desc");//sort是排序字段 字段名 排序类型
            params.set("fl", "id,title"); //fl是 fieldlist缩写，就是需要返回的字段列表，用逗号和空格隔开
            QueryResponse response = null;
            try {
                response = client.query(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SolrDocumentList results = response.getResults();
            if (!results.isEmpty()) {
                List<SolrIndexVO> list = AppUtil.toBeanList(results, SolrIndexVO.class);
                for (SolrIndexVO s : list) {
                    System.out.println(s);
                }
            }
            System.out.println("参数查询完成");
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}
