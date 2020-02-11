package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.Response;
import com.sequarius.titan.sample.system.domain.LoginRequestDTO;
import com.sequarius.titan.sample.system.message.SystemMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@RestController
@Slf4j
public class CommonController {

    @Resource
    private SystemMessage systemMessage;

    @GetMapping("/login")
    public Response<String> debugLogin(LoginRequestDTO requestDTO){
        return login(requestDTO);
    }


    @PostMapping("/login")
    public Response<String> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(requestDTO.getUsername(), requestDTO.getPassword()));
        } catch (UnknownAccountException e) {
            return Response.fail(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            log.error(e.getMessage(), e);
            return Response.fail(systemMessage.getLoginPasswordError());
        } catch (ExcessiveAttemptsException e) {
            return Response.fail(systemMessage.getLoginAccountLock());
        } catch (AuthenticationException e) {
            log.warn("credential fail user{}", requestDTO);
            return Response.fail(systemMessage.getLoginFailed());
        }
        return Response.success(systemMessage.getLoginSuccess());
    }
}
