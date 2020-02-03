package com.sequarius.titan.sample.core.auth;

import com.sequarius.titan.sample.common.Response;
import com.sequarius.titan.sample.util.JacksonUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
public class RestApiFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        Subject subject = getSubject(request, response);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);

        if (subject == null || !subject.isAuthenticated()) {
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().println(JacksonUtil.getObjectMapper()
                    .writeValueAsString(Response.fail("登陆已过期，请重新登陆！")));
            httpResponse.flushBuffer();
        }

        return false;
    }

}