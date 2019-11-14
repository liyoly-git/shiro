package com.springdemo.shiro.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ly
 * @date 2019/11/14
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置shiro核心 安全管理器 SecurityManager
     * SecurityManager安全管理器:所有与安全有关的操作都会与SecurityManage交互;且它管理着所有Subject;负责与后边介绍的其他组件进行交互.
     * @return
     */
    private SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义的realm交给SecurityManager管理
        securityManager.setRealm(new CustomRealm());
        return securityManager;
    }

    /**
     * 配置shiro的web过滤器,拦截浏览器请求并交给SecurityManager处理
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean webFilter(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //配置拦截链 使用LinkedHashMap，因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
        //Map<k,v>k指的是拦截的url v指的是该url是否拦截
        Map<String,String> filterChainMap = new LinkedHashMap<>();

        //配置退出过滤器logout,由shiro实现
        filterChainMap.put("/logout","logout");
        //authc:所有url都必须认证通过才可以访问；anon:所有url都可以匿名访问,先配置anon再配置authc.
        filterChainMap.put("/login","anon");
        filterChainMap.put("/**","authc");

        //设置默认登录的URL.
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilterFactoryBean;
    }
}
