package com.springboot;

import com.alibaba.fastjson.JSON;
import com.springboot.common.util.DateUtil;
import com.springboot.function.DateFormat;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CommonTest {

    @Test
    public  void test1(){
        String str ="/mongo1/63ad5dcebdc5486d9a1a0fad3288c6e3.xls";
        System.out.println(str.split("\\.")[0].split("/")[2]);
        System.out.println(DateUtil.getStrMonth());
        System.out.println(Integer.parseInt("2018.3".replace(".","")));
        System.out.println("12+AND+12+AND+21".split("\\+")[4]);
        System.out.println(StringEscapeUtils.escapeHtml4("12+AND+12+AND+21"));
        List<Long> ids = new ArrayList();
        ids.add(4L);
        Long[] catIds = new Long[]{1L,2L,3L};
        catIds = ids.toArray(new Long[ids.size()]);
        System.out.println(catIds.toString());
        System.out.println("17-15".split("\\|")[0].split("\\-")[1]);
        Long count = 0L;
            count += 1L;
        System.out.println("dqwdqw"+1000*count/342);
        String result = String.format("%.3f",Double.valueOf(1000*count/342));
        System.out.println(result);
        System.out.println(DateUtil.getLastMonth());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,-6);
        System.out.println(c.getTime());
        System.out.println(DateUtil.getStrYear().substring(5,6));
        Integer dateValue = 0;
        String strYear = DateUtil.getStrYear();
        if(strYear.substring(5,6).equals("0")){
            dateValue = Integer.parseInt(strYear.substring(0,4)+strYear.substring(6,7));
        }else{
            dateValue = Integer.parseInt(strYear.substring(0,4)+strYear.substring(5,7));
        }
        for(int i = 0 ; i < 6; i ++){
            System.out.println(dateValue-i);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        System.out.println(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        System.out.println("2018.3".split("\\.")[1]);
        System.out.println(Integer.parseInt("03")%3);
        System.out.println("oracle位置" + "oracle.jdbc.OracleDriver".indexOf("oracle"));
        System.out.println();
        Map map = (Map)JSON.parse("{\"aab004\":\"21\",\"aae006\":\"\",\"aab013\":\"\",\"aab001\":\"\",\"aab036\":\"\",\"aab002\":\"\"}");
        System.out.println(map);
        String paramStr = "{&quot;aab004&quot;:&quot;132&quot;,&quot;aae006&quot;:&quot;&quot;,&quot;aab013&quot;:&quot;&quot;,&quot;aab001&quot;:&quot;&quot;,&quot;aab036&quot;:&quot;&quot;,&quot;aab002&quot;:&quot;&quot;}".replaceAll("&quot;","\"");
        System.out.println(paramStr);
        String content = "美国拒绝向中国遣返东突分子&nbsp;欧洲各国不愿接收 -ISHANGNET- <BR>　　<FONT size=4>美国《华盛顿邮报》日前连续发文，首度披露１５名在押“东突”嫌犯的真实身份和他们在美军恐怖主义嫌犯俘虏营里的真实状况。文章还透露，关塔那摩拘禁营的美军官员悄然将５名在押的“东突”嫌犯转至“轻松监狱”，以便在接到愿意接收他们的“第三国”后能迅速释放。<BR>　　<BR>　　美国在遣返关塔那摩海军基地的被羁押者时，一向是将“中等威胁和低威胁的囚犯一律送回原籍国，由原籍国依照司法程序进行处理。”但美国对中国籍被羁押者采取了不同态度，拒绝将“东突”分子遣返回中国。<BR>　　<BR>　　英国《金融报》曾援引一名美国高级官员的话称，美国非常希望欧洲国家能够接收这些“东突”分子。不过，令美国大失所望的是，意大利、法国、葡萄牙、奥地利和土耳其等国家无一例外地坚拒接收“东突”分子。联合国难民署也一口回绝了美国</FONT>的要求。";
        System.out.println(content.substring(content.indexOf("ISHANGNET")+15,content.length()));
    }


    @Test
    public void test2(){
        // 二元一组
        int a = 3 , b= 2 ,r = 280;
        // 二元二组
        int c = 6 , d= 3 ,r2 = 498;

        // 最小公倍数
        int lcm = get_lcm(a, c);
        System.out.println(lcm);

        // 计数一元组
        int a1=a * (lcm/a),b1= b * (lcm/a),r_= r * (lcm/a);
        // 计数一元组
        int c1=a * (lcm/c),d1= d * (lcm/c),r2_= r2 * (lcm/c);

        // 第二元结果
        int lanqiu_price = (r2_-r_)/(d1-b1);
        System.out.println(lanqiu_price);

    }

    public static int get(int a, int b) {
        int max, min;
        max = (a > b) ? a : b;
        min = (a < b) ? a : b;

        if (max % min != 0) {
            return get(min, max % min);
        } else{
            return min;
        }
    }

    public static int get_lcm(int a, int b) {
        return a * b / get(a, b);
    }
}
