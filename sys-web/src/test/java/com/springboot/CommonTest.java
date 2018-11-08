package com.springboot;

import com.springboot.common.util.DateUtil;
import com.springboot.function.DateFormat;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        System.out.println();
    }
}
