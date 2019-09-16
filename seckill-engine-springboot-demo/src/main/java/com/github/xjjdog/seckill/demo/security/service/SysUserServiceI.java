package com.github.xjjdog.seckill.demo.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.xjjdog.seckill.demo.security.entity.SysUserDO;
import com.github.xjjdog.seckill.demo.security.model.SysUser;

import java.util.List;

public interface SysUserServiceI extends IService<SysUserDO> {

    SysUser sysLogin(String username, String password) throws Exception;

    SysUser getByAccount(String account);

//    List<SysUserVO> getUsers(SysUserSearch userSearch);
//
//    List<SysUserVO> getUserByEmail(String email);

    List<String> getUserEmail();

//    List<SysUserVO> getAllUser();
}
