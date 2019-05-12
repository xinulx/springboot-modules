package com.springboot;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnqingXXGK {

    public static void main(String[] args){
        Connection connect = Jsoup.connect("http://aqxxgk.anqing.gov.cn/list.php?unit=HA002&xxflid=31100000");
        Document document = null;
        try {
            document = connect.get();
            Elements elements = document.select(".f-title");
            for(Element element:elements){
                String title = element.select(">a").text();
                String href = "http://aqxxgk.anqing.gov.cn/" + element.select(">a").attr("href");
                String date = element.parent().select(">td:eq(2)").html();
                System.out.println(title + " " + href + " " + date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject resultObj = HttpRequestUtil.sendGet("http://scjg.huangshan.gov.cn/Content/getContents/34300/1/10/2019-04-26/2019-04-29", null);
        System.out.println(resultObj);
        System.out.println("1".concat("2"));
    }

    @Test
    public void test(){
        Connection connect = Jsoup.connect("http://aqxxgk.anqing.gov.cn/index.html");
        Document document = null;
        try {
            document = connect.get();
            Elements elements = document.select(".gk5>.i-box-c>div");
            Map map = new HashMap();
            for(Element element:elements){
                List<List> list = new ArrayList<List>();
                Elements listElements = element.select(".i-box-content>.list");
                List items = new ArrayList();
                for(Element listElement:listElements){
                    Elements lis = listElement.select("li");
                    for(Element li:lis){
                        String date = li.select("span").html();
                        String href = "http://aqxxgk.anqing.gov.cn" + li.select("a").attr("href");
                        String title = li.select("a").attr("title");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(document);
    }
}
