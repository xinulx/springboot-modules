package com.springboot;

import com.alibaba.fastjson.JSON;
import com.springboot.common.business.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * http连接池 ADD REASON. <br/>
 *
 * @author fangtinghua
 * @date: 2016年1月13日 上午8:57:13 <br/>
 */
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    public static final String REQUEST_AUTHORIZATION = HttpClientUtils.class.getName() + ".Authorization"; // http请求认证信息
    public static final String REQUEST_ENTITY = HttpClientUtils.class.getName() + ".Entity"; // http entity请求，主要用在post请求上，把内容直接当做entity提交
    public static final String REQUEST_GET = "GET"; // get请求
    public static final String REQUEST_POST = "POST";// post请求
    public static final String REQUEST_PUT = "PUT"; // put请求
    public static final String REQUEST_DELETE = "DELETE"; // delete请求

    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
    private static final String encoding = "UTF-8";// 编码
    private static final int timeout = 120000;// 超时时间 2分钟
    private static PoolingHttpClientConnectionManager cm = null;
    private static CloseableHttpClient httpClient = null;
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout).build();

    /**
     * 连接池初始化
     */
    static {
        try {
            //采用绕过验证的方式处理https请求
            //SSLContext sslContext = SslUtil.createIgnoreVerifySSL();
            //SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> registry =
                    RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
                        /*.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();*/
                            .register("https", sslConnectionSocketFactory).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
            // Create connection configuration
            ConnectionConfig connectionConfig =
                    ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE)
                            .setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();
            cm.setDefaultConnectionConfig(connectionConfig);
            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(30);
            // 请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = new DefaultHttpRequestRetryHandler();
            httpClient = HttpClients.custom().setConnectionManager(cm).setRetryHandler(httpRequestRetryHandler).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求
     *
     * @param url    请求url
     * @param params 请求参数
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        return request(REQUEST_GET, url, params);
    }

    /**
     * delete请求
     *
     * @param url    请求url
     * @param params 请求参数
     * @return
     */
    public static String delete(String url, Map<String, String> params) {
        return request(REQUEST_DELETE, url, params);
    }

    /**
     * post
     *
     * @param url    请求url
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        return request(REQUEST_POST, url, params);
    }

    /**
     * put
     *
     * @param url    请求url
     * @param params 请求参数
     * @return
     */
    public static String put(String url, Map<String, String> params) {
        return request(REQUEST_PUT, url, params);
    }


    /**
     * http 请求，用来转换为业务json数据
     *
     * @param method 请求方法，为get、post、put、delete方法
     * @param url    请求的url路径
     * @param params 请求参数体
     * @return
     */
    public static String request(String method, String url, Map<String, String> params) {
        if (StringUtils.isEmpty(method) || StringUtils.isEmpty(url)) {
            //throw new BaseRunTimeException(TipsMode.Message.toString(), "http请求缺少method或url！");
        }
        // 构建请求参数体
        HttpRequestBase httpRequestBase = buildHttpRequest(method, url, params);
        return requestString(httpRequestBase);
    }

    /**
     * http 请求，多用于文件下载
     *
     * @param method 请求方法，为get、post、put、delete方法
     * @param url    请求的url路径
     * @param params 请求参数体
     * @return
     */
    public static byte[] requestByte(String method, String url, Map<String, String> params) {
        if (StringUtils.isEmpty(method) || StringUtils.isEmpty(url)) {
        }
        // 构建请求参数体
        HttpRequestBase httpRequestBase = buildHttpRequest(method, url, params);
        return requestByte(httpRequestBase);
    }

    /**
     * 封装请求体参数
     *
     * @param method 请求方法，为get、post、put、delete方法
     * @param url    请求url
     * @param params 请求参数
     * @return
     */
    private static HttpRequestBase buildHttpRequest(String method, String url, Map<String, String> params) {
        try {
            String authorization = "";// 认证信息
            HttpRequestBase httpRequestBase = null;
            // Create HttpGet or HttpDelete
            if (REQUEST_GET.equals(method) || REQUEST_DELETE.equals(method)) {
                URIBuilder builder = new URIBuilder(url);
                if (null != params && !params.isEmpty()) {
                    authorization = params.remove(REQUEST_AUTHORIZATION); // 获取认证信息
                    for (String key : params.keySet()) {
                        builder.addParameter(key, params.get(key));
                    }
                }
                URI uri = builder.build();
                httpRequestBase = REQUEST_GET.equals(method) ? new HttpGet(uri) : new HttpDelete(uri);
            }// Create HttpGet or HttpDelete
            else if (REQUEST_POST.equals(method) || REQUEST_PUT.equals(method)) {
                httpRequestBase = REQUEST_POST.equals(method) ? new HttpPost(url) : new HttpPut(url);
                if (null != params && !params.isEmpty()) {
                    authorization = params.remove(REQUEST_AUTHORIZATION); // 获取认证信息
                    String entity = params.remove(REQUEST_ENTITY); // entity内容
                    HttpEntityEnclosingRequestBase requestBase = (HttpEntityEnclosingRequestBase) httpRequestBase;
                    if (StringUtils.isNotEmpty(entity)) {
                        requestBase.setEntity(new StringEntity(entity, Consts.UTF_8));
                    } else {
                        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
                        for (Entry<String, String> entry : params.entrySet()) {
                            paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                        }
                        requestBase.setEntity(new UrlEncodedFormEntity(paramsList, Consts.UTF_8));
                    }
                }
            } else {
            }
            httpRequestBase.setConfig(requestConfig);
            httpRequestBase.setHeader("User-Agent", userAgent);// 模拟浏览器请求
            if (StringUtils.isNotEmpty(authorization)) { // 添加认证信息
                httpRequestBase.setHeader("Authorization", authorization);
            }
            return httpRequestBase;
        } catch (Throwable e) {
            LOGGER.error("http请求错误，请稍后重试！", e);
            throw new RuntimeException();
        }
    }

    /**
     * http请求
     *
     * @param httpRequestBase 请求体
     * @return 返回字符格式
     */
    private static String requestString(HttpRequestBase httpRequestBase) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpRequestBase);
            int status = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == status) {// 正常响应
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, encoding);
            }
            LOGGER.error(String.format("http请求错误，状态码：%s！", status));
            throw new RuntimeException(CommonException.TipsMode.Message.toString()+"http请求错误，请稍后重试！");
        } catch (Throwable e) {
            LOGGER.error("http请求错误，请稍后重试！", e);
            throw new RuntimeException(CommonException.TipsMode.Message.toString()+"http请求错误，请稍后重试！");
        } finally {
            closeResponse(response, httpRequestBase);
        }
    }



    /**
     * http请求
     *
     * @param httpRequestBase 请求体
     * @return 返回字节格式
     */
    private static byte[] requestByte(HttpRequestBase httpRequestBase) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpRequestBase);
            int status = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == status) {// 正常响应
                HttpEntity entity = response.getEntity();
                return EntityUtils.toByteArray(entity);
            }
            LOGGER.error(String.format("http请求错误，状态码：%s！", status));
        } catch (Throwable e) {
            LOGGER.error("http请求错误，请稍后重试！", e);
        } finally {
            closeResponse(response, httpRequestBase);
        }
        throw new RuntimeException();
    }

    /**
     * 关闭
     *
     * @param response http响应
     * @param request  http请求
     */
    private static void closeResponse(CloseableHttpResponse response, HttpRequestBase request) {
        try {
            if (null != response) {
                response.close();
            }
            if (null != request) {
                request.releaseConnection();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * post，5秒超时
     *
     * @param url
     * @param params
     * @return
     * @author fangtinghua
     */
    public static String postJson(String url, Map<String, String> params) {
        HttpPost httpPost = null;
        String responseString = "";
        CloseableHttpResponse response = null;
        try {
            // Create HttpPost
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("User-Agent", userAgent);
            httpPost.setHeader("Content-Type", "application/json");
            if (null != params && !params.isEmpty()) {
                StringEntity entity = new StringEntity(JSON.toJSONString(params), encoding);// 解决中文乱码问题
                entity.setContentEncoding(encoding);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == status) {// 正常响应
                HttpEntity entity = response.getEntity();
                responseString = EntityUtils.toString(entity, encoding);
                LOGGER.info(String.format("[HttpUtils Post] Debug url:%s , response string:%s", url, responseString));
            } else {
                LOGGER.error(String.format("[HttpUtils Post] invoke post error, url:%s , response status:%s", url, status));
            }
        } catch (Throwable e) {
            LOGGER.error(String.format("[HttpUtils Post] invoke post error, url:%s", url), e);
        } finally {
            closeResponse(response, httpPost);
        }
        return responseString;
    }
}