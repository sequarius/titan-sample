package com.sequarius.titan.sample.core.auth;

import com.sequarius.sample.system.api.service.SystemService;
import com.sequarius.titan.sample.common.message.CommonMessage;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 01/02/2020
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm(SystemService systemService, CommonMessage commonMessage) {
        return new DefaultRealm(systemService, commonMessage);
    }

//    @Bean(name = "securityManager")
//    public SecurityManager securityManager(Realm realm) {
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setRealm(realm);
////        manager.setCacheManager(cacheManager());
////        manager.setSessionManager(defaultWebSessionManager());
//        return manager;
//    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition,
                                                         CommonMessage commonMessage) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
//        bean.setLoginUrl("/manage/sign_in");
//        bean.setUnauthorizedUrl("/manage/sign_in");

        Map<String, Filter> filters = new HashMap<>();
        filters.put("rest", new RestApiFilter(commonMessage));
        filters.put("anon", new AnonymousFilter());
        bean.setFilters(filters);

//        Map<String, String> chains = new HashMap<>();
//        chains.put("/manage/sign_in", "anon");
//        chains.put("/manage/sign_out", "logout");
//        chains.put("/base/**", "anon");
//        chains.put("/css/**", "anon");
//        chains.put("/js/**", "anon");
//        chains.put("/manage/**", "authc,perms");
//        bean.setFilterChainDefinitionMap(chains);
        bean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        return bean;
    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/login", "anon");

        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "rest");

        return chainDefinition;
    }
}
