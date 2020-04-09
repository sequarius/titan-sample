package com.sequarius.titan.sample.system.controller;


import com.sequarius.titan.sample.common.CurrentUser;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.system.domain.ChangePasswordRequestDTO;
import com.sequarius.titan.sample.system.domain.LoginRequestDTO;
import com.sequarius.titan.sample.system.message.SystemMessage;
import com.sequarius.titan.sample.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.http.ResponseEntity;
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
@Api(tags = "通用公共接口", description = "公共接口")
public class CommonController {

    @Resource
    private SystemMessage systemMessage;

    @Resource
    private SysUserService userService;


    @PostMapping("/logout")
    @ApiOperation("登出")
    public ResponseEntity<Response<String>> logout() {
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok(Response.success(systemMessage.getLogoutSuccess()));
    }

    @PostMapping("/password")
    @ApiOperation("修改密码")
    public Response<String> changePassword(@Valid @RequestBody ChangePasswordRequestDTO passwordRequestDTO) {
        int result = 0;
        if (passwordRequestDTO.getPassword().length() >= 6) {
            result = userService.changePassWord(passwordRequestDTO.getOriginPassword(), passwordRequestDTO.getPassword());
        }
        if (result == 1) {
            return Response.success(systemMessage.getChangepassword().get(1));
        }
        return Response.fail(systemMessage.getChangepassword().get(result));
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Response<CurrentUser> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        try {
            // 先调用登出刷新session id防止之前session被记录攻击
            SecurityUtils.getSubject().logout();
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
            return Response.fail(String.format("登录失败：%s",e.getMessage()));
        }
        CurrentUser currentUser = CurrentUser.getCurrentUser();
        return Response.success(systemMessage.getLoginSuccess(), currentUser);
    }
}
