package com.sequarius.titan.sample.core.componet;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */

import com.sequarius.titan.sample.common.domain.Response;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class DefaultErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Resource
    private ErrorAttributes errorAttributes;

    @RequestMapping(PATH)
    @ResponseBody
    public Response<Map<String, Object>> error(WebRequest request) {
        return Response.fail(errorAttributes.getErrorAttributes(request, false));
    }


    @Override
    public String getErrorPath() {
        return PATH;
    }

}
