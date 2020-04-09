package com.sequarius.titan.sample.core.auth;

import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.common.util.Constant;
import com.sequarius.titan.sample.common.util.JacksonUtil;
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

    private CommonMessage commonMessage;

    public RestApiFilter(CommonMessage commonMessage) {
        this.commonMessage = commonMessage;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        Subject subject = getSubject(request, response);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);

        if (subject == null || !subject.isAuthenticated()) {
            httpResponse.setCharacterEncoding(Constant.DEFAULT_CHARSET);
            httpResponse.setContentType(Constant.CONTENT_TYPE_JSON);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().println(JacksonUtil.getObjectMapper()
                    .writeValueAsString(Response.fail(commonMessage.getRequireToLogin())));
            httpResponse.flushBuffer();
        }

        return false;
    }

}