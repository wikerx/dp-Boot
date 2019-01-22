package com.scott.dp.modules.sys.service;

import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.modules.sys.entity.SysUserEntity;
import com.scott.dp.modules.sys.entity.SysUserTokenEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统用户
 * @author Mr.薛
 */
public interface SysUserService {

	/**
	 * 分页查询用户列表
	 * @param params
	 * @return
	 */
	Page<SysUserEntity> listUser(Map<String, Object> params);
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
	 * 新增用户
	 * @param user
	 * @return
	 */
	R saveUser(SysUserEntity user);

	/**
	 * 根据id查询用户
	 * @param userId
	 * @return
	 */
	R getUserById(Long userId);

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	R updateUser(SysUserEntity user);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	R batchRemove(Long[] id);

	/**
	 * 查询用户权限集合
	 * @param userId
	 * @return
	 */
	Set<String> listUserPerms(Long userId);

	/**
	 * 查询用户角色集合
	 * @param userId
	 * @return
	 */
	Set<String> listUserRoles(Long userId);

	/**
	 * 用户修改密码
	 * @param user
	 * @return
	 */
	R updatePswdByUser(SysUserEntity user);

	/**
	 * 启用用户
	 * @param id
	 * @return
	 */
	R updateUserEnable(Long[] id);

	/**
	 * 禁用用户
	 * @param id
	 * @return
	 */
	R updateUserDisable(Long[] id);

	/**
	 * 重置用户密码
	 * @param user
	 * @return
	 */
	R updatePswd(SysUserEntity user);

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	SysUserEntity getByUserName(String username);

	/**
	 * 用户所有机构id
	 * @param userId
	 * @return
	 */
	List<Long> listAllOrgId(Long userId);

	/**
	 * 保存用户token
	 * @param userId
	 * @return
	 */
	int saveOrUpdateToken(Long userId, String token);

	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	SysUserTokenEntity getUserTokenByToken(String token);

	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	SysUserTokenEntity getUserTokenByUserId(Long userId);

	/**
	 * 根据userId查询：用于token校验
	 * @param userId
	 * @return
	 */
	SysUserEntity getUserByIdForToken(Long userId);

	/**
	 * 根据用户ID查询用户的相关信息
	 * @param userId
	 * @return
	 */
	SysUserEntity getByUserIdOne(Long userId);
}
