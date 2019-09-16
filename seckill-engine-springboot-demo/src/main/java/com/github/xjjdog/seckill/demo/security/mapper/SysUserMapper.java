package com.github.xjjdog.seckill.demo.security.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.xjjdog.seckill.demo.security.entity.SysUserDO;
import com.github.xjjdog.seckill.demo.security.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    SysUser getByAccount(String account);

//    List<SysUserVO> getUsers(@Param("userSearch") SysUserSearch userSearch);
//
//    List<SysUserVO> getByEmail(@Param("email") String email);

    List<String> getUserEmail();

//    List<SysUserVO> getAllUser();

}
