package com.github.xjjdog.seckill.demo.security.shiro.web.filter;

import com.github.xjjdog.seckill.demo.security.common.Constant;
import com.github.xjjdog.seckill.demo.security.model.SysUser;
import com.github.xjjdog.seckill.demo.security.service.SysUserServiceI;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Getter
@Setter
public class SysUserFilter extends AccessControlFilter {

    @Autowired
    private SysUserServiceI userService;
    private String userNotfoundUrl;
    private String userBlockedUrl;
    private String userUnknownErrorUrl;


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        if ((subject == null) || (StringUtils.isBlank((String) subject.getPrincipal()))) {
            return true;
        }
        String loginname = (String) subject.getPrincipal();
        SysUser user = this.userService.getByAccount(loginname);
        request.setAttribute(Constant.CURRENT_USER, user);
        ((HttpServletRequest) request).getSession().setAttribute("username", loginname);

        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        SysUser user = (SysUser) request.getAttribute(Constant.CURRENT_USER);
        if (user == null) {
            return true;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        SysUser user = (SysUser) request.getAttribute(Constant.CURRENT_USER);
        String url = null;
        WebUtils.issueRedirect(request, response, url);
    }


}
