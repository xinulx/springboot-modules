package com.springboot.common.business;

import com.springboot.common.util.AjaxRequestUtil;
import com.springboot.entity.vo.InternalAccount;
import com.springboot.entity.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component("myFormAuthentication")
public class CommonAuthentication extends FormAuthenticationFilter {

    /**
     * 跳转到登录页面
     *
     * @see org.apache.shiro.web.filter.AccessControlFilter#saveRequestAndRedirectToLogin(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse)
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        if (AjaxRequestUtil.isAjax(request)) {// 返回ajax
            String description = "登录超时！请<a href='/login/loginIn?etc=" + new Date().getTime() + "'>重新登陆</a>";
            // 请求类型
            String dataType = StringUtils.lowerCase(WebUtils.getCleanParam(request, InternalAccount.USER_DATATYPE));
            if ("html".equals(dataType)) {// 请求的是html
                StringBuffer script = new StringBuffer();
                script.append("<script>layer.confirm('登陆超时，是否重新登陆？', {icon: 3,btn: ['确定', '取消']}, function () {location.href='/login/loginIn?etc='+new Date().getTime();});</script>");
                AjaxRequestUtil.printString(response, script.toString());
            } else {
                ResultVO vo = new ResultVO();
                vo.setStatus(ResultVO.Status.Timeout.getValue());
                vo.setDesc(description);
                AjaxRequestUtil.printAjax(response, vo);
            }
        } else {
            // 判断当前请求地址是否是登录地址，不是则返回404
            if (!isLoginRequest(request, response)) { // 非登录请求
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            super.saveRequest(request);
            super.redirectToLogin(request, response);
        }
    }
}
