package com.github.xjjdog.seckill.demo.security.shiro.web.filter;

import com.github.xjjdog.seckill.demo.security.common.SecurityConstant;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Setter
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    private String adminSuccessUrl;

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String errMsg = ae.getMessage();
        request.setAttribute(getFailureKeyAttribute(), errMsg);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        if(subject.hasRole(SecurityConstant.ROLE_ADMIN)){
            WebUtils.issueRedirect(request, response, this.adminSuccessUrl);
        }else{
            WebUtils.issueRedirect(request, response, super.getSuccessUrl());
        }
        return false;
    }

}
