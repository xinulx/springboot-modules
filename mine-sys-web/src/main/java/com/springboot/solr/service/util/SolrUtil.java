package com.springboot.solr.service.util;

import com.springboot.common.util.AppUtil;
import com.springboot.common.util.PropertiesUtil;
import com.springboot.solr.service.vo.SolrIndexVO;
import com.springboot.solr.service.vo.SolrQueryVO;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrUtil {
    private static HttpSolrClient solrClient = null;

    /**
     * 使用单例模式
     *
     * @return solrClient
     */
    public static HttpSolrClient getSolrClient() {
        if (solrClient == null) {
            solrClient = new HttpSolrClient(PropertiesUtil.getProperty("spring.data.solr.host"));
            //solrClient.setParser(new XMLResponseParser()); // 设置响应解析器
            //solrClient.setConnectionTimeout(500); // 建立连接的最长时间
        }
        return solrClient;
    }

    /**
     * 直接添加索引
     *
     * @param map
     * @return
     */
    public static void addDocument(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }
        HttpSolrClient solrClient = getSolrClient();
        SolrInputDocument document = new SolrInputDocument();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            document.addField(entry.getKey(), entry.getValue());
        }
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加索引
     *
     * @param vo
     */
    public static void add(SolrIndexVO vo) {
        HttpSolrClient solrClient = getSolrClient();
        try {
            solrClient.addBean(vo);
            solrClient.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量添加索引
     *
     * @param vos
     */
    public static void add(List<?> vos) {
        HttpSolrClient solrClient = getSolrClient();
        try {
            solrClient.addBeans(vos);
            solrClient.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引
     */
    public static void delete(String id) {
        try {
            HttpSolrClient client = getSolrClient();
            client.deleteById(id);
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据条件删除索引，若条件为空则删除所有
     *
     * @throws Exception
     */
    public void deleteByQuery(Map<String, Object> queryVO) throws Exception {
        HttpSolrClient solrClient = getSolrClient();
        if (MapUtils.isEmpty(queryVO)) {
            solrClient.deleteByQuery("*:*");
            return;
        } else {
            StringBuffer queryStr = new StringBuffer();
            for (Map.Entry<String, Object> entry : queryVO.entrySet()) {
                queryStr.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            }
            solrClient.deleteByQuery(queryStr.substring(0, queryStr.length() - 1));
        }
        solrClient.commit();
    }


    /**
     * 修改:覆盖更新，不传的字段会被null覆盖
     **/
    public static void update(SolrIndexVO vo) {
        try {
            HttpSolrClient client = getSolrClient();
            client.addBean(vo);
            client.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改:覆盖更新，不传的字段会被null覆盖
     **/
    public static void update(List<?> vos) {
        try {
            HttpSolrClient client = getSolrClient();
            client.addBeans(vos);
            client.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    /**
     * solr查询
     */
    public static List<?> query(SolrQueryVO vo) {
        List<SolrIndexVO> list = null;
        try {
            HttpSolrClient client = getSolrClient();
            ModifiableSolrParams params = new ModifiableSolrParams();
            if (!AppUtil.isEmpty(vo.getQuery())) {
                params.set("q", vo.getQuery()); // q表示查询字符串
            }
            if (!AppUtil.isEmpty(vo.getPageIndex())) {
                params.set("start", vo.getPageIndex()); // start是开始记录数 分页用
            }
            if (!AppUtil.isEmpty(vo.getPageSize())) {
                params.set("rows", vo.getPageSize()); // rows是返回记录条数 分页用
            }
            if (!AppUtil.isEmpty(vo.getSort())) {
                params.set("sort", vo.getSort()); //sort是排序字段 字段名 排序类型
            }
            if (!AppUtil.isEmpty(vo.getFieldList())) {
                params.set("fl", vo.getFieldList()); //fl是 fieldlist缩写，就是需要返回的字段列表，用逗号和空格隔开
            }

            QueryResponse response = null;
            try {
                response = client.query(params);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SolrDocumentList results = response.getResults();
            if (!results.isEmpty()) {
                list = AppUtil.toBeanList(results, SolrIndexVO.class);
                for (SolrIndexVO solrIndexVO : list) {
                    list.add(solrIndexVO);
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @Function: Solr多核索引分片(Sharding index)查询
     *  参数“shards”能够使请求被分发到shards所指定的小索引上
     *  shards 的格式  ：host:port/base_url[,host:port/base_url]*
     *  @description:   多核(例如core1、core2..)中添加索引field :coreFlag
     *                  用于标识shards指定的小索引
     */
    public List<SolrIndexVO> querySharding () throws SolrServerException, IOException {
        String SHARDS_URL = "http://localhost:8818/solr/collection1,http://localhost:8818/solr/collection2";
        String queryValue = "((coreFlag:0 AND ..core1上的其他条件) OR (coreFlag:1 AND  ..core2 上的条件)) AND 其他条件 ";
        SolrQuery query = new SolrQuery(queryValue);
        query.setStart(0).setRows(10).setSort("name", SolrQuery.ORDER.desc);
        if(StringUtils.isNotBlank(SHARDS_URL)){
            query.set("shards",SHARDS_URL);
        }
        HttpSolrClient solrClient = getSolrClient();
        QueryResponse resp = solrClient.query(query);
        SolrDocumentList sdList = resp.getResults();
        List<SolrIndexVO> searchList = solrClient.getBinder().getBeans(SolrIndexVO.class,sdList);
        Long totalRecord = new Long(sdList.getNumFound());
        System.out.println("已检索到" + totalRecord + "条记录");
        return searchList;
    }
}
