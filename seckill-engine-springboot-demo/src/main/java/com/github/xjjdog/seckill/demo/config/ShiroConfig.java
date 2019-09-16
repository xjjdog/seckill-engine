package com.github.xjjdog.seckill.demo.config;


import com.github.xjjdog.seckill.demo.security.common.SecurityConstant;
import com.github.xjjdog.seckill.demo.security.shiro.cache.spring.SpringCacheManagerWrapper;
import com.github.xjjdog.seckill.demo.security.shiro.realm.SecKillShiroRealm;
import com.github.xjjdog.seckill.demo.security.shiro.session.SecKillSessionManager;
import com.github.xjjdog.seckill.demo.security.shiro.web.filter.MyFormAuthenticationFilter;
import com.github.xjjdog.seckill.demo.security.shiro.web.filter.SysUserFilter;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@AutoConfigureBefore({ShiroAutoConfiguration.class, ShiroAnnotationProcessorAutoConfiguration.class})
@EnableConfigurationProperties({ShiroProperties.class})
@ConditionalOnProperty(prefix = "shiro", name = {"enabled"})
public class ShiroConfig {

    @Autowired
    private SpringCacheManagerWrapper springCacheManager;
    @Autowired
    private ShiroProperties properties;

    private SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(this.properties.getCookieName());
        simpleCookie.setDomain(this.properties.getCookieDomain());
        simpleCookie.setPath(this.properties.getCookiePath());
        simpleCookie.setHttpOnly(this.properties.isCookieHttpOnly());
        simpleCookie.setMaxAge(this.properties.getCookieMaxAge());
        return simpleCookie;
    }

    private SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(this.properties.getRememeberMeCookieName());
        simpleCookie.setDomain(this.properties.getCookieDomain());
        simpleCookie.setPath(this.properties.getCookiePath());
        simpleCookie.setHttpOnly(this.properties.isCookieHttpOnly());
        simpleCookie.setMaxAge(this.properties.getCookieMaxAge());
        return simpleCookie;
    }

    private EnterpriseCacheSessionDAO onlineSessionDAO() {
        EnterpriseCacheSessionDAO cacheSessionDAO = new EnterpriseCacheSessionDAO();
        cacheSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        cacheSessionDAO.setActiveSessionsCacheName(this.properties.getActiveSessionCacheName());
        return cacheSessionDAO;
    }

    private SimpleSessionFactory onlineSessionFactory() {
        return new SimpleSessionFactory();
    }

    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode(this.properties.getRememeberMeCookieBase64CipherKey()));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean
    public SecKillShiroRealm shiroRealm() {
        SecKillShiroRealm shiroRealm = new SecKillShiroRealm();
        shiroRealm.setAuthenticationCachingEnabled(false);
        shiroRealm.setAuthorizationCachingEnabled(false);
        return shiroRealm;
    }

    private ExecutorServiceSessionValidationScheduler sessionValidationScheduler(SecKillSessionManager sessionManager) {
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        sessionValidationScheduler.setInterval(this.properties.getSessionValidationInterval());
        sessionValidationScheduler.setSessionManager(sessionManager);
        return sessionValidationScheduler;
    }

    @Bean
    public SecKillSessionManager sessionManager() {
        SecKillSessionManager sessionManager = new SecKillSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setCacheManager(this.springCacheManager);
        sessionManager.setGlobalSessionTimeout(this.properties.getGlobalSessionTimeout());
        sessionManager.setSessionFactory(onlineSessionFactory());
        sessionManager.setSessionDAO(onlineSessionDAO());
        sessionManager.setDeleteInvalidSessions(true);

        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler(sessionManager));
        sessionManager.setSessionValidationInterval(120000L);

        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());

        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(SecKillSessionManager sessionManager, SecKillShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(this.springCacheManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(shiroRealm);
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    private MyFormAuthenticationFilter formAuthenticationFilter() {
        MyFormAuthenticationFilter formAuthenticationFilter = new MyFormAuthenticationFilter();
        formAuthenticationFilter.setSuccessUrl(this.properties.getDefaultSuccessUrl());
        formAuthenticationFilter.setAdminSuccessUrl(this.properties.getDefaultAdminSuccessUrl());
        formAuthenticationFilter.setLoginUrl(this.properties.getLoginUrl());
        formAuthenticationFilter.setUsernameParam("username");
        formAuthenticationFilter.setPasswordParam("passwd");
        formAuthenticationFilter.setRememberMeParam("rmme");
        return formAuthenticationFilter;
    }

    private LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl(this.properties.getLogoutUrl());
        return logoutFilter;
    }

    @Bean
    public SysUserFilter sysUserFilter() {
        SysUserFilter sysUserFilter = new SysUserFilter();
        sysUserFilter.setLoginUrl(this.properties.getLoginUrl());
        sysUserFilter.setUserBlockedUrl(this.properties.getUserBlockedUrl());
        sysUserFilter.setUserNotfoundUrl(this.properties.getUserNotFoundUrl());
        sysUserFilter.setUserUnknownErrorUrl(this.properties.getUserUnknownErrorUrl());
        return sysUserFilter;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));

        filterRegistration.addInitParameter("targetFilterLifecycle", "true");

        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, SysUserFilter sysUserFilter) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        factoryBean.setLoginUrl(this.properties.getLoginUrl());
        factoryBean.setSuccessUrl(this.properties.getDefaultSuccessUrl());
        factoryBean.setUnauthorizedUrl(this.properties.getUnauthorizedUrl());

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", formAuthenticationFilter());
        filters.put("logout", logoutFilter());
        filters.put("sysUser", sysUserFilter);

        factoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/static/index.html", "user,sysUser");
        filterChainDefinitionMap.put("/static/edit.html", "user,sysUser");
        filterChainDefinitionMap.put("/static/log.html", "user,sysUser");
        filterChainDefinitionMap.put("/static/count.html", "user,sysUser");

        filterChainDefinitionMap.put("/ops/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/styles/**", "anon");
        filterChainDefinitionMap.put("/jslib/**", "anon");
        filterChainDefinitionMap.put("/tools/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/error", "anon");

        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login", "authc");

        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");


        filterChainDefinitionMap.put("/monitoring/**", "user,roles["+ SecurityConstant.ROLE_ADMIN +"]");
        filterChainDefinitionMap.put("/druid/**", "user,roles["+ SecurityConstant.ROLE_ADMIN +"]");

        filterChainDefinitionMap.put("/center", "user,roles["+ SecurityConstant.ROLE_ADMIN +"]");


        filterChainDefinitionMap.put("/**", "user,sysUser");
//        filterChainDefinitionMap.put("/**", "anon");

        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

}
