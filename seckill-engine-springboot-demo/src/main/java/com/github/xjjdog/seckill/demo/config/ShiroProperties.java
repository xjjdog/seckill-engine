package com.github.xjjdog.seckill.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class ShiroProperties {

    @Value("${shiro.session.globalSessionTimeout}")
    private long globalSessionTimeout;
    @Value("${shiro.session.validation.interval}")
    private long sessionValidationInterval;
    @Value("${shiro.active.session.cacheName}")
    private String activeSessionCacheName;
    @Value("${shiro.uid.cookie.name}")
    private String cookieName;
    @Value("${shiro.uid.cookie.domain}")
    private String cookieDomain;
    @Value("${shiro.uid.cookie.path}")
    private String cookiePath;
    @Value("${shiro.uid.cookie.httpOnly}")
    private boolean cookieHttpOnly;
    @Value("${shiro.uid.cookie.maxAge}")
    private int cookieMaxAge;
    @Value("${shiro.uid.rememeberMe.cookie.name}")
    private String rememeberMeCookieName;
    @Value("${shiro.uid.rememeberMe.cookie.maxAge}")
    private long rememeberMeCookieMaxAge;
    @Value("${shiro.uid.rememeberMe.cookie.base64.cipherKey}")
    private String rememeberMeCookieBase64CipherKey;
    @Value("${shiro.login.url}")
    private String loginUrl;
    @Value("${shiro.logout.url}")
    private String logoutUrl;
    @Value("${shiro.user.notfound.url}")
    private String userNotFoundUrl;
    @Value("${shiro.user.blocked.url}")
    private String userBlockedUrl;
    @Value("${shiro.user.unknown.error.url}")
    private String userUnknownErrorUrl;
    @Value("${shiro.unauthorized.url}")
    private String unauthorizedUrl;
    @Value("${shiro.default.success.url}")
    private String defaultSuccessUrl;
    @Value("${shiro.default.admin.success.url}")
    private String defaultAdminSuccessUrl;
    @Value("${shiro.jcaptcha.enable}")
    private boolean enableJcaptcha;
    @Value("${shiro.jcaptcha.error.url}")
    private String jcaptchaErrorUrl;

}
