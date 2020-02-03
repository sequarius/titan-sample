package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.Response;
import com.sequarius.titan.sample.system.domain.LoginRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public Response<String> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(requestDTO.getUsername(), requestDTO.getPassword()));
        } catch (UnknownAccountException e) {
            return Response.fail(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            log.error(e.getMessage(), e);
            return Response.fail("密码错误，请重新输入！");
        } catch (ExcessiveAttemptsException e) {
            return Response.fail("密码输入连续错误5次以上，帐号已被安全锁定10分钟！");
        } catch (AuthenticationException e) {
            log.warn("credential fail user{}", requestDTO);
            return Response.fail("登录失败，请确认用户名密码是否正确！");
        }
        return Response.success("ok");
    }
}
