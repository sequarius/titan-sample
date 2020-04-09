package com.sequarius.titan.sample.system.controller;

import com.sequarius.sample.system.api.domain.UserBO;
import com.sequarius.sample.system.api.service.SystemService;
import com.sequarius.titan.sample.common.CurrentUser;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.common.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * project erp-bundle
 *
 * @author Sequarius *
 * @since 19/03/2020
 */
@RestController
@RequestMapping("/debug")
@Api(tags = "调试API",description = "调试API,仅在非生产环境加载")
@ConditionalOnProperty(name = "ablesz.erp.env",havingValue = "dev")
public class DebugController {
    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SystemService systemService;

    @Resource
    private Realm realm;

    @GetMapping("/login")
    @ApiOperation("暂用的调试登录接口")
    public Response<CurrentUser> debugLogin(String username) {
        SecurityUtils.getSubject().login(new UsernamePasswordToken("xdsjsd3", "xdsjsdjsd"));
        UserBO user = systemService.findUser(username);
        if (user == null) {
            throw new UnknownAccountException(String.format(commonMessage.getLoginUserNotFound(), username));
        }

        CurrentUser currentUser = new CurrentUser();
        BeanUtils.copyProperties(user, currentUser);

        //TODO 先使用测试头像
        currentUser.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        SecurityUtils.getSubject().runAs(new SimplePrincipalCollection(currentUser, realm.getName()));
        return Response.success(CurrentUser.getCurrentUser());
    }

}
