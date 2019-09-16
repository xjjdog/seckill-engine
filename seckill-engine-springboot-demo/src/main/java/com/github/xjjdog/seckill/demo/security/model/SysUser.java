package com.github.xjjdog.seckill.demo.security.model;

import lombok.Data;

/**
 * 用户
 */
@Data
public class SysUser  {

	public static final String STATUS_ACTIVE = "1";

	public static final String STATUS_FREEZE = "2";

	public static final String STATUS_DELETE = "3";

	private Long id;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 密码
	 */
	private String passwd;

	/**
	 * md5密码盐
	 */
	private String salt;

	/**
	 * 名字
	 */
	private String name;


	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 角色id
	 */
	private String roleid;



	/**
	 * 状态(1：启用  2：冻结  3：删除）
	 */
	private Integer status;


}
