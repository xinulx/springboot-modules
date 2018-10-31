package com.springboot;

import com.springboot.common.util.DateUtil;
import org.junit.Test;

public class CommonTest {

    @Test
    public  void test1(){
        String str ="/mongo1/63ad5dcebdc5486d9a1a0fad3288c6e3.xls";
        System.out.println(str.split("\\.")[0].split("/")[2]);
        System.out.println(DateUtil.getStrMonth());
        System.out.println(Integer.parseInt("2018.3".replace(".","")));
    }
}
