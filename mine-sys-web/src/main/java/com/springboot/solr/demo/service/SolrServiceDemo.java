package com.springboot.solr.demo.service;

import com.springboot.common.util.AppUtil;
import com.springboot.solr.demo.vo.Items;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolrServiceDemo {
    private final static String url = "http://localhost:8818/solr/collection1";

    public static void main(String[] args) {
        //new SolrServiceDemo().add();
        //new SolrServiceDemo().delete();
        //new SolrServiceDemo().update();
        new SolrServiceDemo().query();
    }

    /**
     * 添加
     */
    public void add() {
        try {
            HttpSolrServer server = getSolrServer();
            List<Items> list = new ArrayList<Items>();
            for (int i = 0; i < 5; i++) {
                Items item = new Items();
                item.setId(i + 1);
                item.setName("item_" + (i + 1));
                item.setPrice(500 * i);
                item.setRelease_time((int) (System.currentTimeMillis() / 1000));
                item.setDeals(10 + i);
                item.setHits(50 * i);
                list.add(item);
            }
            server.addBeans(list);
            server.commit();
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
            HttpSolrServer server = getSolrServer();
            server.deleteById("1");
            server.commit();
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
            HttpSolrServer server = getSolrServer();
            Items item = new Items();
            item.setId(3);
            item.setName("item_modify");
            item.setPrice(5009);
            item.setRelease_time((int) (System.currentTimeMillis() / 1000));
            item.setDeals(109);
            item.setHits(509);
            server.addBean(item);
            server.commit();
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
            HttpSolrServer server = getSolrServer();
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("q", "id:3"); // q表示查询字符串
            params.set("start", 0); // start是开始记录数 分页用
            params.set("rows", 3); // rows是返回记录条数 分页用
            params.set("sort", "price desc");//sort是排序字段 字段名 排序类型
            params.set("fl", "id,name,price,releaseTime,deals,hits"); //fl是 fieldlist缩写，就是需要返回的字段列表，用逗号和空格隔开
            QueryResponse response = null;
            try {
                response = server.query(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SolrDocumentList results = response.getResults();
            if (!results.isEmpty()) {
                List<Items> list = AppUtil.toBeanList(results, Items.class);
                for (Items s : list) {
                    System.out.println(s);
                }
            }
            System.out.println("参数查询完成");
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    /**
     * solrServer是线程安全的，所以在使用时需要使用单例的模式，减少资源的消耗
     */
    private HttpSolrServer solrServer = null;

    public HttpSolrServer getSolrServer() {
        if (solrServer == null) {
            solrServer = new HttpSolrServer(url);
        }
        return solrServer;
    }
}
