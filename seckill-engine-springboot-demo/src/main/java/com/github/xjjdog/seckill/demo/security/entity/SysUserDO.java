package com.github.xjjdog.seckill.demo.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xjjdog.seckill.demo.common.entity.IDAutoEntity;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 */
@Data
@TableName(" ")
public class SysUserDO extends IDAutoEntity<SysUserDO> {


	/**
	 * 账号
	 */
	@TableField("account")
	private String account;

	/**
	 * 密码
	 */
	@TableField("passwd")
	private String passwd;

	/**
	 * md5密码盐
	 */
	@TableField("salt")
	private String salt;

	/**
	 * 名字
	 */
	@TableField("name")
	private String name;

	/**
	 * 电子邮件
	 */
	@TableField("email")
	private String email;

	/**
	 * 电话
	 */
	@TableField("phone")
	private String phone;

	/**
	 * 角色id
	 */
	@TableField("roleid")
	private String roleid;


	/**
	 * 状态(1：启用  2：冻结  3：删除）
	 */
	@TableField("status")
	private Integer status;

	@TableField("createDate")
	private Date createDate;

	@TableField("modifyDate")
	private Date modifyDate;


	public void clearPasswd(){
		this.passwd = null;
		this.salt = null;
	}

	public void editUser(SysUserDO user){
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
	}


}
