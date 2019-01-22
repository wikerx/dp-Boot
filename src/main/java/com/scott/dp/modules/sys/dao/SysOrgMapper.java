package com.scott.dp.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import com.scott.dp.modules.sys.entity.SysOrgEntity;

import java.util.List;

/**
 * 组织架构
 *
 * @author Mr.薛
 * @email *****@163.com
 * @url
 * @date 2017年8月17日 上午11:26:05
 */
@Mapper
public interface SysOrgMapper extends BaseMapper<SysOrgEntity> {

	/**
	 * 统计子机构数量
	 * @param parentId
	 * @return
	 */
	int countOrgChildren(Long parentId);

	/**
	 * 查询子机构集合
	 * @param parentId
	 * @return
	 */
	List<Long> listOrgChildren(Long parentId);
	
}
