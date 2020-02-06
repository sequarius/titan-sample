package com.sequarius.titan.sample.core.auth;

import com.sequarius.sample.system.api.domain.UserBO;
import com.sequarius.sample.system.api.service.SystemService;
import com.sequarius.titan.sample.common.CurrentUser;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.common.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.util.TreeSet;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@Slf4j
public class DefaultRealm extends AuthorizingRealm {
    private SystemService systemService;

    private CommonMessage commonMessage;

    public DefaultRealm(SystemService systemService, CommonMessage commonMessage) {
        this.systemService = systemService;
        this.commonMessage = commonMessage;
        this.setCredentialsMatcher(new PasswordMatcher());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        CurrentUser currentUser = (CurrentUser) super.getAvailablePrincipal(principals);
        String username = currentUser.getUsername();

        UserBO user = systemService.findUser(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.setRoles(user.getRoles());
        info.setStringPermissions(new TreeSet<String>(){{
            add("*:*:*");
        }});

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String userName = upt.getUsername();
        if (StringUtils.isEmpty(userName)) {
            throw new UnknownAccountException(commonMessage.getLoginEmptyUsernameError());
        }


        UserBO user = systemService.findUser(userName);
        if (user == null) {
            throw new UnknownAccountException(String.format(commonMessage.getLoginUserNotFound(), userName));
        }

        CurrentUser currentUser = new CurrentUser();
        BeanUtils.copyProperties(user, currentUser);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(currentUser, user.getPassword(), getName());
        return info;
    }
}
