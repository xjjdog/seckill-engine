package com.github.xjjdog.seckill.demo.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xjjdog.seckill.demo.security.entity.SysUserDO;
import com.github.xjjdog.seckill.demo.security.exception.UserBlockedException;
import com.github.xjjdog.seckill.demo.security.exception.UserNotExistsException;
import com.github.xjjdog.seckill.demo.security.exception.UserPasswordNotMatchException;
import com.github.xjjdog.seckill.demo.security.mapper.SysUserMapper;
import com.github.xjjdog.seckill.demo.security.model.SysUser;
import com.github.xjjdog.seckill.demo.security.service.SysUserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserServiceI {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser sysLogin(String username, String password) throws Exception {
        if ((StringUtils.isEmpty(username)) || (username.length() < 4) || (username.length() > 60)) {
            throw new UserNotExistsException();
        }
        if ((StringUtils.isEmpty(password)) || (password.length() < 4) || (password.length() > 20)) {
            throw new UserPasswordNotMatchException();
        }
        SysUser user = null;

        user = getByAccount(username);
        if ((user == null) || (user.getStatus().equals(SysUser.STATUS_DELETE))) {
            throw new UserNotExistsException();
        }
        if (user.getStatus().equals(SysUser.STATUS_FREEZE)) {
            throw new UserBlockedException();
        }
        String input = BCrypt.hashpw(password, user.getSalt());
        if (!StringUtils.equals(input, user.getPasswd())) {
            throw new UserPasswordNotMatchException();
        }
        return user;
    }

    @Override
    public SysUser getByAccount(String account) {
        return userMapper.getByAccount(account);
    }

//    @Override
//    public List<SysUserVO> getUsers(SysUserSearch userSearch) {
//
//        return userMapper.getUsers(userSearch);
//    }

//    @Override
//    public List<SysUserVO> getUserByEmail(String email) {
//
//        return userMapper.getByEmail(email);
//    }

    @Override
    public List<String> getUserEmail() {

        return userMapper.getUserEmail();
    }

//    @Override
//    public List<SysUserVO> getAllUser() {
//
//        return userMapper.getAllUser();
//    }


}
