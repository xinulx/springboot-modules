package com.springboot.solr.service.util;

import com.springboot.common.business.CommonException;
import com.springboot.common.util.AppUtil;
import com.springboot.common.util.PropertiesUtil;
import com.springboot.solr.service.vo.SolrIndexVO;
import com.springboot.solr.service.vo.SolrQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class SolrUtil {
    private static HttpSolrClient solrClient = null;

    /**
     * 使用单例模式
     *
     * @return solrClient
     */
    public static HttpSolrClient getSolrClient() {
        log.info("获取solrClient");
        if (solrClient == null) {
            solrClient = new HttpSolrClient(PropertiesUtil.getProperty("spring.data.solr.host"));
            //solrClient.setParser(new XMLResponseParser()); // 设置响应解析器
            solrClient.setConnectionTimeout(0); // 建立连接的最长时间
        }
        return solrClient;
    }

    /**
     * 按照schema定义的索引字段，将key-value放入map创建索引
     * 测试使用，项目中使用映射实体模型进行操作
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
            solrClient.close();
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        } catch (IOException e) {
            log.error("添加索引异常", e);
        }
    }

    /**
     * 通过映射好的索引对象生成索引
     * 测试使用，项目中使用映射实体模型进行操作
     *
     * @param vo
     */
    public static void add(SolrIndexVO vo) {
        HttpSolrClient solrClient = getSolrClient();
        try {
            solrClient.addBean(vo);
            solrClient.commit();
            solrClient.close();
        } catch (IOException e) {
            log.error("添加索引异常", e);
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        }
    }

    /**
     * 通过映射好的索引对象集合生成索引
     *
     * @param vos
     */
    public static void add(List<?> vos) {
        HttpSolrClient solrClient = getSolrClient();
        try {
            solrClient.addBeans(vos);
            solrClient.commit();
            solrClient.close();
        } catch (IOException e) {
            log.error("添加索引异常", e);
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        }
    }

    /**
     * 根据索引ID删除索引
     */
    public static void delete(Object id) {
        if (AppUtil.isEmpty(id)) {
            log.error("删除索引时，必须指定ID");
            return;
        }
        HttpSolrClient client = getSolrClient();
        try {
            if (id instanceof String) {
                client.deleteById((String) id);
            } else if (id instanceof List) {
                client.deleteById((List<String>) id);
            } else {
                throw new CommonException("删除索引的参是必须是String或String的集合");
            }
            client.commit();
            client.close();
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        } catch (IOException e) {
            log.error("删除索引异常", e);
        }
    }

    /**
     * 根据条件删除索引，若条件为空则删除所有
     *
     * @throws Exception
     */
    public static void deleteByQuery(Map<String, Object> queryVO) {
        HttpSolrClient solrClient = getSolrClient();

        try {
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
            solrClient.close();
        } catch (SolrServerException e) {
            log.error("删除索引异常", e);
        } catch (IOException e) {
            log.error("solr服务异常", e);
        }
    }


    /**
     * 单个索引更新，不传的字段会被null覆盖
     **/
    public static void update(SolrIndexVO vo) {
        try {
            HttpSolrClient client = getSolrClient();
            client.addBean(vo);
            client.commit();
            client.close();
        } catch (IOException e) {
            log.error("更新索引异常", e);
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        }
    }

    /**
     * 批量索引更新，不传的字段会被null覆盖
     **/
    public static void update(List<?> vos) {
        try {
            HttpSolrClient client = getSolrClient();
            client.addBeans(vos);
            client.commit();
            client.close();
        } catch (IOException e) {
            log.error("更新索引异常", e);
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        }
    }

    /**
     * solr查询
     */
    public static List<SolrIndexVO> query(SolrQueryVO vo) {
        List<SolrIndexVO> list = null;
        try {
            HttpSolrClient client = getSolrClient();
            SolrQuery solrQuery = new SolrQuery();
            if (!AppUtil.isEmpty(vo.getQuery())) {
                solrQuery.set("q", vo.getQuery()); // q表示查询字符串
            } else {
                solrQuery.set("q","*:*");
            }
            if (!AppUtil.isEmpty(vo.getFilterQuery())) {
                solrQuery.set("fq", vo.getFilterQuery()); // fq表示过滤查询字符串,同一查询多次使用，后面的会被覆盖
            }
            if (!AppUtil.isEmpty(vo.getPageIndex())) {
                solrQuery.set("start", vo.getPageIndex()); // start是开始记录数 分页用
            }
            if (!AppUtil.isEmpty(vo.getPageSize())) {
                solrQuery.set("rows", vo.getPageSize().intValue()); // rows是返回记录条数 分页用
            }
            if (!AppUtil.isEmpty(vo.getSort())) {
                solrQuery.set("createDate", vo.getSort()); //sort是排序字段 字段名 排序类型
            } else{
                solrQuery.set("createDate", SolrQuery.ORDER.desc.name());
            }
            if (!AppUtil.isEmpty(vo.getFieldList())) {
                solrQuery.set("fl", vo.getFieldList()); //fl是 fieldlist缩写，就是需要返回的字段列表，用逗号和空格隔开
            }

            solrQuery.set("df", "author");//df 默认搜索的域

            //开启高亮
            solrQuery.setHighlight(true);
            //高亮域
            solrQuery.addHighlightField("title");
            //前缀
            solrQuery.setHighlightSimplePre("<span style='color:red'>");
            //后缀
            solrQuery.setHighlightSimplePost("</span>");

            QueryResponse response = null;
            try {
                response = client.query(solrQuery);
            } catch (IOException e) {
                log.error("查询索引异常", e);
            }
            //查询出来的数量
            SolrDocumentList results = response.getResults();
            Long numFound = results.getNumFound();
            log.info("总查询出[{}]条记录", numFound);
            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            if (!results.isEmpty()) {
                list = AppUtil.toBeanList(results, SolrIndexVO.class);
                for (SolrIndexVO solrIndexVO : list) {
                    Map<String, List<String>> stringListMap = highlighting.get(solrIndexVO.getId());
                    // 输出某个域的高亮信息
                    log.info("输出高亮信息:{}", stringListMap.get("title"));
                }
            }
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        }
        return list;
    }

    /**
     * Solr多核索引分片(Sharding index)查询
     * 参数“shards”能够使请求被分发到shards所指定的小索引上
     * shards 的格式  ：host:port/base_url[,host:port/base_url]*
     *
     * @description: 多核(例如core1 、 core2..)中添加索引field :coreFlag 用于标识shards指定的小索引
     */
    public static List<?> querySharding(SolrQueryVO queryVO) {
        String SHARDS_URL = "http://localhost:8818/solr/collection1,http://localhost:8818/solr/collection2";
        //String SHARDS_URL = "http://localhost:8818/solr/collection2";
        SolrQuery query = new SolrQuery();
        if (StringUtils.isNotBlank(SHARDS_URL)) {
            query.set("shards", SHARDS_URL);
        }
        if (!AppUtil.isEmpty(queryVO.getQuery())) {
            query.set("q", queryVO.getQuery()); // q表示查询字符串
        } else {
            query.set("q","*:*");
        }
        if (StringUtils.isNotBlank(SHARDS_URL)) {
            query.set("shards", SHARDS_URL);
        }
        if (!AppUtil.isEmpty(queryVO.getSort())) {
            query.set("createDate", queryVO.getSort()); //sort是排序字段 字段名 排序类型
        } else{
            query.set("createDate", SolrQuery.ORDER.desc.name());
        }
        query.setStart(0).setRows(10);
        HttpSolrClient solrClient = getSolrClient();
        QueryResponse resp = null;
        try {
            resp = solrClient.query(query);
        } catch (SolrServerException e) {
            log.error("solr服务异常", e);
        } catch (IOException e) {
            log.error("查询索引异常", e);
        }
        SolrDocumentList sdList = resp.getResults();
        //List<SolrIndexVO> searchList = solrClient.getBinder().getBeans(SolrIndexVO.class, sdList);
        for (SolrDocument solrDocument : sdList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("title"));
        }

        Long totalRecord = sdList.getNumFound();
        System.out.println("已检索到" + totalRecord + "条记录");
        return sdList;
    }
}
