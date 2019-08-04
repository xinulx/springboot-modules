package com.springboot.common.filter;

import com.alibaba.fastjson.JSON;
import com.springboot.common.busi.BaseRunTimeException;
import com.springboot.common.util.AjaxRequestUtil;
import com.springboot.common.util.AppUtil;
import com.springboot.common.util.DateUtil;
import com.springboot.entity.business.PublishStrategyEO;
import com.springboot.entity.vo.ResultVO;
import com.springboot.service.hibernate.ILimitMappingService;
import com.springboot.service.hibernate.IPublishStrategyService;
import com.springboot.websocket.disruptor.entity.Message;
import com.springboot.websocket.util.MessageSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 这是一个置自定义的filter
 * 添加多视图解析，按优先级查找，直到找到为止
 */
@Configuration
@ImportResource("applicationContext.xml")
@Slf4j
public class WebFilter {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean mappingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new PublishStrategyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "publishStrategyFilter");
        registration.setName("webFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 限时发布策略过滤器
     *
     * @author wangshibao
     * @version V1.0
     * @date 2019年5月3日 下午3:05:39
     */
    public class PublishStrategyFilter implements Filter {

        private IPublishStrategyService publishStrategyService;

        private ILimitMappingService limitMappingService;

        private String filterType = "work";

        @Override
        public void destroy() {

        }

        @Override
        public void doFilter(ServletRequest arg0, ServletResponse arg1,
                             FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) arg0;
            HttpServletResponse response = (HttpServletResponse) arg1;

            initBean(request.getServletContext());

            // 地址在配置中
            String url = request.getRequestURI();
            if (!StringUtils.isEmpty(url) && !url.contains("css") && !url.contains("js")
                    && !url.contains("images") && !url.contains("plugins") && !url.contains("favicon")) {
                log.info("current request url= {}", request.getRequestURI());
                log.info("current request param = {}", JSON.toJSONString(request.getParameterMap()));
            }
            Long count = limitMappingService.getCountByUrl(url, 1);
            if (count > 0 && !checkAllowPublish()) {
                String userJson = filterType.equals("work") ? "请在上班时间发布" : "节假日不允许发布";
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                ResultVO vo = new ResultVO();
                vo.setStatus(ResultVO.Status.Failure.getValue());
                vo.setDesc(userJson);
                AjaxRequestUtil.printAjax(response, vo);
                // 发送消息
                Message loggerMessage = new Message();
                loggerMessage.setStatus(Message.Status.warning.getValue());
                loggerMessage.setType(Message.Type.business.name());
                loggerMessage.setType("限时发布系统提醒");
                loggerMessage.setBody("限时发布系统已开启，您当前的操作受到限制，请在规定时间内操作！");
                final Message message = loggerMessage;
                Executors.newCachedThreadPool().execute(() ->
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new BaseRunTimeException("推送消息发生错误:" + e.getMessage());
                    }
                    MessageSendUtil.sendTopicMessage(message);
                });
            } else {
                chain.doFilter(arg0, response);
            }
        }

        @Override
        public void init(FilterConfig arg0) {
            // 不能在这里初始化bean实例
            log.info("load init param " + arg0.getInitParameterNames());
        }

        public void initBean(ServletContext context) {
            if (publishStrategyService == null) {
                ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
                publishStrategyService = (IPublishStrategyService) ctx.getBean("publishStrategyService");
                limitMappingService = (ILimitMappingService) ctx.getBean("limitMappingService");
            }
        }

        public Boolean checkAllowPublish() {
            // 获取限时发布配置
            PublishStrategyEO config = publishStrategyService.getEOBySiteId(4697351L);

            // 如果已关闭策略则直接返回true
            if (config == null || config.getIsUsed() == 0) {
                return true;
            }

            // 判断检测类型
            Date curDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
            DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
            String week = DateUtil.getWeek(curDate);
            if (week.equals("星期六") || week.equals("星期日")) {
                filterType = "work";
                if (!AppUtil.isEmpty(config.getSaturday()) && !AppUtil.isEmpty(config.getSunday())) {
                    return DateUtil.isEffectiveDate(curDate, config.getWeekStartDate(), config.getWeekEndDate());
                } else if (!AppUtil.isEmpty(config.getSaturday()) && AppUtil.isEmpty(config.getSunday())) {
                    return week.equals("星期六") && DateUtil.isEffectiveDate(curDate, config.getWeekStartDate(), config.getWeekEndDate());
                } else if (AppUtil.isEmpty(config.getSaturday()) && !AppUtil.isEmpty(config.getSunday())) {
                    return week.equals("星期日") && DateUtil.isEffectiveDate(curDate, config.getWeekStartDate(), config.getWeekEndDate());
                }
            } else {
                String curYear = dateFormat.format(curDate).substring(0, 11);
                Date startDate = null;
                try {
                    startDate = dateFormat.parse(curYear + dateFormat2.format(config.getWorkStartDate()) + ":00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date endDate = null;
                try {
                    endDate = dateFormat.parse(curYear + dateFormat2.format(config.getWorkEndDate()) + ":00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 判断节假日
                String holiDaysDates = config.getHoliDaysDates();
                Boolean isHoliday = false;
                if (!AppUtil.isEmpty(holiDaysDates)) {
                    // [{"holidayName":"","startDate":"","endDate":""}...]
                    List<Map> maps = JSON.parseArray(holiDaysDates, Map.class);
                    for (Map map : maps) {
                        String startDateStr = map.get("startDate").toString();
                        String endDateStr = map.get("endDate").toString();
                        if (!AppUtil.isEmpty(startDateStr) && !AppUtil.isEmpty(endDateStr)) {
                            try {
                                isHoliday = DateUtil.isEffectiveDate(curDate, dateFormat3.parse(startDateStr + " 00:00:00"), dateFormat3.parse(endDateStr + " 23:59:59"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (isHoliday) {
                                filterType = "holiday";
                                break;
                            } else {
                                filterType = "work";
                            }
                        }
                    }
                }
                return !isHoliday && DateUtil.isEffectiveDate(curDate, startDate, endDate);
            }
            return false;
        }
    }
}