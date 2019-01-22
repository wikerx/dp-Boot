package com.scott.dp.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.scott.dp.common.entity.Query;
import com.scott.dp.modules.sys.entity.SysUserEntity;

/**
 * 系统用户dao
 * @author Mr.薛
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

	/**
	 * 根据用户名查询
	 * @param username
	 * @return
	 */
	SysUserEntity getByUserName(String username);

	/**
	 * 查询用户所有菜单id
	 * @param userId
	 * @return
	 */
	List<Long> listAllMenuId(Long userId);

	/**
	 * 查询用户所有机构id
	 * @param userId
	 * @return
	 */
	List<Long> listAllOrgId(Long userId);

	/**
	 * 用户修改密码
	 * @param query
	 * @return
	 */
	int updatePswdByUser(Query query);

	/**
	 * 更新用户状态
	 * @param query
	 * @return
	 */
	int updateUserStatus(Query query);

	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	int updatePswd(SysUserEntity user);


	/**
	 * 查询当前部门的所有人员信息
	 * @param deptId
	 * @return
	 */
	List<SysUserEntity> select(Long deptId);

	/**
	 * 查询当前所有人员信息
	 * @return
	 */
	List<SysUserEntity> selectAll();

	/**
	 * 根据用户ID查询用户的相关信息
	 * @param userId
	 * @return
	 */
	SysUserEntity getByUserIdOne(Long userId);

}
