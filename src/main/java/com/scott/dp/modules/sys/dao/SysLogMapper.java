package com.scott.dp.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.scott.dp.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 * @author Mr.薛
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

	/**
	 * 批量删除
	 * @return
	 */
	int batchRemoveAll();
	
}
