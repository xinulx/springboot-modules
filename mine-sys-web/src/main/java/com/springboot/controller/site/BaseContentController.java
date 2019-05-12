package com.springboot.controller.site;


import com.springboot.entity.vo.ContentPageVO;
import com.springboot.service.hibernate.IBaseContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "content")
public class BaseContentController {

    private Logger logger = LoggerFactory.getLogger("logs/content.out");

    @Autowired
    private IBaseContentService baseContentService;

    @RequestMapping("index")
    public String index(){
        logger.debug("正在访问普通页面");
        return "site/site/content_mgr";
    }

    @RequestMapping("ordinaryPage")
    public String ordinaryPage(){
        logger.debug("正在访问普通页面");
        return "site/site/ordinaryPage";
    }

    @RequestMapping("articleNews")
    public String articleNews(){
        logger.debug("正在访问文字新闻");
        return "site/site/articleNews";
    }

    @RequestMapping("globalSearch")
    public String globalSearch(){
        logger.debug("正在访问全文");
        return "site/site/global_search";
    }

    @RequestMapping("getPage")
    @ResponseBody
    public Object getPage(ContentPageVO pageVO){
        return baseContentService.getPage(pageVO);
    }
}
