package com.scott.dp.modules.sys.dao;

import java.util.List;

import com.scott.dp.modules.sys.entity.SysRoleOrgEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与机构的关系
 * @author Mr.薛
 */
@Mapper
public interface SysRoleOrgMapper extends BaseMapper<SysRoleOrgEntity> {

	/**
	 * 查询角色所有机构id集合
	 * @param roleId
	 * @return
	 */
	List<Long> listOrgId(Long roleId);

	/**
	 * 根据机构id删除
	 * @param id
	 * @return
	 */
	int batchRemoveByOrgId(Long[] id);

	/**
	 * 根据角色id删除
	 * @param id
	 * @return
	 */
	int batchRemoveByRoleId(Long[] id);
	
}
