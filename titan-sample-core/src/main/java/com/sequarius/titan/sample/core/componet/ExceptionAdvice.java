package com.sequarius.titan.sample.core.componet;

import com.sequarius.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        errorReportNum = new AtomicLong(10000);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<String> uncatchedException(Exception e) {
        Long errorNumber = errorReportNum.incrementAndGet();
        log.error("exception happen：code = " + errorNumber + " , message = " + e.getMessage(), e);
        return Response.fail(String.format("服务异常:错误回执号[%d],请提供该回执号给管理员获得更多帮助！", errorNumber));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<String> validaException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return Response.fail(String.join(" , ", errorMessages));
    }
}
