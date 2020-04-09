package com.sequarius.titan.sample.core.componet;

import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 31/01/2020
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    private AtomicLong errorReportNum;

    @Resource
    private CommonMessage commonMessage;

    @PostConstruct
    public void init() {
        errorReportNum = new AtomicLong(10000);
    }

    /**
     * 全局去除输入前后空格
     * @param binder
     */
    @InitBinder
    public void initBinder ( WebDataBinder binder )
    {
        StringTrimmerEditor trimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, trimmer);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<String> uncatchedException(Exception e) {
        Long errorNumber = errorReportNum.incrementAndGet();
        log.error("exception happen：code = " + errorNumber + " , message = " + e.getMessage(), e);
        return Response.fail(String.format(commonMessage.getServiceError(), errorNumber));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Response<String> methodNotSupportException(Exception e) {
        log.warn(e.getMessage(),e);
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<String> validaException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return Response.fail(String.join(" , ", errorMessages));
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Response<String> validaException(BindException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return Response.fail(String.join(" , ", errorMessages));
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Response<String> forbiddenException(UnauthorizedException e) {
        return Response.fail(e.getMessage());
    }
}
