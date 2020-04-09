package com.sequarius.titan.sample.core.auth;

import com.sequarius.sample.system.api.service.SystemService;
import com.sequarius.titan.sample.common.message.CommonMessage;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
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
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public Realm realm(SystemService systemService, CommonMessage commonMessage) {
        return new DefaultRealm(systemService, commonMessage);
    }

    @Bean(name = "securityManager")
    public SessionsSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
//        manager.setCacheManager(cacheManager());
//        manager.setSessionManager(defaultWebSessionManager());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         CommonMessage commonMessage) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("rest", new RestApiFilter(commonMessage));
        filters.put("anon", new AnonymousFilter());
        bean.setFilters(filters);

        Map<String,String> chainDefinition = new LinkedHashMap<>();

        chainDefinition.put("/**/login", "anon");

        // all other paths require a logged in user
        chainDefinition.put("/**", "rest");


//        Map<String, String> chains = new HashMap<>();
//        chains.put("/manage/sign_in", "anon");
//        chains.put("/manage/sign_out", "logout");
//        chains.put("/base/**", "anon");
//        chains.put("/css/**", "anon");
//        chains.put("/js/**", "anon");
//        chains.put("/manage/**", "authc,perms");
//        bean.setFilterChainDefinitionMap(chains);
        bean.setFilterChainDefinitionMap(chainDefinition);
        return bean;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }


//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//
//        chainDefinition.addPathDefinition("/login", "anon");
//
//        // all other paths require a logged in user
//        chainDefinition.addPathDefinition("/**", "rest");
//
//        return chainDefinition;
//    }
}
