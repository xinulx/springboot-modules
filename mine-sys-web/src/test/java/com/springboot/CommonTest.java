package com.springboot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.common.util.AppUtil;
import com.springboot.common.util.DateUtil;
import jdk.nashorn.internal.scripts.JO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CommonTest {

    @Test
    public void test1() {
        String str = "/mongo1/63ad5dcebdc5486d9a1a0fad3288c6e3.xls";
        System.out.println(str.split("\\.")[0].split("/")[2]);
        System.out.println(DateUtil.getStrMonth());
        System.out.println(Integer.parseInt("2018.3".replace(".", "")));
        System.out.println("12+AND+12+AND+21".split("\\+")[4]);
        System.out.println(StringEscapeUtils.escapeHtml4("12+AND+12+AND+21"));
        List<Long> ids = new ArrayList();
        ids.add(4L);
        Long[] catIds = new Long[]{1L, 2L, 3L};
        catIds = ids.toArray(new Long[ids.size()]);
        System.out.println(catIds.toString());
        System.out.println("17-15".split("\\|")[0].split("\\-")[1]);
        Long count = 0L;
        count += 1L;
        System.out.println("dqwdqw" + 1000 * count / 342);
        String result = String.format("%.3f", Double.valueOf(1000 * count / 342));
        System.out.println(result);
        System.out.println(DateUtil.getLastMonth());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -6);
        System.out.println(c.getTime());
        System.out.println(DateUtil.getStrYear().substring(5, 6));
        Integer dateValue = 0;
        String strYear = DateUtil.getStrYear();
        if (strYear.substring(5, 6).equals("0")) {
            dateValue = Integer.parseInt(strYear.substring(0, 4) + strYear.substring(6, 7));
        } else {
            dateValue = Integer.parseInt(strYear.substring(0, 4) + strYear.substring(5, 7));
        }
        for (int i = 0; i < 6; i++) {
            System.out.println(dateValue - i);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        System.out.println(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        System.out.println("2018.3".split("\\.")[1]);
        System.out.println(Integer.parseInt("03") % 3);
        System.out.println("oracle位置" + "oracle.jdbc.OracleDriver".indexOf("oracle"));
        System.out.println();
        Map map = (Map) JSON.parse("{\"aab004\":\"21\",\"aae006\":\"\",\"aab013\":\"\",\"aab001\":\"\",\"aab036\":\"\",\"aab002\":\"\"}");
        System.out.println(map);
        String paramStr = "{&quot;aab004&quot;:&quot;132&quot;,&quot;aae006&quot;:&quot;&quot;,&quot;aab013&quot;:&quot;&quot;,&quot;aab001&quot;:&quot;&quot;,&quot;aab036&quot;:&quot;&quot;,&quot;aab002&quot;:&quot;&quot;}".replaceAll("&quot;", "\"");
        System.out.println(paramStr);
        String content = "美国拒绝向中国遣返东突分子&nbsp;欧洲各国不愿接收 -ISHANGNET- <BR>　　<FONT size=4>美国《华盛顿邮报》日前连续发文，首度披露１５名在押“东突”嫌犯的真实身份和他们在美军恐怖主义嫌犯俘虏营里的真实状况。文章还透露，关塔那摩拘禁营的美军官员悄然将５名在押的“东突”嫌犯转至“轻松监狱”，以便在接到愿意接收他们的“第三国”后能迅速释放。<BR>　　<BR>　　美国在遣返关塔那摩海军基地的被羁押者时，一向是将“中等威胁和低威胁的囚犯一律送回原籍国，由原籍国依照司法程序进行处理。”但美国对中国籍被羁押者采取了不同态度，拒绝将“东突”分子遣返回中国。<BR>　　<BR>　　英国《金融报》曾援引一名美国高级官员的话称，美国非常希望欧洲国家能够接收这些“东突”分子。不过，令美国大失所望的是，意大利、法国、葡萄牙、奥地利和土耳其等国家无一例外地坚拒接收“东突”分子。联合国难民署也一口回绝了美国</FONT>的要求。";
        System.out.println(content.substring(content.indexOf("ISHANGNET") + 15, content.length()));
    }


    @Test
    public void test2() {
        // 二元一组
        int a = 3, b = 2, r = 280;
        // 二元二组
        int c = 6, d = 3, r2 = 498;

        // 最小公倍数
        int lcm = get_lcm(a, c);
        System.out.println(lcm);

        // 计数一元组
        int a1 = a * (lcm / a), b1 = b * (lcm / a), r_ = r * (lcm / a);
        // 计数一元组
        int c1 = a * (lcm / c), d1 = d * (lcm / c), r2_ = r2 * (lcm / c);

        // 第二元结果
        int lanqiu_price = (r2_ - r_) / (d1 - b1);
        System.out.println(lanqiu_price);

    }

    public static int get(int a, int b) {
        int max, min;
        max = (a > b) ? a : b;
        min = (a < b) ? a : b;

        if (max % min != 0) {
            return get(min, max % min);
        } else {
            return min;
        }
    }

    public static int get_lcm(int a, int b) {
        return a * b / get(a, b);
    }

    @Test
    public void test() {
        Double a = 1.2;
        Double b = 2.0;
        System.out.println(a + b);
    }

    @Test
    public void main1() {
        Document doc = null;
        StringBuilder content = new StringBuilder();
        URL urlfile = null;
        try {
            urlfile = new URL("http://aqxxgk.anqing.gov.cn/list.php?unit=HA018&xxflid=39000000");
            BufferedReader in = new BufferedReader(new InputStreamReader(urlfile.openStream()));
            String inputLine = in.readLine();
            while (inputLine != null) {
                content.append(inputLine);
                inputLine = in.readLine();
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc = Jsoup.parse(content.toString());
        Elements tdItemDatas = doc.getElementsByClass("f-title");
        for (Element tdItemData : tdItemDatas) {
            String title = tdItemData.getElementsByTag("a").get(0).html();
            String href = tdItemData.getElementsByTag("a").get(0).attr("href");
            String date = tdItemData.parent().select(">td:eq(2)").html();

            System.out.println(date);
        }

    }

    /**
     * 从新闻内容中获取原文件视频地址
     *
     * @param content
     * @return
     */
    public static String getOldFilePath(String content) {
        Document doc = Jsoup.parse(content);
        String flashvars = doc.getElementsByTag("embed").get(0).attr("flashvars");
        String path = "";
        if (!AppUtil.isEmpty(flashvars)) {
            path = flashvars.split("&")[1];
            path = path.replace("http://www.zglh.gov.cn", "");
            path = path.split("=")[1];
        }
        return path;
    }

    public static void main(String[] args) {
        System.out.println(getOldFilePath("<p align=\"center\">\n" +
                "    <embed style=\"text-transform:none;text-indent:0px;font:13px/24px\n" +
                " Simsun;white-space:normal;letter-spacing:normal;color:#333333;word-spacing:0px;-webkit\n" +
                " -text-stroke-width:0px;\" id=\"CuPlayerV4\" height=\"180\" name=\"CuPlayerV4\" type=\"application/x-shockw\n" +
                " ave-flash\" width=\"250\" src=\"http://www.zglh.gov.cn/cuplayer/CuPlayerMiniV4.swf\" flashvars=\"CuPlayerSetFile=http://www.zglh.gov.cn/cuplayer/CuPlayerSetFile.asp&amp;\n" +
                " CuPlayerFile=http://www.zglh.gov.cn/files/lhsp/2016/04/lhtv160415.flv&amp;\n" +
                " CuPlayerImage=http://www.zglh.gov.cn/cuplayer/images/start.jpg&amp;CuPlayerWidth=250&amp;CuPlayerHeight=180&amp;CuPlayerAutoPlay=yes&amp;CuPlayerLogo=http://www.zglh.gov.cn/cuplayer/images/logo.png&amp;CuPlayerPosition=bottom-right\" salign=\"lt\" wmode=\"opaque\"\n" +
                "    allowscriptaccess=\"always\" allowfullscreen=\"true\" quality=\"high\" bgcolor=\"#000000\" />\n" +
                "</p>"));

        //MultipartFile Filedata = (MultipartFile) new File("C:\\Users\\lenovo\\Desktop\\work\\1.png");
        //System.out.println(Filedata);

        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        System.out.println(dateFormat2.format(new Date()));
    }

    @Test
    public void testGuZhen() {
        String html = HttpRequestUtil.getHTML("http://zwgk.bengbu.gov.cn/api/list.jsp?LmId=626871976&count=3");
        Document document = Jsoup.parse(html);
        Elements lis = document.getElementsByTag("li");
        for (Element e : lis) {
            String url = e.select("a").get(0).attr("href");
            String title = e.select("a").get(0).attr("title");
        }
    }

    @Test
    public void testHuaibeihbj() {
        Connection connect = Jsoup.connect("http://sthjt.ah.gov.cn/pages/SJZX_List.aspx?CityCode=340600&LX=4");
        List rtList = new ArrayList();
        try {
            Document document = connect.post();
            Elements elements = document.select(".data_table>tbody>tr:lt(4)");
            for (Element e : elements) {
                Elements tds = e.select("td");
                if (tds == null || tds.size() == 0) {
                    continue;
                }
                List item = new ArrayList();
                for (Element td : tds) {
                    item.add(td.text());
                }
                rtList.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(rtList);// 倒序排列
        System.out.println(rtList);
    }

    @Test
    public void testReplace() {
        String url = "http://www.baidu.baidu.com/${date}";
        url = url.replace("${date}", "2019");
        System.out.println(url);
        String content = "<div id=\"message1\"></div><span class=\"message2\"><table><tr></tr><tr></tr><tr></tr></table></span>";
        Document document = Jsoup.parse(content);
        Elements elements = document.select("#message11");
        if (elements != null && elements.size() > 0) {
            elements.remove();
        }
        document.select(".message2 table").last().append("<a></a>");
        System.out.println(document.select("body").html());
        System.out.println(new Random().nextInt(7) + 1);

        System.out.println("aaa".concat("bbb"));
    }

    @Test
    public void testZBCG() {
        String url = "http://jyggzy.my.gov.cn/ceinwz/WebInfo_List.aspx?zfcg=0100000&PubDateSort=0&ShowPre=0&newsid=201&FromUrl=zfcg";
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(".myGVClass tr:lt(21)");
            for (Element element : elements) {
                if (element.hasClass("myGVHead")) {
                    continue;
                }
                String title = element.select("td:eq(1)>a").html();
                String href = "http://jyggzy.my.gov.cn/ceinwz/" + element.select("td:eq(1)>a").attr("href");
                Document infoDoc = Jsoup.connect(href).get();
                Elements date1 = infoDoc.select("#lblWriteDate");
                String date;
                if (date1.size() > 0) {
                    date = date1.text().substring(5);
                } else {
                    Elements date2 = infoDoc.select("#lblZbgsWzKsDate");
                    date = date2.text().substring(5);
                }
                System.out.println(date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testZWFW() {
        String url = "http://aq.ahzwfw.gov.cn/portal/orgService/query.shtml?siteId=2&random=0.09739801829450245&sxlx=&orgId=34080011060000000000&directoryName=&serviceCharacter=";
        JSONObject jsonObject = HttpRequestUtil.sendGet(url, null);
        System.out.println(jsonObject);
    }

    @Test
    public void testdangtu() {
        String url = "http://mas.ahzwfw.gov.cn/xzspserver/services/interface/getBjgs";
        String params = "page=1&pagesize=4&areacode=340521000000";
        Object get = HttpRequestUtil.sendRequest(url, params, "get", null);
        System.out.println(get);
    }

    @Test
    public void testYJBJGS() {
        String domain = "http://la.ahzwfw.gov.cn/bsdt/apas/queryApasInfoQueryByPage.do?currentPageNo=1&pageSize=12&acceptDeptCode=&xzqhbm=341504000000&applprojName=&proprojName=&applyFromCode=&_=1517185598032";
        String jsonObject = HttpRequestUtil.getHTML(domain);
        System.out.println(jsonObject);
    }

    @Test
    public void testSZF() {
        String url = "http://xxgk.ah.gov.cn/UserData/SortHtml/702/GK5802228611.html";
        Connection connect = Jsoup.connect(url);
        try {
            Document document = connect.get();
            Elements datas = document.select(".xxgk_lb table:eq(1)>tbody>tr:lt(6)");
            if (datas.size() > 0) {
                // 2个tr为一条数据，需要跳过偶数行
                int i = 0;
                for (Element element : datas) {
                    i++;
                    if (i % 2 == 0) {
                        continue;
                    }
                    Element select = element.select(">td>a").get(0);
                    String href = "http://xxgk.ah.gov.cn".concat(select.attr("href"));
                    String title = select.text();
                    Element dateElement = datas.get(i);
                    String date = dateElement.select("td>span").get(1).text().substring(5);
                    System.out.println(href);
                    System.out.println(title);
                    System.out.println(date);
                }
            }
        } catch (IOException e) {
            System.out.println("抓取数据【" + url + "】出错");
        }
    }

    @Test
    public void testGWY() {
        String url = "http://www.gov.cn/zhengce/zuixin.htm";
        Connection connect = Jsoup.connect(url);
        try {
            Document document = connect.get();
            Elements datas = document.select(".news_box ul li:lt(3)");
            if (datas.size() > 0) {
                for (Element element : datas) {
                    Element select = element.select("a").get(0);
                    String href = "http://www.gov.cn".concat(select.attr("href"));
                    String title = select.text();
                    Element dateElement = element.select("span").get(0);
                    String date = dateElement.text();
                    System.out.println(href);
                    System.out.println(title);
                    System.out.println(date);
                }
            }
        } catch (IOException e) {
            System.out.println("抓取数据【" + url + "】出错");
        }
    }

    @Test
    public void testLUAN() {
        JSONObject jsonObject = HttpRequestUtil.sendGet("http://scjgj.luan.gov.cn/spcjApi/?searchK=5d0096b76eb33ea899000029&startDate=2019-06-15&endDate=2019-06-16", null);
        System.out.println(JSON.toJSONString(jsonObject));
    }

    @Test
    public void testTL() throws IOException {
        InputStream inputStream = new URL("http://scjgj.tl.gov.cn/ztzl/spcjxx/NewsContents_2018.json").openStream();
        String content = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
        System.out.println(JSON.parse(content));
        JSONObject jsonObject = HttpRequestUtil.sendGet("http://scjgj.tl.gov.cn/ztzl/spcjxx/NewsContents_2018.json", null);
        System.out.println(JSON.toJSONString(jsonObject));
    }

    @Test
    public void testHf() throws IOException {
        InputStream inputStream = new URL("http://amr.hefei.gov.cn/sj/spcjtg/defaul_5178.json").openStream();
        String content = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
        System.out.println(JSON.parse(content));
        JSONObject jsonObject = HttpRequestUtil.sendGet("http://amr.hefei.gov.cn/sj/spcjtg/defaul_5178.json", null);
        System.out.println(JSON.toJSONString(jsonObject));
    }

    @Test
    public void testDate() throws ParseException, IOException {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:hh:ss");
        //System.out.println(format.parse("2019-10-01 00:00:00"));
        //SimpleDateFormat format2 = new SimpleDateFormat("HH");
        //System.out.println(Integer.valueOf("01"));
        //System.out.println(format2.format(new Date()));

        Document doc = Jsoup.connect("http://xxgk.cnbz.gov.cn/GPI/index.aspx?gpiid=40007&dept=92337799").get();
        Elements items = doc.select("#form1>div:eq(1)>table:eq(3)>tbody>tr>td>table:eq(0)>tbody>tr>td:eq(2)>table:eq(2)>tbody>tr:eq(1)>td table tr:gt(0)");
        System.out.println(items.size());
        for (Element e : items) {
            //System.out.println(e.select("td:eq(1)").text());
        }
        System.out.println(new SimpleDateFormat("yyyyMMdd").parse("20150101"));
    }

    @Test
    public void getInfo() {
        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            URL wsUrl = new URL("http://60.171.153.50/buildingservice/service.asmx");
            conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setRequestProperty("Content-Length", "");
            conn.setRequestProperty("SOAPAction", "WS_BuildingService/BuildingService");
            os = conn.getOutputStream();

            StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
            sb.append("<soap:Body>");
            sb.append("<BuildingService xmlns=\"WS_BuildingService\">");
            sb.append(" <strSQL>" + 6 + "</strSQL>");
            sb.append("</BuildingService>");

            sb.append("</soap:Body>");
            sb.append("</soap:Envelope>");
            os.write(sb.toString().getBytes());
            is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            String s = "";
            while ((len = is.read(b)) != -1) {

                String ss = new String(b, 0, len, "UTF-8");
                s += ss;
            }
            //返回的是拦截中的返回体；
            s = Jsoup.parse(s).html();
            FileUtils.write(new File("D:\\upload\\test.txt"), s);
        } catch (Exception e) {
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (null != conn) {
                conn.disconnect();
            }
        }
    }

    @Test
    public void test111() throws IOException {
        String domain = "http://zwgk.mas.gov.cn";
        String url = "http://zwgk.mas.gov.cn/opennessTarget/?branch_id=57a3df762c262ea9a00aae7e&column_code=320000";
        Boolean isReadFile = true;
        List<PostItemVO> list = new ArrayList<PostItemVO>();
        Document doc = null;
        StringBuilder content = new StringBuilder();
        if (!AppUtil.isEmpty(url)) {
            URL urlfile = null;
            try {
                urlfile = new URL(url);
                BufferedReader in = new BufferedReader(new InputStreamReader(urlfile.openStream()));
                String inputLine = in.readLine();
                while (inputLine != null) {
                    content.append(inputLine);
                    inputLine = in.readLine();
                }
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            doc = Jsoup.parse(content.toString());
            Elements datas = doc.select(".m-lsbody ul>li");
            for (Element data : datas) {
                PostItemVO item = new PostItemVO();
                String title = data.select(".m-listview>ol>li:eq(4)").text().replace("名称：", "");
                if (isReadFile == null || !isReadFile) {
                } else {
                    Elements readFiles = data.select(".u-ls2 a:gt(0)");
                    if (readFiles.size() > 0) {
                        String readFilePaths = "";
                        for (Element readFile : readFiles) {
                            String readFilePath = readFile.outerHtml();
                            if (!readFilePath.contains("http")) {
                                readFilePaths += readFilePath.replaceAll("/openness", domain + "/openness") + "|";
                            }
                        }
                        item.setReadFile(readFilePaths.substring(0, readFilePaths.length() - 1));
                    }
                }
                String href = data.select(".u-ls2 a").attr("href");
                String date = data.select(".u-ls4").text();
                item.setInfoTitle(title.trim());
                item.setInfoDate(date);
                if (href.contains("http")) {
                    item.setInfoUrl(href);
                } else {
                    item.setInfoUrl(domain + href);
                }
                item.setImageLink("");
                list.add(item);
            }
        }
    }


    @Test
    public void testDangtu() {
        //String url = "http://u.mas.gov.cn/api/opennessContent/masDtContent.php";
        //String params = "branch=57a3df762c262ea9a00aadf0&column=010000&curTime=1447893796&key=7297d92677ab177da6b0a9f5fa77d5d7";
        //Map<String, String> paramsMap = new HashMap<String, String>();
        //paramsMap.put("curTime", "1447893796");
        //paramsMap.put("key", "7297d92677ab177da6b0a9f5fa77d5d7");
        //String[] paramsArr = params.split("&");
        //for (String param : paramsArr) {
        //    String[] strArr = param.split("=");
        //    paramsMap.put(strArr[0], strArr[1]);
        //}
        //String result = HttpClientUtils.get(url, paramsMap);
        //JSONObject jsonObject = JSON.parseObject(result);
        //JSONObject jsonObject2 = HttpRequestUtil.sendGet(url, params);
        //System.out.println(jsonObject2);
        String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        //JSONObject json = HttpRequestUtil.doPost(TOKEN_URL.replace("APPID", "wx4d5b3e2a6fa7726b").replace("APPSECRET", "fc1cf2f12490ef3a2bb934bae7cef26f"), null);
        //System.out.println(json);

        JSONObject json = null;
        try {
            URL l = new URL(TOKEN_URL.replace("APPID", "wx4d5b3e2a6fa7726b").replace("APPSECRET", "fc1cf2f12490ef3a2bb934bae7cef26f"));
            URI uri = new URI(l.getProtocol(), l.getHost(), l.getPath(), l.getQuery(), null);
            HttpPost post = new HttpPost(uri);
            CloseableHttpClient client = HttpClients.createDefault();
            StringEntity entity = new StringEntity("", ContentType.APPLICATION_JSON);// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String entityUtils = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                json = JSONObject.parseObject(entityUtils);
                System.out.println(entityUtils);
                System.out.println(json);

            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test11() {
        List<PostItemVO> list = new ArrayList<PostItemVO>();
        Document doc = null;
        try {
            Connection conn = Jsoup.connect("http://www.xinyu.gov.cn/xyywn2/list.shtml").timeout(5000);
            conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.header("Accept-Encoding", "gzip, deflate, sdch");
            conn.header("Accept-Language", "zh-CN,zh;q=0.8");
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            doc = conn.get();
            Elements datas = doc.select(".lph_listnrqj li");

            for (Element data : datas) {
                PostItemVO item = new PostItemVO();
                String title = data.select("a").attr("title");
                String href = "http://www.xinyu.gov.cn" + data.select("a").attr("href");
                String date = data.select("span").text();
                item.setInfoTitle(title);
                item.setInfoDate(date);
                item.setInfoUrl(href);
                item.setImageLink("");
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("validateData", "epointjweb4.0@Q2Y6ljqyf6MEERWjsys0q3IalMk=@MTg5MDM0ODcxMQ==");//安全验证码
        infoMap.put("categoryNum", "010");//栏目
        infoMap.put("titleType", "Text");
        infoMap.put("infoId", "fbcd977f-e395-4eb8-9710-42e3491b1ad6");
        infoMap.put("loginId", "admin");
        infoMap.put("startDate", "");
        infoMap.put("endDate", "");
        infoMap.put("author", "马鞍山市公共资源交易中心" );
        infoMap.put("zhuanZai", "马鞍山市公共资源交易中心");
        infoMap.put("siteGuid", "");
        infoMap.put("strComment", "");
        infoMap.put("title", "111");
        infoMap.put("content", "111");
        infoMap.put("infoDate", format.format(new Date()));
        infoMap.put("keyWord", "");
        infoMap.put("subTitle", "");
        infoMap.put("linkUrl", "");
        infoMap.put("infoProperty", "News");
        infoMap.put("isShowTitle", "");
        infoMap.put("titleEx", "111");
        infoMap.put("otherAttr",new HashMap());

        String params2 = "params = {\n" +
                "    \"loginId\": \"admin\",\n" +
                "    \"startDate\": \"\",\n" +
                "    \"zhuanZai\": \"市公管局\",\n" +
                "    \"linkUrl\": \"\",\n" +
                "    \"otherAttr\": {},\n" +
                "    \"infoProperty\": \"News\",\n" +
                "    \"validateData\": \"epointjweb4.0@Q2Y6ljqyf6MEERWjsys0q3IalMk=@MTg5MDM0ODcxMQ==\",\n" +
                "    \"strComment\": \"\",\n" +
                "    \"endDate\": \"\",\n" +
                "    \"infoId\": \"10055147\",\n" +
                "    \"keyWord\": \"\",\n" +
                "    \"titleEx\": \"市公管局赴省招标投标协会交流工作\",\n" +
                "    \"content\": \"\",\n" +
                "    \"subTitle\": \"\",\n" +
                "    \"author\": \"交易管理科\",\n" +
                "    \"title\": \"市公管局赴省招标投标协会交流工作\",\n" +
                "    \"siteGuid\": \"4706331\",\n" +
                "    \"infoDate\": \"2020-01-16 17:00:29\",\n" +
                "    \"isShowTitle\": \"\",\n" +
                "    \"titleType\": \"Text\",\n" +
                "    \"categoryNum\": \"001001\"\n" +
                "}\n";

        // 格式化参数
        String params = "params=" + JSON.toJSONString(infoMap);
        String url = "http://wstb.mas.gov.cn/mas_epointwebbuilder/rest/webBuilderRestForThree/informationAdd";
        JSONObject resultObj = (JSONObject) HttpRequestUtil.sendPost(url, params2,"");
        System.out.println(JSON.toJSONString(resultObj));
        String string = resultObj.getJSONObject("EpointDataBody").getJSONObject("DATA").getJSONObject("ReturnInfo").getString("Status");
        System.out.println(string);
    }
}
